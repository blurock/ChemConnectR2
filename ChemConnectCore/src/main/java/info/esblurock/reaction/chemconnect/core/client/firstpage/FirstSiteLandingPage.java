package info.esblurock.reaction.chemconnect.core.client.firstpage;


import java.util.Random;

import com.google.gwt.core.client.GWT;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.ui.view.FirstSiteLandingPageView;

public class FirstSiteLandingPage extends Composite implements FirstSiteLandingPageView {

	private static FirstSiteLandingPageUiBinder uiBinder = GWT.create(FirstSiteLandingPageUiBinder.class);
	
	private static String REDIRECT_URI = "oauth2callback";


	interface FirstSiteLandingPageUiBinder extends UiBinder<Widget, FirstSiteLandingPage> {
	}

	Presenter listener;

	
	public FirstSiteLandingPage() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	@UiField
	MaterialButton googleLogin;
	@UiField
	MaterialButton linkedinLogin;
	//@UiField
	//MaterialButton facebookLogin;
	@UiField
	MaterialPanel footerpanel;
	@UiField
	MaterialPanel panel;


	public FirstSiteLandingPage(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	
	void init() {
		StandardFooter footer = new StandardFooter();
		footerpanel.add(footer);
	}
	/*
	@UiHandler("facebookLogin")
	void onClickFacebook(ClickEvent e) {
		String CLIENT_ID = "618453741934565";
		String secretState = "facebook" + new Random().nextInt(999_999);
		Cookies.setCookie("secret", secretState);

		String redirect = callbackWithServer();
		String authurl = "https://graph.facebook.com/oauth/access_token?";
		String reststr = "response_type=code&"
				+ "client_id=" + CLIENT_ID + "&"
				+ "redirect_uri=" + redirect + "&"
				+ "state=" + secretState + "&"
				+ "client_secret=2d96d4af1565af4c1a8f0226c870b8aa"
				+ "&grant_type=client_credentials";

		String authurl = "https://www.facebook.com/v3.2/dialog/oauth?";
		String reststr = "response_type=code&"
				+ "client_id=" + CLIENT_ID + "&"
				+ "redirect_uri=" + redirect + "&"
				+ "state=" + secretState + "&"
				+ "client_secret=2d96d4af1565af4c1a8f0226c870b8aa";
		
		String urlS = authurl + reststr;
		Window.alert(urlS);

		Window.open(urlS, "_blank", "");
		
	}
	*/
	
	@UiHandler("linkedinLogin")
	void onClickLinkedIn(ClickEvent e) {
		String CLIENT_ID = "77lvn5zzefwzq0";
		String secretState = "linkedin" + new Random().nextInt(999_999);
		Cookies.setCookie("secret", secretState);

		String authurl = "https://www.linkedin.com/oauth/v2/authorization?";
		String redirect = callbackWithServer();

		String reststr = "response_type=code&"
				+ "client_id=" + CLIENT_ID + "&"
				+ "redirect_uri=" + redirect + "&"
				+ "state=" + secretState + "&"
				+ "scope=r_basicprofile%20r_emailaddress";
		String urlS = authurl + reststr;

		Window.open(urlS, "_blank", "");
	
	}
	
	private String callbackWithServer() {
		String server = "http://localhost:8080";
		String redirect = server + "/" + REDIRECT_URI;
		return redirect;
	}
	
	@UiHandler("googleLogin")
	void onClickGoogle(ClickEvent e) {
		String CLIENT_ID = "664636228487-pbsb9lh39tvi2ec1rg0lqk4uq371bhr9.apps.googleusercontent.com";
		String SCOPE = "https://www.googleapis.com/auth/drive.metadata.readonly";
		
		String secretState = "google" + new Random().nextInt(999_999);
		Cookies.setCookie("secret", secretState);
		
		String authurl = "https://accounts.google.com/o/oauth2/v2/auth?";
		String redirect = callbackWithServer();
		
		String reststr =
				"scope=" + SCOPE + "&" + 
				"access_type=offline&" + 
				"include_granted_scopes=true&" + 
				"state=" + secretState + "&" + 
				"redirect_uri=" + redirect + "&" + 
				"response_type=code&" + 
				"client_id=" + CLIENT_ID;
		String urlS = authurl + reststr;

		Window.open(urlS, "_blank", "");
		
	}

	@Override
	public void setName(String helloName) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

}
