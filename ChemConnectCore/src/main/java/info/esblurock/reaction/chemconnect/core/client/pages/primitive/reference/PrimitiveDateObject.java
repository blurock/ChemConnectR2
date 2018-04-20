package info.esblurock.reaction.chemconnect.core.client.pages.primitive.reference;

import gwt.material.design.client.constants.Color;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitivePersonNameInformation;

public class PrimitiveDateObject extends PrimitiveDataStructureBase  {

	PrimitiveDateObjectRow row;
	
	public PrimitiveDateObject() {
		row = new PrimitiveDateObjectRow();
		add(row);
	}
	public PrimitiveDateObject(PrimitiveDataStructureInformation primitiveinfo) {
		row = new PrimitiveDateObjectRow(primitiveinfo);
		add(row);
	}

	@Override
	public void fill(PrimitiveDataStructureInformation primitiveinfo) {
		row.fill(primitiveinfo);
		add(row);
	}

	public String getIdentifier() {
		return row.getIdentifier();
	}

}
