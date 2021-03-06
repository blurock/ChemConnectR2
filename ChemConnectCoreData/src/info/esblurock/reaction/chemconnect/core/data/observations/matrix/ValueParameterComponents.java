package info.esblurock.reaction.chemconnect.core.data.observations.matrix;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@Entity
@SuppressWarnings("serial")

public class ValueParameterComponents  extends ChemConnectCompoundDataStructure {

	@Index
	int position;
	@Index
	String parameterLabel;
	@Index
	String unitsOfValue;
	@Index
	boolean isUncertaintyValue;
	
	public ValueParameterComponents(ChemConnectCompoundDataStructure structure, int position, String parameterLabel, String unitsOfValue, boolean isUncertaintyValue) {
		super(structure);
		this.parameterLabel = parameterLabel;
		this.unitsOfValue = unitsOfValue;
		this.isUncertaintyValue = isUncertaintyValue;
		this.position = position;
	}
	public String getParameterLabel() {
		return parameterLabel;
	}
	public void setParameterLabel(String parameterLabel) {
		this.parameterLabel = parameterLabel;
	}
	public String getUnitsOfValue() {
		return unitsOfValue;
	}
	public void setUnitsOfValue(String unitsOfValue) {
		this.unitsOfValue = unitsOfValue;
	}
	public boolean isUncertaintyValue() {
		return isUncertaintyValue;
	}
	public void setUncertaintyValue(boolean isUncertaintyValue) {
		this.isUncertaintyValue = isUncertaintyValue;
	}
	
	public int getPosition() {
		return position;
	}
	public void setPostion(int postion) {
		this.position = postion;
	}
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + position + ": ");
		build.append(parameterLabel);
		build.append(" (" + unitsOfValue + ")");
		if(isUncertaintyValue) {
			build.append(" uncertainty");
		} else {
			build.append(" value");
		}
		build.append("\n");
		return build.toString();
	}
	
	
	
	
	
}
