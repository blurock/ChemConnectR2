package info.esblurock.reaction.core.server.db.extract;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.poi.ss.usermodel.charts.ValueAxis;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataObjectLink;
import info.esblurock.reaction.chemconnect.core.data.dataset.DatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.CompoundDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DatasetInformationFromOntology;
import info.esblurock.reaction.chemconnect.core.data.transfer.ElementsOfASetOfMainStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveInterpretedInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.RecordInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.InterpretData;
import info.esblurock.reaction.core.server.initialization.CreateDefaultObjectsFactory;
import info.esblurock.reaction.io.db.QueryBase;
import info.esblurock.reaction.io.metadata.StandardDatasetMetaData;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class ExtractCatalogInformation {
	
	
	public static DatabaseObjectHierarchy getCatalogObject(String id, String type) {
		DataElementInformation element = new DataElementInformation(type, 
				null, true, 0, null, null,null);
		return getDatabaseObjectAndSubElements(id,element,true);
	}
	public static DatabaseObjectHierarchy getDatabaseObjectAndSubElements(String id, 
			DataElementInformation dataelement, boolean asSinglet) {
		ClassificationInformation classify = DatasetOntologyParsing.getIdentificationInformation(null, dataelement);
		String type = dataelement.getDataElementName();
		List<DataElementInformation> substructures = DatasetOntologyParsing.subElementsOfStructure(type);
		
		//System.out.println("DatasetOntologyParsing.subElementsOfStructure: " + substructures.size());
		//System.out.println("DatasetOntologyParsing.subElementsOfStructure\n" + substructures);
		//System.out.println("DatasetOntologyParsing.subElementsOfStructure: end");
		
		DatabaseObjectHierarchy hierarchy = null;
		try {
			InterpretData interpret = InterpretData.valueOf(classify.getDataType());
			if(asSinglet) {
				DatabaseObject obj = interpret.readElementFromDatabase(id);
				hierarchy = new DatabaseObjectHierarchy(obj);
				Map<String,Object> mapping = interpret.createYamlFromObject(obj);
				for(DataElementInformation element : substructures) {
					String identifier = element.getIdentifier();
					Object elementobj = mapping.get(identifier);
					if(elementobj != null) {
						if(elementobj.getClass().getCanonicalName().compareTo(String.class.getCanonicalName()) == 0) {
							String newid = (String) elementobj;
							DatabaseObjectHierarchy sub = getDatabaseObjectAndSubElements(newid,element,element.isSinglet());
							if(sub != null) {
								hierarchy.addSubobject(sub);
							}
						} else if(ConceptParsing.isAArrayListDataObject(element.getDataElementName())) {
							System.out.println("getDatabaseObjectAndSubElements: ArrayList: " + element.getDataElementName());
						} else {
							System.out.println("getDatabaseObjectAndSubElements: \n" + obj.toString());
							System.out.println("getDatabaseObjectAndSubElements: \n" + element.toString());
							System.out.println("getDatabaseObjectAndSubElements: \n" + elementobj.toString());
						}
					} else {
						System.out.println("--------------------------------------");
						System.out.println("Couldn't find Identifier: \n" + element.toString());
						System.out.println("Couldn't find Identifier: " + classify.getDataType());
						System.out.println("Couldn't find Identifier: " + type);
						System.out.println("Couldn't find Identifier: " + identifier);
						System.out.println("Couldn't find Identifier: " + mapping.keySet());
						System.out.println("--------------------------------------");
					}
				}
			} else {
				InterpretData multiinterpret = InterpretData.valueOf("ChemConnectCompoundMultiple");
				ChemConnectCompoundMultiple multi = (ChemConnectCompoundMultiple) multiinterpret.readElementFromDatabase(id);
				hierarchy = new DatabaseObjectHierarchy(multi);
				HashSet<String> ids = multi.getIds();
				for(String objID : ids) {
					DatabaseObjectHierarchy sub = getDatabaseObjectAndSubElements(objID,dataelement,true);
					hierarchy.addSubobject(sub);
				}
			}
		} catch(IllegalArgumentException ex) {
			//System.out.println("No interpret: " + classify.getDataType());
			//System.out.println(ex.getClass().getSimpleName());
		} catch(IOException ex) {
			System.out.println("IOException: '" + classify.getDataType() + "' with ID: '" + id + "' singlet(" + asSinglet + ")");
			System.out.println(ex.toString());
			
		} catch(Exception ex) {
			System.out.println("Unknown Exception: " + classify.getDataType());
			System.out.println("Unknown Exception: \n" + ex.toString());
		}
		
		return hierarchy;
	}


	public static DatasetInformationFromOntology extract(String identifier, String dataElementName) throws IOException {
		DataElementInformation dataelement = new DataElementInformation(dataElementName, null, true, 0, null, null,
				null);
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
		System.out.println("extractRecordElementsFromStructure: " + clsinfo.getIdName() + "(" + clsinfo.getIdName()
				+ "): " + clsinfo.getDataType());
		System.out.println("extractRecordElementsFromStructure: map: " + map);
		ElementsOfASetOfMainStructure structure = new ElementsOfASetOfMainStructure(clsinfo.getIdName(),
				clsinfo.getIdentifier(), clsinfo.getDataType());
		for (DataElementInformation element : subelements) {
			System.out.println("extractRecordElementsFromStructure: Element: " + element.toString());
			String link = element.getLink();
			if (link.compareTo("dcat:record") == 0) {
				DataElementInformation subelement = DatasetOntologyParsing
						.getSubElementStructureFromIDObject(element.getDataElementName());
				System.out.println("extractRecordElementsFromStructure: ID: " + subelement.toString());
				ClassificationInformation subclassify = DatasetOntologyParsing
						.getIdentificationInformation(subelement.getDataElementName());
				System.out.println("extractRecordElementsFromStructure: ID: " + subclassify.toString());
				if (element.isSinglet()) {
					String identifier = (String) map.get(element.getIdentifier());
					addStructure(identifier, subclassify, subelement, structure);
				} else {
					@SuppressWarnings("unchecked")
					List<String> lst = (List<String>) map.get(element.getIdentifier());
					for (String identifier : lst) {
						addStructure(identifier, subclassify, subelement, structure);
					}
				}
			} else {
				System.out.println("extractRecordElementsFromStructure: link:");
			}
		}
		RecordInformation record = new RecordInformation(object, clsinfo.getDataType(), object.getIdentifier(),
				structure);
		return record;
	}

	private static void addStructure(String identifier, ClassificationInformation subclassify,
			DataElementInformation subelement, ElementsOfASetOfMainStructure structure) throws IOException {
		System.out.println("extractRecordElementsFromStructure: begin");
		System.out.println("extractRecordElementsFromStructure: subelement: " + subelement.toString());
		System.out.println("extractRecordElementsFromStructure: Identifier: " + identifier);
		String dataElementName = subclassify.getDataType();
		System.out.println("extractRecordElementsFromStructure: dataElementName: " + dataElementName);
		InterpretData subinterpret = InterpretData.valueOf(dataElementName);
		System.out.println("extractRecordElementsFromStructure: dataElementName: " + subinterpret.canonicalClassName());
		DatabaseObject obj = null;
		try {
			obj = subinterpret.readElementFromDatabase(identifier);
		} catch (IOException ex) {
			System.out.println("No structure found for identifier: " + identifier);
		}
		if (obj != null) {
			System.out.println(
					"extractRecordElementsFromStructure: dataElementName: " + obj.getClass().getCanonicalName());
			System.out.println("extractRecordElementsFromStructure: dcat:record " + subelement.getIdentifier());
			System.out.println("extractRecordElementsFromStructure: dcat:record " + obj.getClass().getCanonicalName());
			Map<String, Object> submap = subinterpret.createYamlFromObject(obj);
			CompoundDataStructureInformation substructures = extractCompoundDataStructure(obj, submap, subelement);
			substructures.setObject(obj);
			System.out.println("extractRecordElementsFromStructure: Substructures " + substructures);
			structure.addCompoundStructure(substructures);
		} else {
			System.out.println("No structure added");
		}

	}

	/*
	 * From a ChemConnectCompoundDataStructure the set of primitive objects are
	 * collected
	 * 
	 */
	private static CompoundDataStructureInformation extractCompoundDataStructure(DatabaseObject obj,
			Map<String, Object> map, DataElementInformation element) throws IOException {
		ChemConnectCompoundDataStructure primitives = DatasetOntologyParsing
				.subElementsOfStructure(element.getDataElementName());
		CompoundDataStructureInformation compound = new CompoundDataStructureInformation(
				element.getChemconnectStructure(), element.getDataElementName());
		for (DataElementInformation primitive : primitives) {
			System.out.println("extractCompoundDataStructure primitive=\n" + primitive);
			String valueType = primitive.getDataElementName();
			List<String> primitivetype = DatasetOntologyParsing
					.getPrimitiveStructureType(primitive.getDataElementName());
			String primitiveclass = DatasetOntologyParsing.getPrimitiveStructureClass(primitive.getDataElementName());
			String objid = obj.getIdentifier() + "-" + primitive.getSuffix();
			System.out.println("extractCompoundDataStructure primitive id=" + objid);
			String simplestructure = "dataset:ChemConnectPrimitiveDataStructure";
			String compoundstructure = "dataset:ChemConnectPrimitiveCompound";
			if (primitivetype.contains(simplestructure)) {
				System.out.println("dataset:ChemConnectPrimitiveDataStructure");
				if (primitive.isSinglet()) {
					String value = (String) map.get(primitive.getIdentifier());
					System.out.println("dataset:ChemConnectPrimitiveDataStructure: '" + value + "'  non-null");
					if (value != null) {
						System.out.println("dataset:ChemConnectPrimitiveDataStructure: size" + value.length());
						if (value.length() > 0) {
							StringTokenizer tok = new StringTokenizer(value, ",");
							while (tok.hasMoreTokens()) {
								String subvalue = tok.nextToken();
								DatabaseObject baseobj = new DatabaseObject(obj);
								baseobj.setIdentifier(objid);
								PrimitiveDataStructureInformation primitivedata = new PrimitiveDataStructureInformation(
										baseobj, valueType, primitiveclass, subvalue);
								compound.addPrimitive(primitivedata);
							}
						} else {
							DatabaseObject baseobj = new DatabaseObject(obj);
							baseobj.setIdentifier(objid);
							PrimitiveDataStructureInformation primitivedata = new PrimitiveDataStructureInformation(
									baseobj, valueType, primitiveclass, value);
							compound.addPrimitive(primitivedata);
						}
					} else {
						System.out.println("dataset:ChemConnectPrimitiveDataStructure: " + map);
					}
				} else {
					DatabaseObject baseobj = new DatabaseObject(obj);
					baseobj.setIdentifier(objid);
					Object mapobj = map.get(primitive.getIdentifier());
					if (mapobj != null) {
						String valueS = (String) mapobj.toString();
						PrimitiveDataStructureInformation primitivedata = new PrimitiveDataStructureInformation(baseobj,
								valueType, primitiveclass, valueS);
						compound.addPrimitive(primitivedata);
					}
				}
			} else if (primitivetype.contains(compoundstructure)) {
				System.out.println("ChemConnectPrimitiveCompound:   " + primitive.getChemconnectStructure());
				InterpretData subinterpret = InterpretData.valueOf(primitive.getChemconnectStructure());
				if (subinterpret != null) {
					try {
						DatabaseObject subobj = subinterpret.readElementFromDatabase(objid);
						System.out.println("ChemConnectPrimitiveCompound:   " + subobj.toString());
						PrimitiveDataStructureInformation subprimitive = new PrimitiveDataStructureInformation(subobj,
								primitive.getDataElementName(), primitive.getChemconnectStructure(), objid);
						PrimitiveInterpretedInformation interpreted = new PrimitiveInterpretedInformation(subprimitive,
								subobj);
						System.out.println("ChemConnectPrimitiveCompound:\n" + interpreted.toString());
						compound.addPrimitive(interpreted);
					} catch (IOException ex) {
						System.out.println("No elements found:   " + ex.toString());
					}
				} else {
					System.out
							.println("Sub object not found to be interpreted: " + primitive.getChemconnectStructure());
					@SuppressWarnings("unchecked")
					Map<String, Object> submap = (Map<String, Object>) map.get(primitive.getIdentifier());
					InterpretData objinterpret = InterpretData.valueOf("DatabaseObject");
					DatabaseObject subobj = objinterpret.fillFromYamlString(obj, submap, obj.getSourceID());
					CompoundDataStructureInformation subcompound = extractCompoundDataStructure(subobj, submap,
							primitive);
					System.out.println("ChemConnectPrimitiveCompound:   Compound=\n" + subcompound.toString());
					compound.addCompound(subcompound);
				}
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
	
	public static DatabaseObjectHierarchy getDatabaseObjectHierarchy(String catid) throws IOException {
		DatabaseObjectHierarchy hierarchy = ExtractCatalogInformation.getCatalogObject(catid, "dataset:DatasetCatalogHierarchy");
		if(hierarchy != null) {
		InterpretData interpret = InterpretData.valueOf("DatasetCatalogHierarchy");
		//String classname = interpret.canonicalClassName();
		Map<String,Object> mapping = interpret.createYamlFromObject(hierarchy.getObject());
		//Set<String> keys = mapping.keySet();
		String objlinkid = (String) mapping.get(StandardDatasetMetaData.parameterObjectLinkS);
		DatabaseObjectHierarchy multihier = hierarchy.getSubObject(objlinkid);
		ChemConnectCompoundMultiple multi = (ChemConnectCompoundMultiple) multihier.getObject();
		HashSet<String> ids = multi.getIds();
		for(String lnkid : ids) {
			DatabaseObjectHierarchy subhier = multihier.getSubObject(lnkid);
			if(subhier != null) {
				DataObjectLink lnk = (DataObjectLink) subhier.getObject();
				String type = lnk.getLinkConcept();
				if(type.compareTo(MetaDataKeywords.linkSubCatalog) == 0) {
					String subid = lnk.getDataStructure();
					DatabaseObjectHierarchy subhierarchy = getDatabaseObjectHierarchy(subid);
					hierarchy.addSubobject(subhierarchy);
				}
			} else {
				System.out.println("getDatabaseObjectHierarchy: id not found " + lnkid);
				System.out.println("getDatabaseObjectHierarchy: id not found " + ids);
				System.out.println("getDatabaseObjectHierarchy: catalog " + catid);
			}
		}
		} else {
			throw new IOException("DatasetCatalogHierarchy not found: " + catid);
		}
		return hierarchy;
	}
	public static DatabaseObjectHierarchy createNewCatalogHierarchy(DatabaseObject obj, 
			String id, String onelinedescription,String sourceID, String catagorytype)
			throws IOException {
		DatabaseObject newobj = new DatabaseObject(obj);
		newobj.setSourceID(sourceID);
		String classname = DatasetCatalogHierarchy.class.getCanonicalName();
		DatasetCatalogHierarchy catalog = (DatasetCatalogHierarchy) QueryBase.getDatabaseObjectFromIdentifier(classname,
				obj.getIdentifier());
		DatabaseObjectHierarchy subs = CreateDefaultObjectsFactory.fillDatasetCatalogHierarchy(catalog, newobj, id,
				onelinedescription,catagorytype);
		return subs;
	}

}
