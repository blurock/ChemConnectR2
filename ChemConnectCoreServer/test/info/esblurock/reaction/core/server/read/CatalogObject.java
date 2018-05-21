package info.esblurock.reaction.core.server.read;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.core.server.db.extract.ExtractCatalogInformation;

public class CatalogObject {

	@Test
	public void test()  {
		System.out.println("-------------------------------");
		System.out.println("dataset:DatasetCatalogHierarchy");
		System.out.println("-------------------------------");
		ExtractCatalogInformation.getCatalogObject("ID", "dataset:DatasetCatalogHierarchy");
		/*
		System.out.println("-------------------------------");
		System.out.println("dataset:ParameterValue");
		System.out.println("-------------------------------");
		DataElementInformation element2 = new DataElementInformation("dataset:ParameterValue", 
				null, true, 0, null, null,null);
		ExtractCatalogInformation.getCatalogObject("ID", element2);
		System.out.println("-------------------------------");
		System.out.println("dataset:DescriptionDataData");
		System.out.println("-------------------------------");
		DataElementInformation element3 = new DataElementInformation("dataset:DescriptionDataData",
				null, true, 0, null, null,null);
		ExtractCatalogInformation.getCatalogObject("ID", element3);
		*/
	}

}
