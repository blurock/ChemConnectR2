package info.esblurock.reaction.chemconnect.core.client;

import java.util.Random;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.Place;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.activity.ClientFactory;
import info.esblurock.reaction.chemconnect.core.client.firstpage.FirstSiteLandingPage;
import info.esblurock.reaction.chemconnect.core.client.firstpage.StandardFooter;
import info.esblurock.reaction.chemconnect.core.client.place.AboutSummaryPlace;
import info.esblurock.reaction.chemconnect.core.client.place.ChemConnectObservationPlace;
import info.esblurock.reaction.chemconnect.core.client.place.DatabasePersonDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.client.place.DeviceWithSubystemsDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.client.place.FirstPagePlace;
import info.esblurock.reaction.chemconnect.core.client.place.FirstSiteLandingPagePlace;
import info.esblurock.reaction.chemconnect.core.client.place.IsolateMatrixBlockPlace;
import info.esblurock.reaction.chemconnect.core.client.place.ManageCatalogHierarchyPlace;
import info.esblurock.reaction.chemconnect.core.client.place.MissionStatementPlace;
import info.esblurock.reaction.chemconnect.core.client.place.OrganizationDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.client.place.ProtocolDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.client.place.TutorialExamplePlace;
import info.esblurock.reaction.chemconnect.core.client.place.UploadFileToBlobStoragePlace;
import info.esblurock.reaction.chemconnect.core.client.resources.info.about.InfoAboutResources;
import info.esblurock.reaction.chemconnect.core.common.client.async.LoginService;
import info.esblurock.reaction.chemconnect.core.common.client.async.LoginServiceAsync;

public class TopChemConnectPanel extends Composite {

	private static TopChemConnectPanelUiBinder uiBinder = GWT.create(TopChemConnectPanelUiBinder.class);

	interface TopChemConnectPanelUiBinder extends UiBinder<Widget, TopChemConnectPanel> {
	}

	InfoAboutResources inforesources = GWT.create(InfoAboutResources.class);

	
	@UiField
	MaterialLabel title;
	@UiField
	MaterialLabel subtitle;
	@UiField
	SimplePanel contentPanel;
	@UiField
	SimplePanel footerpanel;
	@UiField
	MaterialLink catalog;
	@UiField
	MaterialLink upload;
	@UiField
	MaterialLink isolate;
	@UiField
	MaterialLink specification;
	@UiField
	MaterialLink protocol;
	@UiField
	MaterialLink dataSet;
	@UiField
	MaterialLink devices;
	@UiField
	MaterialLink people;
	@UiField
	MaterialLink organizations;
	@UiField
	MaterialLink tutorialreadfile;
	@UiField
	MaterialLink mission;
	@UiField
	MaterialLink home;
	@UiField
	MaterialLink about;
	@UiField
	MaterialPanel cornerIcon;
	@UiField
	MaterialLink linkedinLogin;
	@UiField
	MaterialLink googleLogin;
	@UiField
	MaterialLink logout;
	@UiField
	MaterialTooltip logouttooltip;
	@UiField
	MaterialTooltip logintooltip;
	@UiField
	MaterialLink loginchoice;
	ClientFactory clientFactory;
	String hosturl;
	
