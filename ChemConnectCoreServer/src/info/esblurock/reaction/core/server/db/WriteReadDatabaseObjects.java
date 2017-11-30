package info.esblurock.reaction.core.server.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructureObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.io.dataset.InterpretData;
import info.esblurock.reaction.io.db.QueryBase;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class WriteReadDatabaseObjects {

	public static void writeChemConnectDataStructureObject(ChemConnectDataStructureObject object) {
		writeDatabaseObjectHierarchy(object.getObjecthierarchy());
	}

	public static void writeDatabaseObjectHierarchy(DatabaseObjectHierarchy objecthierarchy) {
		DatabaseWriteBase.writeDatabaseObject(objecthierarchy.getObject());
		for (DatabaseObjectHierarchy subhierarchy : objecthierarchy.getSubobjects()) {
			writeDatabaseObjectHierarchy(subhierarchy);
		}
	}

	public static void readChemConnectDataStructureObject(String elementType, String identifier) throws IOException {
		System.out.println("------------------------------------------");
		ChemConnectDataStructure chemconnect = DatasetOntologyParsing.getChemConnectDataStructure(elementType);
		ClassificationInformation classification = chemconnect.getClassification();
		InterpretData interpret = InterpretData.valueOf(classification.getDataType());
		String classname = interpret.canonicalClassName();
		DatabaseObject object = QueryBase.getDatabaseObjectFromIdentifier(classname, identifier);
		Map<String, Object> map = interpret.createYamlFromObject(object);
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(object);
		System.out.println("------------------------------------------");
		System.out.println(chemconnect.getRecords().size());
		for (DataElementInformation info : chemconnect.getRecords()) {
			System.out.println("Information: " + info);
			System.out.println("Information: " + map);
			Object mapobject = map.get(info.getIdentifier());
			System.out.println(mapobject.getClass().getSimpleName());
			if (mapobject.getClass().getSimpleName().compareTo("String") == 0) {
				String id = (String) mapobject;
				String chemstructure = info.getChemconnectStructure();
				InterpretData subinterpret = InterpretData.valueOf(chemstructure);
				String canonical = subinterpret.canonicalClassName();
				DatabaseObject obj = null;
				try {
					obj = QueryBase.getDatabaseObjectFromIdentifier(canonical, id);
					DatabaseObjectHierarchy subhierarchy = new DatabaseObjectHierarchy(obj);
					hierarchy.addSubobject(subhierarchy);
					Map<String, Object> submap = subinterpret.createYamlFromObject(obj);
					System.out.println("\t\tMap: " + submap);
					readChemConnectCompoundObject(info, submap, subhierarchy);
				} catch (IOException io) {

				}

			} else {
				System.out.println("MultipleObject" + mapobject);
				String chemstructure = info.getChemconnectStructure();
				InterpretData subinterpret = InterpretData.valueOf(chemstructure);
				String canonical = subinterpret.canonicalClassName();
				ArrayList<String> lst = (ArrayList<String>) mapobject;
				DatabaseObject obj = null;
				for(String name : lst) {
					obj = QueryBase.getDatabaseObjectFromIdentifier(canonical, name);
					DatabaseObjectHierarchy subhierarchy = new DatabaseObjectHierarchy(obj);
					hierarchy.addSubobject(subhierarchy);
					Map<String, Object> submap = subinterpret.createYamlFromObject(obj);
					System.out.println("\t\tMap: " + submap);
					readChemConnectCompoundObject(info, submap, subhierarchy);
				}
			}
		}

		System.out.println("Hierarchy: \n" + hierarchy);
	}

	private static void readChemConnectCompoundObject(DataElementInformation info, Map<String, Object> submap,
			DatabaseObjectHierarchy subhierarchy) {
		System.out.println("readChemConnectCompoundObject: " + info);
		System.out.println("readChemConnectCompoundObject: " + info.getDataElementName());
		ChemConnectCompoundDataStructure subs = DatasetOntologyParsing
				.subElementsOfStructure(info.getDataElementName());
		System.out.println(subs);
		for (DataElementInformation element : subs) {
			if (DatasetOntologyParsing.isChemConnectPrimitiveDataStructure(element.getDataElementName()) == null) {
				System.out.println("Compound Object: " + element);
				if (element.isSinglet()) {
					String idlabel = element.getIdentifier();
					String id = (String) submap.get(idlabel);
					if (id != null) {
						String chemstructure = element.getChemconnectStructure();
						InterpretData subinterpret = InterpretData.valueOf(chemstructure);
						String canonical = subinterpret.canonicalClassName();
						DatabaseObject obj;
						try {
							obj = QueryBase.getDatabaseObjectFromIdentifier(canonical, id);
							DatabaseObjectHierarchy next = new DatabaseObjectHierarchy(obj);
							subhierarchy.addSubobject(next);
						} catch (IOException e) {
							System.out.println("not found");
						}
					}
				} else {
					String idlabel = element.getIdentifier();
					ArrayList<String> lst = (ArrayList<String>) submap.get(idlabel);
					for(String id : lst) {
						String chemstructure = element.getChemconnectStructure();
						InterpretData subinterpret = InterpretData.valueOf(chemstructure);
						String canonical = subinterpret.canonicalClassName();
						DatabaseObject obj;
						try {
							obj = QueryBase.getDatabaseObjectFromIdentifier(canonical, id);
							DatabaseObjectHierarchy next = new DatabaseObjectHierarchy(obj);
							subhierarchy.addSubobject(next);
						} catch (IOException e) {
							System.out.println("not found");
						}						
					}
				}

			} else {
				System.out.println("Simple: " + element.getDataElementName());
			}

		}
	}

}
