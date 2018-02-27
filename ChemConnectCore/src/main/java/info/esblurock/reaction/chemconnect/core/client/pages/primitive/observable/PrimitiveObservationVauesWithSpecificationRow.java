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

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.blobstorage.UploadPhoto;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterSpecificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.SetOfObservationsInformation;

public class PrimitiveObservationVauesWithSpecificationRow extends Composite implements  ChooseFromConceptHeirarchy{

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
	MaterialLink readhttp;
	@UiField
	MaterialTextBox httpsourceline;
	@UiField
	MaterialTextArea textarea;
	@UiField
	MaterialSwitch toggleinfo;
	@UiField
	MaterialRow inputrow;
	@UiField
	MaterialRow sourcerow;
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
	/*
	@UiField 
	MaterialFileUploader cardUploader;
	@UiField MaterialImage imgPreview;
	@UiField MaterialProgress progress;
	@UiField MaterialLabel lblName, lblSize;
	*/
	boolean visible;
	String identifier;
	String chosenParameter;

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
		
		identifier = "id";
		chosenParameter = null;
		identifiertip.setText(identifier);
		topconcept.setText("Value Concept");
		valuetype.setText("Device");
		httpsourceline.setLabel("HTTP Source");
		httpsourceline.setText("http://cms.heatfluxburner.org/wp-content/uploads/Bosschaart_CH4_Air_1atm_Tu_295K_thesis.xls");
		readinput.setText("Read File");
		textarea.setLabel("Source Input Area");
		textarea.setText("xxxx,yyyy,zzz .....\naaa,bbb,ccc\n...");
		blocktip.setText("Form of Input");
		blockform.setText("MatrixOfValues");
		readhttp.setText("Read URL");
		visible = true;
		toggleHideElements();

		SetOfObservationsRow obsrow = new SetOfObservationsRow();
		specificationpanel.add(obsrow);
		
		UploadPhoto photo = new UploadPhoto(identifier);
		uploadpanel.add(photo);
		/*
		cardUploader.addSuccessHandler(new SuccessEvent.SuccessHandler<UploadFile>() {
			@Override
			public void onSuccess(SuccessEvent<UploadFile> event) {
				lblName.setText(event.getTarget().getName());
				lblSize.setText(event.getTarget().getType());
				imgPreview.setUrl(GWT.getHostPageBaseURL() + "uploadedFiles/" + event.getTarget().getName());
				}
			});
			*/
	}
	
	public void fill(SetOfObservationsInformation obsspec) {
		chosenParameter = null;
		identifier = obsspec.getIdentifier();
		setFullIdentifier();
			specificationpanel.clear();
		SetOfObservationsRow obsrow = new SetOfObservationsRow(obsspec.getIdentifier(), 
				obsspec.getTopConcept(),
				obsspec.getValueType());
		specificationpanel.add(obsrow);
		for(PrimitiveParameterSpecificationInformation info: obsspec.getDimensions()) {
			String subid = obsspec.getIdentifier() + "-" + TextUtilities.removeNamespace(info.getPropertyType());
			info.setIdentifier(subid);
			obsrow.addParameter(info);
		}
		for(PrimitiveParameterSpecificationInformation info: obsspec.getMeasures()) {
			String subid = obsspec.getIdentifier() + "-" + TextUtilities.removeNamespace(info.getPropertyType());
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
		sourcerow.setVisible(visible);
		inputrow.setVisible(visible);
		specificationpanel.setVisible(visible);;
	}
	
	@UiHandler("toggleinfo")
	void onClickToggle(ClickEvent e) {
		toggleHideElements();
	}
	@UiHandler("readhttp")
	void onClickReadInput(ClickEvent e) {
		String sourceType = SpreadSheetInputInformation.URL;
		String source = httpsourceline.getText();
		String type = SpreadSheetInputInformation.CVS;
		if(source.endsWith("xls")) {
			type = SpreadSheetInputInformation.XLS;
		}
		SpreadSheetInputInformation input = new SpreadSheetInputInformation(type, sourceType, source);
		readInSpreadSheet(input);
	}
	
	@UiHandler("valuetype")
	void onClickValueType(ClickEvent e) {
		chooseConceptHieararchy();
	}
	
	private void readInSpreadSheet(SpreadSheetInputInformation input) {
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		ReadInSpreadSheetCallback callback = new ReadInSpreadSheetCallback(this);
		async.interpretSpreadSheet(input,callback);
		
	}
	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
		identifiertip.setText(this.identifier);
	}
	public String setFullIdentifier() {
		String id = identifier;
		if(chosenParameter != null) {
			id = identifier + "-" + TextUtilities.removeNamespace(chosenParameter);
		}
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

	public void setUpResultMatrix(ObservationsFromSpreadSheet results) {
		ArrayList<SpreadSheetBlockInformation> blocks = results.getBlocks();
		for(SpreadSheetBlockInformation block : blocks) {
			Window.alert(block.toString());
			if(block.isJustTitle()) {
				SpreadSheetBlockTitle titleblock = new SpreadSheetBlockTitle(block,this);
				tablepanel.add(titleblock);
			} else {
				SpreadSheetBlockMatrix matrixblock = new SpreadSheetBlockMatrix(block, this);
				tablepanel.add(matrixblock);
			}
		}
		
	}

}
