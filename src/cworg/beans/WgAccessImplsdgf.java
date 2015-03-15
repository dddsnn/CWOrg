package cworg.beans;

import java.time.Duration;
import java.time.Instant;

import javax.ejb.Singleton;

import cworg.web.ProlongateResponse;

@Singleton
// (name="sdfg")
public class WgAccessImplsdgf implements WgAccesssdfg {
	// @Inject
	// private ServletContext ctx;
//	private String appId;

//	public WgAccessImpl() {
//		// TODO Auto-generated constructor stub
//	}

//	@PostConstruct
//	private void init() {
		// appId = (String) ctx.getAttribute("app-id");
//		appId = "sdfg";
//	}

	@Override
	public String getLoginUrl(String redirectUrl) {// throws WgApiException,
		// WebException {
//		String url = "https://api.worldoftanks.eu/wot/auth/login/";
//		Map<String, String> params = new HashMap<String, String>(3);
//		params.put("expires_at", "300");
//		params.put("nofollow", "1");
//		params.put("redirect_uri", redirectUrl);
//		JsonObject resp = (JsonObject) this.getResponseData(url, params, "GET");
//		return resp.getString("location");
		return "";
	}

	@Override
	public ProlongateResponse prolongate(String accessToken, Duration duration) {
		// throws WebException, WgApiException {
//		String url = "https://api.worldoftanks.eu/wot/auth/prolongate/";
//		long seconds = duration.getSeconds();
//		Map<String, String> params = new HashMap<String, String>(3);
//		params.put("access_token", accessToken);
//		params.put("expires_at", Long.toString(seconds));
//		JsonObject resp =
//				(JsonObject) this.getResponseData(url, params, "POST");
//		String newToken = resp.getString("access_token");
//		String accountId = resp.get("account_id").toString();
//		long expiryTimeStamp = resp.getJsonNumber("expires_at").longValue();
//		Instant expiryTime = Instant.ofEpochSecond(expiryTimeStamp);
//		return new ProlongateResponse(newToken, accountId, expiryTime);
		return new ProlongateResponse("", "", Instant.EPOCH);
	}

//	private static String makeQueryString(String appId,
//			Map<String, String> params) {
//		Charset charset = StandardCharsets.UTF_8;
//		StringBuilder sb = new StringBuilder();
//		sb.append("application_id=");
//		sb.append(appId);
//		for (Map.Entry<String, String> e : params.entrySet()) {
//			if (e.getKey().equals("application_id"))
//				continue;
//			sb.append('&');
//			try {
//				sb.append(URLEncoder.encode(e.getKey(), charset.name()));
//				sb.append('=');
//				sb.append(URLEncoder.encode(e.getValue(), charset.name()));
//			} catch (UnsupportedEncodingException ex) {
//				// TODO
//				ex.printStackTrace();
//			}
//		}
//		return sb.toString();
//	}
//
//	private static HttpsURLConnection makeRequest(String urlString,
//			String method, Charset charset, String queryString)
//			throws WebException {
//		URL url = null;
//		if ("GET".equalsIgnoreCase(method)) {
//			urlString = urlString + "?" + queryString;
//		}
//		try {
//			url = new URL(urlString);
//		} catch (MalformedURLException e) {
//			throw new WebException(urlString + " is not a valid URL", e);
//		}
//		HttpsURLConnection con = null;
//		try {
//			con = (HttpsURLConnection) url.openConnection();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// paranoid tls settings
//		con.setSSLSocketFactory(new SecureSSLSocketFactory());
//		con.setRequestProperty("Accept-Charset", charset.name());
//		if ("POST".equalsIgnoreCase(method)) {
//			con.setRequestProperty(
//					"Content-Type",
//					"application/x-www-form-urlencoded;charset="
//							+ charset.name());
//			// also sets request method implicitly to POST
//			con.setDoOutput(true);
//			try (OutputStream os = con.getOutputStream()) {
//				os.write(queryString.getBytes(charset));
//			} catch (IOException e) {
//				// TODO
//			}
//		}
//		return con;
//	}
//
//	private JsonStructure getResponse(String urlString,
//			Map<String, String> params, String method) throws WebException {
//		if (!"GET".equalsIgnoreCase(method) && !"POST".equalsIgnoreCase(method)) {
//			throw new UnsupportedOperationException(
//					"Can only handle GET and POST");
//		}
//		Charset charset = StandardCharsets.UTF_8;
//		String queryString = makeQueryString(appId, params);
//		HttpsURLConnection con =
//				makeRequest(urlString, method, charset, queryString);
//
//		// read the response
//		JsonStructure json = null;
//		try (JsonReader reader = Json.createReader(con.getInputStream())) {
//			// parse the response
//			try {
//				json = reader.read();
//			} catch (JsonException | IllegalStateException e) {
//				// TODO
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return json;
//	}
//
//	/**
//	 * App id is added implicitly
//	 * @param urlString
//	 * @param params
//	 * @return
//	 */
//	private JsonValue getResponseData(String urlString,
//			Map<String, String> params, String method) throws WebException,
//			WgApiException {
//		JsonObject json;
//		try {
//			json = (JsonObject) getResponse(urlString, params, method);
//			String status = json.getString("status");
//			if (!"ok".equals(status)) {
//				if ("error".equals(status)) {
//					JsonObject error = json.getJsonObject("error");
//					throw new WgApiException(error.getInt("code"),
//							error.getString("field"),
//							error.getString("message"),
//							error.getString("value"));
//				} else {
//					throw new WebException(
//							"Response contained unexpected format");
//				}
//			}
//			return json.get("data");
//		} catch (ClassCastException | NullPointerException e) {
//			throw new WebException("Response contained unexpected format", e);
//		}
//	}
}
