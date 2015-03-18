package cworg.web;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContext;

import cworg.data.Tank;

@Stateless
public class WgAccessImpl implements WgAccess {
	@Inject
	private ServletContext ctx;
	private String appId;

	@PostConstruct
	private void init() {
		appId = (String) ctx.getAttribute("app-id");
	}

	@Override
	public String getLoginUrl(String redirectUrl) throws WgApiError,
			WebException {
		String methodUrl = "https://api.worldoftanks.eu/wot/auth/login/";
		Map<String, String> params = new HashMap<>(3);
		params.put("expires_at", "300");
		params.put("nofollow", "1");
		params.put("redirect_uri", redirectUrl);

		String result = null;
		try {
			JsonObject resp =
					(JsonObject) this.getResponseData(methodUrl, params, "GET");
			result = resp.getString("location");
		} catch (ClassCastException | NullPointerException e) {
			throw new WebException("Unexpected response format", e);
		}
		return result;
	}

	@Override
	public ProlongateResponse prolongate(String accessToken, Duration duration)
			throws WebException, WgApiError {
		String methodUrl = "https://api.worldoftanks.eu/wot/auth/prolongate/";
		long seconds = duration.getSeconds();
		Map<String, String> params = new HashMap<>(2);
		params.put("access_token", accessToken);
		params.put("expires_at", Long.toString(seconds));

		ProlongateResponse result = null;
		try {
			JsonObject resp =
					(JsonObject) this
							.getResponseData(methodUrl, params, "POST");
			String newToken = resp.getString("access_token");
			String accountId = resp.get("account_id").toString();
			long expiryTimeStamp = resp.getJsonNumber("expires_at").longValue();
			Instant expiryTime = Instant.ofEpochSecond(expiryTimeStamp);
			result = new ProlongateResponse(newToken, accountId, expiryTime);
		} catch (ClassCastException | NullPointerException e) {
			throw new WebException("Unexpected response format", e);
		}
		return result;
	}

	@Override
	public void logout(String accessToken) throws WebException, WgApiError {
		String methodUrl = "https://api.worldoftanks.eu/wot/auth/logout/";
		Map<String, String> params = new HashMap<>(1);
		params.put("access_token", accessToken);
		this.getResponseData(methodUrl, params, "POST");
	}

	// TODO factor out parts, remove completely?
	@Override
	public List<String> getVehiclesInGarage(String accountId, String accessToken)
			throws WebException, WgApiError {
		String playerMethodUrl = "https://api.worldoftanks.eu/wot/tanks/stats/";
		Map<String, String> playerParams = new HashMap<>(4);
		playerParams.put("account_id", accountId);
		playerParams.put("access_token", accessToken);
		playerParams.put("in_garage", "1");
		playerParams.put("fields", "tank_id");
		String tankInfoMethodUrl =
				"https://api.worldoftanks.eu/wot/encyclopedia/tanks/";
		Map<String, String> tankInfoParams = new HashMap<>(1);
		tankInfoParams.put("fields", "name_i18n,tank_id");

		List<String> result = new LinkedList<>();
		try {
			// tank map first
			Map<String, String> tankNameMap = new HashMap<>();
			JsonObject resp =
					(JsonObject) this.getResponseData(tankInfoMethodUrl,
							tankInfoParams, "GET");
			for (Map.Entry<String, JsonValue> e : resp.entrySet()) {
				JsonObject o = (JsonObject) e.getValue();
				tankNameMap.put(o.get("tank_id").toString(),
						o.getString("name_i18n"));
			}

			// now player tanks
			resp =
					(JsonObject) this.getResponseData(playerMethodUrl,
							playerParams, "GET");
			JsonArray tankList = resp.getJsonArray(accountId);
			for (JsonValue v : tankList) {
				JsonObject o = (JsonObject) v;
				String tankId = o.get("tank_id").toString();
				result.add(tankNameMap.get(tankId));
			}
		} catch (ClassCastException | NullPointerException e) {
			throw new WebException("Unexpected response format", e);
		}
		return result;
		// List<String> asd = new LinkedList<>();
		// asd.add("foo");asd.add("bar");return asd;
	}

