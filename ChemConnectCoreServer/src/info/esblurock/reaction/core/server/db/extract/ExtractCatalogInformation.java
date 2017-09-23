package info.esblurock.reaction.core.server.db.extract;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DatasetInformationFromOntology;
import info.esblurock.reaction.chemconnect.core.data.transfer.ElementsOfASetOfMainStructure;
import info.esblurock.reaction.io.dataset.InterpretData;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class ExtractCatalogInformation {

	public static DatasetInformationFromOntology extract(String identifier, String dataElementName) throws IOException {
		DataElementInformation dataelement = new DataElementInformation(dataElementName, null,true, 0, null, null);
		ClassificationInformation classify = DatasetOntologyParsing.getIdentificationInformation(null, dataelement);
		List<DataElementInformation> substructures = DatasetOntologyParsing.getSubElementsOfStructure(dataElementName);
		InterpretData interpret = InterpretData.valueOf(classify.getDataType());
		DatabaseObject obj = interpret.readElementFromDatabase(identifier);
		DatasetInformationFromOntology yaml = new DatasetInformationFromOntology(dataElementName,obj,classify,substructures);
		return yaml;
	}
	
	public static ElementsOfASetOfMainStructure extract(ClassificationInformation classify, SingleQueryResult result) throws IOException {
		ElementsOfASetOfMainStructure structures = null;
		String dataElementName = classify.getIdName();
		List<DataElementInformation> substructures = DatasetOntologyParsing.getSubElementsOfStructure(dataElementName);
		InterpretData interpret = InterpretData.valueOf(classify.getDataType());
		for(DatabaseObject obj : result.getResults()) {
			Map<String,Object> map = interpret.createYamlFromObject(obj);
			for(DataElementInformation info : substructures) {
				String chemconnectStructure = info.getChemconnectStructure();
				String id = info.getIdentifier();
				Object subobj = map.get(id);
				System.out.println("Structure: " + chemconnectStructure);
				System.out.println("Structure: " + subobj);
					}
		}
		
		return structures;
	}
}
