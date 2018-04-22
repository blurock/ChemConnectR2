package info.esblurock.reaction.chemconnect.core.client.pages.primitive.reference;

import com.google.gwt.user.client.Window;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveInterpretedInformation;

public class PrimitiveGPSLocation extends PrimitiveDataStructureBase {
	PrimitiveGPSLocationRow row;
	
	public PrimitiveGPSLocation() {
		super();
		row = new PrimitiveGPSLocationRow();
		add(row);
	}
	public PrimitiveGPSLocation(PrimitiveDataStructureInformation primitiveinfo) {
		super();
		Window.alert("PrimitiveGPSLocation : fill");
		fill(primitiveinfo);
	}
	@Override
	public void fill(PrimitiveDataStructureInformation primitiveinfo) {
		PrimitiveInterpretedInformation info = (PrimitiveInterpretedInformation) primitiveinfo;
		row = new PrimitiveGPSLocationRow(info);
		add(row);
	}
	
	
	public String getIdentifier() {
		return row.getIdentifier();
	}

}
