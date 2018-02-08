package info.esblurock.reaction.ontology.test;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.concepts.SetOfUnitProperties;
import info.esblurock.reaction.ontology.units.OntologyUnits;

public class TestMeasurementOntology {

	@Test
	public void test() {
		
		String topunit = "qudt:TimeUnit";
		SetOfUnitProperties set = OntologyUnits.getSetOfUnitProperties(topunit);
		System.out.println(set.toString());
	}

}
