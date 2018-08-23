package info.esblurock.reaction.core.server.initialization;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class CreateSetOfObservations {

	@Test
	public void test() {
		DatabaseObject obj = new DatabaseObject("AdministrationCatalog","Public","Administration","1" );
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj,"");
		DataCatalogID name = new DataCatalogID(structure,"Catalog-Base","Catalog","Simple");
		DatabaseObjectHierarchy hierarchy = CreateDefaultObjectsFactory.fillSetOfObservations(obj,"dataset:BurnerPlateObservations",
				"Set of burner plate observations",name);
		System.out.println("fillSetOfObservations\n" + hierarchy.toString());
	}

}
