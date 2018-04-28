package info.esblurock.reaction.chemconnect.core.client.pages.primitive.reference;


import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveInterpretedInformation;

public class PrimitivePersonName  extends PrimitiveDataStructureBase {

	PrimitivePersonNameRow row;
	
	public PrimitivePersonName() {
		super();
		row = new PrimitivePersonNameRow();
		add(row);		
	}

	public PrimitivePersonName(PrimitiveDataStructureInformation primitiveinfo) {
		super(primitiveinfo);
		PrimitiveInterpretedInformation info = (PrimitiveInterpretedInformation) primitiveinfo;
		row = new PrimitivePersonNameRow(info);
		add(row);
	}
	@Override
	public void fill(PrimitiveDataStructureInformation primitiveinfo) {
		PrimitiveInterpretedInformation info = (PrimitiveInterpretedInformation) primitiveinfo;
		row.fill(info);
		add(row);
	}

	public String getIdentifier() {
		return row.getIdentifier();
	}

}
