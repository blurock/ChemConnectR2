package info.esblurock.reaction.chemconnect.core.client.pages.primitive.text;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class PrimitiveSetOfKeys extends PrimitiveDataStructureBase {

	PrimitiveSetOfKeywordsRow row;
	public PrimitiveSetOfKeys() {
		row = new PrimitiveSetOfKeywordsRow();
		add(row);
	}
	public PrimitiveSetOfKeys(PrimitiveDataStructureInformation info) {
		row = new PrimitiveSetOfKeywordsRow(info);
		add(row);
	}
	
	@Override
	public void fill(PrimitiveDataStructureInformation info) {
		row.fill(info);
	}
}
