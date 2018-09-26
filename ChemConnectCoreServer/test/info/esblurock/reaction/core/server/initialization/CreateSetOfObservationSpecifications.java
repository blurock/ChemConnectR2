package info.esblurock.reaction.core.server.initialization;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.InterpretData;
import info.esblurock.reaction.ontology.OntologyKeys;

public class CreateSetOfObservationSpecifications {

	@Test
	public void test() {
		DatabaseObject obj = new DatabaseObject("AdministrationCatalog","Public","Administration","1" );
		
		DatabaseObjectHierarchy spechier = InterpretData.ChemConnectCompoundMultiple.createEmptyObject(obj);
		InterpretData.setChemConnectCompoundMultipleType(spechier,OntologyKeys.observationSpecs);
		
		String setofobservationsS = "dataset:HeatFluxBurner";
		CreateDefaultObjectsFactory.fillInputOutputObservationSpecifications(setofobservationsS,spechier);
		System.out.println(spechier.toString());
		
		
	}

}
