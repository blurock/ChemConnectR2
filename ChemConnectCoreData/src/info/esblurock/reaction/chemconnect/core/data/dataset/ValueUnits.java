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
		fill(obj,unitClass,unitsOfValue);
	}
	public ValueUnits(ValueUnits valueunits) {
		fill(valueunits);
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
	public String getUnitClass() {
		return unitClass;
	}
	public String getUnitsOfValue() {
		return unitsOfValue;
	}

	
}
