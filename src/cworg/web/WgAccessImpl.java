package cworg.web;

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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
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

import cworg.main.SecureSSLSocketFactory;

@Singleton
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

	// TODO factor out parts
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
//		List<String> asd = new LinkedList<>();
//		asd.add("foo");asd.add("bar");return asd;
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
