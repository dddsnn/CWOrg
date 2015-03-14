package cworg.beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.*;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestScoped
@Named("testBean")
public class TestBean {
	@Inject
	private HttpServletRequest request;
	@Inject
	private ServletContext context;
	@Inject
	private HttpSession session;
	@EJB
	private TestDB db;

	public String getTest() {
		List<TestData> things = db.getAllThings();
		String thingString = "";
		for (TestData thing : things) {
			thingString += thing.getData();
		}
		return request.getMethod() + thingString;
	}

	public String getNick() {
		String nick = (String) session.getAttribute("nick");
		if (nick == null)
			return "";
		else
			return nick;
	}

	public void addAThing(String stuff) {
		db.addStuff(stuff);
	}

	public String getUrl() {
		URL url = null;
		String appId = (String) context.getAttribute("app-id");
		try {
			url =
					new URL(
							"https://api.worldoftanks.eu/wot/auth/login/?application_id="+appId+"&expires_at=200&nofollow=1&redirect_uri="
									+ URLEncoder
											.encode("https://h1186431.serverkompetenz.net:8181/CWOrg/login",
													"UTF-8"));
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpsURLConnection con;
		String response = "";
		try {
			con = (HttpsURLConnection) url.openConnection();
			BufferedReader in =
					new BufferedReader(new InputStreamReader(
							con.getInputStream()));
			String line;
			while ((line = in.readLine()) != null)
				response = response + line;
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsonReader reader = Json.createReader(new StringReader(response));
		JsonObject json = reader.readObject();
		return json.getJsonObject("data").getString("location");
	}

	public String logout() {
		String appId = (String) context.getAttribute("app-id");
		URL url = null;
		String token = (String) session.getAttribute("token");
		if (token == null)
			return "";
		try {
			url =
					new URL(
							"https://api.worldoftanks.eu/wot/auth/logout/?application_id="+appId+"&access_token="
									+ token);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpsURLConnection con;
		String response = "";
		try {
			con = (HttpsURLConnection) url.openConnection();
			BufferedReader in =
					new BufferedReader(new InputStreamReader(
							con.getInputStream()));
			String line;
			while ((line = in.readLine()) != null)
				response = response + line;
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.removeAttribute("token");
		session.removeAttribute("nick");
		return "";
	}
}
