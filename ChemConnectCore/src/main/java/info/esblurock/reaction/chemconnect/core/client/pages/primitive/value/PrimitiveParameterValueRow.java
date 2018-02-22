package info.esblurock.reaction.chemconnect.core.client.pages.primitive.value;

import java.util.ArrayList;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.concepts.SetOfUnitProperties;
import info.esblurock.reaction.chemconnect.core.data.concepts.UnitProperties;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterValueInformation;

public class PrimitiveParameterValueRow extends Composite implements HasText,ChooseFromConceptHeirarchy {

	private static PrimitiveParameterValueRowUiBinder uiBinder = GWT.create(PrimitiveParameterValueRowUiBinder.class);

	interface PrimitiveParameterValueRowUiBinder extends UiBinder<Widget, PrimitiveParameterValueRow> {
	}

	String unitsConcept = "qudt:Unit";
	String purposeConcept = "dataset:ChemConnectPurpose";
	String conceptConcept = "dataset:ChemConnectConcept";
	String parameterNames = "dataset:ChemConnectParameter";
	String uncertaintyConcept = "dataset:ChemConnectUncertaintyTypes";
	@UiField
	MaterialLink parameterLabel;
	@UiField
	MaterialTextBox valueTextBox;
	@UiField
	MaterialTextBox uncertaintyTextBox;
	@UiField
	MaterialTextBox unitsTextBox;
	@UiField
	MaterialComboBox<String> parameterUnits;
	@UiField
	MaterialLink purpose;
	@UiField
	MaterialLink concept;
	@UiField
	MaterialLink unitclass;
	@UiField
	MaterialLink uncertaintyclass;
	@UiField
	MaterialSwitch toggleinfo;
	@UiField
	MaterialLink clear;
	@UiField
	MaterialRow extrainfo;
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialPanel toppanel;
	@UiField
	MaterialTooltip identifiertip;
	
	String identifier; 
	String propertyType;
	boolean rowVisible;
	String chosenConcept;
	String chosenUnit;
	String chosenUnitClass;
	String chosenPurpose;
	String chosenParameter;
	String chosenUncertainty;
	
	SetOfUnitProperties setOfUnitProperties;
	UnitProperties unitproperties;
	
	public PrimitiveParameterValueRow() {
		initWidget(uiBinder.createAndBindUi(this));
		PrimitiveParameterValueInformation info = new PrimitiveParameterValueInformation();
		init();
		fill(info);
	}

