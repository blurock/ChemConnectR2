package info.esblurock.reaction.core.server.initialization;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class CreateDefault {

	@Test
	public void test() {
		DatabaseObject obj = new DatabaseObject("AdministrationCatalog","Public","Administration","1" );
		
		/*
		NameOfPerson person = new NameOfPerson(obj,"Mr.", "Edward", "Blurock");
		
		DatabaseObjectHierarchy hierarchy = 
		CreateDefaultObjectsFactory.createMinimalPersonDescription(obj, "UserClass", "Purpose",person);
		
		System.out.println(hierarchy);
		
		DatabaseObject orgobj = new DatabaseObject("BlurockAB","Public","Administration","1" );
		DatabaseObjectHierarchy hierarchy2 = 
		CreateDefaultObjectsFactory.createMinimalOrganization(orgobj,"Blurock Consulting AB", "dataset:Organization");

		System.out.println(hierarchy2);
		
		System.out.println("--------------------------------------------------------------------\n");
		System.out.println("createSubSystemDescription\n");
		System.out.println("--------------------------------------------------------------------\n");
		DatabaseObject subobj = new DatabaseObject("HeatFluxBurner","Public","Administration","1" );
		
		String devicename = "Heat Flux Burner";
		String purpose = "dataset:DeviceComponentSpecification";
		String concept = "dataset:HeatFluxBurnerConfiguration";
		DatabaseObjectHierarchy hierarchy3 = 
				CreateDefaultObjectsFactory.createSubSystemDescription(subobj,devicename, purpose, concept);
		System.out.println(hierarchy3);
		
		*/
		System.out.println("--------------------------------------------------------------------\n");
		System.out.println("createCataogHierarchyForUser\n");
		System.out.println("--------------------------------------------------------------------\n");
		DatabaseObject catobj = new DatabaseObject("Catalog", "Administration","Administration","1");
		DatabaseObjectHierarchy cat = CreateDefaultObjectsFactory.createCataogHierarchyForUser(catobj, "Administration", "BlurockConsultingAB");
		System.out.println(cat);
	}

}
