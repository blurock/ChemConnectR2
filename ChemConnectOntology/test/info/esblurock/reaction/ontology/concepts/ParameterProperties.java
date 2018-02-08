package info.esblurock.reaction.ontology.concepts;

import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterValueInformation;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class ParameterProperties {

	@Test
	public void test() {
		String parameter = "dataset:BurnerPlateDiameter";
		PrimitiveParameterValueInformation info = ConceptParsing.fillParameterInfo(parameter);
		System.out.println(info.toString());
	}

}
