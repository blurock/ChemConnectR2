package info.esblurock.reaction.chemconnect.core.client.device.observations;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.concepts.SetOfUnitProperties;
import info.esblurock.reaction.chemconnect.core.data.concepts.UnitProperties;
import info.esblurock.reaction.chemconnect.core.data.dataset.ParameterSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.PurposeConceptPair;
import info.esblurock.reaction.chemconnect.core.data.dataset.ValueUnits;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class PrimitiveParameterValueRow extends Composite
		implements ChooseFromConceptHeirarchy, PrimitiveValueUnitCallInterface, SetLineContentInterface {

	private static PrimitiveParameterValueRowUiBinder uiBinder = GWT.create(PrimitiveParameterValueRowUiBinder.class);

	interface PrimitiveParameterValueRowUiBinder extends UiBinder<Widget, PrimitiveParameterValueRow> {
	}

	String unitsConcept = "qudt:Unit";
	String purposeConcept = MetaDataKeywords.chemConnectPurpose;
	String conceptConcept = MetaDataKeywords.chemConnectConceptProperties;
	String parameterNames = MetaDataKeywords.chemConnectParameter;
	String uncertaintyConcept = MetaDataKeywords.chemConnectUncertaintyTypes;
	
	@UiField
	MaterialColumn uncertaintycolumn;
	@UiField
	MaterialColumn valuecolumn;
	@UiField
	MaterialColumn labelcolumn;
	
	@UiField
	protected MaterialLink valueTextBox;
	@UiField
	MaterialLink uncertaintyTextBox;
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialLink parameterLabel;
	@UiField
	protected MaterialLink parameterUnits;
	@UiField
	MaterialLink purpose;
	@UiField
	MaterialLink concept;
	@UiField
	MaterialLink unitclass;
	@UiField
	MaterialLink uncertaintyclass;
	@UiField
	MaterialLink delete;
	@UiField
	MaterialRow extrainfo;
	@UiField
	MaterialLink more;
	@UiField
	MaterialLink less;
	@UiField
	MaterialTooltip identifiertip;
	@UiField
	MaterialTooltip uncertaintytip;
	@UiField
	MaterialTooltip purposetip;
	@UiField
	MaterialTooltip concepttip;
	@UiField
	MaterialTooltip unitstip;
	@UiField
	MaterialTooltip valuetip;
	@UiField
	MaterialTooltip uncertaintyclasstip;
	@UiField
	MaterialTooltip unitsclasstip;
	@UiField
	MaterialTooltip deletetip;
	@UiField
	MaterialColumn unitscolumn;
	@UiField
	MaterialColumn unitsclasscolumn;

	String propertyType;
	boolean rowVisible;
	String chosenConcept;
	String chosenUnit;
	String chosenUnitClass;
	String chosenPurpose;
	String chosenParameter;
	String chosenUncertainty;
	DatabaseObjectHierarchy info;
	boolean unitchoice;
	boolean otherchoice;
	boolean valuechoice;
	boolean uncertaintychoice;
	protected boolean valueinput;
	protected boolean uncertaintyinput;
	

	SetOfUnitProperties setOfUnitProperties;
	UnitProperties unitproperties;
	DatabaseObjectHierarchy spechierarchy;
	ParameterSpecification specification;
	ValueUnits units;
	PurposeConceptPair pair;
	
	public PrimitiveParameterValueRow() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public PrimitiveParameterValueRow(DatabaseObjectHierarchy paramhier) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		fill(paramhier);
	}

	protected void init() {
		labelcolumn.setGrid("s5");
		valuecolumn.setVisible(false);
		uncertaintycolumn.setVisible(false);
		
		
		delete.setIconColor(Color.BLACK);
		deletetip.setText("Delete Parameter from list");

		more.setIconColor(Color.BLACK);
		less.setIconColor(Color.BLACK);
		more.setVisible(true);
		less.setVisible(false);
		rowVisible = false;

		purposetip.setText("Purpose");
		concepttip.setText("Concept");
		unitsclasstip.setText("Units Class");
	}

	public void fill(DatabaseObjectHierarchy spechierarchy) {
		this.spechierarchy = spechierarchy;
		specification = (ParameterSpecification) spechierarchy.getObject();
		
		setFullIdentifier();
		
		this.propertyType = specification.getParameterLabel();
		if (this.propertyType != null) {
			chosenParameter = this.propertyType;
			parameterLabel.setText(TextUtilities.removeNamespace(chosenParameter));
		} else {
			parameterLabel.setText("Choose Label");
		}
		if (specification.getDataPointUncertainty() != null) {
			uncertaintyclass.setText(specification.getDataPointUncertainty());
		} else {
			uncertaintyclass.setText("Uncertainty");
		}
		DatabaseObjectHierarchy unithier = spechierarchy.getSubObject(specification.getUnits());
		units = (ValueUnits) unithier.getObject();
		if (units.getUnitsOfValue() != null) {
			chosenUnit = units.getUnitsOfValue();
			parameterUnits.setVisible(false);
		} else {
			parameterUnits.setVisible(false);
		}
		if (units.getUnitClass() != null) {
			chosenUnitClass = units.getUnitClass();
			setUnits(chosenUnitClass);
			unitclass.setText(TextUtilities.removeNamespace(chosenUnitClass));
		} else {
			chosenUnitClass = null;
			unitclass.setText("Choose Units");
		}
		DatabaseObjectHierarchy purposehier = spechierarchy.getSubObject(specification.getPurposeandconcept());
		pair = (PurposeConceptPair) purposehier.getObject();

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
		rowVisible = false;
		extrainfo.setVisible(false);
		more.setVisible(true);
		less.setVisible(false);
	}

	@UiHandler("more")
	public void onClickMore(ClickEvent event) {
		extrainfo.setVisible(true);
		more.setVisible(false);
		less.setVisible(true);
	}

	@UiHandler("less")
	public void onClickLess(ClickEvent event) {
		extrainfo.setVisible(false);
		more.setVisible(true);
		less.setVisible(false);
	}

	@UiHandler("concept")
	public void onClickConcept(ClickEvent event) {
		findConcept(conceptConcept);
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

	@UiHandler("delete")
	public void onClickClear(ClickEvent event) {
		MaterialToast.fireToast("Delete not implemented");
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
		}
		if (set.isClassification()) {
			uncertaintyclass.setVisible(false);
			parameterUnits.setVisible(true);
			unitsclasscolumn.setGrid("s5");
			unitscolumn.setGrid("s5");
		} else {
			parameterUnits.setVisible(true);
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

	public void setUpUnitList(SetOfUnitProperties set) {
		this.setOfUnitProperties = set;
		setVisibility(set);
		if (set.isClassification()) {
			parameterUnits.setText("Choose " + TextUtilities.removeNamespace(set.getTopUnitType()));
		} else {
			parameterUnits.setText(TextUtilities.removeNamespace(chosenUnit));
		}
	}

	@UiHandler("parameterUnits")
	void onDropdown(ClickEvent event) {
		unitchoice = true;
		AskForUnitsModal ask = new AskForUnitsModal(chosenUnit, setOfUnitProperties, this);
		modalpanel.clear();
		modalpanel.add(ask);
		ask.openModal();
	}

	@Override
	public void conceptChosen(String topconcept, String chosenConcept, ArrayList<String> path) {
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
		specification.setParameterLabel(chosenParameter);
		specification.setDataPointUncertainty(chosenUncertainty);
		units.setUnitClass(chosenUnitClass);
		units.setUnitsOfValue(chosenUnit);
		pair.setConcept(chosenConcept);
		pair.setPurpose(chosenPurpose);

		return false;
	}

	public String getIdentifier() {
		return specification.getIdentifier();
	}

	public void setIdentifier(DatabaseObject obj) {
		specification.setIdentifier(obj.getIdentifier());
		identifiertip.setText(obj.getIdentifier());
	}

	public String setFullIdentifier() {
		String id = specification.getIdentifier();
		if (chosenParameter != null) {
			id = specification.getIdentifier() + "-" + TextUtilities.removeNamespace(chosenParameter);
		}
		identifiertip.setText(id);
		return id;
	}

	@Override
	public void setLineContent(String line) {
		if (unitchoice) {
			unitchoice = false;
			if (setOfUnitProperties.isClassification()) {
				if(line.compareTo("Other") == 0) {
					InputLineModal linemodal = new InputLineModal("Input Alternative Classification", "type value here: ", this);
					modalpanel.clear();
					modalpanel.add(linemodal);
					linemodal.openModal();
					otherchoice = true;
				} else {
				chosenUnit = line;
				}
			} else {
				unitproperties = setOfUnitProperties.getUnitPropertyFromAbbreviation(line);
				if (unitproperties != null) {
					chosenUnit = unitproperties.getUnitName();
					parameterUnits.setText(TextUtilities.removeNamespace(chosenUnit));
				} else {
					MaterialToast.fireToast("Units for '" + line + "' are null");
				}
			}
		} else if(otherchoice) {
			otherchoice = false;
			chosenUnit = line;
		}

	}
}
