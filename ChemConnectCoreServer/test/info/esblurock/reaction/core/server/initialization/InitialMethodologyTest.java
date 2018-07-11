package info.esblurock.reaction.core.server.initialization;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class InitialMethodologyTest {

	@Test
	public void test() {
		DatabaseObject obj = new DatabaseObject("AdministrationCatalog","Public","Administration","1" );
		String methodology = "dataset:BurnerPlateThermocoupleMeasurements";
		String title = "The methodology";
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj,"");
		DataCatalogID name = new DataCatalogID(structure,"Catalog-Base","Catalog","Simple");
		DatabaseObjectHierarchy hierarchy = CreateDefaultObjectsFactory.fillMethodologyDefinition(obj, methodology, title,name);
		System.out.println(hierarchy);
	}

}
