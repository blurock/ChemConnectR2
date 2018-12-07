package info.esblurock.reaction.core.server.authentification;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import info.esblurock.reaction.chemconnect.core.data.contact.IndividualInformation;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.core.server.initialization.CreateDefaultObjectsFactory;
import info.esblurock.reaction.io.db.QueryBase;

@SuppressWarnings("serial")
public class Oauth2CallbackServlet extends HttpServlet {

  private static final Collection<String> SCOPES = Arrays.asList("email", "profile");
  private static final String USERINFO_ENDPOINT
      = "https://www.googleapis.com/plus/v1/people/me/openIdConnect";
  private static final JsonFactory JSON_FACTORY = new JacksonFactory();
  private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

  private GoogleAuthorizationCodeFlow flow;

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
      ServletException {

	  System.out.println("Oauth2CallbackServlet");
	  
	  System.out.println("Oauth2CallbackServlet:\n" + req.getParameter("state"));
	  System.out.println("Oauth2CallbackServlet:\n" + req.getSession().getAttribute("state"));

	  
    // Ensure that this is no request forgery going on, and that the user
    // sending us this connect request is the user that was supposed to.
    if (req.getSession().getAttribute("state") == null
        || !req.getParameter("state").equals((String) req.getSession().getAttribute("state"))) {
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      //resp.sendRedirect("");
	  System.out.println("Oauth2CallbackServlet after sendRedirect  SC_UNAUTHORIZED");
      
      return;
    }

    req.getSession().removeAttribute("state");     // Remove one-time use state.

    flow = new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT,
        JSON_FACTORY,
        getServletContext().getInitParameter("clientID"),
        getServletContext().getInitParameter("clientSecret"),
        SCOPES).build();

    final TokenResponse tokenResponse =
        flow.newTokenRequest(req.getParameter("code"))
            .setRedirectUri(getServletContext().getInitParameter("callback"))
            .execute();

    req.getSession().setAttribute("token", tokenResponse.toString()); // Keep track of the token.
    final Credential credential = flow.createAndStoreCredential(tokenResponse, null);
    final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);

    final GenericUrl url = new GenericUrl(USERINFO_ENDPOINT);      // Make an authenticated request.
    final HttpRequest request = requestFactory.buildGetRequest(url);
    request.getHeaders().setContentType("application/json");

    final String jsonIdentity = request.execute().parseAsString();
    @SuppressWarnings("unchecked")
    HashMap<String, String> userIdResult =
        new ObjectMapper().readValue(jsonIdentity, HashMap.class);
    // From this map, extract the relevant profile info and store it in the session.
    req.getSession().setAttribute("userEmail", userIdResult.get("email"));
    req.getSession().setAttribute("userId", userIdResult.get("sub"));
    req.getSession().setAttribute("userImageUrl", userIdResult.get("picture"));
    
    System.out.println("Oauth2CallbackServle keyset\n" + userIdResult.keySet());
    for(String key : userIdResult.keySet()) {
    	System.out.println("Key: " + key + ": " + userIdResult.get(key));
    }
    
    System.out.println("Oauth2CallbackServle values\n" + userIdResult.values());
    System.out.println("Oauth2CallbackServle email\n" + userIdResult.get("email"));
    System.out.println("Oauth2CallbackServle sub\n" + userIdResult.get("sub"));
    System.out.println("Oauth2CallbackServle picture\n" + userIdResult.get("picture"));
    System.out.println("Oauth2CallbackServle getServerInfo\n" + this.getServletContext().getServerInfo());
    System.out.println("Oauth2CallbackServle getLocalHost\n" + InetAddress.getLocalHost());
    System.out.println("Oauth2CallbackServle getInitParameterNames\n" + this.getServletConfig().getInitParameterNames());
    
    String emailS = userIdResult.get("email");
    Cookie emailC = new Cookie("email", emailS);
    emailC.setMaxAge(60 * 60);
    resp.addCookie(emailC);
    
    Cookie picC = new Cookie("userpicture", userIdResult.get("picture"));
    picC.setMaxAge(60 * 60);
    resp.addCookie(picC);
    
    Cookie given_nameC = new Cookie("given_name", userIdResult.get("given_name"));
    given_nameC.setMaxAge(60 * 60);
    resp.addCookie(given_nameC);
    
    Cookie family_nameC = new Cookie("family_name", userIdResult.get("family_name"));
    family_nameC.setMaxAge(60 * 60);
    resp.addCookie(family_nameC);
    
    Cookie inSystemC = new Cookie("hasAccount", Boolean.TRUE.toString());
    inSystemC.setMaxAge(60 * 60);    
	try {
		//IndividualInformation person = (IndividualInformation)
		QueryBase.getFirstDatabaseObjectsFromSingleProperty(IndividualInformation.class.getCanonicalName(),
						"owner", emailS);
	} catch (IOException ex) {
		inSystemC.setValue(Boolean.FALSE.toString());
	}
    resp.addCookie(inSystemC);


    //String userrole = MetaDataKeywords.accessTypeStandardUser;
    //LoginAuthorization.findOrCreateIndividual(username, organization, shortorganization, userrole);
    
    resp.sendRedirect("http://localhost:8080/#FirstPagePlace:First%20Page");
    //resp.sendRedirect((String) req.getSession().getAttribute("loginDestination"));
  }
}