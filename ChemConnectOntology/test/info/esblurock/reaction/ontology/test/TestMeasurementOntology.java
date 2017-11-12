package info.esblurock.reaction.ontology.test;

//import static org.junit.Assert.*;

import java.util.ArrayList;

import org.apache.jena.rdf.model.Resource;
import org.junit.Test;

import info.esblurock.reaction.ontology.units.OntologyUnits;
import info.esblurock.reaction.ontology.units.UnitProperties;

public class TestMeasurementOntology {

	@Test
	public void test() {
		ArrayList<Resource> lst = OntologyUnits.getUnitSet("quant:ThermodynamicTemperature");
		System.out.println("Size: " + lst.size());
		for (Resource resource : lst) {
			System.out.println("-------------------------------\nUnit Result: " + resource.getLocalName());
			UnitProperties unit = OntologyUnits.UnitInformation(resource.getLocalName());
			System.out.println(unit.toString());
		}
		System.out.println("End");
	}

}
