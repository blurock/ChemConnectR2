package info.esblurock.reaction.core.server.initialization;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.InterpretData;

public class CreateObservationSpecification {

	@Test
	public void test() {
		String id = "Catalog-BurnerPlateObservations";
		String access = "Public";
		String owner = "Administration";
		String sourceID = "1";
		DatabaseObject obj = new DatabaseObject(id,access,owner,sourceID);
		String parentID = "parent";
	
		String setofobservationsS = "dataset:BurnerPlateObservations";
		DatabaseObjectHierarchy obspecset = InterpretData.ObservationSpecification.createEmptyObject(obj);
		CreateDefaultObjectsFactory.fillObservationSpecification(obspecset,setofobservationsS, parentID);
		System.out.println("-----------------------------------------------------------------");
		System.out.println(obspecset.toString());
		System.out.println("-----------------------------------------------------------------");

		String setofobservationsS1 = "dataset:FuelCompositionPercentage";
		DatabaseObjectHierarchy obspecset1 = InterpretData.ObservationSpecification.createEmptyObject(obj);
		CreateDefaultObjectsFactory.fillObservationSpecification(obspecset1,setofobservationsS1, parentID);
		System.out.println("-----------------------------------------------------------------");
		System.out.println(obspecset1.toString());
		System.out.println("-----------------------------------------------------------------");

	
	}

}
