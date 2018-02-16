package info.esblurock.reaction.chemconnect.core.client.pages.primitive.text;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class PrimitiveOneLine extends PrimitiveDataStructureBase {
	
	PrimitiveOneLineRow row;
	
	public PrimitiveOneLine() {
		row = new PrimitiveOneLineRow();
		add(row);
	}
	public PrimitiveOneLine(PrimitiveDataStructureInformation info) {
		row = new PrimitiveOneLineRow(info);
		add(row);
	}
	@Override
	public void fill(PrimitiveDataStructureInformation info) {
		row.fill(info);
	}
	
}
