package info.esblurock.reaction.core.server.initialization;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class CreateDefault {

	@Test
	public void test() {
		
		DatabaseObject obj = new DatabaseObject("ID","Public","Administration","99" );
		
		NameOfPerson person = new NameOfPerson(obj,"Mr.", "Edward", "Blurock");
		
		DatabaseObjectHierarchy hierarchy = 
		CreateDefaultObjectsFactory.createMinimalPersonDescription(obj, "UserClass", "Purpose",person);
		
		System.out.println(hierarchy);
		
		DatabaseObjectHierarchy hierarchy2 = 
		CreateDefaultObjectsFactory.createMinimalOrganization(obj,"Blurock Consulting AB", "dataset:Organization");

		System.out.println(hierarchy2);
		
		System.out.println("--------------------------------------------------------------------\n");
		
		String devicename = "Heat Flux Burner";
		String purpose = "dataset:DeviceComponentSpecification";
		String concept = "dataset:HeatFluxBurnerConfiguration";
		DatabaseObjectHierarchy hierarchy3 = 
				CreateDefaultObjectsFactory.createSubSystemDescription(obj,devicename, purpose, concept);
		System.out.println(hierarchy3);
		
		
		DatabaseObjectHierarchy hierarchy4 = 
				CreateDefaultObjectsFactory.createCataogHierarchyForUser(obj,"userid","orgid");
		System.out.println(hierarchy4);
	}

}
