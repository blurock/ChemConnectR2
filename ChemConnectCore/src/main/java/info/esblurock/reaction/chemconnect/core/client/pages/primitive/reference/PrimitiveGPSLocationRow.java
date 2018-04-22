package info.esblurock.reaction.chemconnect.core.client.pages.primitive.reference;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialTextBox;
import info.esblurock.reaction.chemconnect.core.data.contact.GPSLocation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveInterpretedInformation;

public class PrimitiveGPSLocationRow extends Composite {

	private static PrimitiveGPSLocationRowUiBinder uiBinder = GWT.create(PrimitiveGPSLocationRowUiBinder.class);

	interface PrimitiveGPSLocationRowUiBinder extends UiBinder<Widget, PrimitiveGPSLocationRow> {
	}

	public PrimitiveGPSLocationRow() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialButton getCoordinates;
	@UiField
	MaterialTextBox gpslatitude;
	@UiField
	MaterialTextBox gpslongitude;
	
	String identifier;
	GPSLocation location;
	
	public PrimitiveGPSLocationRow(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		identifier = null;
	}

	public PrimitiveGPSLocationRow(PrimitiveDataStructureInformation primitiveinfo) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		identifier = primitiveinfo.getIdentifier();
		Window.alert("PrimitiveGPSLocationRow: " + primitiveinfo.toString());
		PrimitiveInterpretedInformation info = (PrimitiveInterpretedInformation) primitiveinfo;
		fill(info);
	}

	private void init() {
		
		
	}

	public String getIdentifier() {
		return identifier;
	}

	public void fill(PrimitiveInterpretedInformation info) {
		location = (GPSLocation) info.getObj();
		Window.alert("PrimitiveGPSLocationRow: fill");
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


}
