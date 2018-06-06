package info.esblurock.reaction.chemconnect.core.client.pages.primitive.link;

import info.esblurock.reaction.chemconnect.core.client.pages.catalog.link.PrimitiveDataObjectLinkRow;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class PrimitiveDataObjectLink extends PrimitiveDataStructureBase {
	PrimitiveDataObjectLinkRow row;
	
	public PrimitiveDataObjectLink() {
		row = new PrimitiveDataObjectLinkRow();
		add(row);
	}
	
	public PrimitiveDataObjectLink(PrimitiveDataStructureInformation info) {
		row = new PrimitiveDataObjectLinkRow(info);
		add(row);
	}
	
	
	@Override
	public void fill(PrimitiveDataStructureInformation info) {
		row.fill(info);
	}

}
