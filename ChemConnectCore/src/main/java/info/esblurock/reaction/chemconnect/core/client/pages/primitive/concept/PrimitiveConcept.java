package info.esblurock.reaction.chemconnect.core.client.pages.primitive.concept;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class PrimitiveConcept extends PrimitiveDataStructureBase {
	PrimitiveConceptRow row;
	
	public PrimitiveConcept() {
		row = new PrimitiveConceptRow();
		add(row);
	}
	public PrimitiveConcept(PrimitiveDataStructureInformation info) {
		/*
		row = new PrimitiveConceptRow(info);
		add(row);
		*/
	}

}
