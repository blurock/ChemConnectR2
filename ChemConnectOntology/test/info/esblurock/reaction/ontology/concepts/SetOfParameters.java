package info.esblurock.reaction.ontology.concepts;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.concepts.SetOfUnitProperties;
import info.esblurock.reaction.ontology.units.OntologyUnits;

public class SetOfParameters {

	@Test
	public void test() {
		String topunit = "dataset:MassFlowControllerClassification";
		
		Set<String> cls = OntologyUnits.classifications(topunit);
		System.out.println(cls);
	
		SetOfUnitProperties set = OntologyUnits.getSetOfUnitProperties(topunit);
		System.out.println(set.toString());
		
	}

}