	@Override
	public Set<Tank> getAllTankInfo() throws WebException, WgApiError {
		String methodUrl =
				"https://api.worldoftanks.eu/wot/encyclopedia/tanks/";
		Map<String, String> params = new HashMap<>(0);

		Set<Tank> result = new HashSet<>();
		try {
			JsonObject resp =
					(JsonObject) this.getResponseData(methodUrl, params, "GET");
			for (Map.Entry<String, JsonValue> e : resp.entrySet()) {
				JsonObject o = (JsonObject) e.getValue();
				String contourImageUrl = o.getString("contour_image");
				String imageUrl = o.getString("image");
				String smallImageUrl = o.getString("image_small");
				boolean premium = o.getBoolean("is_premium");
				int tier = o.getInt("level");
				String internalName = o.getString("name");
				String name = o.getString("name_i18n");
				String internalNation = o.getString("nation");
				String nation = o.getString("nation_i18n");
				String shortName = o.getString("short_name_i18n");
				String id = o.get("tank_id").toString();
				String internalType = o.getString("type");
				String type = o.getString("type_i18n");
				result.add(new Tank(id, name, shortName, type, nation, tier,
						imageUrl, smallImageUrl, contourImageUrl, premium,
						internalName, internalNation, internalType));
			}
		} catch (ClassCastException | NullPointerException e) {
			throw new WebException("Unexpected response format", e);
		}
		return result;
	}

	@Override
	public GetPlayerResponse getPlayer(String accountId) throws WebException,
			WgApiError {
		String playerMethodUrl =
				"https://api.worldoftanks.eu/wot/account/info/";
		String tankMethodUrl = "https://api.worldoftanks.eu/wot/account/tanks/";
		String playerFields =
				"clan_id," + "created_at," + "last_battle_time," + "logout_at,"
						+ "nickname";
		Map<String, String> playerParams = new HashMap<>(2);
		playerParams.put("account_id", accountId);
		playerParams.put("fields", playerFields);
		Map<String, String> tankParams = new HashMap<>(2);
		tankParams.put("account_id", accountId);
		tankParams.put("fields", "tank_id");

		GetPlayerResponse result = null;
		try {
			JsonObject playerResp =
					(JsonObject) this.getResponseData(playerMethodUrl,
							playerParams, "GET");
			JsonObject tankResp =
					(JsonObject) this.getResponseData(tankMethodUrl,
							tankParams, "GET");
			JsonObject playerJson = playerResp.getJsonObject(accountId);
			JsonArray tankList = tankResp.getJsonArray(accountId);
			Instant creationTime =
					Instant.ofEpochSecond(playerJson
							.getJsonNumber("created_at").longValue());
			Instant lastBattleTime =
					Instant.ofEpochSecond(playerJson.getJsonNumber(
							"last_battle_time").longValue());
			Instant lastLogoutTime =
					Instant.ofEpochSecond(playerJson.getJsonNumber("logout_at")
							.longValue());
			String nick = playerJson.getString("nickname");
			JsonValue clanIdJson = playerJson.get("clan_id");
			// check if clan id is a null json value, otherwise set to the
			// actual clan id
			String clanId = null;
			if (clanIdJson.getValueType() != JsonValue.ValueType.NULL) {
				clanId = clanIdJson.toString();
			}
			Set<String> tankIds = new HashSet<>(tankList.size());
			for (JsonValue v : tankList) {
				JsonObject o = (JsonObject) v;
				tankIds.add(o.get("tank_id").toString());
			}
			result =
					new GetPlayerResponse(creationTime, lastBattleTime,
							lastLogoutTime, nick, clanId, tankIds);
		} catch (ClassCastException | NullPointerException e) {
			throw new WebException("Unexpected response format", e);
		}
		return result;
	}

