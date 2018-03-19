package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet.ReadInSpreadSheetInformation;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterSpecificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.SetOfObservationsInformation;

public class PrimitiveObservationVauesWithSpecificationRow extends Composite implements  ChooseFromConceptHeirarchy {
	private static PrimitiveObservationVauesWithSpecificationRowUiBinder uiBinder = GWT
			.create(PrimitiveObservationVauesWithSpecificationRowUiBinder.class);

	interface PrimitiveObservationVauesWithSpecificationRowUiBinder
			extends UiBinder<Widget, PrimitiveObservationVauesWithSpecificationRow> {
	}

	@UiField
	MaterialPanel toppanel;
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialTooltip identifiertip;
	@UiField
	MaterialLink topconcept;
	@UiField
	MaterialLink valuetype;
	@UiField
	MaterialPanel specificationpanel;
	@UiField
	MaterialLink blockform;
	@UiField
	MaterialLink readinput;
	@UiField
	MaterialSwitch toggleinfo;
	@UiField
	MaterialRow inputrow;
	@UiField
	MaterialTooltip toggletip;
	@UiField
	MaterialTooltip blocktip;
	@UiField
	MaterialPanel tablepanel;
	@UiField
	MaterialColumn uploadcolumn;
	@UiField
	MaterialCollapsible uploadpanel;

	boolean visible;
	DatabaseObject obj;
	//UploadFileToGCS photo;
	SetOfObservationsRow obsrow;
	
	public PrimitiveObservationVauesWithSpecificationRow() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public PrimitiveObservationVauesWithSpecificationRow(SetOfObservationsInformation obsspec) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		fill(obsspec);
	}

	public void init() {
		obj = new DatabaseObject();
		identifiertip.setText(obj.getIdentifier());
		topconcept.setText("Value Concept");
		valuetype.setText("Device");
		readinput.setText("Read File");
		blocktip.setText("Form of Input");
		blockform.setText("MatrixOfValues");
		visible = true;
		toggleHideElements();

		obsrow = new SetOfObservationsRow();
		specificationpanel.add(obsrow);
		
		//photo = new UploadFileToGCS(modalpanel);
		//uploadpanel.add(photo);
	}
	
	public void fill(SetOfObservationsInformation obsspec) {
		obj = new DatabaseObject(obsspec);
		setFullIdentifier();
		specificationpanel.clear();
		obsrow = new SetOfObservationsRow(obsspec, 
				obsspec.getTopConcept(),
				obsspec.getValueType());
		specificationpanel.add(obsrow);
		
		ArrayList<String> properties = new ArrayList<String>();
		
		for(PrimitiveParameterSpecificationInformation info: obsspec.getDimensions()) {
			String property = TextUtilities.removeNamespace(info.getPropertyType());
			properties.add(property);
			String subid = obsspec.getIdentifier() + "-" + property;
			info.setIdentifier(subid);
			obsrow.addParameter(info);
		}
		for(PrimitiveParameterSpecificationInformation info: obsspec.getMeasures()) {
			String property = TextUtilities.removeNamespace(info.getPropertyType());
			properties.add(property);
			String subid = obsspec.getIdentifier() + "-" + property;
			info.setIdentifier(subid);
			obsrow.addParameter(info);			
		}
	}
	
	public void toggleHideElements() {
		if(visible) {
			visible = false;
			toggletip.setText("Show");
		} else {
			visible = true;
			toggletip.setText("Hide");
		}
		toggleinfo.setValue(visible);
		inputrow.setVisible(visible);
		specificationpanel.setVisible(visible);;
	}
	
	@UiHandler("readinput")
	void onClickReadInfo(ClickEvent e) {
		ReadInSpreadSheetInformation sheet = new ReadInSpreadSheetInformation(identifiertip.getText(), modalpanel);
		sheet.open();
		modalpanel.add(sheet);
	}
	
	@UiHandler("toggleinfo")
	void onClickToggle(ClickEvent e) {
		toggleHideElements();
	}
	
	@UiHandler("valuetype")
	void onClickValueType(ClickEvent e) {
		chooseConceptHieararchy();
	}
	

	public String getIdentifier() {
		return obj.getIdentifier();
	}
	public void setIdentifier(DatabaseObject obj) {
		this.obj = obj;
		setFullIdentifier();
		obsrow.setIdentifier(this.obj);
	}
	public String setFullIdentifier() {
		String id = obj.getIdentifier();
		identifiertip.setText(id);
		return id;
	}
	private void chooseConceptHieararchy() {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("dataset:DataTypeDevice");
		choices.add("dataset:DataTypeSubSystem");
		choices.add("dataset:DataTypeComponent");
		ChooseFromConceptHierarchies choosedevice = new ChooseFromConceptHierarchies(choices,this);
		modalpanel.add(choosedevice);
		choosedevice.open();		
	}

	@Override
	public void conceptChosen(String topconcept, String concept) {
		valuetype.setText(concept);
	}


}
