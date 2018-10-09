package info.esblurock.reaction.chemconnect.core.client.device.observations.units;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.device.observations.PrimitiveValueUnitCallInterface;
import info.esblurock.reaction.chemconnect.core.client.device.observations.PrimitiveValueUnitCallback;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.concepts.SetOfUnitProperties;
import info.esblurock.reaction.chemconnect.core.data.dataset.ValueUnits;

public class StandardDatasetValueUnitsHeader extends Composite
		implements PrimitiveValueUnitCallInterface, ChooseFromConceptHeirarchy {

	private static StandardDatasetValueUnitsHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetValueUnitsHeaderUiBinder.class);

	interface StandardDatasetValueUnitsHeaderUiBinder extends UiBinder<Widget, StandardDatasetValueUnitsHeader> {
	}

	public StandardDatasetValueUnitsHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	String unitsConcept = "qudt:Unit";

	@UiField
	MaterialLink unitclass;
	@UiField
	MaterialComboBox<String> parameterUnits;
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialTextBox unitsTextBox;

	String chosenUnit;
	String chosenUnitClass;
	SetOfUnitProperties setOfUnitProperties;
	ValueUnits units;
	ArrayList<String> chosenPath;
	
	public StandardDatasetValueUnitsHeader(DatabaseObject object) {
		initWidget(uiBinder.createAndBindUi(this));
		units = (ValueUnits) object;
		fill(units);
	}

	public void fill(ValueUnits units) {
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
			chosenUnitClass = null;
			unitclass.setText("Choose Units");
		}
	}

	public void updateValues() {
		units.setUnitClass(chosenUnitClass);
		units.setUnitsOfValue(chosenUnit);
	}

	public void setUnits(String unitname) {
		this.chosenUnitClass = unitname;
		unitclass.setText(TextUtilities.removeNamespace(unitname));

		PrimitiveValueUnitCallback callback = new PrimitiveValueUnitCallback(this);
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		async.unitProperties(unitname, callback);
	}

	public void setUpUnitList(SetOfUnitProperties set) {
		this.setOfUnitProperties = set;
		parameterUnits.setVisible(true);
		unitsTextBox.setVisible(false);
		SetUpParameterValue.setup(parameterUnits, chosenUnit, set);
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

	@Override
	public void conceptChosen(String topconcept, String chosenConcept, ArrayList<String> path) {
		setUnits(chosenConcept);
		this.chosenUnit = chosenConcept;
		this.chosenPath = path;
	}

	@UiHandler("parameterUnits")
	void onDropdown(ClickEvent callback) {
		String chosen = parameterUnits.getSingleValue();
		chosenUnit = chosen;
	}
}
