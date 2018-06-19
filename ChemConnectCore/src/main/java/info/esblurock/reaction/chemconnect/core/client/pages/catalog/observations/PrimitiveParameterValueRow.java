package info.esblurock.reaction.chemconnect.core.client.pages.catalog.observations;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
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
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.concepts.SetOfUnitProperties;
import info.esblurock.reaction.chemconnect.core.data.concepts.UnitProperties;
import info.esblurock.reaction.chemconnect.core.data.dataset.ParameterSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.ParameterValue;
import info.esblurock.reaction.chemconnect.core.data.dataset.PurposeConceptPair;
import info.esblurock.reaction.chemconnect.core.data.dataset.ValueUnits;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class PrimitiveParameterValueRow extends Composite
		implements ChooseFromConceptHeirarchy, PrimitiveValueUnitCallInterface {

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
	@UiField
	MaterialTooltip typetip;

	DatabaseObject obj;
	String propertyType;
	boolean rowVisible;
	String chosenConcept;
	String chosenUnit;
	String chosenUnitClass;
	String chosenPurpose;
	String chosenParameter;
	String chosenUncertainty;
	DatabaseObjectHierarchy info;

	SetOfUnitProperties setOfUnitProperties;
	UnitProperties unitproperties;

	public PrimitiveParameterValueRow() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public PrimitiveParameterValueRow(DatabaseObjectHierarchy paramhier) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		fill(paramhier);
	}

	private void init() {
		clear.setIconColor(Color.BLACK);
		valueTextBox.setTextColor(Color.BLACK);
		uncertaintyTextBox.setTextColor(Color.BLACK);
		valueTextBox.setLabel("Value");
		uncertaintyTextBox.setLabel("Uncertainty");
	}

	public void fill(DatabaseObjectHierarchy info) {
		this.info = info;
		ParameterValue parameter = (ParameterValue) info.getObject();
		typetip.setText(parameter.getClass().getSimpleName());
		obj = info.getObject();
		setFullIdentifier();
		this.propertyType = parameter.getParameterLabel();
		if (this.propertyType != null) {
			chosenParameter = this.propertyType;
			parameterLabel.setText(TextUtilities.removeNamespace(chosenParameter));
		} else {
			parameterLabel.setText("Choose Label");
		}
		if (parameter.getValueAsString() != null) {
			valueTextBox.setText(parameter.getValueAsString());
		} else {
			valueTextBox.setPlaceholder("Value");
		}
		if (parameter.getUncertainty() != null) {
			uncertaintyTextBox.setText(parameter.getUncertainty());
		} else {
			uncertaintyTextBox.setPlaceholder("0.0");
		}
		DatabaseObjectHierarchy spechier = info.getSubObject(parameter.getParameterSpec());
		ParameterSpecification spec = (ParameterSpecification) spechier.getObject();
		if (spec.getDataPointUncertainty() != null) {
			uncertaintyclass.setText(spec.getDataPointUncertainty());
		} else {
			uncertaintyclass.setText("Uncertainty");
		}

		DatabaseObjectHierarchy unithier = spechier.getSubObject(spec.getUnits());
		ValueUnits units = (ValueUnits) unithier.getObject();
		if (units.getUnitsOfValue() != null) {
			chosenUnit = units.getUnitsOfValue();
			parameterUnits.setVisible(false);
			unitsTextBox.setVisible(true);
			unitsTextBox.setText(TextUtilities.removeNamespace(chosenUnit));
		} else {
			parameterUnits.setVisible(false);
			unitsTextBox.setVisible(true);
			unitsTextBox.setPlaceholder("Units");
		}
		if (units.getUnitClass() != null) {
			chosenUnitClass = units.getUnitClass();
			setUnits(chosenUnitClass);
			unitclass.setText(TextUtilities.removeNamespace(chosenUnitClass));
		} else {
			unitclass.setText("Choose Units");
		}

		DatabaseObjectHierarchy purposehier = spechier.getSubObject(spec.getPurposeandconcept());
		PurposeConceptPair pair = (PurposeConceptPair) purposehier.getObject();

		if (pair.getPurpose() != null) {
			chosenPurpose = pair.getPurpose();
			purpose.setText(TextUtilities.removeNamespace(chosenPurpose));
		} else {
			purpose.setText("Choose Purpose");
		}
		if (pair.getConcept() != null) {
			chosenConcept = pair.getConcept();
			concept.setText(TextUtilities.removeNamespace(chosenConcept));
		} else {
			concept.setText("Choose Concept");
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
		ChooseFromConceptHierarchies choosedevice = new ChooseFromConceptHierarchies(choices, this);
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
		if (rowVisible) {
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
		ChooseFromConceptHierarchies choosedevice = new ChooseFromConceptHierarchies(choices, this);
		modalpanel.add(choosedevice);
		choosedevice.open();

	}

	public void setUnits(String unitname) {
		this.chosenUnitClass = unitname;
		unitclass.setText(TextUtilities.removeNamespace(unitname));

		PrimitiveValueUnitCallback callback = new PrimitiveValueUnitCallback(this);
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		async.unitProperties(unitname, callback);
	}

	public void setVisibility(SetOfUnitProperties set) {
		if (set.isKeyword()) {
			parameterUnits.setVisible(false);
			unitsTextBox.setVisible(false);
		} else {
			parameterUnits.setVisible(true);
			unitsTextBox.setVisible(false);
		}		
	}
	
	public void setUpUnitList(SetOfUnitProperties set) {
		this.setOfUnitProperties = set;
		setVisibility(set);
		SetUpParameterValue.setup(parameterUnits, chosenUnit, set);
		/*
		if (!set.isKeyword()) {
			parameterUnits.clear();
			String abbrev = null;
			UnitProperties prop;
			if (chosenUnit != null) {
				String compareunit = chosenUnit;
				if (!set.isClassification()) {
					compareunit = TextUtilities.removeNamespace(chosenUnit);
				}
				prop = set.getUnitProperty(compareunit);
				if (prop != null) {
					if (set.isClassification()) {
					} else {
						abbrev = prop.getAbbreviation();
					}
				} else {
					Window.alert("Unit not listed: " + chosenUnit + "\n" + set.toString());
				}
			}
			int index = 0;
			int selected = 0;
			ArrayList<String> names = set.getAbbreviations();
			if(set.isClassification()) {
				names = set.getNames();
			}
			for (String name : names) {
				if (abbrev != null) {
					if (abbrev.compareTo(name) == 0) {
						selected = index;
					}
				}
				parameterUnits.addItem(name);
				index++;
			}
			Window.alert("setUpUnitList:" + chosenUnit + "   " + abbrev + "   " + selected + " \n" + names + "\n");
			parameterUnits.addItem("Other");
			parameterUnits.setSelectedIndex(selected);
		}
		*/
	}
	
	
	@UiHandler("parameterUnits")
	void onDropdown(ClickEvent event) {
		String name = parameterUnits.getSingleValue();
		unitproperties = setOfUnitProperties.getUnitPropertyFromAbbreviation(name);
		if(setOfUnitProperties.isClassification()) {
			valueTextBox.setText(name);
			chosenUnit = name;
		} else {
			chosenUnit = unitproperties.getUnitName();			
		}
	 }

	@Override
	public void conceptChosen(String topconcept, String chosenConcept) {
		if (topconcept.compareTo(unitsConcept) == 0) {
			setUnits(chosenConcept);
			this.chosenUnit = chosenConcept;
		} else if (topconcept.compareTo(purposeConcept) == 0) {
			purpose.setText(TextUtilities.removeNamespace(chosenConcept));
			this.chosenPurpose = chosenConcept;
		} else if (topconcept.compareTo(conceptConcept) == 0) {
			this.chosenConcept = chosenConcept;
			concept.setText(TextUtilities.removeNamespace(chosenConcept));
		} else if (topconcept.compareTo(parameterNames) == 0) {
			this.chosenParameter = chosenConcept;
			parameterLabel.setText(TextUtilities.removeNamespace(chosenConcept));
		} else if (topconcept.compareTo(uncertaintyConcept) == 0) {
			this.chosenUncertainty = chosenConcept;
			uncertaintyclass.setText(TextUtilities.removeNamespace(chosenConcept));
		}
	}

	public boolean updateObject() {
		ParameterValue value = (ParameterValue) info.getObject();
		DatabaseObjectHierarchy spechier = info.getSubObject(value.getParameterSpec());
		ParameterSpecification spec = (ParameterSpecification) spechier.getObject();
		DatabaseObjectHierarchy purposehier = spechier.getSubObject(spec.getPurposeandconcept());
		PurposeConceptPair p = (PurposeConceptPair) purposehier.getObject();
		DatabaseObjectHierarchy unitshier = spechier.getSubObject(spec.getUnits());
		ValueUnits units = (ValueUnits) unitshier.getObject();

		value.setValueAsString(valueTextBox.getText());
		value.setUncertainty(uncertaintyTextBox.getText());
		value.setParameterLabel(chosenParameter);
		spec.setDataPointUncertainty(chosenUncertainty);
		units.setUnitClass(chosenUnitClass);
		units.setUnitsOfValue(chosenUnit);
		p.setConcept(chosenConcept);
		p.setPurpose(chosenPurpose);

		return false;
	}

	public String getIdentifier() {
		return obj.getIdentifier();
	}

	public void setIdentifier(DatabaseObject obj) {
		this.obj = obj;
		identifiertip.setText(this.obj.getIdentifier());
	}

	public String setFullIdentifier() {
		String id = obj.getIdentifier();
		if (chosenParameter != null) {
			id = obj.getIdentifier() + "-" + TextUtilities.removeNamespace(chosenParameter);
		}
		identifiertip.setText(id);
		return id;
	}
}
