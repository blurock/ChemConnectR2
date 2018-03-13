package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterSpecificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.SetOfObservationsInformation;

public class SetOfObservationsField extends PrimitiveDataStructureBase {

	String parameterSpecificationS = "ParameterSpecification";
	public SetOfObservationsField() {
		super();
	}
	public SetOfObservationsField(SetOfObservationsInformation obs) {
		SetOfObservationsRow obsrow = new SetOfObservationsRow(obs, obs.getTopConcept(),obs.getValueType());
		add(obsrow);
		for(PrimitiveParameterSpecificationInformation info: obs.getDimensions()) {
			String subid = obs.getIdentifier() + "-" + TextUtilities.removeNamespace(info.getPropertyType());
			info.setIdentifier(subid);
			obsrow.addParameter(info);
		}
		for(PrimitiveParameterSpecificationInformation info: obs.getMeasures()) {
			String subid = obs.getIdentifier() + "-" + TextUtilities.removeNamespace(info.getPropertyType());
			info.setIdentifier(subid);
			obsrow.addParameter(info);			
		}
	}
}
