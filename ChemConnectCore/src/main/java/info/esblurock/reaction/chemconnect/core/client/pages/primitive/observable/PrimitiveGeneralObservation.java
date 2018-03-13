package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable;

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

import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;

public class PrimitiveGeneralObservation extends Composite {

	private static PrimitiveGeneralObservationUiBinder uiBinder = GWT.create(PrimitiveGeneralObservationUiBinder.class);

	interface PrimitiveGeneralObservationUiBinder extends UiBinder<Widget, PrimitiveGeneralObservation> {
	}

	@UiField
	MaterialLabel observationtype;
	@UiField
	MaterialLink info;
	@UiField
	MaterialLink clear;
	@UiField
	MaterialPanel observation;
	
	public PrimitiveGeneralObservation() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public PrimitiveGeneralObservation(String observationtype) {
		initWidget(uiBinder.createAndBindUi(this));
		this.observationtype.setText(observationtype);
		init();
	}

	void init() {
		
	}
	
	@UiHandler("info")
	void onClick(ClickEvent e) {
	}
}
