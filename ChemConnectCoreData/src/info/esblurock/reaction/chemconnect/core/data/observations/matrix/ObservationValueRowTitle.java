package info.esblurock.reaction.chemconnect.core.data.observations.matrix;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@Entity
@SuppressWarnings("serial")
public class ObservationValueRowTitle extends ChemConnectCompoundDataStructure {

	@Unindex
	ArrayList<String> parameterLabel;
	
	
	public ObservationValueRowTitle() {
		parameterLabel = new ArrayList<String>();
	}
	
	public ObservationValueRowTitle(ChemConnectCompoundDataStructure structure, ArrayList<String> parameterLabel) {
		super(structure);
		this.parameterLabel = parameterLabel;
	}
	
	public ArrayList<String> getParameterLabel() {
		return parameterLabel;
	}

	public void setParameterLabel(ArrayList<String> parameterLabel) {
		this.parameterLabel = parameterLabel;
	}
	public void addParameterTitle(String name) {
		parameterLabel.add(name);
	}

	public String toString() {
		return toString("");
	}

	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + ": ");
		if (parameterLabel != null) {
			build.append(parameterLabel.size() + "  Titles: ");
			for (String cell : parameterLabel) {
				build.append("'" + cell + "' \t");
			}
		} else {
			build.append(" no titles");
		}
		build.append("\n");
		return build.toString();
	}

	

}