	public PrimitiveParameterValueRow(PrimitiveParameterValueInformation paraminfo) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		fill(paraminfo);
	}
	
	private void init() {
		clear.setIconColor(Color.BLACK);
		valueTextBox.setTextColor(Color.BLACK);
		uncertaintyTextBox.setTextColor(Color.BLACK);
		valueTextBox.setLabel("Value");
		uncertaintyTextBox.setLabel("Uncertainty");
	}
	
	public void fill(PrimitiveDataStructureInformation info) {
		PrimitiveParameterValueInformation paraminfo = (PrimitiveParameterValueInformation) info;
		this.identifier = paraminfo.getIdentifier();
		setFullIdentifier();
		this.propertyType = paraminfo.getPropertyType();
		if(paraminfo.getPropertyType() != null) {
			chosenParameter = paraminfo.getPropertyType();
			parameterLabel.setText(TextUtilities.removeNamespace(chosenParameter));
		} else {
			parameterLabel.setText("Choose Label");
		}
		if(paraminfo.getValue() != null) {
			valueTextBox.setText(paraminfo.getValue());
		} else {
			valueTextBox.setPlaceholder("Value");
		}
		if(paraminfo.getUncertaintyValue() != null) {
			uncertaintyTextBox.setText(paraminfo.getUncertaintyValue());
		} else {
			uncertaintyTextBox.setPlaceholder("0.0");
		}
		if(paraminfo.getUnit() != null) {
			chosenUnit = paraminfo.getUnit();
			parameterUnits.setVisible(false);
			unitsTextBox.setVisible(true);
			unitsTextBox.setText(TextUtilities.removeNamespace(chosenUnit));
		} else {
			parameterUnits.setVisible(false);
			unitsTextBox.setVisible(true);
			unitsTextBox.setPlaceholder("Units");			
		}
		if(paraminfo.getPurpose() != null) {
			chosenPurpose = paraminfo.getPurpose();
			purpose.setText(TextUtilities.removeNamespace(chosenPurpose));
		} else {
			purpose.setText("Choose Purpose");
		}
		if(paraminfo.getConcept() != null) {
			chosenConcept = paraminfo.getConcept();
			concept.setText(TextUtilities.removeNamespace(chosenConcept));
		} else {
			concept.setText("Choose Concept");
		}
		if(paraminfo.getUnitclass() != null) {
			chosenUnitClass = paraminfo.getUnitclass();
			setUnits(chosenUnitClass);
			unitclass.setText(TextUtilities.removeNamespace(chosenUnitClass));
		} else {
			unitclass.setText("Choose Units");
		}
		if(paraminfo.getUncertaintyType() != null) {
			uncertaintyclass.setText(paraminfo.getUncertaintyType());
		} else {
			uncertaintyclass.setText("Uncertainty");
		}
		
		extrainfo.setVisible(false);
		rowVisible = false;
	}
	
	@UiHandler("concept")
	public void onClickConcept(ClickEvent event) {
		findConcept(purposeConcept);
	}
	@UiHandler("purpose")
	public void onClickPurpose(ClickEvent event) {
		findConcept(purposeConcept);		
	}
	@UiHandler("unitclass")
	public void onClickUnitclass(ClickEvent event) {
		findConcept(unitsConcept);		
	}
	
	
	
	private void findConcept(String concept) {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(concept);
		ChooseFromConceptHierarchies choosedevice = new ChooseFromConceptHierarchies(choices,this);
		modalpanel.add(choosedevice);
		choosedevice.open();
	}
	
	@UiHandler("uncertaintyclass")
	public void onClickUncertaintyclass(ClickEvent event) {
		findConcept(uncertaintyConcept);	
	}
	
	@UiHandler("clear")
	public void onClickClear(ClickEvent event) {
		MaterialToast.fireToast("Clear");
	}
	
	@UiHandler("toggleinfo") 
	void onValueChange(ValueChangeEvent<Boolean> e) { 
		if(rowVisible) {
			rowVisible = false;
		} else {
			rowVisible = true;
		}
		extrainfo.setVisible(rowVisible);
		}
	@UiHandler("parameterLabel")
	public void onClickLabel(ClickEvent event) {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(parameterNames);
		ChooseFromConceptHierarchies choosedevice = new ChooseFromConceptHierarchies(choices,this);
		modalpanel.add(choosedevice);
		choosedevice.open();
		
	}
	
	
	public void setText(String text) {
		parameterLabel.setText(text);
	}

	public String getText() {
		return parameterLabel.getText();
	}
	
	public void setUnits(String unitname) {
		this.chosenUnitClass = unitname;		
		unitclass.setText(TextUtilities.removeNamespace(unitname));
		
		PrimitiveValueUnitCallback callback = new PrimitiveValueUnitCallback(this);
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		async.unitProperties(unitname,callback);
	}
	
	public void setUpUnitList(SetOfUnitProperties set) {
		Set<String> names = set.getAbbreviations();
		this.setOfUnitProperties = set;
		parameterUnits.setVisible(true);
		unitsTextBox.setVisible(false);
		parameterUnits.clear();
		
		String abbrev = null;
		if(chosenUnit != null) {
			UnitProperties prop = set.getUnitProperty(TextUtilities.removeNamespace(chosenUnit));
			if(prop != null) {
				abbrev = prop.getAbbreviation();
			}
		}
		int index = 0;
		int selected = 0;
		for(String name : names) {
			if(abbrev != null) {
				if(abbrev.compareTo(name) == 0) {
					selected = index;
				}
			}
			parameterUnits.addItem(name);
			index++;
		}
		parameterUnits.addItem("Other");
		parameterUnits.setSelectedIndex(selected);
	}
	
	@UiHandler("parameterUnits")
	public void onClickCombo(ClickEvent event) {
		String name = parameterUnits.getSelectedValue().toString();
		MaterialToast.fireToast("Selected: " + name);
		unitproperties = setOfUnitProperties.getUnitPropertyFromAbbreviation(name);
		chosenUnit = unitproperties.getUnitName();
	}
	
	@Override
	public void conceptChosen(String topconcept, String chosenConcept) {
		if(topconcept.compareTo(unitsConcept) == 0) {
			setUnits(chosenConcept);
			this.chosenUnit = chosenConcept;
		} else if(topconcept.compareTo(purposeConcept) == 0) {
			purpose.setText(TextUtilities.removeNamespace(chosenConcept));
			this.chosenPurpose = chosenConcept;
		} else if(topconcept.compareTo(conceptConcept) == 0) {
			this.chosenConcept = chosenConcept;
			concept.setText(TextUtilities.removeNamespace(chosenConcept));
		} else if(topconcept.compareTo(parameterNames) == 0) {
			this.chosenParameter = chosenConcept;
			parameterLabel.setText(TextUtilities.removeNamespace(chosenConcept));
		} else if(topconcept.compareTo(uncertaintyConcept) == 0) {
			this.chosenUncertainty = chosenConcept;
			uncertaintyclass.setText(TextUtilities.removeNamespace(chosenConcept));
		}
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
}
