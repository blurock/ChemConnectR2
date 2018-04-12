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
		CreatePersonDescriptionFactory.createMinimalPersonDescription(obj, "UserClass", "Purpose",person);
		
		System.out.println(hierarchy);
		
	}

}
