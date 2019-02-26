package info.esblurock.reaction.chemconnect.core.client.firstpage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.Place;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import info.esblurock.reaction.chemconnect.core.client.activity.ClientFactory;
import info.esblurock.reaction.chemconnect.core.client.place.ChemConnectPartnersPlace;
import info.esblurock.reaction.chemconnect.core.client.place.ContactInformationPlace;

public class StandardFooter extends Composite {

	private static StandardFooterUiBinder uiBinder = GWT.create(StandardFooterUiBinder.class);

	interface StandardFooterUiBinder extends UiBinder<Widget, StandardFooter> {
	}


	@UiField
	MaterialButton btnPartners;
	@UiField
	MaterialButton btnContact;
	@UiField
	MaterialButton btnManagement;
	
	ClientFactory clientFactory;

	public StandardFooter(ClientFactory clientFactory) {
		initWidget(uiBinder.createAndBindUi(this));
		this.clientFactory = clientFactory;
	}

	@UiHandler("btnPartners")
	void onClickPartners(ClickEvent e) {
		Window.alert("Partner Search");
		goTo(new ChemConnectPartnersPlace("Partner Search"));
	}
	@UiHandler("btnContact")
	void onClickContact(ClickEvent e) {
		Window.alert("Contact Information");
		goTo(new ContactInformationPlace("Contact Information"));
	}
	@UiHandler("btnManagement")
	void onClickDataManagement(ClickEvent e) {
		Window.open("https://sites.google.com/view/blurock-consulting-ab", "_blank", "");

		//Window.alert("Data Management");
		//goTo(new DataManagementPlace("Data Management"));
	}
	
	private void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
		Window.alert("goTo: " + place.getClass().getSimpleName());
	}
}
