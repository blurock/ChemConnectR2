package info.esblurock.reaction.core.server.db.rdf;

import java.io.IOException;
import java.util.List;
import java.util.Map;


import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.io.dataset.InterpretData;
import info.esblurock.reaction.io.rdf.StoreObject;
import info.esblurock.reaction.ontology.dataset.DataElementInformation;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class WriteDatabaseObjectRDF {

	
	public static void writeRDF(DatabaseObject obj, StoreObject store) throws IOException {
		String classname = obj.getClass().getSimpleName();
		
		InterpretData interpret = InterpretData.valueOf(classname);
		Map<String,Object> map = interpret.createYamlFromObject(obj);
		
		store.storeObjectRDF(obj);
		List<DataElementInformation> lst = DatasetOntologyParsing.elementsFromSimpleType(classname);
		store.isA(classname);
		for(DataElementInformation info : lst) {
			DataElementInformation subelement = DatasetOntologyParsing.getSubElementStructureFromIDObject(info.getDataElementName());
			String value = (String) map.get(info.getIdentifier());
			if(info.isID()) {
				store.storeStringTypeInfoRDF(subelement.getIdentifier(), value,info.getDataElementName());
			} else if(subelement != null) {
				store.storeStringRDF(subelement.getIdentifier(), value);
			} else {
				store.storeStringRDF(info.getIdentifier(),value);
			}
		}
		
	}
}