	public TopChemConnectPanel(ClientFactory clientFactory) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		this.clientFactory = clientFactory;
		hosturl = GWT.getHostPageBaseURL();
		StandardFooter footer = new StandardFooter(this.clientFactory);
		footerpanel.add(footer);	
	}
	
	void init() {
		ImageResource iconimage = inforesources.ChemConnectedDetailedIcon();
		Image icon = new Image(iconimage);
		cornerIcon.add(icon);
		catalog.setText("Manage Data Catalog Structure");
		upload.setText("file staging and interpretation");
		isolate.setText("isolate block out of spreadsheet");
		specification.setText("parameter specification");
		protocol.setText("protocol specification");
		dataSet.setText("dataset from a protocol");
		devices.setText("Manage Device Information");
		people.setText("researchers in database");
		organizations.setText("organizations in database");
		tutorialreadfile.setText("Interpret data files");
		logout.setText("Logout");
		logouttooltip.setText("Log out current user (to Guest");
		logintooltip.setText("Choose method of login");
		String account = Cookies.getCookie("account_name");
		if(account != null) {
			if(account.compareTo("Guest") == 0) {
				setLoginVisibility(true);
			} else {
				setLoginVisibility(false);
			}
		} else {
			setLoginVisibility(true);
		}
		
		home.setText("Home");
		title.setText("ChemConnect: The Intelligent Repository");
	}
	
	void setLoginVisibility(boolean loginvisible) {
		logout.setVisible(!loginvisible);
		loginchoice.setVisible(loginvisible);
	}
	
	@UiHandler("linkedinLogin")
	void onClickLinkedIn(ClickEvent e) {
		String CLIENT_ID = "77lvn5zzefwzq0";
		String secretState = "linkedin" + new Random().nextInt(999_999);
		Cookies.setCookie("secret", secretState);

		String authurl = "https://www.linkedin.com/oauth/v2/authorization?";
		String redirect = callbackWithServer();
		MaterialToast.fireToast("Redirect: " + redirect);
		String reststr = "response_type=code&"
				+ "client_id=" + CLIENT_ID + "&"
				+ "redirect_uri=" + redirect + "&"
				+ "state=" + secretState + "&"
				+ "scope=r_basicprofile%20r_emailaddress";
		String urlS = authurl + reststr;
		MaterialToast.fireToast("URL: " + redirect);
		setLoginVisibility(false);
		Window.open(urlS, "_blank", "");
	
	}
	@UiHandler("googleLogin")
	void onClickGoogle(ClickEvent e) {
		String CLIENT_ID = "571384264595-am69s6l6nuu1hg4o2vmlcmaj63pscd3d.apps.googleusercontent.com";
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
		setLoginVisibility(false);

		Window.open(urlS, "_blank", "");
		
	}
	private String callbackWithServer() {
		MaterialToast.fireToast("callbackWithServer()");
		MaterialToast.fireToast("callbackWithServer(): '" + Window.Location.getHostName() + "'");
		String redirect = "http://blurock-chemconnect.appspot.com/oauth2callback";
		if(Window.Location.getHostName().compareTo("localhost") == 0) {
			redirect = "http://localhost:8080/oauth2callback";
		}
		MaterialToast.fireToast("callbackWithServer(): " + redirect);
		return redirect;
	}

	@UiHandler("home")
	public void onHome(ClickEvent event) {
		subtitle.setText("ChemConnect: The Intelligent Repository");
		goTo(new FirstSiteLandingPagePlace("Home"));
	}
	@UiHandler("logout")
	public void onLogout(ClickEvent event) {
		MaterialToast.fireToast("Logout");
		subtitle.setText("");
		setLoginVisibility(true);
		LoginServiceAsync async = LoginService.Util.getInstance();
		SimpleLoginCallback callback = new SimpleLoginCallback();
		async.loginGuestServer(callback);		
	}
	
	@UiHandler("catalog")
	public void onCatalogClick(ClickEvent event) {
		subtitle.setText("Manage Catalog Structure");
		goTo(new ManageCatalogHierarchyPlace("Manage Catalog Structure"));
	}
	
	@UiHandler("upload")
	public void onUploadClick(ClickEvent event) {
		subtitle.setText("File staging");
		goTo(new UploadFileToBlobStoragePlace("File staging"));
	}
	@UiHandler("isolate")
	public void onIsolateClick(ClickEvent event) {
		subtitle.setText("Isolate data block");
		goTo(new IsolateMatrixBlockPlace("Isolate data block"));
	}
	@UiHandler("specification")
	public void onSpecificationClick(ClickEvent event) {
		subtitle.setText("File staging");
		goTo(new ChemConnectObservationPlace("File staging"));
	}
	@UiHandler("protocol")
	public void onProtocolClick(ClickEvent event) {
		subtitle.setText("Protocol Specification");
		goTo(new ProtocolDefinitionPlace("Protocol Specification"));
	}
	@UiHandler("dataSet")
	public void onDataSetClick(ClickEvent event) {
		subtitle.setText("Observation Specification");
		goTo(new ChemConnectObservationPlace("Observation Specification"));
	}
	@UiHandler("devices")
	public void onDevicesClick(ClickEvent event) {
		subtitle.setText("Manage Device Info");
		goTo(new DeviceWithSubystemsDefinitionPlace("Manage Device Info"));
	}
	@UiHandler("people")
	public void onPeopleClick(ClickEvent event) {
		subtitle.setText("Manage Contact Info");
		goTo(new DatabasePersonDefinitionPlace("Manage Contact Info"));
	}
	@UiHandler("organizations")
	public void onOrganizationsClick(ClickEvent event) {
		subtitle.setText("Manage Organizations");
		goTo(new OrganizationDefinitionPlace("Manage Organizations"));
	}
	@UiHandler("tutorialreadfile")
	public void onTutorialReadFileClick(ClickEvent event) {
		subtitle.setText("Tutorial: Reading and Interpreting data files");
		goTo(new TutorialExamplePlace("Tutorial: Reading and Interpreting data files"));
	}
	
	@UiHandler("mission")
	public void onMissionClick(ClickEvent event) {
		subtitle.setText("Mission Statement");
		goTo(new MissionStatementPlace("Mission Statement"));
	}
	@UiHandler("about")
	public void onAboutClick(ClickEvent event) {
		subtitle.setText("About ChemConnect");
		goTo(new AboutSummaryPlace("About ChemConnect"));
	}

	public SimplePanel getContentPanel() {
		return contentPanel;
	}

	private void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}
}
