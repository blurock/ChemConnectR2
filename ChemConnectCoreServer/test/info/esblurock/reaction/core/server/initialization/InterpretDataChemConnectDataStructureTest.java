package info.esblurock.reaction.core.server.initialization;

//import static org.junit.Assert.*;


import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.InterpretData;

public class InterpretDataChemConnectDataStructureTest {

	@Test
	public void test() {
		String id = "A1-B2-C3-D4-E5-Catalog-Simple";
		String access = "Public";
		String owner = "Admin";
		String sourceID = "1";
		
		
		DatabaseObject obj = new DatabaseObject(id,access,owner,sourceID);
		DatabaseObjectHierarchy structure = InterpretData.ChemConnectDataStructure.createEmptyObject(obj);

		System.out.println(structure.toString());
	}

}
