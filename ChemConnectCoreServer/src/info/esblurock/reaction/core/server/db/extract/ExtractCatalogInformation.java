package info.esblurock.reaction.core.server.db.extract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataObjectLink;
import info.esblurock.reaction.chemconnect.core.data.dataset.DatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.metadata.StandardDatasetMetaData;
import info.esblurock.reaction.chemconnect.core.data.query.ListOfQueries;
import info.esblurock.reaction.chemconnect.core.data.query.QueryPropertyValue;
import info.esblurock.reaction.chemconnect.core.data.query.SetOfQueryPropertyValues;
import info.esblurock.reaction.chemconnect.core.data.query.SetOfQueryResults;
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
import info.esblurock.reaction.ontology.DatasetOntologyParsing;
import info.esblurock.reaction.ontology.QueryBase;
import info.esblurock.reaction.ontology.QueryFactory;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class ExtractCatalogInformation {
	
	public static DatabaseObjectHierarchy getTopCatalogObject(String id, String type) throws IOException {
		DataElementInformation element = new DataElementInformation(type, 
				null, true, 0, null, null,null);
		ClassificationInformation classify = DatasetOntologyParsing.getIdentificationInformation(null, element);
		InterpretData interpret = InterpretData.valueOf(classify.getDataType());
		DatabaseObject subobj = interpret.readElementFromDatabase(id);
		DatabaseObject obj = findTopObject(subobj);
		String dtype = DatasetOntologyParsing.getTypeFromDataType(obj.getClass().getSimpleName());
		DatabaseObjectHierarchy hierarchy = getObjectHierarchy(obj.getIdentifier(),dtype, obj.getClass().getSimpleName(),true);
		return hierarchy;
	}
	
	public static DatabaseObject findTopObject(DatabaseObject subobject) throws IOException {
		DatabaseObject ans = subobject;
		boolean assignable = info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure.class.isAssignableFrom(subobject.getClass());
		if(assignable) {
			info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure compound 
				= (info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure) subobject;
			String classname = subobject.getClass().getSimpleName();
			ArrayList<String> lst = DatasetOntologyParsing.asSubObject(classname);
			DatabaseObject parent = null;
			for(String name : lst ) {
				if(parent == null) {
					try {
						InterpretData interpret = InterpretData.valueOf(name);
						if(interpret != null) {
							parent = interpret.readElementFromDatabase(compound.getParentLink());
						}
					} catch(Exception ex) {
						
					}

				}
			}
			ans = findTopObject(parent);
		}
		return ans;
	}
	
	public static DatabaseObjectHierarchy getCatalogObject(String id, String type) {
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(type);
		return getDatabaseObjectAndSubElements(id,element,true);
	}
	
	public static DatabaseObjectHierarchy getDatabaseObjectAndSubElements(String id, 
			DataElementInformation dataelement, boolean asSinglet) {
		return getObjectHierarchy(id,dataelement.getDataElementName(),dataelement.getChemconnectStructure(),asSinglet);
	}
	public static DatabaseObjectHierarchy getObjectHierarchy(String id, String t, String type, boolean asSinglet) {
		List<DataElementInformation> substructures = DatasetOntologyParsing.subElementsOfStructure(t);
		DatabaseObjectHierarchy hierarchy = null;
		try {
			InterpretData interpret = InterpretData.valueOf(type);
			if(asSinglet) {
				DatabaseObject obj = interpret.readElementFromDatabase(id);
				hierarchy = readSingletInformation(obj, interpret, substructures);
			} else {
				InterpretData multiinterpret = InterpretData.valueOf("ChemConnectCompoundMultiple");
				ChemConnectCompoundMultiple multi = (ChemConnectCompoundMultiple) multiinterpret.readElementFromDatabase(id);
				hierarchy = new DatabaseObjectHierarchy(multi);
				String parentid = multi.getIdentifier();
				ClassificationInformation classification = DatasetOntologyParsing.getIdentificationInformation(multi.getType());
				List<DataElementInformation> mulitsubstructures = DatasetOntologyParsing.subElementsOfStructure(t);
				InterpretData clsinterpret = InterpretData.valueOf(classification.getDataType());
				String classtype = clsinterpret.canonicalClassName();
				SetOfQueryPropertyValues values = new SetOfQueryPropertyValues();
				QueryPropertyValue value1 = new QueryPropertyValue("parentLink",parentid);
				values.add(value1);
				ListOfQueries queries = QueryFactory.accessQueryForUser(classtype, multi.getOwner(), values);
				SetOfQueryResults results;
				try {
					results = QueryBase.StandardSetOfQueries(queries);
					List<DatabaseObject> objs = results.retrieveAndClear();
					for(DatabaseObject obj: objs) {
						DatabaseObjectHierarchy subhier = readSingletInformation(obj, interpret, mulitsubstructures);
						hierarchy.addSubobject(subhier);
					}
				} catch (ClassNotFoundException e) {
					throw new IOException("getDatabaseObjectAndSubElements Class not found: " + classtype);
				}
			}
		} catch(IllegalArgumentException ex) {
			//System.out.println("No interpret: " + classify.getDataType());
			//System.out.println(ex.getClass().getSimpleName());
		} catch(IOException ex) {
			System.out.println("IOException: '" + type + "' with ID: '" + id + "' singlet(" + asSinglet + ")");
			System.out.println(ex.toString());
			
		}
		/*catch(Exception ex) {
			System.out.println("Unknown Exception: " + classify.getDataType());
			System.out.println("Unknown Exception: \n" + ex.toString());
		}
		*/
		return hierarchy;
	}

	private static DatabaseObjectHierarchy readSingletInformation(DatabaseObject obj, 
			InterpretData interpret,
			List<DataElementInformation> substructures) throws IOException {
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(obj);
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
					System.out.println("getDatabaseObjectAndSubElements: obj\n" + obj.toString());
					System.out.println("getDatabaseObjectAndSubElements: element\n" + element.toString());
					System.out.println("getDatabaseObjectAndSubElements: elementobj\n" 
							+ elementobj.getClass().getCanonicalName());
					System.out.println("getDatabaseObjectAndSubElements: elementobj\n" 
							+ elementobj.toString());
				}
			} else {
				System.out.println("--------------------------------------");
				System.out.println("Couldn't find Identifier: \n" + element.toString());
				System.out.println("Couldn't find Identifier: " + interpret.canonicalClassName());
				System.out.println("Couldn't find Identifier: " + identifier);
				System.out.println("Couldn't find Identifier: " + mapping.keySet());
				System.out.println("--------------------------------------");
			}
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
			String simplestructure = MetaDataKeywords.chemConnectPrimitiveDataStructure;
			String compoundstructure = MetaDataKeywords.chemConnectPrimitiveCompound;
			if (primitivetype.contains(simplestructure)) {
				if (primitive.isSinglet()) {
					String value = (String) map.get(primitive.getIdentifier());
					if (value != null) {
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
				InterpretData subinterpret = InterpretData.valueOf(primitive.getChemconnectStructure());
				if (subinterpret != null) {
					try {
						DatabaseObject subobj = subinterpret.readElementFromDatabase(objid);
						PrimitiveDataStructureInformation subprimitive = new PrimitiveDataStructureInformation(subobj,
								primitive.getDataElementName(), primitive.getChemconnectStructure(), objid);
						PrimitiveInterpretedInformation interpreted = new PrimitiveInterpretedInformation(subprimitive,
								subobj);
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
		DatabaseObjectHierarchy hierarchy = ExtractCatalogInformation.getCatalogObject(catid, 
				MetaDataKeywords.datasetCatalogHierarchy);
		if(hierarchy != null) {
		InterpretData interpret = InterpretData.valueOf("DatasetCatalogHierarchy");
		//String classname = interpret.canonicalClassName();
		Map<String,Object> mapping = interpret.createYamlFromObject(hierarchy.getObject());
		//Set<String> keys = mapping.keySet();
		String objlinkid = (String) mapping.get(StandardDatasetMetaData.parameterObjectLinkS);
		DatabaseObjectHierarchy multihier = hierarchy.getSubObject(objlinkid);
		for(DatabaseObjectHierarchy subhier : multihier.getSubobjects()) {
				DataObjectLink lnk = (DataObjectLink) subhier.getObject();
				String type = lnk.getLinkConcept();
				if(type.compareTo(MetaDataKeywords.linkSubCatalog) == 0) {
					String subid = lnk.getDataStructure();
					DatabaseObjectHierarchy subhierarchy = getDatabaseObjectHierarchy(subid);
					hierarchy.addSubobject(subhierarchy);
				}
		}
		} else {
			throw new IOException("DatasetCatalogHierarchy not found: " + catid);
		}
		return hierarchy;
	}
	
	/*
	 * id: The id of the super catagory hierarchy
	 * obj: The base class, used to get new id
	 * catagorytype: The type of catalog hierarchy
	 * sourceID: The new source ID (overrides sourceID of obj)
	 * onelinedescription: Goes into the title.
	 */
	public static DatabaseObjectHierarchy createNewCatalogHierarchy(DatabaseObject obj,
			String simpleName,
			String id, String onelinedescription,String sourceID, String catagorytype)
			throws IOException {
		DatabaseObject newobj = new DatabaseObject(obj);
		newobj.setSourceID(sourceID);
		String classname = DatasetCatalogHierarchy.class.getCanonicalName();
		DatasetCatalogHierarchy catalog = (DatasetCatalogHierarchy) QueryBase.getDatabaseObjectFromIdentifier(classname,
				id);
		DatabaseObjectHierarchy subs = CreateDefaultObjectsFactory.fillDatasetCatalogHierarchy(catalog, simpleName, newobj,
				onelinedescription,catagorytype);
		return subs;
	}

}
