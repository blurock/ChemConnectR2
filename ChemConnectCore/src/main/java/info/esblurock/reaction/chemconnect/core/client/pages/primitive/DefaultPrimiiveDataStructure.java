package info.esblurock.reaction.chemconnect.core.client.pages.primitive;

import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class DefaultPrimiiveDataStructure extends PrimitiveDataStructureBase {
	
	DefaultPrimitiveStructureRow row;

	public DefaultPrimiiveDataStructure() {
		super();
		row = new DefaultPrimitiveStructureRow(primitiveinfo);
		add(row);
	}

	public DefaultPrimiiveDataStructure(PrimitiveDataStructureInformation primitiveinfo) {
		super(primitiveinfo);
		row = new DefaultPrimitiveStructureRow(primitiveinfo);
		add(row);
	}

}
