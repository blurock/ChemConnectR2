package info.esblurock.reaction.core.server.db.rdf;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.core.server.db.InterpretData;
import info.esblurock.reaction.core.server.db.gcs.StoreObject;
import info.esblurock.reaction.ontology.DatasetOntologyParsing;

public class WriteDatabaseObjectRDF {

	public static void writeRDF(DatabaseObject obj, StoreObject store) throws IOException {
		String classname = obj.getClass().getSimpleName();

		InterpretData interpret = InterpretData.valueOf(classname);
		Map<String, Object> map = interpret.createYamlFromObject(obj);

		store.storeObjectRDF(obj);
		List<DataElementInformation> lst = DatasetOntologyParsing.elementsFromSimpleType(classname);
		store.isA(classname);
		for (DataElementInformation info : lst) {
			DataElementInformation subelement = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(info.getDataElementName());
			//System.out.println("writeRDF: " + info.getIdentifier());
			//System.out.println("writeRDF: " + map.get(info.getIdentifier().toString()));
			//System.out.println("writeRDF: " + map.keySet());
			Object valueobj = map.get(info.getIdentifier());
			if (valueobj != null) {
				if (valueobj.getClass().getSimpleName().compareTo(String.class.getSimpleName()) == 0) {

					String value = (String) valueobj;
					//System.out.println("writeRDF: " + value);
					if (info.isID()) {
						if (info.isSinglet()) {
							store.storeStringTypeInfoRDF(info.getIdentifier(), value, info.getDataElementName());
						} else {
							HashSet<String> keywords = interpret.parseKeywords(value);
							for (String name : keywords) {
								store.storeStringTypeInfoRDF(info.getIdentifier(), name, info.getDataElementName());
							}
						}

					} else if (info.isKeywords()) {
						HashSet<String> keywords = interpret.parseKeywords(value);
						for (String name : keywords) {
							store.storeStringRDF(info.getIdentifier(), name);
						}
					} else if (subelement != null) {
						store.storeStringRDF(subelement.getIdentifier(), value);
					} else {
						store.storeStringRDF(info.getIdentifier(), value);
					}
				} else {
					System.out.println("writeRDF  not stored: " + valueobj.toString());
				}
			}
		}
	}
}
