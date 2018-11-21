package info.esblurock.reaction.ontology.concepts;

//import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.concepts.AttributeDescription;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterValueInformation;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class Observations {

	@Test
	public void test() {
		Set<String> measure = ConceptParsing.setOfObservationsForSubsystem("dataset:HeatFluxBurner",true);
		System.out.println("Measure Observations\n" + measure);
		Set<String> dimension = ConceptParsing.setOfObservationsForSubsystem("dataset:HeatFluxBurner",false);
		System.out.println("Dimension Observations\n" + dimension);
		
		for(String parameter : dimension) {
			Set<AttributeDescription> set = ConceptParsing.totalSetOfAttributesInConcept(parameter);
			System.out.println(set);
			for(AttributeDescription descr : set) {
				PrimitiveParameterValueInformation param = ConceptParsing.fillParameterInfo(descr.getAttributeName());
				System.out.println(param);
			}
		}
		
		Set<String> set2 = ConceptParsing.subsystemsForSubsystem("dataset:HeatFluxBurner");
		System.out.println("dataset:HeatFluxBurner\n" + set2);
		Set<String> set3 = ConceptParsing.setOfObservationsForProtocol("dataset:RapidCompressionMachineReportingProtocol",false);
		System.out.println("dataset:RapidCompressionMachineReportingProtocol input\n" + set3);
		Set<String> set4 = ConceptParsing.setOfObservationsForProtocol("dataset:RapidCompressionMachineReportingProtocol",true);
		System.out.println("dataset:RapidCompressionMachineReportingProtocol output\n" + set4);
	}

}
