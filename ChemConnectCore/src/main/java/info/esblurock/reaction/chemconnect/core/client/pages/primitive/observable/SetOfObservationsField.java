package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.CreatePrimitiveStructure;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterSpecificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.SetOfObservationsInformation;

public class SetOfObservationsField extends PrimitiveDataStructureBase {

	String parameterSpecificationS = "ParameterSpecification";
	public SetOfObservationsField() {
		super();
	}
	public SetOfObservationsField(SetOfObservationsInformation obs) {
		SetOfObservationsRow obsrow = new SetOfObservationsRow(obs.getTopConcept(),obs.getValueType());
		add(obsrow);
		CreatePrimitiveStructure create = CreatePrimitiveStructure.valueOf(parameterSpecificationS);
		for(PrimitiveParameterSpecificationInformation info: obs.getDimensions()) {
			obsrow.addParameter(info);
		}
		for(PrimitiveParameterSpecificationInformation info: obs.getMeasures()) {
			PrimitiveParameterSpecification spec = (PrimitiveParameterSpecification) create.createStructure(info);
			obsrow.addParameter(info);			
		}
	}
}
