package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterSpecificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.SetOfObservationsInformation;

public class SetOfObservationsField extends PrimitiveDataStructureBase {

	String parameterSpecificationS = "ParameterSpecification";
	public SetOfObservationsField() {
		super();
	}
	public SetOfObservationsField(SetOfObservationsInformation obs) {
		SetOfObservationsRow obsrow = new SetOfObservationsRow(obs.getIdentifier(), obs.getTopConcept(),obs.getValueType());
		add(obsrow);
		for(PrimitiveParameterSpecificationInformation info: obs.getDimensions()) {
			obsrow.addParameter(info);
		}
		for(PrimitiveParameterSpecificationInformation info: obs.getMeasures()) {
			obsrow.addParameter(info);			
		}
	}
}
