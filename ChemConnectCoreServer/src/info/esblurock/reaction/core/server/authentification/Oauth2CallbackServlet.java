package info.esblurock.reaction.core.server.authentification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.IndividualInformation;
import info.esblurock.reaction.chemconnect.core.data.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;
import info.esblurock.reaction.core.server.services.util.ContextAndSessionUtilities;
import info.esblurock.reaction.core.server.services.util.VerifyServerTransaction;
import info.esblurock.reaction.io.db.QueryBase;

@SuppressWarnings("serial")
public class Oauth2CallbackServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(Oauth2CallbackServlet.class.getName());

	private static final Collection<String> SCOPES = Arrays.asList("email", "profile");
	private static final String USERINFO_ENDPOINT = "https://www.googleapis.com/plus/v1/people/me/openIdConnect";
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	private GoogleAuthorizationCodeFlow flow;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String state = req.getParameter("state");

		Cookie[] cookies = req.getCookies();
		String expected = "";
		if(cookies != null) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().compareTo("secret") == 0) {
				expected = cookie.getValue();
			}
		}
		} else {
			System.out.println("Oauth2CallbackServlet after sendRedirect  SC_UNAUTHORIZED no cookies");
		}
		// Ensure that this is no request forgery going on, and that the user
		// sending us this connect request is the user that was supposed to.
		if (state == null || !state.equals(expected)) {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			System.out.println("Oauth2CallbackServlet after sendRedirect  SC_UNAUTHORIZED");
			resp.sendRedirect(req.getContextPath());
		}
		String firstname = "";
		String lastname = "";
		String auth_id = "";
		String authType = "";
		if (state.startsWith("google")) {
			req.getSession().removeAttribute("state"); // Remove one-time use state.

			flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
					getServletContext().getInitParameter("clientID"),
					getServletContext().getInitParameter("clientSecret"), SCOPES).build();

			final TokenResponse tokenResponse = flow.newTokenRequest(req.getParameter("code"))
					.setRedirectUri(getServletContext().getInitParameter("callback")).execute();

			req.getSession().setAttribute("token", tokenResponse.toString()); // Keep track of the token.
			final Credential credential = flow.createAndStoreCredential(tokenResponse, null);
			final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);

			final GenericUrl url = new GenericUrl(USERINFO_ENDPOINT); // Make an authenticated request.
			final HttpRequest request = requestFactory.buildGetRequest(url);
			request.getHeaders().setContentType("application/json");

			final String jsonIdentity = request.execute().parseAsString();
			@SuppressWarnings("unchecked")
			HashMap<String, String> userIdResult = new ObjectMapper().readValue(jsonIdentity, HashMap.class);
			// From this map, extract the relevant profile info and store it in the session.
			req.getSession().setAttribute("userEmail", userIdResult.get("email"));
			req.getSession().setAttribute("userId", userIdResult.get("sub"));
			req.getSession().setAttribute("userImageUrl", userIdResult.get("picture"));
			
			String emailS = userIdResult.get("email");
			Cookie emailC = new Cookie("email", emailS);
			emailC.setMaxAge(60 * 60);
			resp.addCookie(emailC);

			Cookie picC = new Cookie("userpicture", userIdResult.get("picture"));
			picC.setMaxAge(60 * 60);
			resp.addCookie(picC);
			
			firstname = userIdResult.get("given_name");
			lastname = userIdResult.get("family_name");
			auth_id = userIdResult.get("sub");

			authType = "Google";

		
		} else if (state.startsWith("linkedin")) {
			String code = req.getParameter("code");
			List<String> list = Collections.list(req.getParameterNames());
			for (String name : list) {
				System.out.println(name + ": " + req.getParameter(name));
			}
			String newstate = "tokenlinkedin" + state;
			for (Cookie cookie : cookies) {
				if (cookie.getName().compareTo("secret") == 0) {
					cookie.setValue(newstate);
				}
			}

			System.out.println("Canonical Hostname: '" + req.getLocalAddr() + "'");
			System.out.println("Canonical Hostname: '" + req.getLocalPort() + "'");
			int serverport = req.getServerPort();
			String servername = req.getServerName();
			String red = "http://blurock-chemconnect.appspot.com/oauth2callback";
			if(servername.compareTo("localhost") == 0) {
				red = "http://localhost:8080/oauth2callback";
			}
			String response = "https://www.linkedin.com/oauth/v2/accessToken?state=" + newstate + "&"
					+ "client_id=77lvn5zzefwzq0&" 
					+ "redirect_uri=" + red + "&"
					+ "grant_type=authorization_code&" 
					+ "client_secret=fnmtW4at0KZBeeuN&" + "code=" + code + "&"
					+ "format=json";

			log.info("Response: " + response);
			JSONObject jsonobj = getJSONObject(response);
			String accesstoken = (String) jsonobj.get("access_token");

			resp.addHeader("Authorization", "Bearer " + accesstoken);
			String data = "https://api.linkedin.com/v1/people/~?format=json";
			// String data = "https://api.linkedin.com/v2/me?format=json";
			URL url = new URL(data);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.addRequestProperty("Authorization", "Bearer " + accesstoken);
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException(
						"Failed : HTTP error code : " + conn.getResponseCode() + ": " + conn.getResponseMessage());
			}

			JSONObject json = getJSON(conn.getInputStream());
			firstname = json.getString("firstName");
			lastname = json.getString("lastName");
			auth_id = json.getString("id");
			
			authType = "LinkedIn";
			
		} else if (state.startsWith("facebook")) {
			String access_token = req.getParameter("access_token");
			String CLIENT_ID = "618453741934565";
			String newstate = "nextfacebook";
			Cookie newstateC = new Cookie("secret", newstate);
			resp.addCookie(newstateC);
			String tokenurl = "https://graph.facebook.com/v3.2/me/accounts?";
			String tokenparameters = "&state=" + newstate + "&client_id=" + CLIENT_ID + "&access_token=" + access_token;

			System.out.println(tokenparameters);
			System.out.println("Size of call: " + tokenparameters.length());
			String url = tokenurl + java.net.URLEncoder.encode(tokenparameters, "UTF-8");
			System.out.println("Size of call: " + url.length());

			JSONObject jsonobj = getJSONObject(url);
			String accesstoken = (String) jsonobj.get("access_token");

			System.out.println(jsonobj);
			System.out.println("Access Token: " + accesstoken);

		} else if (state.startsWith("nextfacebook")) {
			System.out.println("nextfacebook");
			List<String> list = Collections.list(req.getParameterNames());
			for (String name : list) {
				System.out.println(name + ": " + req.getParameter(name));
			}

		}
		
		Cookie given_nameC = new Cookie("given_name", firstname);
		given_nameC.setMaxAge(60 * 60);
		resp.addCookie(given_nameC);
		Cookie family_nameC = new Cookie("family_name", lastname);
		family_nameC.setMaxAge(60 * 60);
		resp.addCookie(family_nameC);
		Cookie google_idC = new Cookie("auth_id", auth_id);
		google_idC.setMaxAge(60 * 60);
		resp.addCookie(google_idC);
		Cookie authtypeC = new Cookie("authorizationType", authType);
		authtypeC.setMaxAge(60 * 60);
		resp.addCookie(authtypeC);

		Cookie inSystemC = new Cookie("hasAccount", Boolean.TRUE.toString());
		inSystemC.setMaxAge(60 * 60);
		String suggestion = "";
		try {
			System.out.println("accountUserName: " + auth_id);
			DatabaseObject obj = QueryBase.getFirstDatabaseObjectsFromSingleProperty(UserAccount.class.getCanonicalName(),
					"authorizationName", auth_id);
			UserAccount account = (UserAccount) obj;
			System.out.println("authorizationName: TRUE\n" + account.toString());
			suggestion = account.getAccountUserName();
			String ip = req.getRemoteAddr();
			String host = req.getRemoteHost();
			HttpSession session = req.getSession();
			System.out.println("SessionID: " + session.getId());
			ContextAndSessionUtilities util 
			= new ContextAndSessionUtilities(getServletContext(), session);
			UserDTO user = new UserDTO(account.getAccountUserName(), session.getId(), ip, host,
					account.getAccountPrivilege(), LoginServiceImpl.standardMaxTransitions);
			ArrayList<String> privs = VerifyServerTransaction.getPrivledges(account.getAccountPrivilege());
			user.setPrivledges(privs);
			System.out.println("Oauth2CallbackServlet\n" + user.toString());
			util.removeUser();
			
			System.out.println("After Remove: " + util.getUserInfo());
			
			util.setUserInfo(user);
			
			System.out.println("Oauth2CallbackServlet from context\n" + util.getUserInfo());

		} catch (IOException ex) {
			inSystemC.setValue(Boolean.FALSE.toString());
			suggestion = suggestALoginName(firstname, lastname);
			System.out.println("authorizationName: FALSE\n" + suggestion);
		}
		resp.addCookie(inSystemC);

		Cookie accountNameC = new Cookie("account_name", suggestion);
		accountNameC.setMaxAge(60 * 60);
		resp.addCookie(accountNameC);

		System.out.println("");
		String redirect = req.getContextPath() + "/#FirstPagePlace:First%20Page";
		System.out.println("Redirect: " + redirect);
		resp.sendRedirect(redirect);
	}

	JSONObject getJSONObject(String response) throws IOException {
		System.out.println(response);
		URL url = new URL(response);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException(
					"Failed : HTTP error code : \n" + conn.getResponseCode() + ": " + conn.getResponseMessage() + "\n");
		}
		return getJSON(conn.getInputStream());
	}

	private JSONObject getJSON(InputStream inputStream) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader((inputStream)));
		String output;
		StringBuilder build = new StringBuilder();
		while ((output = br.readLine()) != null) {
			build.append(output);
			build.append("\n");
		}
		String msg = build.toString();
		System.out.println("Message:\n" + msg);
		JSONObject jsonobj = new JSONObject(msg);
		return jsonobj;
	}

	private String suggestALoginName(String firstname, String lastname) {
		String suggestion = lastname;
		if (trySuggestion(suggestion) != null) {
			int count = 0;
			suggestion = firstname + "_" + lastname;
			while (trySuggestion(suggestion) != null) {
				suggestion = firstname + "_" + lastname + "_" + Integer.toString(count++);
			}
		}
		return suggestion;
	}

	private String trySuggestion(String name) {
		String answer = name;
		try {
			QueryBase.getFirstDatabaseObjectsFromSingleProperty(UserAccount.class.getCanonicalName(), "accountUserName",
					name);
		} catch (IOException ex) {
			answer = null;
		}
		return answer;
	}

}