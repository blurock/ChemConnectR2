package info.esblurock.reaction.chemconnect.core.client.firstpage;

import com.google.gwt.core.client.GWT;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialParallax;
import info.esblurock.reaction.chemconnect.core.client.resources.info.about.InfoAboutResources;
import info.esblurock.reaction.chemconnect.core.client.ui.view.FirstSiteLandingPageView;

public class FirstSiteLandingPage extends Composite implements FirstSiteLandingPageView {

	private static FirstSiteLandingPageUiBinder uiBinder = GWT.create(FirstSiteLandingPageUiBinder.class);
	
	//private static String REDIRECT_URI = "oauth2callback";


	interface FirstSiteLandingPageUiBinder extends UiBinder<Widget, FirstSiteLandingPage> {
	}

	InfoAboutResources inforesources = GWT.create(InfoAboutResources.class);
	Presenter listener;

	
	public FirstSiteLandingPage() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	@UiField
	MaterialPanel headerpanel;
	@UiField
	HTML introduction;
	@UiField
	MaterialParallax smartconnected;
	@UiField
	MaterialParallax interactionpanel;
	@UiField
	MaterialParallax developerpanel;
	@UiField
	MaterialParallax smartobjects;
	@UiField
	HTML introduction2;
	@UiField
	HTML introduction3;
	@UiField
	HTML developer;
	
	@UiField
	MaterialPanel footerpanel;
	@UiField
	MaterialPanel panel;


	void init() {
		ImageResource headerimage = inforesources.ChemConnectHeader();
		Image image = new Image(headerimage.getSafeUri());
		headerpanel.add(image);
		introduction.setTitle("CHEMCONNECT: Connected Smart Data");
		introduction.setHTML(inforesources.IntroductionAbstract().getText());
		
		ImageResource smartimage = inforesources.ConnectedSmartDatabase();
		Image smart = new Image(smartimage.getSafeUri());
		smartconnected.add(smart);
		introduction2.setTitle("Center of data management");
		introduction2.setHTML(inforesources.Introduction2Abstract().getText());
		
		ImageResource interactimage = inforesources.ChemConnectInteractions();
		Image interact = new Image(interactimage);
		interactionpanel.add(interact);
		introduction3.setTitle("CHEMCONNECT: Knowledge-Base driving interface, data and descriptions");
		introduction3.setHTML(inforesources.Introduction3Abstract().getText());
		
		ImageResource developerimage = inforesources.DeveloperMe();
		Image developerme = new Image(developerimage);
		developerpanel.add(developerme);
		developer.setTitle("Who is behind CHEMCONNECT");
		developer.setHTML(inforesources.Developer().getText());
		
		ImageResource smartdataimage = inforesources.ConnectedSmartDataObjects();
		Image smartdata = new Image(smartdataimage.getSafeUri());
		smartobjects.add(smartdata);
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
	
	
	

	@Override
	public void setName(String helloName) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

}
