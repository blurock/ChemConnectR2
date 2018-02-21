package info.esblurock.reaction.chemconnect.core.client.device;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.client.modal.SetLineContentInterface;

public class MethodologyDefinition extends Composite implements ChooseFromConceptHeirarchy, SetLineContentInterface {

	private static MethodologyDefinitionUiBinder uiBinder = GWT.create(MethodologyDefinitionUiBinder.class);

	interface MethodologyDefinitionUiBinder extends UiBinder<Widget, MethodologyDefinition> {
	}

	String enterkeyS;
	String keynameS;
	@UiField
	MaterialLink parameter;
	@UiField
	MaterialTextBox topname;
	@UiField
	MaterialCollapsible contentcollapsible;
	@UiField
	MaterialLink choose;
	@UiField
	MaterialPanel modalpanel;

	InputLineModal line; 

	public MethodologyDefinition() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public MethodologyDefinition(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	
	private void init() {
		parameter.setText("Methodology");
		choose.setText("Choose Methodology");
		topname.setLabel("Catagory Name");
		topname.setText("");
		String name = Cookies.getCookie("user");
		topname.setPlaceholder(name);
		enterkeyS = "Enter Methodology Catagory";
		keynameS = "Catagory";
	}
	
	@UiHandler("choose")
	public void chooseConcept(ClickEvent event) {
		if(topname.getText().length() == 0 ) {
			line = new InputLineModal(enterkeyS,keynameS,this);
			modalpanel.add(line);
			line.openModal();
		} else {
			chooseConceptHieararchy();
		}
	}

	private void chooseConceptHieararchy() {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("dataset:DataTypeMethodology");
		ChooseFromConceptHierarchies choosedevice = new ChooseFromConceptHierarchies(choices,this);
		modalpanel.add(choosedevice);
		choosedevice.open();		
	}

	@Override
	public void setLineContent(String line) {
		topname.setText(line);
		chooseConceptHieararchy();
	}

	@Override
	public void conceptChosen(String topconcept, String concept) {
		Window.alert("Methodology: " + concept);
	}


}