	@Override
	public GetClanMemberInfoResponse getClanMemberInfo(String accountId)
			throws WebException, WgApiError {
		String methodUrl = "https://api.worldoftanks.eu/wgn/clans/membersinfo/";
		String fields = "joined_at," + "role," + "role_i18n," + "clan.clan_id";
		Map<String, String> params = new HashMap<>(2);
		params.put("account_id", accountId);
		params.put("fields", fields);

		GetClanMemberInfoResponse result = null;
		try {
			JsonObject resp =
					(JsonObject) this.getResponseData(methodUrl, params, "GET");
			JsonObject infoJson = resp.getJsonObject(accountId);
			String internalRole = infoJson.getString("role");
			String role = infoJson.getString("role_i18n");
			Instant joinTime =
					Instant.ofEpochSecond(infoJson.getJsonNumber("joined_at")
							.longValue());
			String clanId =
					infoJson.getJsonObject("clan").get("clan_id").toString();
			result =
					new GetClanMemberInfoResponse(clanId, joinTime, role,
							internalRole);
		} catch (ClassCastException | NullPointerException e) {
			throw new WebException("Unexpected response format", e);
		}
		return result;
	}

	@Override
	public GetClanResponse getClan(String clanId) throws WebException,
			WgApiError {
		String methodUrl = "https://api.worldoftanks.eu/wgn/clans/info/";
		String fields =
				"color," + "created_at," + "creator_id," + "description,"
						+ "is_clan_disbanded," + "leader_id,"
						+ "members_count," + "motto," + "name," + "tag,"
						+ "emblems," + "members.account_id";
		Map<String, String> params = new HashMap<>(2);
		params.put("clan_id", clanId);
		params.put("fields", fields);

		GetClanResponse result = null;
		try {
			JsonObject resp =
					(JsonObject) this.getResponseData(methodUrl, params, "GET");
			JsonObject infoJson = resp.getJsonObject(clanId);
			String commanderId = infoJson.get("leader_id").toString();
			String description = infoJson.getString("description");
			Instant creationTime =
					Instant.ofEpochSecond(infoJson.getJsonNumber("created_at")
							.longValue());
			String creatorId = infoJson.get("creator_id").toString();
			String clanTag = infoJson.getString("tag");
			boolean disbanded = infoJson.getBoolean("is_clan_disbanded");
			String motto = infoJson.getString("motto");
			int memberCount = infoJson.getInt("members_count");
			String name = infoJson.getString("name");
			String colorString = infoJson.getString("color");
			int r = Integer.parseInt(colorString.substring(1, 3), 16);
			int g = Integer.parseInt(colorString.substring(3, 5), 16);
			int b = Integer.parseInt(colorString.substring(5, 7), 16);
			Color color = new Color(r, g, b);
			String globalMapEmblem24Url = null;
			String recruitingStationEmblem32Url = null;
			String recruitingStationEmblem64Url = null;
			String profileEmblem195Url = null;
			String tankEmblem64Url = null;
			String aircraftEmblem256Url = null;
			JsonArray emblemList = infoJson.getJsonArray("emblems");
			for (JsonValue v : emblemList) {
				JsonObject o = (JsonObject) v;
				String url = o.getString("url");
				switch (o.getString("type")) {
				case "24x24":
					globalMapEmblem24Url = url;
					break;
				case "32x32":
					recruitingStationEmblem32Url = url;
					break;
				case "64x64":
					if ("wot".equals(o.getString("game"))) {
						tankEmblem64Url = url;
					} else {
						recruitingStationEmblem64Url = url;
					}
					break;
				case "195x195":
					profileEmblem195Url = url;
					break;
				case "256x256":
					aircraftEmblem256Url = url;
					break;
				}
			}
			JsonArray memberList = infoJson.getJsonArray("member");
			Set<String> memberIds = new HashSet<>(memberList.size());
			for (JsonValue v : memberList) {
				JsonObject o = (JsonObject) v;
				memberIds.add(o.getString("account_id"));
			}

			result =
					new GetClanResponse(color, creationTime, creatorId,
							description, disbanded, commanderId, memberCount,
							motto, name, clanTag, globalMapEmblem24Url,
							recruitingStationEmblem32Url,
							recruitingStationEmblem64Url, profileEmblem195Url,
							tankEmblem64Url, aircraftEmblem256Url, memberIds);
		} catch (ClassCastException | NullPointerException e) {
			throw new WebException("Unexpected response format", e);
		}
		return result;
	}

