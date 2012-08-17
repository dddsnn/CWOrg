package cworg.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cworg.Clan;
import cworg.Player;
import cworg.Tank;

public class WebAccess {
	private static WebAccess instance = new WebAccess();

	public static WebAccess getInstance() {
		return instance;
	}

	public long getPlayerID(String name) throws UnknownPlayerException,
			UnknownFormatException, IOException {
		String response = getResponse("http://worldoftanks.eu/community/accounts/api/1.1/?source_token=WG-WoT_Assistant-1.2.2&search="
				+ name + "&offset=0&limit=1");

		JSONObject json = null;
		try {
			json = new JSONObject(response);
		} catch (JSONException e) {
			throw new UnknownFormatException();
		}
		try {
			if (!json.getString("status").equals("ok"))
				throw new UnknownFormatException();
		} catch (JSONException e1) {
			throw new UnknownFormatException();
		}
		JSONArray results;
		try {
			results = json.getJSONObject("data").getJSONArray(
					"items");
		} catch (JSONException e) {
			throw new UnknownFormatException();
		}
		if (results.length() == 0)
			throw new UnknownPlayerException("Player " + name
					+ " not found.");
		// check that it's the exact same player
		String receivedName = null;
		try {
			receivedName = results.getJSONObject(0).getString(
					"name");
		} catch (JSONException e) {
			throw new UnknownFormatException();
		}
		if (!receivedName.equals(name))
			throw new UnknownPlayerException("Player " + name
					+ " doesn't exist. Closest match is "
					+ receivedName);
		long id = 0;
		try {
			id = json.getJSONObject("data").getJSONArray("items")
					.getJSONObject(0).getLong("id");
		} catch (JSONException e) {
			throw new UnknownFormatException();
		}
		return id;
	}

	public Player getPlayer(long id) throws UnknownFormatException,
			IOException {
		String response = getResponse("http://worldoftanks.eu/community/accounts/"
				+ id
				+ "/api/1.3/?source_token=WG-WoT_Assistant-1.2.2");
		JSONObject json;
		try {
			json = new JSONObject(response);
		} catch (JSONException e) {
			throw new UnknownFormatException();
		}
		try {
			if (!json.getString("status").equals("ok"))
				throw new UnknownFormatException();
		} catch (JSONException e1) {
			throw new UnknownFormatException();
		}
		JSONArray tanks;
		try {
			tanks = json.getJSONObject("data").getJSONArray(
					"vehicles");
		} catch (JSONException e) {
			throw new UnknownFormatException();
		}
		String name;
		try {
			name = json.getJSONObject("data").getString("name");
		} catch (JSONException e) {
			throw new UnknownFormatException();
		}
		Player p = new Player(name, id);
		for (int i = 0; i < tanks.length(); i++) {
			String tankName;
			try {
				tankName = tanks.getJSONObject(i).getString(
						"name");
			} catch (JSONException e) {
				throw new UnknownFormatException();
			}
			p.addTank(new Tank(WebAdapter.getLocalType(tankName)));
		}
		return p;
	}

	public Player getPlayer(String name) throws UnknownPlayerException,
			UnknownFormatException, IOException {
		long id = getPlayerID(name);
		return getPlayer(id);
	}

	public long getClanID(String name) throws UnknownClanException,
			UnknownFormatException, IOException {
		String response = getResponse("http://worldoftanks.eu/community/clans/api/1.1/?source_token=WG-WoT_Assistant-1.2.2&search="
				+ name + "&offset=0&limit=1");

		JSONObject json = null;
		try {
			json = new JSONObject(response);
		} catch (JSONException e) {
			throw new UnknownFormatException();
		}
		try {
			if (!json.getString("status").equals("ok"))
				throw new UnknownFormatException();
		} catch (JSONException e1) {
			throw new UnknownFormatException();
		}
		JSONArray results;
		try {
			results = json.getJSONObject("data").getJSONArray(
					"items");
		} catch (JSONException e) {
			throw new UnknownFormatException();
		}
		if (results.length() == 0)
			throw new UnknownClanException("Clan " + name
					+ " not found.");
		// check that it's the exact clan
		String receivedName = null;
		try {
			receivedName = results.getJSONObject(0).getString(
					"name");
		} catch (JSONException e) {
			throw new UnknownFormatException();
		}
		if (!receivedName.equals(name))
			throw new UnknownClanException("Clan " + name
					+ " doesn't exist. Closest match is "
					+ receivedName);
		long id = 0;
		try {
			id = json.getJSONObject("data").getJSONArray("items")
					.getJSONObject(0).getLong("id");
		} catch (JSONException e) {
			throw new UnknownFormatException();
		}
		return id;
	}

	public Clan getClan(long id) throws UnknownFormatException, IOException {
		String response = getResponse("http://worldoftanks.eu/community/clans/"
				+ id
				+ "/api/1.1/?source_token=WG-WoT_Assistant-1.2.2");
		JSONObject json;
		try {
			json = new JSONObject(response);
		} catch (JSONException e) {
			throw new UnknownFormatException();
		}
		try {
			if (!json.getString("status").equals("ok"))
				throw new UnknownFormatException();
		} catch (JSONException e) {
			throw new UnknownFormatException();
		}

		String name, clantag;
		try {
			name = json.getJSONObject("data").getString("name");
			clantag = json.getJSONObject("data").getString(
					"abbreviation");
		} catch (JSONException e) {
			throw new UnknownFormatException();
		}
		Clan clan = new Clan(clantag, name, id);

		// add members
		JSONArray members;
		try {
			members = json.getJSONObject("data").getJSONArray(
					"members");
		} catch (JSONException e) {
			throw new UnknownFormatException();
		}
		for (int i = 0; i < members.length(); i++) {
			long playerID;
			try {
				playerID = members.getJSONObject(i).getLong(
						"account_id");
			} catch (JSONException e) {
				throw new UnknownFormatException();
			}
			clan.addPlayer(getPlayer(playerID));
		}
		return clan;
	}

	public Clan getClan(String name) throws UnknownClanException,
			UnknownFormatException, IOException {
		long id = getClanID(name);
		return getClan(id);
	}

	private String getResponse(String urlString) throws IOException {
		URL url = new URL(urlString.replace(' ', '+'));
		HttpURLConnection con = (HttpURLConnection) url
				.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String line;
		String response = "";
		while ((line = in.readLine()) != null)
			response = response + line;
		in.close();
		return response;
	}
}
