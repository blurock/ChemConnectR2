package info.esblurock.reaction.chemconnect.core.data.observations.matrix;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@Entity
@SuppressWarnings("serial")
public class ObservationRowUnits extends ChemConnectCompoundDataStructure {
	
	@Unindex
	ArrayList<String> units;

	public ObservationRowUnits() {
		super();
		this.units = null;
	}
	public ObservationRowUnits(ChemConnectCompoundDataStructure structure, ArrayList<String> units) {
		super(structure);
		this.units = units;
	}
	public ArrayList<String> getUnits() {
		return units;
	}
	public void setUnits(ArrayList<String> units) {
		this.units = units;
	}
	
	public String toString() {
		return toString("");
	}

	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + ": ");
		if (units != null) {
			build.append(units.size() + "  Titles: ");
			for (String cell : units) {
				build.append("'" + cell + "' \t");
			}
		} else {
			build.append(" no titles");
		}
		build.append("\n");
		return build.toString();
	}
	


}
