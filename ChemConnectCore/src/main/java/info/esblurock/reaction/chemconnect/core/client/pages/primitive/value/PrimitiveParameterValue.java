package info.esblurock.reaction.chemconnect.core.client.pages.primitive.value;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
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

	public String getIdentifier() {
		return row.getIdentifier();
	}
	@Override
	public void setIdentifier(DatabaseObject obj) {
		super.setIdentifier(obj);
		row.setIdentifier(obj);
	}

}
