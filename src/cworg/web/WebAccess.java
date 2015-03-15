//package cworg.web;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import cworg.data.Clan;
//import cworg.data.Player;
//import cworg.data.Tank;
//
//public class WebAccess {
//	private static WebAccess instance = new WebAccess();
//
//	public static WebAccess getInstance() {
//		return instance;
//	}
//
//	public long getPlayerID(String name) throws Exception,
//			Exception, IOException {
//		String response =
//				getResponse("http://worldoftanks.eu/community/accounts/api/1.1/?source_token=WG-WoT_Assistant-1.2.2&search="
//						+ name + "&offset=0&limit=1");
//
//		JSONObject json = null;
//		try {
//			json = new JSONObject(response);
//		} catch (JSONException e) {
//			throw new Exception();
//		}
//		try {
//			if (!json.getString("status").equals("ok"))
//				throw new Exception();
//		} catch (JSONException e1) {
//			throw new Exception();
//		}
//		JSONArray results;
//		try {
//			results = json.getJSONObject("data").getJSONArray("items");
//		} catch (JSONException e) {
//			throw new Exception();
//		}
//		if (results.length() == 0)
//			throw new Exception("Player " + name + " not found.");
//		// check that it's the exact same player
//		String receivedName = null;
//		try {
//			receivedName = results.getJSONObject(0).getString("name");
//		} catch (JSONException e) {
//			throw new Exception();
//		}
//		if (!receivedName.equals(name))
//			throw new Exception("Player " + name
//					+ " doesn't exist. Closest match is " + receivedName);
//		long id = 0;
//		try {
//			id =
//					json.getJSONObject("data").getJSONArray("items")
//							.getJSONObject(0).getLong("id");
//		} catch (JSONException e) {
//			throw new Exception();
//		}
//		return id;
//	}
//
//	public Player getPlayer(long id) throws Exception,
//			IOException {
//		String response =
//				getResponse("http://worldoftanks.eu/community/accounts/" + id
//						+ "/api/1.3/?source_token=WG-WoT_Assistant-1.2.2");
//		JSONObject json;
//		try {
//			json = new JSONObject(response);
//		} catch (JSONException e) {
//			throw new Exception();
//		}
//		try {
//			if (!json.getString("status").equals("ok"))
//				throw new Exception();
//		} catch (JSONException e1) {
//			throw new Exception();
//		}
//		JSONArray tanks;
//		try {
//			tanks = json.getJSONObject("data").getJSONArray("vehicles");
//		} catch (JSONException e) {
//			throw new Exception();
//		}
//		String name;
//		try {
//			name = json.getJSONObject("data").getString("name");
//		} catch (JSONException e) {
//			throw new Exception();
//		}
//		Player p = new Player();
//		p.setName(name);
//		p.setId(id);
//		for (int i = 0; i < tanks.length(); i++) {
//			String tankName;
//			try {
//				tankName = tanks.getJSONObject(i).getString("name");
//			} catch (JSONException e) {
//				throw new Exception();
//			}
//			// TODO this needs to call the DBHelper to get the id in the db and
//			// get the tankinfo from the db
////			p.addTank(new Tank(WebAdapter.getLocalType(tankName)));
//		}
//		return p;
//	}
//
//	public Player getPlayer(String name) throws Exception,
//			Exception, IOException {
//		long id = getPlayerID(name);
//		return getPlayer(id);
//	}
//
//	public long getClanIDByName(String name) throws Exception,
//			Exception, IOException {
//		String response =
//				getResponse("http://worldoftanks.eu/community/clans/api/1.1/?source_token=WG-WoT_Assistant-1.2.2&search="
//						+ name + "&offset=0&limit=1");
//
//		JSONObject json = null;
//		try {
//			json = new JSONObject(response);
//		} catch (JSONException e) {
//			throw new Exception();
//		}
//		try {
//			if (!json.getString("status").equals("ok"))
//				throw new Exception();
//		} catch (JSONException e1) {
//			throw new Exception();
//		}
//		JSONArray results;
//		try {
//			results = json.getJSONObject("data").getJSONArray("items");
//		} catch (JSONException e) {
//			throw new Exception();
//		}
//		if (results.length() == 0)
//			throw new Exception("Clan " + name + " not found.");
//		// check that it's the exact clan
//		String receivedName = null;
//		try {
//			receivedName = results.getJSONObject(0).getString("name");
//		} catch (JSONException e) {
//			throw new Exception();
//		}
//		if (!receivedName.equals(name))
//			throw new Exception("Clan " + name
//					+ " doesn't exist. Closest match is " + receivedName);
//		long id = 0;
//		try {
//			id =
//					json.getJSONObject("data").getJSONArray("items")
//							.getJSONObject(0).getLong("id");
//		} catch (JSONException e) {
//			throw new Exception();
//		}
//		return id;
//	}
//
//	public long getClanIDByTag(String clanTag) throws Exception,
//			Exception, IOException {
//		String response =
//				getResponse("http://worldoftanks.eu/community/clans/api/1.1/?source_token=WG-WoT_Assistant-1.2.2&search="
//						+ clanTag + "&offset=0&limit=10");
//
//		JSONObject json = null;
//		try {
//			json = new JSONObject(response);
//		} catch (JSONException e) {
//			throw new Exception();
//		}
//		try {
//			if (!json.getString("status").equals("ok"))
//				throw new Exception();
//		} catch (JSONException e1) {
//			throw new Exception();
//		}
//		JSONArray results;
//		try {
//			results = json.getJSONObject("data").getJSONArray("items");
//		} catch (JSONException e) {
//			throw new Exception();
//		}
//		if (results.length() == 0)
//			throw new Exception("No clan with clan tag" + clanTag
//					+ " found.");
//		// check that it's the exact clan
//		String receivedTag = null;
//		int index;
//		try {
//			for (index = 0; index < results.length(); index++) {
//				receivedTag =
//						results.getJSONObject(index).getString("abbreviation");
//				if (receivedTag.equals(clanTag))
//					break;
//			}
//		} catch (JSONException e) {
//			throw new Exception();
//		}
//		if (!receivedTag.equals(clanTag))
//			throw new Exception("Clan with tag " + clanTag
//					+ " wasn't found. First match was " + receivedTag);
//		long id = 0;
//		try {
//			id =
//					json.getJSONObject("data").getJSONArray("items")
//							.getJSONObject(index).getLong("id");
//		} catch (JSONException e) {
//			throw new Exception();
//		}
//		return id;
//	}
//
//	public Clan getClan(long id) throws Exception, IOException {
//		String response =
//				getResponse("http://worldoftanks.eu/community/clans/" + id
//						+ "/api/1.1/?source_token=WG-WoT_Assistant-1.2.2");
//		JSONObject json;
//		try {
//			json = new JSONObject(response);
//		} catch (JSONException e) {
//			throw new Exception();
//		}
//		try {
//			if (!json.getString("status").equals("ok"))
//				throw new Exception();
//		} catch (JSONException e) {
//			throw new Exception();
//		}
//
//		String name, clantag;
//		try {
//			name = json.getJSONObject("data").getString("name");
//			clantag = json.getJSONObject("data").getString("abbreviation");
//		} catch (JSONException e) {
//			throw new Exception();
//		}
//		Clan clan = new Clan(clantag, name, id);
//
//		// add members
//		JSONArray members;
//		try {
//			members = json.getJSONObject("data").getJSONArray("members");
//		} catch (JSONException e) {
//			throw new Exception();
//		}
//		for (int i = 0; i < members.length(); i++) {
//			long playerID;
//			try {
//				playerID = members.getJSONObject(i).getLong("account_id");
//			} catch (JSONException e) {
//				throw new Exception();
//			}
//			clan.addPlayer(getPlayer(playerID));
//		}
//		return clan;
//	}
//
//	public Clan getClanByName(String name) throws Exception,
//			Exception, IOException {
//		long id = getClanIDByName(name);
//		return getClan(id);
//	}
//
//	public Clan getClanByTag(String clanTag) throws Exception,
//			Exception, IOException {
//		long id = getClanIDByTag(clanTag);
//		return getClan(id);
//	}
//
//	private String getResponse(String urlString) throws IOException {
//		URL url = new URL(urlString.replace(' ', '+'));
//		HttpURLConnection con = (HttpURLConnection) url.openConnection();
//		BufferedReader in =
//				new BufferedReader(new InputStreamReader(con.getInputStream()));
//		String line;
//		String response = "";
//		while ((line = in.readLine()) != null)
//			response = response + line;
//		in.close();
//		return response;
//	}
//}
