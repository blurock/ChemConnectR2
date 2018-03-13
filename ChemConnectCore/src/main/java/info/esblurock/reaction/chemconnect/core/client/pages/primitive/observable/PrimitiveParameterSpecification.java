package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterSpecificationInformation;

public class PrimitiveParameterSpecification  extends PrimitiveDataStructureBase {

	ObservableSpecificationRow row;
	
	public PrimitiveParameterSpecification() {
		row = null;
	}
	public PrimitiveParameterSpecification(PrimitiveDataStructureInformation info) {
		super(info);
		init();
		PrimitiveParameterSpecificationInformation obs = (PrimitiveParameterSpecificationInformation) info;
		setIdentifier(obs);
		row = new ObservableSpecificationRow(obs);
		this.add(row);
	}
	@Override
	public void fill(PrimitiveDataStructureInformation info) {
		row.fill(info);
	}
	public void setIdentifier(DatabaseObject obj) {
		DatabaseObject newobj = new DatabaseObject(obj);
		super.setIdentifier(newobj);
		if(row != null) {
			row.setIdentifier(newobj);
		}
	}

}
