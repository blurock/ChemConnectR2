package info.esblurock.reaction.chemconnect.core.client.pages.primitive.reference;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

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
