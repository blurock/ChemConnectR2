package info.esblurock.reaction.ontology.concepts;

//import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.concepts.AttributesOfObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterValueInformation;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class ParameterProperties {

	@Test
	public void test() {
		/*
		String parameter = "dataset:UnburntGasTemperature";
		PrimitiveParameterValueInformation info = ConceptParsing.fillParameterInfo(parameter);
		System.out.println(info.toString());
		*/
		String parameter1 = "dataset:ThermocoupleType";
		PrimitiveParameterValueInformation info1 = ConceptParsing.fillParameterInfo(parameter1);
		System.out.println(info1.toString());
		
		String parameter2 = "dataset:IonizationSource";
		PrimitiveParameterValueInformation info2 = ConceptParsing.fillParameterInfo(parameter2);
		System.out.println(info2.toString());
		
		/*
		AttributesOfObject attributes = ConceptParsing.parseAttributes("dataset:HeatFluxBurner");
		System.out.println("Attributes            -------------------------------------------------------------");
		System.out.println(attributes.toString());
		Set<String> set = attributes.getSubsystemsOfAttributes().keySet();
		for(String attr : set) {
			PrimitiveParameterValueInformation attrinfo = ConceptParsing.fillParameterInfo(attr);
			System.out.println(attrinfo.toString());
			
		}
		*/
	}

}