	private static String makeQueryString(String appId,
			Map<String, String> params) throws WebException {
		Charset charset = StandardCharsets.UTF_8;
		StringBuilder sb = new StringBuilder();
		sb.append("application_id=");
		sb.append(appId);
		for (Map.Entry<String, String> e : params.entrySet()) {
			if (e.getKey().equals("application_id"))
				continue;
			sb.append('&');
			try {
				sb.append(URLEncoder.encode(e.getKey(), charset.name()));
				sb.append('=');
				sb.append(URLEncoder.encode(e.getValue(), charset.name()));
			} catch (UnsupportedEncodingException ex) {
				throw new WebException(
						"Server doesn't support needed encoding", ex);
			}
		}
		return sb.toString();
	}

	private static HttpsURLConnection makeRequest(String urlString,
			String method, Charset charset, String queryString)
			throws WebException {
		URL url = null;
		if ("GET".equalsIgnoreCase(method)) {
			urlString = urlString + "?" + queryString;
		}
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			throw new WebException(urlString + " is not a valid URL", e);
		}
		HttpsURLConnection con = null;
		try {
			con = (HttpsURLConnection) url.openConnection();
		} catch (IOException e) {
			throw new WebException("Error opening connection to WG", e);
		}
		// paranoid tls settings
		con.setSSLSocketFactory(new SecureSSLSocketFactory());
		con.setRequestProperty("Accept-Charset", charset.name());
		if ("POST".equalsIgnoreCase(method)) {
			con.setRequestProperty(
					"Content-Type",
					"application/x-www-form-urlencoded;charset="
							+ charset.name());
			// also sets request method implicitly to POST
			con.setDoOutput(true);
			try (OutputStream os = con.getOutputStream()) {
				os.write(queryString.getBytes(charset));
			} catch (IOException e) {
				throw new WebException("Error writing request parameters", e);
			}
		}
		return con;
	}

	private JsonStructure getResponse(String urlString,
			Map<String, String> params, String method) throws WebException {
		if (!"GET".equalsIgnoreCase(method) && !"POST".equalsIgnoreCase(method)) {
			throw new UnsupportedOperationException(
					"Can only handle GET and POST");
		}
		Charset charset = StandardCharsets.UTF_8;
		String queryString = makeQueryString(appId, params);
		HttpsURLConnection con =
				makeRequest(urlString, method, charset, queryString);

		// read the response
		JsonStructure json = null;
		JsonReader reader = null;
		try {
			InputStream is = con.getInputStream();
			reader = Json.createReader(is);
			try {
				json = reader.read();
			} catch (JsonException | IllegalStateException e) {
				throw new WebException("Error parsing response", e);
			}
		} catch (IOException e) {
			throw new WebException("Error reading response", e);
		} finally {
			reader.close();
		}
		return json;
	}

	/**
	 * App id is added implicitly
	 * @param urlString
	 * @param params
	 * @return
	 */
	private JsonValue getResponseData(String urlString,
			Map<String, String> params, String method) throws WebException,
			WgApiError {
		JsonObject json;
		try {
			json = (JsonObject) getResponse(urlString, params, method);
			String status = json.getString("status");
			if (!"ok".equals(status)) {
				if ("error".equals(status)) {
					JsonObject error = json.getJsonObject("error");
					throw new WgApiError(error.getInt("code"),
							error.getString("field"),
							error.getString("message"),
							error.getString("value"));
				} else {
					throw new WebException(
							"Response contained unexpected format");
				}
			}
			return json.get("data");
		} catch (ClassCastException | NullPointerException e) {
			throw new WebException("Response contained unexpected format", e);
		}
	}

}
