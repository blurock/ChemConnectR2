package info.esblurock.reaction.core.server.authentification;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

  private static final Collection<String> SCOPES = Arrays.asList("email", "profile");
  private static final JsonFactory JSON_FACTORY = new JacksonFactory();
  private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

  private GoogleAuthorizationCodeFlow flow;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
	    System.out.println("LoginServlet");

	BigInteger stateI = new BigInteger(130, new SecureRandom());
    String state = stateI.toString(32);  // prevent request forgery
    
    System.out.println("LoginServlet: " + state);
    
    req.getSession().setAttribute("state", state);

    if (req.getAttribute("loginDestination") != null) {
      req
          .getSession()
          .setAttribute("loginDestination", (String) req.getAttribute("loginDestination"));
    } else {
      req.getSession().setAttribute("loginDestination", "/books");
    }

    System.out.println("LoginServlet:after set loginDestination " + state);
    System.out.println("LoginServlet:after set loginDestination " + getServletContext().getInitParameter("clientID"));
    System.out.println("LoginServlet:after set loginDestination " + getServletContext().getInitParameter("clientSecret"));
    
    flow = new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT,
        JSON_FACTORY,
        getServletContext().getInitParameter("clientID"),
        getServletContext().getInitParameter("clientSecret"),
        SCOPES)
        .build();
    System.out.println("LoginServlet:after flow definition " + state);

    // Callback url should be the one registered in Google Developers Console
    String url =
        flow.newAuthorizationUrl()
            .setRedirectUri(getServletContext().getInitParameter("callback"))
            .setState(state)            // Prevent request forgery
            .build();
    System.out.println("LoginServlet: send " + url);
   
    resp.sendRedirect(url);
    System.out.println("LoginServlet: done " + url);
    
  }
}