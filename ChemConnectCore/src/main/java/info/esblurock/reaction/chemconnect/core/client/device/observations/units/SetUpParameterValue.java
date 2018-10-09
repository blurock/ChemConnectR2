package info.esblurock.reaction.chemconnect.core.client.device.observations.units;

import java.util.ArrayList;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.concepts.SetOfUnitProperties;
import info.esblurock.reaction.chemconnect.core.data.concepts.UnitProperties;

public class SetUpParameterValue {
	public static void setup(MaterialComboBox<String> parameterUnits, String chosenUnit, SetOfUnitProperties set) {
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
					//Window.alert("Unit not listed: " + chosenUnit + "\n" + set.toString());
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
			parameterUnits.addItem("Other");
			parameterUnits.setSelectedIndex(selected);
		}
	}
}
