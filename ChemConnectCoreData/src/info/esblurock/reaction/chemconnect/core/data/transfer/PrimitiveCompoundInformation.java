package info.esblurock.reaction.chemconnect.core.data.transfer;

import java.io.Serializable;
import java.util.ArrayList;

public class PrimitiveCompoundInformation implements Serializable {
	private static final long serialVersionUID = 1L;

	ArrayList<PrimitiveDataStructureInformation> primitiveelements;

	public PrimitiveCompoundInformation() {
	}
	
	public ArrayList<PrimitiveDataStructureInformation> getPrimitiveelements() {
		return primitiveelements;
	}

}
