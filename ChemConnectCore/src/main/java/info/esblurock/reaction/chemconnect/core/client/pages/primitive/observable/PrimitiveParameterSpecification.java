package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterSpecificationInformation;

public class PrimitiveParameterSpecification  extends PrimitiveDataStructureBase {

	ObservableSpecificationRow row;
	
	public PrimitiveParameterSpecification() {
		
	}
	public PrimitiveParameterSpecification(PrimitiveDataStructureInformation info) {
		super(info);
		PrimitiveParameterSpecificationInformation obs = (PrimitiveParameterSpecificationInformation) info;
		row = new ObservableSpecificationRow(obs);
		this.add(row);
	}
	
	@Override
	public void fill(PrimitiveDataStructureInformation info) {
		row.fill(info);
	}

}
