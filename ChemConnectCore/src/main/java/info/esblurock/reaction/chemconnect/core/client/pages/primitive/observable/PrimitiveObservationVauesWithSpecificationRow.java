package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable;

import java.util.ArrayList;

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

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.transfer.ObservationsAndSpecificationsInformation;
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
	MaterialTextBox sourceline;
	@UiField
	MaterialTextBox readfile;
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
		sourceline.setLabel("Source Line");
		sourceline.setText("http://");
		readinput.setText("Read File");
		textarea.setLabel("Source Input Area");
		textarea.setText("xxxx,yyyy,zzz .....\naaa,bbb,ccc\n...");
		blocktip.setText("Form of Input");
		blockform.setText("MatrixOfValues");
		visible = true;
		toggleHideElements();

		SetOfObservationsRow obsrow = new SetOfObservationsRow();
		specificationpanel.add(obsrow);
	}
	
	public void fill(SetOfObservationsInformation obsspec) {
		chosenParameter = null;
		identifier = obsspec.getIdentifier();
		setFullIdentifier();
		String classtype = obsspec.getClass().getCanonicalName();
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
	@UiHandler("readinput")
	void onClickReadInput(ClickEvent e) {
		Window.alert("readinput");
	}
	
	@UiHandler("valuetype")
	void onClickValueType(ClickEvent e) {
		chooseConceptHieararchy();
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

}
