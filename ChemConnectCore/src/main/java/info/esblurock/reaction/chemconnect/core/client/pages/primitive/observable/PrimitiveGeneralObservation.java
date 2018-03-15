package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.CreatePrimitiveStructure;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.SetOfObservationsInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.observations.SetOfObservationsTransfer;

public class PrimitiveGeneralObservation extends Composite {

	private static PrimitiveGeneralObservationUiBinder uiBinder = GWT.create(PrimitiveGeneralObservationUiBinder.class);

	interface PrimitiveGeneralObservationUiBinder extends UiBinder<Widget, PrimitiveGeneralObservation> {
	}

	@UiField
	MaterialTooltip obstooltip;
	@UiField
	MaterialLabel observationtype;
	@UiField
	MaterialLink info;
	@UiField
	MaterialLink clear;
	@UiField
	MaterialPanel observation;
	
	ArrayList<PrimitiveDataStructureBase> set;
	DatabaseObject baseobj;
	String observationname;
	SetOfObservationsTransfer transfer;
	
	public PrimitiveGeneralObservation() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public PrimitiveGeneralObservation(SetOfObservationsTransfer transfer) {
		initWidget(uiBinder.createAndBindUi(this));
		this.transfer = transfer;
		init();
		fill(transfer);
	}

	void init() {
		set = new ArrayList<PrimitiveDataStructureBase>();
		obstooltip.setText("ID");
	}

	public void fill(SetOfObservationsTransfer transfer) {
		observationname = "Observation";
		this.observationtype.setText(observationname);
		baseobj = transfer.getBaseobject();
		SetOfObservationsInformation observations = transfer.getObservations();
		CreatePrimitiveStructure create = CreatePrimitiveStructure.ObservationValuesWithSpecification;
		PrimitiveDataStructureBase base = create.createStructure(observations);
		set.add(base);
		base.setIdentifier(baseobj);
		observationtype.setText("Observation");
		observation.add(base);
	}
	
	@UiHandler("info")
	void onClick(ClickEvent e) {
	}

	public void setIdentifier(DatabaseObject obj) {
		baseobj = new DatabaseObject(obj);
		String id = obj.getIdentifier() + "-" + observationname;
		obstooltip.setText(id);
		baseobj.setIdentifier(id);
		for(PrimitiveDataStructureBase base : set) {
			base.setIdentifier(baseobj);
		}
	}
}
