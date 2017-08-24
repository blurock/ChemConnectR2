package info.esblurock.reaction.ontology.dataset;

import java.io.Serializable;
import java.util.ArrayList;

public class ListOfDataElementInformation extends ArrayList<DataElementInformation> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String toString() {
		StringBuilder build = new StringBuilder();
		for(DataElementInformation info : this) {
			build.append(info.toString());
			build.append("\n");
		}
		return build.toString();
	}

}
