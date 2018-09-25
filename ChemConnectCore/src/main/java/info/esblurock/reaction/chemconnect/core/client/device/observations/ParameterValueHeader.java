package info.esblurock.reaction.chemconnect.core.client.device.observations;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiHandler;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.TextAlign;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.concepts.SetOfUnitProperties;
import info.esblurock.reaction.chemconnect.core.data.dataset.ParameterValue;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class ParameterValueHeader extends PrimitiveParameterValueRow {
	
	
	boolean valueinput;
	boolean uncertaintyinput;
	ParameterValue parameter;
	DatabaseObjectHierarchy parameterinfo;
	
	public ParameterValueHeader() {
		super();
	}
	public ParameterValueHeader(DatabaseObjectHierarchy paramhier) {
		super(paramhier);
	}

	protected void init() {
		super.init();
		labelcolumn.setGrid("s3");
		valuecolumn.setVisible(true);
		uncertaintycolumn.setVisible(true);
		
		valueTextBox.setTextColor(Color.BLACK);
		valueTextBox.setTextAlign(TextAlign.LEFT);
		valueTextBox.setText("Value");
		uncertaintyTextBox.setTextColor(Color.BLACK);
		uncertaintyTextBox.setText("Uncertainty");
		valuetip.setText("Value");
		uncertaintytip.setText("Uncertainty");
		unitstip.setText("Units");
		uncertaintyclasstip.setText("Uncertainty Type");
		valueinput = false;
		uncertaintyinput = false;
	}
	
	public void fill(DatabaseObjectHierarchy info) {
		parameter  = (ParameterValue) info.getObject();
		this.parameterinfo = info;
		
		DatabaseObjectHierarchy spechier = info.getSubObject(parameter.getParameterSpec());
		super.fill(spechier);
		setFullIdentifier();
		if (parameter.getValueAsString() != null) {
			valueTextBox.setText(TextUtilities.removeNamespace(parameter.getValueAsString()));
		} else {
			valueTextBox.setText("Value");
		}
		if (parameter.getUncertainty() != null) {
			uncertaintyTextBox.setText(parameter.getUncertainty());
		} else {
			uncertaintyTextBox.setText("0.0");
		}
	}
	public void setVisibility(SetOfUnitProperties set) {
		super.setVisibility(set);
		if (set.isClassification()) {
			uncertaintyTextBox.setVisible(false);			
		}
	}
	
	@UiHandler("valueTextBox")
	public void onClickInputValue(ClickEvent event) {
		if (!setOfUnitProperties.isClassification()) {
			InputLineModal line = new InputLineModal("Parameter Value", "type value here: ", this);
			modalpanel.clear();
			modalpanel.add(line);
			line.openModal();
			valueinput = true;
		} else {
			MaterialToast.fireToast("Choose from units");
		}
	}
	
	@UiHandler("uncertaintyTextBox")
	public void onClickUncertainty(ClickEvent event) {
		InputLineModal line = new InputLineModal("Uncertainty Value", "type uncertainty here: ", this);
		modalpanel.clear();
		modalpanel.add(line);
		line.openModal();
		uncertaintyinput = true;
	}

	public boolean updateObject() {
		super.updateObject();
		ParameterValue value = (ParameterValue) info.getObject();
		value.setValueAsString(valueTextBox.getText());
		value.setUncertainty(uncertaintyTextBox.getText());
		
		return false;
	}
	public String getIdentifier() {
		return parameter.getIdentifier();
	}

	public void setIdentifier(DatabaseObject obj) {
		parameter.setIdentifier(obj.getIdentifier());
		identifiertip.setText(parameter.getIdentifier());
	}
	public void setLineContent(String line) {
		if (valueinput) {
			valueinput = false;
			valueTextBox.setText(line);
		} else if (uncertaintyinput) {
			uncertaintyinput = false;
			uncertaintyTextBox.setText(line);
		} else if (unitchoice) {
			if (setOfUnitProperties.isClassification()) {
				if(line.compareTo("Other") == 0) {
					InputLineModal linemodal = new InputLineModal("Input Alternative Classification", "type value here: ", this);
					modalpanel.clear();
					modalpanel.add(linemodal);
					linemodal.openModal();
					otherchoice = true;
				} else {
				valueTextBox.setText(TextUtilities.removeNamespace(line));
				chosenUnit = line;
				}
			}
		} else if(otherchoice) {
			otherchoice = false;
			valueTextBox.setText(line);
			chosenUnit = line;
		};		
	}
}
