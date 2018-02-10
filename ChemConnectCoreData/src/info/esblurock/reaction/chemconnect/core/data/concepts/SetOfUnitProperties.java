package info.esblurock.reaction.chemconnect.core.data.concepts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SetOfUnitProperties implements Serializable {
	
	private static final long serialVersionUID = 1L;
	String topUnitType;
	ArrayList<String> names;
	Map<String,UnitProperties> abbreviations;
	Map<String, UnitProperties> units;
	boolean classification; 
	boolean keyword; 
	
	public SetOfUnitProperties() {
		
	}
	public SetOfUnitProperties(String topUnitType) {
		this.topUnitType = topUnitType;
		names = new ArrayList<String>();
		units = new HashMap<String, UnitProperties>();
		abbreviations = new HashMap<String,UnitProperties>();
		classification = false;
	}

	public void addUnitProperties(UnitProperties unit) {
		names.add(unit.getUnitName());
		units.put(unit.getUnitName(),unit);
		String abbrev = unit.getAbbreviation();
		if(abbrev != null) {
			abbreviations.put(abbrev, unit);
		} else {
			abbreviations.put(unit.getUnitName(), unit);
		}
	}
	
	public Set<String> getAbbreviations() {
		return abbreviations.keySet();
	}
	public String getAbbreviation(String unit) {
		UnitProperties prop = units.get(unit);
		String abbrev = prop.getAbbreviation();
		return abbrev;
	}
	
	
	public String getTopUnitType() {
		return topUnitType;
	}

	public ArrayList<String> getNames() {
		return names;
	}

	public UnitProperties getUnitProperty(String unitname) {
		return units.get(unitname);
	}
	public UnitProperties getUnitPropertyFromAbbreviation(String unitname) {
		return abbreviations.get(unitname);
	}
	
	public Map<String, UnitProperties> getUnits() {
		return units;
	}

	public boolean isClassification() {
		return classification;
	}
	public void setClassification(boolean classification) {
		this.classification = classification;
	}
	
	public boolean isKeyword() {
		return keyword;
	}
	public void setKeyword(boolean keyword) {
		this.keyword = keyword;
	}
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix + "Set of Units: " + topUnitType + "\n");
		Set<String> names = units.keySet();
		for(String unitname : names) {
			UnitProperties unit = units.get(unitname);
			build.append(unit.toString(prefix));
		}
		return build.toString();
	}
}
