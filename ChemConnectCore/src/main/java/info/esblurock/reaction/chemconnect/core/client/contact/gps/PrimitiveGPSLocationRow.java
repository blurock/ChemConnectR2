package info.esblurock.reaction.chemconnect.core.client.contact.gps;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import info.esblurock.reaction.chemconnect.core.client.cards.CardModal;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.GPSLocation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveInterpretedInformation;

public class PrimitiveGPSLocationRow extends Composite implements InsertGPSCoordinatesInterface {

	private static PrimitiveGPSLocationRowUiBinder uiBinder = GWT.create(PrimitiveGPSLocationRowUiBinder.class);

	interface PrimitiveGPSLocationRowUiBinder extends UiBinder<Widget, PrimitiveGPSLocationRow> {
	}

	public PrimitiveGPSLocationRow() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialPanel panel;
	@UiField
	MaterialButton getCoordinates;
	@UiField
	MaterialTextBox gpslatitude;
	@UiField
	MaterialTextBox gpslongitude;
	
	String identifier;
	GPSLocation location;
	PrimitiveInterpretedInformation info;
	
	public PrimitiveGPSLocationRow(DatabaseObject object) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		identifier = object.getIdentifier();
		fill(object);
	}
	
	private void init() {
	}

	@UiHandler("getCoordinates")
	public void onGetCoordinates(ClickEvent event) {
		DatabaseObject obj = new DatabaseObject(info);
		AskGPSLocationModal modal = new AskGPSLocationModal(obj,this);
		CardModal cardmodal = new CardModal();
		cardmodal.setContent(modal, true);
		panel.add(cardmodal);
		cardmodal.open();
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public void fill(DatabaseObject object) {
		location = (GPSLocation) object;
		if(location != null) {
			if(location.getGPSLatitude() != null) {
				gpslatitude.setText(location.getGPSLatitude());
			}
			if(location.getGPSLongitude() != null) {
				gpslongitude.setText(location.getGPSLongitude());
			}
		} else {
			Window.alert("No location specified");
		}
	}
	
	public void update() {
		location.setGPSLatitude(gpslatitude.getText());
		location.setGPSLongitude(gpslongitude.getText());
	}

	public String getLatitude() {
		return gpslatitude.getText();
	}
	public String getLongitude() {
		return gpslongitude.getText();
	}
	
	@Override
	public void insertGPSCoordinates(GPSLocation location) {
		gpslatitude.setText(location.getGPSLatitude());
		gpslongitude.setText(location.getGPSLongitude());
	}


}
