package info.esblurock.reaction.ontology.test;

import java.util.Set;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.concepts.SetOfUnitProperties;
import info.esblurock.reaction.ontology.units.OntologyUnits;

public class TestMeasurementOntology {

	@Test
	public void test() {
		System.out.println("---------------------------------------------------------------");
		String topunit1 = "qudt:TimeUnit";
		SetOfUnitProperties set1 = OntologyUnits.getSetOfUnitProperties(topunit1);
		System.out.println(set1.toString());
		System.out.println("---------------------------------------------------------------");
		String topunit2 = "qudt:TemperatureUnit";
		SetOfUnitProperties set2 = OntologyUnits.getSetOfUnitProperties(topunit2);
		System.out.println(set2.toString());
		System.out.println("---------------------------------------------------------------");
		String topunit = "dataset:ThermocoupleTypeClassification";
		SetOfUnitProperties set = OntologyUnits.getSetOfUnitProperties(topunit);
		Set<String> classifications = OntologyUnits.classifications(topunit);
		System.out.println("Classifications\n" + classifications);
		System.out.println(set.toString());
		System.out.println("---------------------------------------------------------------");
		String topunit3 = "dataset:ManufacturerName";
		SetOfUnitProperties set3 = OntologyUnits.getSetOfUnitProperties(topunit3);
		System.out.println(set3.toString());
		String topunit4 = "dataset:SensorManufacturerName";
		SetOfUnitProperties set4 = OntologyUnits.getSetOfUnitProperties(topunit4);
		System.out.println(set4.toString());
		
	}

}
