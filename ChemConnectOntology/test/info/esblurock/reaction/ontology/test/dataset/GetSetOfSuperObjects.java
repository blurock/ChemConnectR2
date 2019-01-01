package info.esblurock.reaction.ontology.test.dataset;

//import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.contact.IndividualInformation;
import info.esblurock.reaction.chemconnect.core.data.contact.PersonalDescription;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class GetSetOfSuperObjects {

	@Test
	public void test() {
		String sub1 = "NameOfPerson";
		ArrayList<String> lst1 = DatasetOntologyParsing.asSubObject(sub1);
		
		System.out.println(sub1 + "\n" + lst1);

		String sub2 = "PersonalDescription";
		ArrayList<String> lst2 = DatasetOntologyParsing.asSubObject(sub2);
		
		System.out.println(sub2 + "\n" + lst2);
		
		String sub3 = "OrganizationDescription";
		ArrayList<String> lst3 = DatasetOntologyParsing.asSubObject(sub3);
		
		System.out.println(sub3 + "\n" + lst3);
		
		String s1 = PersonalDescription.class.getSimpleName();
		String t1 = DatasetOntologyParsing.getTypeFromDataType(s1);
		System.out.println(s1 + ": " + t1);
		String s2 = IndividualInformation.class.getSimpleName();
		String t2 = DatasetOntologyParsing.getTypeFromDataType(s2);
		System.out.println(s2 + ": " + t2);

}

}
