package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@Entity
@SuppressWarnings("serial")
public class ValueUnits extends DatabaseObject {

	@Index
	String unitClass;
	@Index
	String unitsOfValue;

	public ValueUnits() {
	}
	public ValueUnits(DatabaseObject obj, String unitClass, String unitsOfValue) {
		this.fill(obj,unitClass,unitsOfValue);
	}
	public ValueUnits(ValueUnits valueunits) {
		this.fill(valueunits);
	}
	public void fill(DatabaseObject obj, String unitClass, String unitsOfValue) {
		super.fill(obj);
		this.unitClass = unitClass;
		this.unitsOfValue = unitsOfValue;
	}
	public void fill(ValueUnits valueunits) {
		super.fill(valueunits);
		this.unitClass = valueunits.getUnitClass();
		this.unitsOfValue = valueunits.getUnitsOfValue();
	}
	@Override
	public void fill(DatabaseObject object) {
		super.fill(object);
		ValueUnits valueunits = (ValueUnits) object;
		this.unitClass = valueunits.getUnitClass();
		this.unitsOfValue = valueunits.getUnitsOfValue();
	}
	public String getUnitClass() {
		return unitClass;
	}
	public String getUnitsOfValue() {
		return unitsOfValue;
	}
	public void setUnitClass(String unitClass) {
		this.unitClass = unitClass;
	}
	public void setUnitsOfValue(String unitsOfValue) {
		this.unitsOfValue = unitsOfValue;
	}

	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Units: " + unitsOfValue + " (" + unitClass + ")\n");
		return build.toString();
	}
}
