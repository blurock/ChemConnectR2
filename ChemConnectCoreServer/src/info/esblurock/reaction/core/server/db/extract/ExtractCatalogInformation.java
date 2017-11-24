package info.esblurock.reaction.core.server.db.extract;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.CompoundDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DatasetInformationFromOntology;
import info.esblurock.reaction.chemconnect.core.data.transfer.ElementsOfASetOfMainStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.RecordInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.io.dataset.InterpretData;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class ExtractCatalogInformation {

	public static DatasetInformationFromOntology extract(String identifier, String dataElementName) throws IOException {
		DataElementInformation dataelement = new DataElementInformation(dataElementName, null, true, 0, null, null,null);
		ClassificationInformation classify = DatasetOntologyParsing.getIdentificationInformation(null, dataelement);
		List<DataElementInformation> substructures = DatasetOntologyParsing.subElementsOfStructure(dataElementName);
		InterpretData interpret = InterpretData.valueOf(classify.getDataType());
		DatabaseObject obj = interpret.readElementFromDatabase(identifier);
		DatasetInformationFromOntology yaml = new DatasetInformationFromOntology(dataElementName, obj, classify,
				substructures);
		return yaml;
	}

	/*
	 * From a ChemConnectDataStructure (Catalog), the sub-elements are extracted.
	 * The information is extracted from the DatabaseObject instance
	 * (createYamlFromObject) to a map The sub-elements are looped: If a sub-element
	 * is an ID pointing to a ChemConnectCompoundDataStructure - The identifier is
	 * extracted from the DatabaseObject map - The corresponding
	 * ChemConnectStructure is identified (element.getChemconnectStructure()) -
	 * Through the InterpretData the corresponding DatabaseObject is read - If the
	 * link type is a record, then the primitive structures are extracted
	 * (extractCompoundDataStructure)
	 * 
	 */
	public static RecordInformation extractRecordElementsFromChemStructure(ClassificationInformation clsinfo,
			ChemConnectCompoundDataStructure subelements, DatabaseObject object) throws IOException {
		InterpretData interpret = InterpretData.valueOf(clsinfo.getDataType());
		Map<String, Object> map = interpret.createYamlFromObject(object);
		System.out.println("extractRecordElementsFromStructure: " + clsinfo.getIdName() 
			+ "(" + clsinfo.getIdName() + "): " + clsinfo.getDataType());
		System.out.println("extractRecordElementsFromStructure: map: " + map);
		ElementsOfASetOfMainStructure structure = new ElementsOfASetOfMainStructure(clsinfo.getIdName(),
				clsinfo.getIdentifier(), 
				clsinfo.getDataType());
		for (DataElementInformation element : subelements) {
			System.out.println("extractRecordElementsFromStructure: Element: " + element.toString());
			if (element.isID()) {
				String link = element.getLink();
				if (link.compareTo("dcat:record") == 0) {
					DataElementInformation subelement = DatasetOntologyParsing.getSubElementStructureFromIDObject(element.getDataElementName());
					System.out.println("extractRecordElementsFromStructure: ID: " + subelement.toString());
					ClassificationInformation subclassify = DatasetOntologyParsing.getIdentificationInformation(subelement.getDataElementName());
					System.out.println("extractRecordElementsFromStructure: ID: " + subclassify.toString());
					String identifier = (String) map.get(element.getIdentifier());
					System.out.println("extractRecordElementsFromStructure: Identifier: " + identifier);
					String dataElementName = subclassify.getDataType();
					System.out.println("extractRecordElementsFromStructure: dataElementName: " + dataElementName);
					InterpretData subinterpret = InterpretData.valueOf(dataElementName);
					System.out.println("extractRecordElementsFromStructure: dataElementName: " + subinterpret.canonicalClassName());
					DatabaseObject obj = subinterpret.readElementFromDatabase(identifier);
					System.out.println("extractRecordElementsFromStructure: dataElementName: " + obj.getClass().getCanonicalName());
					System.out.println("extractRecordElementsFromStructure: dcat:record " + subelement.getIdentifier());
					System.out.println("extractRecordElementsFromStructure: dcat:record " + obj.getClass().getCanonicalName());
					Map<String, Object> submap = subinterpret.createYamlFromObject(obj);
					CompoundDataStructureInformation substructures = extractCompoundDataStructure(obj, submap, subelement);
					System.out.println("extractRecordElementsFromStructure: Substructures " + substructures);					
					structure.addCompoundStructure(substructures);
				} else {
					System.out.println("extractRecordElementsFromStructure: link:" );
				}
			}
		}
		RecordInformation record = new RecordInformation(object, clsinfo.getDataType(), object.getIdentifier(), structure);
		return record;
	}

	/*
	 * From a ChemConnectCompoundDataStructure the set of primitive objects are
	 * collected
	 * 
	 */
	private static CompoundDataStructureInformation extractCompoundDataStructure(DatabaseObject obj, Map<String, Object> map,
			DataElementInformation element) throws IOException {
		ChemConnectCompoundDataStructure primitives = DatasetOntologyParsing
				.subElementsOfStructure(element.getDataElementName());
		CompoundDataStructureInformation compound = new CompoundDataStructureInformation(element.getChemconnectStructure(),element.getDataElementName());
		for (DataElementInformation primitive : primitives) {
			String primitivetype = DatasetOntologyParsing.getPrimitiveStructureType(primitive.getDataElementName());
			String primitiveclass = DatasetOntologyParsing.getPrimitiveStructureClass(primitivetype);
			String identifier = primitive.getIdentifier();
			String value = (String) map.get(primitive.getIdentifier());
			System.out.println("extractCompoundDataStructure:   " + primitivetype + ", " + primitiveclass + ", " + identifier + ", " + value);
			if (primitiveclass.compareTo("dataset:ChemConnectPrimitiveDataStructure") == 0
					||
					primitivetype.compareTo("dataset:ChemConnectPrimitiveDataStructure") == 0) {
				System.out.println("dataset:ChemConnectPrimitiveDataStructure");
				if (primitive.isSinglet()) {
					StringTokenizer tok = new StringTokenizer(value, ",");
					while (tok.hasMoreTokens()) {
						String subvalue = tok.nextToken();
						PrimitiveDataStructureInformation primitivedata = new PrimitiveDataStructureInformation(
								primitivetype, identifier, subvalue);
						compound.addPrimitive(primitivedata);
					}
				} else {
					PrimitiveDataStructureInformation primitivedata = new PrimitiveDataStructureInformation(
							primitivetype, identifier, value);
					compound.addPrimitive(primitivedata);
				}
			} else if(primitiveclass.compareTo("dataset:ChemConnectPrimitiveCompound") == 0
					||
					primitivetype.compareTo("dataset:ChemConnectPrimitiveCompound") == 0) {
				System.out.println("ChemConnectPrimitiveCompound");
				InterpretData subinterpret = InterpretData.valueOf(primitivetype);
				DatabaseObject subobj = subinterpret.readElementFromDatabase(identifier);
				Map<String, Object> submap = subinterpret.createYamlFromObject(subobj);
				CompoundDataStructureInformation subcompound = extractCompoundDataStructure(subobj,submap,primitive);
				compound.addCompound(subcompound);
			}
		}
		return compound;

	}

	public static ElementsOfASetOfMainStructure extract(ClassificationInformation classify, SingleQueryResult result)
			throws IOException {
		ElementsOfASetOfMainStructure structures = null;
		String dataElementName = classify.getIdName();
		List<DataElementInformation> substructures = DatasetOntologyParsing.subElementsOfStructure(dataElementName);
		InterpretData interpret = InterpretData.valueOf(classify.getDataType());
		for (DatabaseObject obj : result.getResults()) {
			Map<String, Object> map = interpret.createYamlFromObject(obj);
			for (DataElementInformation info : substructures) {
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
