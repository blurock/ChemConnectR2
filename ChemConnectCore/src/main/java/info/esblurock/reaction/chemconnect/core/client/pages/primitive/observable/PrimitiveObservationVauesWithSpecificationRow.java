package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet.ReadInSpreadSheetInformation;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet.SpreadSheetInformationExtractionInterface;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockIsolation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetTitleRowCorrespondence;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterSpecificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.SetOfObservationsInformation;

public class PrimitiveObservationVauesWithSpecificationRow extends Composite 
	implements  ChooseFromConceptHeirarchy,SpreadSheetInformationExtractionInterface {
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
	MaterialLink readinput;
	@UiField
	MaterialSwitch toggleinfo;
	@UiField
	MaterialTooltip toggletip;
	@UiField
	MaterialPanel tablepanel;
	@UiField
	MaterialRow matrixrow;
	@UiField
	MaterialRow interpretationrow;
	
	boolean visible;
	DatabaseObject obj;
	//UploadFileToGCS photo;
	SetOfObservationsRow obsrow;
	ArrayList<String> parameterNames;
	
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
		visible = true;
		toggleHideElements();

		obsrow = new SetOfObservationsRow();
		specificationpanel.add(obsrow);
		
		//photo = new UploadFileToGCS(modalpanel);
		//uploadpanel.add(photo);
	}
	
	public void fill(SetOfObservationsInformation obsspec) {
		parameterNames = new ArrayList<String>(); 
		obj = new DatabaseObject(obsspec);
		setFullIdentifier();
		specificationpanel.clear();
		obsrow = new SetOfObservationsRow(obsspec, 
				obsspec.getTopConcept(),
				obsspec.getValueType());
		specificationpanel.add(obsrow);
		
		for(PrimitiveParameterSpecificationInformation info: obsspec.getDimensions()) {
			String property = TextUtilities.removeNamespace(info.getPropertyType());
			parameterNames.add(property);
			String subid = obsspec.getIdentifier() + "-" + property;
			info.setIdentifier(subid);
			obsrow.addParameter(info);
		}
		for(PrimitiveParameterSpecificationInformation info: obsspec.getMeasures()) {
			String property = TextUtilities.removeNamespace(info.getPropertyType());
			parameterNames.add(property);
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
		specificationpanel.setVisible(visible);
	}
	
	@UiHandler("readinput")
	void onClickReadInfo(ClickEvent e) {
		String parent = identifiertip.getText();
		DatabaseObject readobj = new DatabaseObject(obj);
		String id = parent + "-read";
		readobj.setIdentifier(id);
		ReadInSpreadSheetInformation sheet = new ReadInSpreadSheetInformation(
				readobj,parent,
				id, parameterNames, this);
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
		choices.add(MetaDataKeywords.dataTypeDevice);
		choices.add(MetaDataKeywords.dataTypeSubSystem);
		choices.add(MetaDataKeywords.dataTypeComponent);
		ChooseFromConceptHierarchies choosedevice = new ChooseFromConceptHierarchies(choices,this);
		modalpanel.add(choosedevice);
		choosedevice.open();		
	}

	@Override
	public void conceptChosen(String topconcept, String concept, ArrayList<String> path) {
		valuetype.setText(concept);
	}

	@Override
	public void setCorrespondences(ArrayList<SpreadSheetTitleRowCorrespondence> correspondences) {
		for(SpreadSheetTitleRowCorrespondence corr : correspondences) {
			SpreadSheetColumnInterpretationRow row = new SpreadSheetColumnInterpretationRow(corr);
			interpretationrow.add(row);
		}
	}

	@Override
	public void setIsolatedMatrix(ArrayList<ObservationValueRow> matrix) {
		//String title = obj.getIdentifier() + "-read";
		Window.alert("PrimitiveObservationVauesWithSpecificationRow   setIsolatedMatrix: not implemented");
		//SpreadSheetMatrix spread = new SpreadSheetMatrix(title, matrix);
		//matrixrow.add(spread);
	}

	@Override
	public void setMatrixInterpretation(SpreadSheetBlockIsolation interpretation) {
		Window.alert(interpretation.toString());
	}

	

}
