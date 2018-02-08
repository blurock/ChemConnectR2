package info.esblurock.reaction.chemconnect.core.client.pages.primitive.value;

import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterValueInformation;

public class PrimitiveParameterValue extends PrimitiveDataStructureBase {

	PrimitiveParameterValueRow row;
	
	public PrimitiveParameterValue() {
		super();
	}

	public PrimitiveParameterValue(PrimitiveDataStructureInformation info) {
		super(info);
		PrimitiveParameterValueInformation paraminfo = (PrimitiveParameterValueInformation) info;
		row = new PrimitiveParameterValueRow(paraminfo);
		this.add(row);
	}
	
	@Override
	public void fill(PrimitiveDataStructureInformation info) {
		row.fill(info);
	}

}
