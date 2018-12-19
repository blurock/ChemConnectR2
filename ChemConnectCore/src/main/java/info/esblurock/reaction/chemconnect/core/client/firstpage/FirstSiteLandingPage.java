package info.esblurock.reaction.chemconnect.core.client.firstpage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.ui.view.FirstSiteLandingPageView;

public class FirstSiteLandingPage extends Composite implements FirstSiteLandingPageView {

	private static FirstSiteLandingPageUiBinder uiBinder = GWT.create(FirstSiteLandingPageUiBinder.class);

	interface FirstSiteLandingPageUiBinder extends UiBinder<Widget, FirstSiteLandingPage> {
	}

	Presenter listener;

	
	public FirstSiteLandingPage() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	@UiField
	MaterialLink loginButton;
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

	@UiHandler("loginButton")
	void onClick(ClickEvent e) {
		String hostname = GWT.getHostPageBaseURL();
		String service = "login";
		String callname = hostname + service;
		Window.alert("onClick\n" + callname);
		//JSONObject jsonObject = new JSONObject();

		/*
		String email = "";
		String password = "";
		jsonObject.put("email", new JSONString(email));
		jsonObject.put("password", new JSONString(password));
		System.out.println("Password at Presenter:"
		    + jsonObject.get("password"));
		    */
		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,callname);
		builder.setHeader("Content-Type", "application/json");
		try {
			builder.sendRequest("", new RequestCallback() {
				
				@Override
				public void onResponseReceived(Request arg0, Response response) {
					/*
					Window.alert("onResponseReceived: \n" + response.getText());
					Window.alert("onResponseReceived: \n" + response.getStatusText());
					Window.alert("onResponseReceived: \n" + response.getHeadersAsString());
					*/
				}
				
				@Override
				public void onError(Request arg0, Throwable error) {
					Window.alert("onError: \n" + error.getMessage());
				}
			});
			
		} catch(Exception ex) {
			Window.alert("Exception: \n" + ex.toString());
		}
		
		Window.alert("Hello!");
	}

	@Override
	public void setName(String helloName) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

}
