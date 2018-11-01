package info.esblurock.reaction.core.server.db;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.io.db.QueryBase;
import info.esblurock.reaction.io.metadata.StandardDatasetMetaData;
import info.esblurock.reaction.chemconnect.core.data.description.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.data.contact.ContactInfoData;
import info.esblurock.reaction.chemconnect.core.data.contact.ContactLocationInformation;
import info.esblurock.reaction.chemconnect.core.data.contact.OrganizationDescription;
import info.esblurock.reaction.chemconnect.core.data.description.DataSetReference;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.contact.PersonalDescription;
import info.esblurock.reaction.chemconnect.core.data.contact.Organization;
import info.esblurock.reaction.chemconnect.core.data.contact.IndividualInformation;
import info.esblurock.reaction.chemconnect.core.data.contact.GPSLocation;
import info.esblurock.reaction.chemconnect.core.data.dataset.Consortium;
import info.esblurock.reaction.chemconnect.core.data.login.UserAccountInformation;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataSpecification;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataObjectLink;
import info.esblurock.reaction.chemconnect.core.data.dataset.ParameterValue;
import info.esblurock.reaction.chemconnect.core.data.dataset.ParameterSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.MeasureParameterSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.DimensionParameterSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.ValueUnits;
import info.esblurock.reaction.chemconnect.core.data.dataset.ObservationCorrespondenceSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.device.SubSystemDescription;
import info.esblurock.reaction.chemconnect.core.data.dataset.PurposeConceptPair;
import info.esblurock.reaction.ontology.OntologyKeys;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.spreadsheet.ConvertInputDataBase;
import info.esblurock.reaction.core.server.db.spreadsheet.ConvertToMatrixOfObjects;
import info.esblurock.reaction.chemconnect.core.data.dataset.DatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.data.dataset.MeasurementParameterValue;
import info.esblurock.reaction.chemconnect.core.data.dataset.DimensionParameterValue;
import info.esblurock.reaction.chemconnect.core.data.dataset.ObservationSpecification;
import info.esblurock.reaction.chemconnect.core.data.methodology.ChemConnectMethodology;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.MatrixSpecificationCorrespondenceSet;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.MatrixSpecificationCorrespondence;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRowTitle;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationMatrixValues;
import info.esblurock.reaction.chemconnect.core.data.contact.ContactHasSite;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockIsolation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationBlockFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheetFull;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;

public enum InterpretData {

	DatabaseObject {
		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			String identifierS = (String) yaml.get(StandardDatasetMetaData.identifierKeyS);
			String ownerS = (String) yaml.get(StandardDatasetMetaData.ownerKeyS);
			String accessS = (String) yaml.get(StandardDatasetMetaData.accessKeyS);

			if (top != null) {
				if (identifierS == null) {
					identifierS = top.getIdentifier();
				}
				if (ownerS == null) {
					ownerS = top.getOwner();
				}
				if (accessS == null) {
					accessS = top.getAccess();
				}
			}
			DatabaseObject obj = new DatabaseObject(identifierS, ownerS, accessS, sourceID);

			return obj;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {

			Map<String, Object> map = new HashMap<String, Object>();

			map.put(StandardDatasetMetaData.identifierKeyS, object.getIdentifier());
			map.put(StandardDatasetMetaData.ownerKeyS, object.getOwner());
			map.put(StandardDatasetMetaData.accessKeyS, object.getAccess());
			map.put(StandardDatasetMetaData.sourceIDS, object.getSourceID());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(DatabaseObject.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return DatabaseObject.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject objcpy = new DatabaseObject(obj);
			objcpy.nullKey();
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(objcpy);
			return hierarchy;
		}



	}, ChemConnectDataStructure {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top,
				Map<String, Object> yaml, String sourceID) throws IOException {
			ChemConnectDataStructure datastructure = null;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String descriptionDataDataS = (String) yaml.get(StandardDatasetMetaData.descriptionDataDataS);			
			String dataSetReferenceS    = (String) yaml.get(StandardDatasetMetaData.dataSetReferenceS);
			String dataObjectLinkS      = (String) yaml.get(StandardDatasetMetaData.parameterObjectLinkS);
			String catalogDataIDS      = (String) yaml.get(StandardDatasetMetaData.DataCatalogIDID);
			String contactHasSiteS      = (String) yaml.get(StandardDatasetMetaData.ContactHasSiteID);
			
			datastructure = new ChemConnectDataStructure(objdata, 
					descriptionDataDataS, dataSetReferenceS,dataObjectLinkS, 
					catalogDataIDS, contactHasSiteS);
			
			
			return datastructure;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ChemConnectDataStructure datastructure = (ChemConnectDataStructure) object;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.descriptionDataDataS, datastructure.getDescriptionDataData());
			map.put(StandardDatasetMetaData.dataSetReferenceS, datastructure.getDataSetReference());
			map.put(StandardDatasetMetaData.parameterObjectLinkS, datastructure.getChemConnectObjectLink());
			map.put(StandardDatasetMetaData.DataCatalogIDID, datastructure.getCatalogDataID());
			map.put(StandardDatasetMetaData.ContactHasSiteID, datastructure.getContactHasSite());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ChemConnectDataStructure.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ChemConnectDataStructure.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject compobj = new DatabaseObject(obj);
			compobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.chemConnectDataStructure);
			String compid = createSuffix(obj, element);
			compobj.setIdentifier(compid);

			DatabaseObjectHierarchy descrhier = InterpretData.DescriptionDataData.createEmptyObject(obj);
			DescriptionDataData descr = (DescriptionDataData) descrhier.getObject();
			
			DatabaseObjectHierarchy sitehier = InterpretData.ChemConnectCompoundMultiple.createEmptyObject(compobj);
			setChemConnectCompoundMultipleType(sitehier,OntologyKeys.contactHasSite);
			DatabaseObjectHierarchy refhier = InterpretData.ChemConnectCompoundMultiple.createEmptyObject(compobj);
			setChemConnectCompoundMultipleType(refhier,OntologyKeys.dataSetReference);
			DatabaseObjectHierarchy lnkhier = InterpretData.ChemConnectCompoundMultiple.createEmptyObject(compobj);
			setChemConnectCompoundMultipleType(lnkhier,OntologyKeys.dataObjectLink);
			DatabaseObjectHierarchy cathier = InterpretData.DataCatalogID.createEmptyObject(obj);
			ChemConnectDataStructure compound = new ChemConnectDataStructure(compobj, 
					descr.getIdentifier(), 
					refhier.getObject().getIdentifier(),
					lnkhier.getObject().getIdentifier(),
					cathier.getObject().getIdentifier(),
					sitehier.getObject().getIdentifier());

			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(compound);
			hierarchy.addSubobject(descrhier);
			hierarchy.addSubobject(refhier);
			hierarchy.addSubobject(lnkhier);
			hierarchy.addSubobject(cathier);
			hierarchy.addSubobject(sitehier);

			return hierarchy;
		}


	},
	ChemConnectCompoundDataStructure {

		@Override
		public DatabaseObject fillFromYamlString(
				DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ChemConnectCompoundDataStructure datastructure = null;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);					
			
			String parentCatalogS = (String) yaml.get(StandardDatasetMetaData.parentCatalogS);
			datastructure = new ChemConnectCompoundDataStructure(objdata, parentCatalogS);
			
			return datastructure;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ChemConnectCompoundDataStructure datastructure = (ChemConnectCompoundDataStructure) object;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.parentCatalogS, datastructure.getParentLink());
			
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ChemConnectCompoundDataStructure.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ChemConnectCompoundDataStructure.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject compobj = new DatabaseObject(obj);
			compobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.chemConnectCompoundDataStructure);
			String compid = createSuffix(obj, element);
			compobj.setIdentifier(compid);

			ChemConnectCompoundDataStructure compound = new ChemConnectCompoundDataStructure(compobj, "no parent");
			compound.setParentLink(obj.getIdentifier());
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(compound);

			return hierarchy;
		}
	}, DataCatalogID {
		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject refobj = new DatabaseObject(obj);
			refobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.datacatalogid);
			String catid = createSuffix(obj, element);
			refobj.setIdentifier(catid);
			
			StringTokenizer tok = new StringTokenizer(obj.getIdentifier(),"-");
			ArrayList<String> names = new ArrayList<String>();
			while(tok.hasMoreElements()) {
				names.add(tok.nextToken());
			}
			String CatalogBaseName = "no catalog base name";
			String DataCatalog = "no catalog";
			String SimpleCatalogName = "no simple name";
			int sze = names.size();
			if(sze >= 3) {
				DataCatalog = names.get(sze-2);
				SimpleCatalogName = names.get(sze-1);
				CatalogBaseName = "";
				for(int i=0;i<sze-3;i++) {
					CatalogBaseName += names.get(i) + "-";
				}
				CatalogBaseName += names.get(sze-3);
			}
			String parentLink = obj.getIdentifier();
			
			ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(refobj,parentLink);
			ArrayList<String> path = new ArrayList<String>();
			DataCatalogID id = new DataCatalogID(structure,
					CatalogBaseName,DataCatalog,SimpleCatalogName,path);
			DatabaseObjectHierarchy refhier = new DatabaseObjectHierarchy(id);
			return refhier;
		}
		
		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			DataCatalogID datastructure = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure objdata = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);					
			
			String CatalogBaseNameS = (String) yaml.get(StandardDatasetMetaData.CatalogBaseName);
			String DataCatalogS = (String) yaml.get(StandardDatasetMetaData.DataCatalog);
			String SimpleCatalogNameS = (String) yaml.get(StandardDatasetMetaData.SimpleCatalogName);
			ArrayList<String> path = interpretMultipleYamlList(StandardDatasetMetaData.catalogPath,yaml);
			datastructure = new DataCatalogID(objdata, CatalogBaseNameS, DataCatalogS, SimpleCatalogNameS,path);
			return datastructure;
		}
		
		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			DataCatalogID datastructure = (DataCatalogID) object;
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.CatalogBaseName, datastructure.getCatalogBaseName());
			map.put(StandardDatasetMetaData.DataCatalog, datastructure.getDataCatalog());
			map.put(StandardDatasetMetaData.SimpleCatalogName, datastructure.getSimpleCatalogName());
			putMultipleInYamlList(StandardDatasetMetaData.catalogPath,map,datastructure.getPath());
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(DataCatalogID.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return DataCatalogID.class.getCanonicalName();
		}
		
	}, ChemConnectCompoundMultiple {

		@Override
		public DatabaseObject fillFromYamlString(
				DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ChemConnectCompoundMultiple multi = null;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);					
			String typeS = (String) yaml.get(StandardDatasetMetaData.elementType);
			multi = new ChemConnectCompoundMultiple(objdata, typeS);
			
			return multi;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				DatabaseObject object) throws IOException {
			ChemConnectCompoundMultiple multi = (ChemConnectCompoundMultiple) object;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);
			map.put(StandardDatasetMetaData.elementType, multi.getType());
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ChemConnectCompoundMultiple.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ChemConnectCompoundMultiple.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject refobj = new DatabaseObject(obj);
			refobj.nullKey();
			String dataType = "noType";
			ChemConnectCompoundMultiple refmult = new ChemConnectCompoundMultiple(refobj,dataType);
			DatabaseObjectHierarchy refhier = new DatabaseObjectHierarchy(refmult);
			return refhier;
		}
		
	}, SpreadSheetBlockIsolation {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject spreadobj = new DatabaseObject(obj);
			spreadobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(StandardDatasetMetaData.spreadSheetBlockIsolation);
			String catid = createSuffix(obj, element);
			spreadobj.setIdentifier(catid);
			
			DatabaseObjectHierarchy obspechier = InterpretData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) obspechier.getObject();

			String startRowType = StandardDatasetMetaData.beginMatrixTopOfSpreadSheet;
			String endRowType = StandardDatasetMetaData.matrixBlockEndAtFileEndOrBlankLine;
			String startColumnType = StandardDatasetMetaData.matrixBlockColumnBeginLeft;
			String endColumnType = StandardDatasetMetaData.matrixBlockColumnEndMaximum;
			String spreadSheetType = StandardDatasetMetaData.chemConnectIsolateBlockEntireMatrix;
			String includeTitle = StandardDatasetMetaData.matrixBlockTitleFirstLine;
			SpreadSheetBlockIsolation interpret = new SpreadSheetBlockIsolation(structure,
					spreadSheetType,
					startRowType,endRowType,startColumnType,endColumnType,
					includeTitle);
			interpret.setIdentifier(catid);
			DatabaseObjectHierarchy interprethier = new DatabaseObjectHierarchy(interpret);
			
			return interprethier;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			SpreadSheetBlockIsolation set = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure objdata = (ChemConnectCompoundDataStructure) 
					interpret.fillFromYamlString(top, yaml, sourceID);
			
			String beginRow = (String) yaml.get(StandardDatasetMetaData.StartRowInMatrix);			
			String endRow = (String) yaml.get(StandardDatasetMetaData.LastRowInMatrix);			
			String beginColumn = (String) yaml.get(StandardDatasetMetaData.StartColumnInMatrix);			
			String endColumn = (String) yaml.get(StandardDatasetMetaData.LastColumnInMatrix);			
			String spreadSheetType = (String) yaml.get(StandardDatasetMetaData.spreadSheetBlockIsolationType);			
			String includeTitle = (String) yaml.get(StandardDatasetMetaData.includeBlockTitle);			
			set = new SpreadSheetBlockIsolation(objdata, 
					beginRow, endRow, beginColumn, endColumn,
					spreadSheetType, includeTitle);
			return set;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject object) throws IOException {
			SpreadSheetBlockIsolation datastructure = (SpreadSheetBlockIsolation) object;
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.StartRowInMatrix,String.valueOf(datastructure.getStartRowType()));
			map.put(StandardDatasetMetaData.LastRowInMatrix, String.valueOf(datastructure.getEndRowType()));
			map.put(StandardDatasetMetaData.StartColumnInMatrix, String.valueOf(datastructure.getStartColumnType()));
			map.put(StandardDatasetMetaData.LastColumnInMatrix, String.valueOf(datastructure.getEndColumnType()));
			map.put(StandardDatasetMetaData.spreadSheetBlockIsolationType, String.valueOf(datastructure.getSpreadSheetBlockIsolationType()));
			map.put(StandardDatasetMetaData.includeBlockTitle, String.valueOf(datastructure.getTitleIncluded()));
			
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(SpreadSheetBlockIsolation.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return SpreadSheetBlockIsolation.class.getCanonicalName();
		}
		
	}, SpreadSheetInputInformation {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject spreadobj = new DatabaseObject(obj);
			spreadobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(StandardDatasetMetaData.spreadSheetInformation);
			String catid = createSuffix(obj, element);
			spreadobj.setIdentifier(catid);
			
			DatabaseObjectHierarchy obspechier = InterpretData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) obspechier.getObject();
			String type = "dataset:CSV";
			String sourceType = "dataset:StringSource";
			String source = "0,0,0,0,0\n0,0,0,0,0\n";
			SpreadSheetInputInformation input = new SpreadSheetInputInformation(structure,type,sourceType,source);
			input.setIdentifier(catid);
			DatabaseObjectHierarchy inputhier = new DatabaseObjectHierarchy(input);
			return inputhier;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			SpreadSheetInputInformation set = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure objdata = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);
					
			String type = (String) yaml.get(StandardDatasetMetaData.spreadSheetSourceType);			
			String source    = (String) yaml.get(StandardDatasetMetaData.fileSourceIdentifier);
			String sourceType    = (String) yaml.get(StandardDatasetMetaData.fileSourceType);

			set = new SpreadSheetInputInformation(objdata, type, sourceType, source);
			return set;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject object) throws IOException {
			SpreadSheetInputInformation datastructure = (SpreadSheetInputInformation) object;
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.spreadSheetSourceType, datastructure.getType());
			map.put(StandardDatasetMetaData.fileSourceIdentifier, datastructure.getSourceID());
			map.put(StandardDatasetMetaData.fileSourceType, datastructure.getSourceType());
			
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(SpreadSheetInputInformation.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return SpreadSheetInputInformation.class.getCanonicalName();
		}
		
	}, ObservationsFromSpreadSheetFull {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject spreadobj = new DatabaseObject(obj);
			spreadobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(MetaDataKeywords.observationsFromSpreadSheetFull);
			String catid = createSuffix(obj, element);
			spreadobj.setIdentifier(catid);
			
			DatabaseObjectHierarchy inputhier = InterpretData.SpreadSheetInputInformation.createEmptyObject(spreadobj);
			DatabaseObjectHierarchy matrixhier = InterpretData.ObservationMatrixValues.createEmptyObject(spreadobj);
			
			DatabaseObjectHierarchy structurehier = InterpretData.ChemConnectDataStructure.createEmptyObject(spreadobj);
			ChemConnectDataStructure structure = (ChemConnectDataStructure) structurehier.getObject();
			ObservationsFromSpreadSheetFull set = new ObservationsFromSpreadSheetFull(structure,
					matrixhier.getObject().getIdentifier(),
					inputhier.getObject().getIdentifier());
			set.setIdentifier(catid);
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(set);
			hierarchy.addSubobject(inputhier);
			hierarchy.addSubobject(matrixhier);
			hierarchy.transferSubObjects(structurehier);
			return hierarchy;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ObservationsFromSpreadSheetFull set = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			ChemConnectDataStructure objdata = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);
					
			String observationMatrixValuesID = (String) yaml.get(StandardDatasetMetaData.observationMatrixValuesID);			
			String spreadSheetInformationID = (String) yaml.get(StandardDatasetMetaData.spreadSheetInformationID);			

			set = new ObservationsFromSpreadSheetFull(objdata, 
					observationMatrixValuesID, 
					spreadSheetInformationID);
			return set;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ObservationsFromSpreadSheetFull datastructure = (ObservationsFromSpreadSheetFull) object;
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);
			map.put(StandardDatasetMetaData.observationMatrixValuesID, datastructure.getObservationMatrixValues());
			map.put(StandardDatasetMetaData.spreadSheetInformationID, datastructure.getSpreadSheetInputInformation());
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ObservationsFromSpreadSheetFull.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ObservationsFromSpreadSheetFull.class.getCanonicalName();
		}
	},
	ObservationsFromSpreadSheet {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject spreadobj = new DatabaseObject(obj);
			spreadobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(MetaDataKeywords.observationsFromSpreadSheet);
			String catid = createSuffix(obj, element);
			spreadobj.setIdentifier(catid);
			
			DatabaseObjectHierarchy titleshier = InterpretData.ObservationValueRowTitle.createEmptyObject(spreadobj);
			
			DatabaseObjectHierarchy structurehier = InterpretData.ObservationsFromSpreadSheetFull.createEmptyObject(spreadobj);
			ObservationsFromSpreadSheetFull structure = (ObservationsFromSpreadSheetFull) structurehier.getObject();
			ObservationsFromSpreadSheet set = new ObservationsFromSpreadSheet(structure,
					titleshier.getObject().getIdentifier());
			set.setIdentifier(catid);
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(set);
			hierarchy.addSubobject(titleshier);
			hierarchy.transferSubObjects(structurehier);
			return hierarchy;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ObservationsFromSpreadSheet set = null;
			InterpretData interpret = InterpretData.valueOf("ObservationsFromSpreadSheetFull");
			ObservationsFromSpreadSheetFull objdata = (ObservationsFromSpreadSheetFull) interpret.fillFromYamlString(top, yaml, sourceID);
					
			String observationValueRowTitleID = (String) yaml.get(StandardDatasetMetaData.observationValueRowTitleID);			

			set = new ObservationsFromSpreadSheet(objdata, 
					observationValueRowTitleID);
			return set;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ObservationsFromSpreadSheet datastructure = (ObservationsFromSpreadSheet) object;
			InterpretData interpret = InterpretData.valueOf("ObservationsFromSpreadSheetFull");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.observationValueRowTitleID, datastructure.getObservationValueRowTitle());
			
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			List<DatabaseObject> lst = QueryBase.getDatabaseObjects(ObservationsFromSpreadSheet.class.getCanonicalName());
			for(DatabaseObject obj : lst) {
				System.out.println("readElementFromDatabase: " + obj.getIdentifier() 
				+ "\nreadElementFromDatabase: " + identifier
				+ "\ncompare: " + obj.getIdentifier().compareTo(identifier));
			}
			return QueryBase.getDatabaseObjectFromIdentifier(ObservationsFromSpreadSheet.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ObservationsFromSpreadSheet.class.getCanonicalName();
		}
		
	}, ObservationCorrespondenceSpecification {


		@Override
		public Map<String, Object> createYamlFromObject(
				DatabaseObject object) throws IOException {
			ObservationCorrespondenceSpecification datastructure = (ObservationCorrespondenceSpecification) object;
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.matrixSpecificationCorrespondenceSetID, datastructure.getMatrixSpecificationCorrespondenceSet());
			map.put(StandardDatasetMetaData.observationSpecificationID, datastructure.getObservationSpecification());
			
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ObservationCorrespondenceSpecification.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ObservationCorrespondenceSpecification.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject obsobj = new DatabaseObject(obj);
			obsobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.observationCorrespondenceSpecification);
			String obsid = createSuffix(obj, element);
			obsobj.setIdentifier(obsid);
			
			DatabaseObjectHierarchy obspechier = InterpretData.ObservationSpecification.createEmptyObject(obsobj);
			DatabaseObjectHierarchy matrixspecehier = InterpretData.MatrixSpecificationCorrespondenceSet.createEmptyObject(obsobj);
			DatabaseObjectHierarchy structurehier = InterpretData.ChemConnectDataStructure.createEmptyObject(obsobj);
			ChemConnectDataStructure structure = (ChemConnectDataStructure) structurehier.getObject();
			ObservationCorrespondenceSpecification set = new ObservationCorrespondenceSpecification(structure,
					obspechier.getObject().getIdentifier(),
					matrixspecehier.getObject().getIdentifier());
			set.setIdentifier(obsid);
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(set);
			hierarchy.addSubobject(obspechier);
			hierarchy.addSubobject(matrixspecehier);
			hierarchy.transferSubObjects(structurehier);
			return hierarchy;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ObservationCorrespondenceSpecification set = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			ChemConnectDataStructure objdata = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);
					
			String parameterTypeS = (String) yaml.get(StandardDatasetMetaData.matrixSpecificationCorrespondenceSetID);			
			String measurementValuesS = (String) yaml.get(StandardDatasetMetaData.observationSpecificationID);			

			set = new ObservationCorrespondenceSpecification(objdata, 
					parameterTypeS, measurementValuesS);
			return set;
		}
		
	}, SubSystemDescription {

		@Override
		public DatabaseObject fillFromYamlString(
				DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			SubSystemDescription datastructure = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			ChemConnectDataStructure objdata = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);					
			String deviceTypeS = (String) yaml.get(StandardDatasetMetaData.deviceTypeS);			
			String observationSpecsS = (String) yaml.get(StandardDatasetMetaData.observationSpecs);			
			String parameterValuesS = (String) yaml.get(StandardDatasetMetaData.parameterValueS);			
			String subSystemsS = (String) yaml.get(StandardDatasetMetaData.subSystems);			
			
			datastructure = new SubSystemDescription(objdata,deviceTypeS,observationSpecsS, parameterValuesS, subSystemsS);
			
			return datastructure;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				DatabaseObject object) throws IOException {
			SubSystemDescription datastructure = (SubSystemDescription) object;
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);
			map.put(StandardDatasetMetaData.deviceTypeS, datastructure.getSubSystemType());
			map.put(StandardDatasetMetaData.observationSpecs, datastructure.getObservationSpecs());
			map.put(StandardDatasetMetaData.parameterValueS, datastructure.getParameterValues());
			map.put(StandardDatasetMetaData.subSystems, datastructure.getSubSystems());
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(SubSystemDescription.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return SubSystemDescription.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject subsysobj = new DatabaseObject(obj);
			subsysobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.subSystemDescription);
			String subsysid = createSuffix(obj, element);
			subsysobj.setIdentifier(subsysid);
			
			DatabaseObjectHierarchy structhier = InterpretData.ChemConnectDataStructure.createEmptyObject(obj);
			ChemConnectDataStructure structure = (ChemConnectDataStructure) structhier.getObject();
			
			DatabaseObjectHierarchy spechier = InterpretData.ChemConnectCompoundMultiple.createEmptyObject(subsysobj);
			setChemConnectCompoundMultipleType(spechier,OntologyKeys.observationSpecs);
			DatabaseObjectHierarchy paramshier = InterpretData.ChemConnectCompoundMultiple.createEmptyObject(subsysobj);
			setChemConnectCompoundMultipleType(paramshier,OntologyKeys.parameterValue);
			DatabaseObjectHierarchy subshier = InterpretData.ChemConnectCompoundMultiple.createEmptyObject(subsysobj);
			setChemConnectCompoundMultipleType(subshier,OntologyKeys.subSystemDescription);
			
			SubSystemDescription device = new SubSystemDescription(structure,
					"Device Type",
					spechier.getObject().getIdentifier(),
					paramshier.getObject().getIdentifier(),
					subshier.getObject().getIdentifier()
					);
			device.setIdentifier(obj.getIdentifier());
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(device);
			hierarchy.transferSubObjects(structhier);
			hierarchy.addSubobject(spechier);
			hierarchy.addSubobject(paramshier);
			hierarchy.addSubobject(subshier);
			return hierarchy;
		}
		
	}, ChemConnectMethodology {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,String sourceID) throws IOException {
			ChemConnectMethodology methodology = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			ChemConnectDataStructure objdata = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);					
			String methodologyTypeS = (String) yaml.get(StandardDatasetMetaData.methodologyTypeS);			
			String parameterValuesS = (String) yaml.get(StandardDatasetMetaData.parameterValueS);			
			String observationSpecS = (String) yaml.get(StandardDatasetMetaData.observationSpecs);			
			
			methodology = new ChemConnectMethodology(objdata,methodologyTypeS,
					observationSpecS, parameterValuesS);
			return methodology;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ChemConnectMethodology methodology = (ChemConnectMethodology) object;
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);
			map.put(StandardDatasetMetaData.methodologyTypeS, methodology.getMethodologyType());
			map.put(StandardDatasetMetaData.parameterValueS, methodology.getParameterValues());
			map.put(StandardDatasetMetaData.observationSpecs, methodology.getObservationSpecs());
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ChemConnectMethodology.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ChemConnectMethodology.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject methobj = new DatabaseObject(obj);
			methobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.methodology);
			String methid = createSuffix(obj, element);
			methobj.setIdentifier(methid);

			
			DatabaseObjectHierarchy structhier = InterpretData.ChemConnectDataStructure.createEmptyObject(methobj);
			ChemConnectDataStructure structure = (ChemConnectDataStructure) structhier.getObject();

			DatabaseObjectHierarchy obshier = InterpretData.ChemConnectCompoundMultiple.createEmptyObject(methobj);
			setChemConnectCompoundMultipleType(obshier,OntologyKeys.observationSpecs);
			DatabaseObjectHierarchy paramhier = InterpretData.ChemConnectCompoundMultiple.createEmptyObject(methobj);
			setChemConnectCompoundMultipleType(paramhier,OntologyKeys.parameterValue);
			
			ChemConnectMethodology methodology = new ChemConnectMethodology(structure,
					"Methodology Type",
					obshier.getObject().getIdentifier(),
					paramhier.getObject().getIdentifier()
					);
			methodology.setIdentifier(obj.getIdentifier());
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(methodology);
			hierarchy.transferSubObjects(structhier);
			hierarchy.addSubobject(obshier);
			hierarchy.addSubobject(paramhier);
			return hierarchy;
		}
		
	}, DatasetCatalogHierarchy {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			DatasetCatalogHierarchy datastructure = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			ChemConnectDataStructure objdata = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);					
			datastructure = new DatasetCatalogHierarchy(objdata);
			
			return datastructure;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject object) throws IOException {
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);
			
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(DatasetCatalogHierarchy.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return DatasetCatalogHierarchy.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject catobj = new DatabaseObject(obj);
			catobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.datasetCatalogHierarchy);
			String catid = createSuffix(obj, element);
			catobj.setIdentifier(catid);

			DatabaseObjectHierarchy structhier = InterpretData.ChemConnectDataStructure.createEmptyObject(catobj);
			ChemConnectDataStructure structure = (ChemConnectDataStructure) structhier.getObject();

			DatasetCatalogHierarchy catalog = new DatasetCatalogHierarchy(structure);
			catalog.setIdentifier(catid);

			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(catalog);
			hierarchy.transferSubObjects(structhier);
			return hierarchy;
		}
		
	},
	PurposeConceptPair {

		@Override
		public info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject fillFromYamlString(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			PurposeConceptPair datastructure = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure objdata = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);					
			
			String conceptS = (String) yaml.get(OntologyKeys.datacubeConcept);
			String purposeS = (String) yaml.get(StandardDatasetMetaData.purposeS);
			datastructure = new PurposeConceptPair(objdata, purposeS, conceptS);
			
			return datastructure;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject object) throws IOException {
			PurposeConceptPair datastructure = (PurposeConceptPair) object;
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(OntologyKeys.datacubeConcept, datastructure.getConcept());
			map.put(StandardDatasetMetaData.purposeS, datastructure.getPurpose());
			
			return map;
		}

		@Override
		public info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(PurposeConceptPair.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return PurposeConceptPair.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.purposeConceptPair);
			DatabaseObject conceptobj = new DatabaseObject(obj);
			conceptobj.nullKey();
			String conceptid = createSuffix(obj, element);
			conceptobj.setIdentifier(conceptid);
			ChemConnectCompoundDataStructure conceptcompound = new ChemConnectCompoundDataStructure(conceptobj,
					obj.getIdentifier());
			PurposeConceptPair pair = new PurposeConceptPair(conceptcompound, noPurposeS, noConceptS);
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(pair);
			return hierarchy;
		}
		
	},
	DescriptionDataData {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			DescriptionDataData descdata = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure objdata = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String titleS = (String) yaml.get(StandardDatasetMetaData.titleKeyS);
			String descriptionS = (String) yaml.get(StandardDatasetMetaData.descriptionKeyS);
			String purposeS = (String) yaml.get(StandardDatasetMetaData.parameterPurposeConceptPairS);
			String datatypeS = (String) yaml.get(StandardDatasetMetaData.dataTypeKeyS);
			String sourceDateS = (String) yaml.get(StandardDatasetMetaData.sourceDateKeyS);
			String keywordsS = (String) yaml.get(StandardDatasetMetaData.keywordKeyS);

			Date dateD = null;
			if (sourceDateS != null) {
				dateD = parseDate(sourceDateS);
			} else {
				dateD = new Date();
			}

			descdata = new DescriptionDataData(objdata,
					titleS, descriptionS, 
					purposeS, dateD, datatypeS, 
					keywordsS);

			return descdata;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			DescriptionDataData description = (DescriptionDataData) object;
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			String sourceDateS = dateToString(description.getSourceDate());

			map.put(StandardDatasetMetaData.titleKeyS, description.getOnlinedescription());
			map.put(StandardDatasetMetaData.descriptionKeyS, description.getFulldescription());
			map.put(StandardDatasetMetaData.parameterPurposeConceptPairS, description.getSourceConcept());
			map.put(StandardDatasetMetaData.dataTypeKeyS, description.getDataType());
			map.put(StandardDatasetMetaData.sourceDateKeyS, sourceDateS);
			map.put(StandardDatasetMetaData.keywordKeyS, description.getKeywords());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(DescriptionDataData.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return DescriptionDataData.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject descrobj = new DatabaseObject(obj);
			descrobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.descriptionDataData);
			String descrid = createSuffix(obj, element);
			descrobj.setIdentifier(descrid);

			DatabaseObjectHierarchy comphier = InterpretData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) comphier.getObject();

			DatabaseObjectHierarchy pairhier = InterpretData.PurposeConceptPair.createEmptyObject(descrobj);
			PurposeConceptPair pair = (PurposeConceptPair) pairhier.getObject();

			DatabaseObjectHierarchy keyshier = InterpretData.ChemConnectCompoundMultiple.createEmptyObject(descrobj);
			setChemConnectCompoundMultipleType(keyshier,OntologyKeys.keyWord);
			ChemConnectCompoundMultiple keywords = (ChemConnectCompoundMultiple) keyshier.getObject();

			DescriptionDataData descr = new DescriptionDataData(compound, "one line", "full description",
					pair.getIdentifier(), new Date(),OntologyKeys.descriptionDataData , keywords.getIdentifier());
			descr.setIdentifier(descrid);
			DatabaseObjectHierarchy descrhier = new DatabaseObjectHierarchy(descr);
			descrhier.addSubobject(pairhier);
			descrhier.addSubobject(keyshier);

			return descrhier;
		}

	},
	DataSpecification {

		@Override
		public info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject fillFromYamlString(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			DataSpecification spec = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure obj = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String purposeS = (String) yaml.get(StandardDatasetMetaData.purposeConceptPairS);
			spec = new DataSpecification(obj,purposeS);
			return spec;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject object) throws IOException {
			DataSpecification spec = (DataSpecification) object;
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.purposeConceptPairS, spec.getPurposeandconcept());

			return map;
		}

		@Override
		public info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(DataSpecification.class.getCanonicalName(), 
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return DataSpecification.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject descrobj = new DatabaseObject(obj);
			descrobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.dataSpecification);
			String descrid = createSuffix(obj, element);
			descrobj.setIdentifier(descrid);
			
			DatabaseObjectHierarchy pairhier = InterpretData.PurposeConceptPair.createEmptyObject(descrobj);
			PurposeConceptPair pair = (PurposeConceptPair) pairhier.getObject();
			ChemConnectCompoundDataStructure struct = new ChemConnectCompoundDataStructure(descrobj,obj.getIdentifier());
			DataSpecification spec = new DataSpecification(struct,pair.getIdentifier());
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(spec);
			hierarchy.addSubobject(pairhier);
			return hierarchy;
		}
		
	}, DimensionParameterValue {

		@Override
		public info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject fillFromYamlString(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			InterpretData interpret = InterpretData.valueOf("ParameterValue");
			ParameterValue obj = (ParameterValue) interpret.fillFromYamlString(top, yaml, sourceID);
			DimensionParameterValue parameter = new DimensionParameterValue(obj);
			return parameter;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject object) throws IOException {
			InterpretData interpret = InterpretData.valueOf("ParameterValue");
			Map<String, Object> map = interpret.createYamlFromObject(object);
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(DimensionParameterValue.class.getCanonicalName(), 
					identifier);
			
		}

		@Override
		public String canonicalClassName() {
			return DimensionParameterValue.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject descrobj = new DatabaseObject(obj);
			descrobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.dimensionParameterValue);
			String descrid = createSuffix(obj, element);
			descrobj.setIdentifier(descrid);


			DatabaseObjectHierarchy paramhier = InterpretData.ParameterValue.createEmptyObject(descrobj);
			ParameterValue param = (ParameterValue) paramhier.getObject();
			DimensionParameterValue value = new DimensionParameterValue(param);
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(value);
			hierarchy.transferSubObjects(paramhier);
			return hierarchy;
		}
		
	}, MeasurementParameterValue {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			InterpretData interpret = InterpretData.valueOf("ParameterValue");
			ParameterValue obj = (ParameterValue) interpret.fillFromYamlString(top, yaml, sourceID);
			MeasurementParameterValue parameter = new MeasurementParameterValue(obj);
			return parameter;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject object) throws IOException {
			InterpretData interpret = InterpretData.valueOf("ParameterValue");
			Map<String, Object> map = interpret.createYamlFromObject(object);
			
			return map;
		}

		@Override
		public info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(MeasurementParameterValue.class.getCanonicalName(), 
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return MeasurementParameterValue.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject descrobj = new DatabaseObject(obj);
			descrobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.measurementParameterValue);
			String descrid = createSuffix(obj, element);
			descrobj.setIdentifier(descrid);

			DatabaseObjectHierarchy paramhier = InterpretData.ParameterValue.createEmptyObject(descrobj);
			ParameterValue param = (ParameterValue) paramhier.getObject();
			MeasurementParameterValue value = new MeasurementParameterValue(param);
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(value);
			hierarchy.transferSubObjects(paramhier);
			return hierarchy;
		}
		
	}, ParameterValue {

		@Override
		public DatabaseObject fillFromYamlString(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ParameterValue spec = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure obj = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String valueAsStringS = (String) yaml.get(StandardDatasetMetaData.valueAsStringS);
			String uncertaintyS = (String) yaml.get(StandardDatasetMetaData.valueUncertaintyS);
			String parameterSpecS = (String) yaml.get(StandardDatasetMetaData.parameterSpecificationS);

			spec = new ParameterValue(obj,valueAsStringS,uncertaintyS,parameterSpecS);
			return spec;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ParameterValue spec = (ParameterValue) object;
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.valueAsStringS, spec.getValueAsString());
			map.put(StandardDatasetMetaData.valueUncertaintyS, spec.getUncertainty());
			map.put(StandardDatasetMetaData.parameterSpecificationS, spec.getParameterSpec());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ParameterValue.class.getCanonicalName(), 
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ParameterValue.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject descrobj = new DatabaseObject(obj);
			descrobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.parameterValue);
			String descrid = createSuffix(obj, element);
			descrobj.setIdentifier(descrid);

			DatabaseObjectHierarchy attribute = InterpretData.ChemConnectCompoundDataStructure.createEmptyObject(descrobj);
			ChemConnectCompoundDataStructure attr = (ChemConnectCompoundDataStructure) attribute.getObject();
			DatabaseObjectHierarchy parameterspec = InterpretData.ParameterSpecification.createEmptyObject(descrobj);
			ParameterSpecification pspec = (ParameterSpecification) parameterspec.getObject();
			ParameterValue value = null;
			value = new ParameterValue(attr, "no value", "no uncertainty", pspec.getIdentifier());
			value.setIdentifier(obj.getIdentifier());

			DatabaseObjectHierarchy hier = new DatabaseObjectHierarchy(value);
			hier.addSubobject(parameterspec);
			hier.transferSubObjects(attribute);
			return hier;
		}
		
	},
	ParameterSpecification {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ParameterSpecification spec = null;
			InterpretData interpret = InterpretData.valueOf("DataSpecification");
			DataSpecification obj = (DataSpecification) interpret.fillFromYamlString(top, yaml, sourceID);

			String dynamicTypeS = (String) yaml.get(StandardDatasetMetaData.dynamicTypeS);
			String parameterLabelS = (String) yaml.get(StandardDatasetMetaData.parameterLabelS);
			String uncertaintyS = (String) yaml.get(StandardDatasetMetaData.dataPointUncertaintyS);
			String UnitsS = (String) yaml.get(StandardDatasetMetaData.unitsS);

			spec = new ParameterSpecification(obj,parameterLabelS,uncertaintyS, UnitsS,dynamicTypeS);
			return spec;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ParameterSpecification spec = (ParameterSpecification) object;

			InterpretData interpret = InterpretData.valueOf("DataSpecification");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.dynamicTypeS, spec.getObservationParameterType());
			map.put(StandardDatasetMetaData.parameterLabelS, spec.getParameterLabel());
			map.put(StandardDatasetMetaData.dataPointUncertaintyS, spec.getDataPointUncertainty());
			map.put(StandardDatasetMetaData.unitsS, spec.getUnits());
			

			return map;
		}

		@Override
		public info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
				return QueryBase.getDatabaseObjectFromIdentifier(ParameterSpecification.class.getCanonicalName(), 
						identifier);
		}

		@Override
		public String canonicalClassName() {
			return ParameterSpecification.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObjectHierarchy dspechier = InterpretData.DataSpecification.createEmptyObject(obj);
			DataSpecification dspec = (DataSpecification) dspechier.getObject();
			/*
			DatabaseObject specobj = new DatabaseObject(obj);
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.parameterSpecification);
			String specsid = createSuffix(obj, element);
			specobj.setIdentifier(specsid);
			 */
			String dynamicType = "dataset:FixedParameter";
			DatabaseObjectHierarchy valuehier = InterpretData.ValueUnits.createEmptyObject(dspec);
			ValueUnits value = (ValueUnits) valuehier.getObject();
			ParameterSpecification specs = new ParameterSpecification(dspec, "no label",
					"no uncertainty", value.getIdentifier(), dynamicType);

			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(specs);
			hierarchy.addSubobject(valuehier);
			hierarchy.transferSubObjects(dspechier);
			hierarchy.transferSubObjects(valuehier);

			return hierarchy;
		}
		
	}, MeasureParameterSpecification {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			MeasureParameterSpecification measure = null;
			InterpretData interpret = InterpretData.valueOf("ParameterSpecification");
			ParameterSpecification spec = (ParameterSpecification) interpret.fillFromYamlString(top, yaml, sourceID);
			
			measure = new MeasureParameterSpecification(spec);
			return measure;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {

			InterpretData interpret = InterpretData.valueOf("ParameterSpecification");
			Map<String, Object> map = interpret.createYamlFromObject(object);
			System.out.println("MeasureParameterSpecification: \n" + map);
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
				return QueryBase.getDatabaseObjectFromIdentifier(MeasureParameterSpecification.class.getCanonicalName(), 
						identifier);
		}

		@Override
		public String canonicalClassName() {
			return MeasureParameterSpecification.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.measureSpecification);
			DatabaseObject specobj = new DatabaseObject(obj);
			specobj.nullKey();
			String specsid = createSuffix(obj, element);
			specobj.setIdentifier(specsid);
			DatabaseObjectHierarchy spechier = InterpretData.ParameterSpecification.createEmptyObject(obj);			
			ParameterSpecification spec = (ParameterSpecification) spechier.getObject();
			MeasureParameterSpecification mspec = new MeasureParameterSpecification(spec);
			spechier.setObject(mspec);
			return spechier;
		}
		
	}, DimensionParameterSpecification {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			DimensionParameterSpecification measure = null;
			InterpretData interpret = InterpretData.valueOf("ParameterSpecification");
			ParameterSpecification spec = (ParameterSpecification) interpret.fillFromYamlString(top, yaml, sourceID);
			
			measure = new DimensionParameterSpecification(spec);
			return measure;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject object) throws IOException {
			InterpretData interpret = InterpretData.valueOf("ParameterSpecification");
			Map<String, Object> map = interpret.createYamlFromObject(object);
			System.out.println("DimensionParameterSpecification: \n" + map);
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
				return QueryBase.getDatabaseObjectFromIdentifier(DimensionParameterSpecification.class.getCanonicalName(), 
						identifier);
		}

		@Override
		public String canonicalClassName() {
			return DimensionParameterSpecification.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.dimensionSpecification);
			DatabaseObject specobj = new DatabaseObject(obj);
			specobj.nullKey();
			String specsid = createSuffix(obj, element);
			specobj.setIdentifier(specsid);

			DatabaseObjectHierarchy spechier = InterpretData.ParameterSpecification.createEmptyObject(obj);
			ParameterSpecification spec = (ParameterSpecification) spechier.getObject();

			DimensionParameterSpecification dspec = new DimensionParameterSpecification(spec);
			spechier.setObject(dspec);
			return spechier;
		}
		
	}, ValueUnits {

		@Override
		public info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject fillFromYamlString(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ValueUnits spec = null;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject obj = interpret.fillFromYamlString(top, yaml, sourceID);

			String unitClassyS = (String) yaml.get(StandardDatasetMetaData.unitClassS);
			String UnitsOfValueS = (String) yaml.get(StandardDatasetMetaData.unitsOfValueS);

			spec = new ValueUnits(obj,unitClassyS, UnitsOfValueS);
			return spec;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject object) throws IOException {
			ValueUnits spec = (ValueUnits) object;

			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.unitClassS, spec.getUnitClass());
			map.put(StandardDatasetMetaData.unitsOfValueS, spec.getUnitsOfValue());

			return map;
		}

		@Override
		public info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ValueUnits.class.getCanonicalName(), 
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ValueUnits.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject unitsobj = new DatabaseObject(obj);
			unitsobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.valueUnits);
			String unitsid = createSuffix(obj, element);
			unitsobj.setIdentifier(unitsid);

			ValueUnits units = new ValueUnits(unitsobj, noUnitClassS, noValueUnitsS);

			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(units);

			return hierarchy;
		}
		
	}, ObservationSpecification {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure objdata = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String parameterType = (String) yaml.get(StandardDatasetMetaData.observationParameterType);
			String parameterLabel = (String) yaml.get(StandardDatasetMetaData.specificationLabel);
			String measureSpecification = (String) yaml.get(StandardDatasetMetaData.measureSpec);
			String dimensionSpecification = (String) yaml.get(StandardDatasetMetaData.dimensionSpec);

			ObservationSpecification contact = new ObservationSpecification(objdata, parameterLabel, parameterType, 
					measureSpecification, dimensionSpecification);
			return contact;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ObservationSpecification obsspec = (ObservationSpecification) object;

			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.specificationLabel, obsspec.getSpecificationLabel());
			map.put(StandardDatasetMetaData.observationParameterType, obsspec.getObservationParameterType());
			map.put(StandardDatasetMetaData.measureSpec, obsspec.getMeasureSpecifications());
			map.put(StandardDatasetMetaData.dimensionSpec, obsspec.getDimensionSpecifications());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ObservationSpecification.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return ObservationSpecification.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject specobj = new DatabaseObject(obj);
			specobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.observationSpecs);
			String specsid = createSuffix(obj, element);
			specobj.setIdentifier(specsid);

			DatabaseObjectHierarchy measurehier = InterpretData.ChemConnectCompoundMultiple.createEmptyObject(specobj);
			setChemConnectCompoundMultipleType(measurehier,OntologyKeys.measureSpecification);
			DatabaseObjectHierarchy dimensionhier = InterpretData.ChemConnectCompoundMultiple.createEmptyObject(specobj);
			setChemConnectCompoundMultipleType(dimensionhier,OntologyKeys.dimensionSpecification);
			DatabaseObjectHierarchy structhier = InterpretData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) structhier.getObject();
			ObservationSpecification specification = new ObservationSpecification(structure,
					"Label",
					"FixedParameterType",
					dimensionhier.getObject().getIdentifier(),
					measurehier.getObject().getIdentifier()
					);
			specification.setIdentifier(specobj.getIdentifier());
			DatabaseObjectHierarchy spechier = new DatabaseObjectHierarchy(specification);
			spechier.addSubobject(dimensionhier);
			spechier.addSubobject(measurehier);
			
			return spechier;
		}
		
	}, MatrixSpecificationCorrespondenceSet {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject matspecobj = new DatabaseObject(obj);
			matspecobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(StandardDatasetMetaData.matrixSpecificationCorrespondenceSet);
			String contactid = createSuffix(obj, element);
			matspecobj.setIdentifier(contactid);
			
			DatabaseObjectHierarchy matspechier = InterpretData.ChemConnectCompoundMultiple.createEmptyObject(matspecobj);
			setChemConnectCompoundMultipleType(matspechier,StandardDatasetMetaData.matrixSpecificationCorrespondence);
			
			DatabaseObjectHierarchy structhier = InterpretData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) structhier.getObject();
			MatrixSpecificationCorrespondenceSet set = new MatrixSpecificationCorrespondenceSet(structure,
					matspechier.getObject().getIdentifier());
			set.setIdentifier(matspecobj.getIdentifier());
			DatabaseObjectHierarchy sethier = new DatabaseObjectHierarchy(set);
			sethier.addSubobject(matspechier);
			
			return sethier;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {

			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure objdata = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String matrixspec = (String) yaml.get(StandardDatasetMetaData.matrixSpecificationCorrespondenceID);

			MatrixSpecificationCorrespondenceSet corr = new MatrixSpecificationCorrespondenceSet(objdata, 
					matrixspec);
			
			return corr;
			
			
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			MatrixSpecificationCorrespondenceSet matspec = (MatrixSpecificationCorrespondenceSet) object;

			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.matrixSpecificationCorrespondenceID, matspec.getMatrixSpecificationCorrespondence());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(MatrixSpecificationCorrespondenceSet.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return MatrixSpecificationCorrespondenceSet.class.getCanonicalName();
		}
		
	}, MatrixSpecificationCorrespondence {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject matobj = new DatabaseObject(obj);
			matobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(StandardDatasetMetaData.matrixSpecificationCorrespondence);
			String matid = createSuffix(obj, element);
			matobj.setIdentifier(matid);

			DatabaseObjectHierarchy comphier =  
					InterpretData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) comphier.getObject();
			MatrixSpecificationCorrespondence corr = new MatrixSpecificationCorrespondence(structure,"0","label",false);
			corr.setIdentifier(matobj.getIdentifier());
			DatabaseObjectHierarchy hier = new DatabaseObjectHierarchy(corr);
			return hier;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) 
					InterpretData.ChemConnectCompoundDataStructure.fillFromYamlString(top, yaml, sourceID);
			String column = (String) yaml.get(StandardDatasetMetaData.matrixColumn);
			String label = (String) yaml.get(StandardDatasetMetaData.specificationLabel);
			String uncertainty = (String) yaml.get(StandardDatasetMetaData.includesUncertaintyParameter);
			boolean uncertaintyB = Boolean.parseBoolean(uncertainty);
			MatrixSpecificationCorrespondence corr = new MatrixSpecificationCorrespondence(structure,column,label,uncertaintyB);
			return corr;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject object) throws IOException {
			MatrixSpecificationCorrespondence matspec = (MatrixSpecificationCorrespondence) object;

			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.matrixColumn, matspec.getMatrixColumn());
			map.put(StandardDatasetMetaData.specificationLabel, matspec.getSpecificationLabel());
			String uncertainty = Boolean.toString(matspec.isIncludesUncertaintyParameter());
			map.put(StandardDatasetMetaData.includesUncertaintyParameter, uncertainty);

			return map;
		}

		@Override
		public info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(MatrixSpecificationCorrespondence.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return MatrixSpecificationCorrespondence.class.getCanonicalName();
		}
		
	}, ObservationMatrixValues {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject matobj = new DatabaseObject(obj);
			matobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(StandardDatasetMetaData.observationMatrixValues);
			String matid = createSuffix(obj, element);
			matobj.setIdentifier(matid);
			
			DatabaseObjectHierarchy titleshier = InterpretData.ObservationValueRowTitle.createEmptyObject(matobj);
			DatabaseObjectHierarchy matspechier = InterpretData.ChemConnectCompoundMultiple.createEmptyObject(matobj);
			setChemConnectCompoundMultipleType(matspechier,StandardDatasetMetaData.observationValueRow);
			DatabaseObjectHierarchy comphier =  
					InterpretData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) comphier.getObject();
			ObservationMatrixValues obsmat = new ObservationMatrixValues(structure,
					matspechier.getObject().getIdentifier());
			obsmat.setIdentifier(matid);
			DatabaseObjectHierarchy hier = new DatabaseObjectHierarchy(obsmat);
			hier.addSubobject(titleshier);
			hier.addSubobject(matspechier);
			hier.transferSubObjects(comphier);
			
			return hier;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) 
					InterpretData.ChemConnectCompoundDataStructure.fillFromYamlString(top, yaml, sourceID);
			String values = (String) yaml.get(StandardDatasetMetaData.observationValueRowID);
			ObservationMatrixValues corr = new ObservationMatrixValues(structure,values);
			return corr;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject object) throws IOException {
			ObservationMatrixValues values = (ObservationMatrixValues) object;

			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.observationValueRowID, values.getObservationRowValue());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ObservationMatrixValues.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return ObservationMatrixValues.class.getCanonicalName();
		}
		
	}, ObservationBlockFromSpreadSheet {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject matobj = new DatabaseObject(obj);
			matobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(StandardDatasetMetaData.observationBlockFromSpreadSheet);
			String matid = createSuffix(obj, element);
			matobj.setIdentifier(matid);
			
			DatabaseObjectHierarchy interpretpechier = InterpretData.SpreadSheetBlockIsolation.createEmptyObject(matobj);
			DatabaseObjectHierarchy comphier = InterpretData.ChemConnectDataStructure.createEmptyObject(matobj);
			ChemConnectDataStructure structure = (ChemConnectDataStructure) comphier.getObject();
			ObservationBlockFromSpreadSheet obsmat = new ObservationBlockFromSpreadSheet(structure,
					interpretpechier.getObject().getIdentifier());
			obsmat.setIdentifier(matid);
			DatabaseObjectHierarchy hier = new DatabaseObjectHierarchy(obsmat);
			hier.addSubobject(interpretpechier);
			hier.transferSubObjects(comphier);
			
			return hier;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ChemConnectDataStructure structure = (ChemConnectDataStructure) 
					InterpretData.ChemConnectDataStructure.fillFromYamlString(top, yaml, sourceID);
			String interpret = (String) yaml.get(StandardDatasetMetaData.spreadSheetBlockIsolationID);
			ObservationBlockFromSpreadSheet obs = new ObservationBlockFromSpreadSheet(structure,interpret);
			return obs;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ObservationBlockFromSpreadSheet values = (ObservationBlockFromSpreadSheet) object;

			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.spreadSheetBlockIsolationID, values.getSpreadBlockIsolation());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ObservationBlockFromSpreadSheet.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return ObservationBlockFromSpreadSheet.class.getCanonicalName();
		}
		
	}, ObservationValueRow {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject rowobj = new DatabaseObject(obj);
			rowobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(StandardDatasetMetaData.observationValueRow);
			String rowid = createSuffix(obj, element);
			rowobj.setIdentifier(rowid);
			
			DatabaseObjectHierarchy compoundhier = InterpretData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) compoundhier.getObject();
			ObservationValueRow valuerow = new ObservationValueRow(structure,0,new ArrayList<String>());
			DatabaseObjectHierarchy hier = new DatabaseObjectHierarchy(valuerow);
			
			return hier;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure objdata = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String position = (String) yaml.get(StandardDatasetMetaData.position);
			int pos = Integer.valueOf(position).intValue();
			ArrayList<String> values = interpretMultipleYamlList(StandardDatasetMetaData.listOfValuesAsString,yaml);
			
			ObservationValueRow row = new ObservationValueRow(objdata, pos,values);
			return row;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ObservationValueRow row = (ObservationValueRow) object;

			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.position, row.getRowNumber());
			putMultipleInYamlList(StandardDatasetMetaData.listOfValuesAsString,map,row.getRow());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ObservationValueRow.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return ObservationValueRow.class.getCanonicalName();
		}
		
	}, ObservationValueRowTitle {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject rowobj = new DatabaseObject(obj);
			rowobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(StandardDatasetMetaData.observationValueRowTitle);
			String rowid = createSuffix(obj, element);
			rowobj.setIdentifier(rowid);
			
			DatabaseObjectHierarchy compoundhier = InterpretData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) compoundhier.getObject();
			ObservationValueRowTitle titlerow = new ObservationValueRowTitle(structure,new ArrayList<String>());
			titlerow.setIdentifier(rowid);
			DatabaseObjectHierarchy hier = new DatabaseObjectHierarchy(titlerow);
			return hier;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure objdata = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);
			ArrayList<String> titles = interpretMultipleYamlList(StandardDatasetMetaData.listOfTitles,yaml);
			ObservationValueRowTitle row = new ObservationValueRowTitle(objdata, titles);
			return row;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ObservationValueRowTitle rowtitles = (ObservationValueRowTitle) object;

			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			putMultipleInYamlList(StandardDatasetMetaData.listOfTitles,map,rowtitles.getParameterLabel());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ObservationValueRowTitle.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return ObservationValueRowTitle.class.getCanonicalName();
		}
		
	}, ContactHasSite {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject rowobj = new DatabaseObject(obj);
			rowobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.contactHasSite);
			String rowid = createSuffix(obj, element);
			rowobj.setIdentifier(rowid);
			
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			DatabaseObjectHierarchy compoundhier = interpret.createEmptyObject(obj);
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) compoundhier.getObject();
			String httpaddress = "https://homepage.com";
			String httpaddressType = "dataset:PersonalHomepage";
			ContactHasSite hassite = new ContactHasSite(structure,httpaddress,httpaddressType);
			hassite.setIdentifier(rowid);
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(hassite);
			return hierarchy;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);
			String httpaddress = (String) yaml.get(StandardDatasetMetaData.siteOfS);
			String httpaddressType = (String) yaml.get(StandardDatasetMetaData.siteTypeS);
			ContactHasSite hassite = new ContactHasSite(compound,httpaddressType, httpaddress);
			return hassite;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ContactHasSite sites = (ContactHasSite) object;

			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.siteOfS, sites.getHttpAddress());
			map.put(StandardDatasetMetaData.siteTypeS, sites.getHttpAddressType());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ContactHasSite.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return ContactHasSite.class.getCanonicalName();
		}
	
	}, ContactInfoData {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure objdata = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String contactkey = (String) yaml.get(StandardDatasetMetaData.contactKeyS);
			String contacttype = (String) yaml.get(StandardDatasetMetaData.contactTypeS);

			ContactInfoData contact = new ContactInfoData(objdata, contacttype, contactkey);
			return contact;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ContactInfoData contact = (ContactInfoData) object;

			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.contactKeyS, contact.getContactType());
			map.put(StandardDatasetMetaData.contactTypeS, contact.getContact());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ContactInfoData.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return ContactInfoData.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject contactobj = new DatabaseObject(obj);
			contactobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.contactInfoData);
			String contactid = createSuffix(obj, element);
			contactobj.setIdentifier(contactid);

			DatabaseObjectHierarchy compoundhier = InterpretData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) compoundhier.getObject();

			ContactInfoData contact = new ContactInfoData(compound, "contactType","contactKey");
			contact.setIdentifier(contactid);
			DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(contact);
			return top;
		}

	}, ContactLocationInformation {
		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String streetaddress = (String) yaml.get(StandardDatasetMetaData.streetaddressKeyS);
			String locality = (String) yaml.get(StandardDatasetMetaData.localityKeyS);
			String postalcode = (String) yaml.get(StandardDatasetMetaData.postalcodeKeyS);
			String country = (String) yaml.get(StandardDatasetMetaData.countryKeyS);
			String gspLocationID = (String) yaml.get(StandardDatasetMetaData.gpsCoordinatesID);

			ContactLocationInformation location = new ContactLocationInformation(compound, 
					streetaddress, locality, country, postalcode,gspLocationID);
			
			return location;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ContactLocationInformation location = (ContactLocationInformation) object;

			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.streetaddressKeyS, location.getAddressAddress());
			map.put(StandardDatasetMetaData.localityKeyS, location.getCity());
			map.put(StandardDatasetMetaData.postalcodeKeyS, location.getPostcode());
			map.put(StandardDatasetMetaData.countryKeyS, location.getCountry());
			map.put(StandardDatasetMetaData.gpsCoordinatesID, location.getGpsLocationID());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ContactLocationInformation.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ContactLocationInformation.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject contactobj = new DatabaseObject(obj);
			contactobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.contactLocationInformation);
			String contactid = createSuffix(obj, element);
			contactobj.setIdentifier(contactid);

			DatabaseObjectHierarchy compoundhier = InterpretData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) compoundhier.getObject();

			DatabaseObjectHierarchy gpshier = InterpretData.GPSLocation.createEmptyObject(contactobj);
			GPSLocation gps = (GPSLocation) gpshier.getObject();

			ContactLocationInformation location = new ContactLocationInformation(compound, gps.getIdentifier());
			location.setIdentifier(contactid);
			DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(location);
			top.addSubobject(gpshier);

			return top;
		}

	},
	GPSLocation {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String GPSLongitude = (String) yaml.get(StandardDatasetMetaData.longitudeKeyS);
			String GPSLatitude = (String) yaml.get(StandardDatasetMetaData.latitudeKeyS);

			GPSLocation location = new GPSLocation(objdata.getIdentifier(), objdata.getAccess(), objdata.getOwner(),
					sourceID, GPSLongitude, GPSLatitude);
			return location;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			GPSLocation location = (GPSLocation) object;

			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.longitudeKeyS, location.getGPSLatitude());
			map.put(StandardDatasetMetaData.latitudeKeyS, location.getGPSLongitude());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(GPSLocation.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return GPSLocation.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject gpsobj = new DatabaseObject(obj);
			gpsobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.gPSLocation);
			String gpsid = createSuffix(obj, element);
			gpsobj.setIdentifier(gpsid);

			GPSLocation gps = new GPSLocation(gpsobj);
			DatabaseObjectHierarchy gpshier = new DatabaseObjectHierarchy(gps);

			return gpshier;
		}

	}, OrganizationDescription {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			OrganizationDescription descdata = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);
			
			String organizationalUnitS = (String) yaml.get(StandardDatasetMetaData.organizationUnit);
			String organizationClassificationS = (String) yaml.get(StandardDatasetMetaData.organizationClassification);
			String organizationNameS = (String) yaml.get(StandardDatasetMetaData.organizationName);
			String subOrganizationOfS = (String) yaml.get(StandardDatasetMetaData.subOrganizationOf);

			descdata = new OrganizationDescription(compound,
					organizationalUnitS, organizationClassificationS, organizationNameS, subOrganizationOfS);
			return descdata;
		}

		@Override
		public Map<String, Object> createYamlFromObject(

				DatabaseObject object) throws IOException {

			OrganizationDescription org = (OrganizationDescription) object;

			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.organizationClassification, org.getOrganizationClassification());
			map.put(StandardDatasetMetaData.organizationName, org.getOrganizationName());
			map.put(StandardDatasetMetaData.organizationUnit, org.getOrganizationUnit());
			map.put(StandardDatasetMetaData.subOrganizationOf, org.getSubOrganizationOf());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(OrganizationDescription.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return OrganizationDescription.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject orgobj = new DatabaseObject(obj);
			orgobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.organizationDescription);
			String orgid = createSuffix(obj, element);
			orgobj.setIdentifier(orgid);

			DatabaseObjectHierarchy compoundhier = InterpretData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) compoundhier.getObject();

			OrganizationDescription descr = new OrganizationDescription(compound, "organization unit", "organization class",
					"organization", "suborganization");
			descr.setIdentifier(orgid);

			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(descr);

			return hierarchy;
		}

	}, DataSetReference {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String referenceDOIS = (String) yaml.get(StandardDatasetMetaData.referenceDOI);
			String referenceTitleS = (String) yaml.get(StandardDatasetMetaData.referenceTitle);
			String referenceBibliographicStringS = (String) yaml
					.get(StandardDatasetMetaData.referenceBibliographicString);
			String authors = (String) yaml.get(StandardDatasetMetaData.referenceAuthors);
			DataSetReference refset = new DataSetReference(compound, referenceDOIS, referenceTitleS, referenceBibliographicStringS,
					authors);

			return refset;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			DataSetReference ref = (DataSetReference) object;

			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.referenceDOI, ref.getDOI());
			map.put(StandardDatasetMetaData.referenceTitle, ref.getTitle());
			map.put(StandardDatasetMetaData.referenceBibliographicString, ref.getReferenceString());
			map.put(StandardDatasetMetaData.referenceAuthors,ref.getAuthors());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(DataSetReference.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return DataSetReference.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject refobj = new DatabaseObject(obj);
			refobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.dataSetReference);
			String refid = createSuffix(obj, element);
			refobj.setIdentifier(refid);
			
			DatabaseObjectHierarchy compoundhier = InterpretData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) compoundhier.getObject();
			
			DatabaseObjectHierarchy authormult = InterpretData.ChemConnectCompoundMultiple.createEmptyObject(refobj);
			setChemConnectCompoundMultipleType(authormult,OntologyKeys.author);
			
			DataSetReference reference = new DataSetReference(compound,
					"DOI","Article Title","Reference String",authormult.getObject().getIdentifier());
			reference.setIdentifier(refobj.getIdentifier());
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(reference);
			return hierarchy;
		}

	}, DataObjectLink {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);


			String dataStructureIdentifierS = (String) yaml.get(StandardDatasetMetaData.dataStructureIdentifierS);
			String linkConceptTypeS = (String) yaml.get(OntologyKeys.datacubeConcept);
			DataObjectLink refset = new DataObjectLink(compound, 
					linkConceptTypeS,dataStructureIdentifierS);

			return refset;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			DataObjectLink ref = (DataObjectLink) object;

			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.dataStructureIdentifierS, ref.getDataStructure());
			map.put(OntologyKeys.datacubeConcept, ref.getLinkConcept());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(DataObjectLink.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return DataObjectLink.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject linkobj = new DatabaseObject(obj);
			linkobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.dataObjectLink);
			String linkid = createSuffix(obj, element);
			linkobj.setIdentifier(linkid);

			DatabaseObjectHierarchy compoundhier = InterpretData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) compoundhier.getObject();

			DataObjectLink userlink = new DataObjectLink(compound, "link concept", "linked object");
			userlink.setIdentifier(linkobj.getIdentifier());
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(userlink);
			return hierarchy;
		}

	},
	PersonalDescription {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String userClassification = (String) yaml.get(StandardDatasetMetaData.userClassification);
			String userNameID = (String) yaml.get(StandardDatasetMetaData.userNameID);

			PersonalDescription person = new PersonalDescription(compound, userClassification, userNameID);

			return person;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			PersonalDescription person = (PersonalDescription) object;

			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.userClassification, person.getUserClassification());
			map.put(StandardDatasetMetaData.userNameID, person.getNameOfPersonIdentifier());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(PersonalDescription.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return PersonalDescription.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject personobj = new DatabaseObject(obj);
			personobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.personalDescription);
			String personid = createSuffix(obj, element);
			personobj.setIdentifier(personid);

			DatabaseObjectHierarchy compoundhier = InterpretData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) compoundhier.getObject();

			DatabaseObjectHierarchy namehier = InterpretData.NameOfPerson.createEmptyObject(personobj);
			NameOfPerson person = (NameOfPerson) namehier.getObject();

			PersonalDescription description = new PersonalDescription(compound, "user class", person.getIdentifier());
			description.setIdentifier(personid);

			DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(description);
			top.addSubobject(namehier);

			return top;
		}

	},
	NameOfPerson {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			NameOfPerson person = null;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String nameTitleS = (String) yaml.get(StandardDatasetMetaData.titleName);
			String givenNameS = (String) yaml.get(StandardDatasetMetaData.givenName);
			String familyNameS = (String) yaml.get(StandardDatasetMetaData.familyName);

			person = new NameOfPerson(objdata.getIdentifier(), objdata.getAccess(), objdata.getOwner(), sourceID,
					nameTitleS, givenNameS, familyNameS);
			return person;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			NameOfPerson name = (NameOfPerson) object;

			map.put(StandardDatasetMetaData.titleName, name.getTitle());
			map.put(StandardDatasetMetaData.givenName, name.getGivenName());
			map.put(StandardDatasetMetaData.familyName, name.getFamilyName());
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(NameOfPerson.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return NameOfPerson.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject personobj = new DatabaseObject(obj);
			personobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.nameOfPerson);
			String personid = createSuffix(obj, element);
			personobj.setIdentifier(personid);

			NameOfPerson person = new NameOfPerson(personobj, "title", "firstname", "lastname");
			DatabaseObjectHierarchy personhier = new DatabaseObjectHierarchy(person);

			return personhier;
		}

	},
	IndividualInformation {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			IndividualInformation org = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			ChemConnectDataStructure datastructure = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String contactLocationInformationID = (String) yaml.get(StandardDatasetMetaData.locationKeyS);
			String personalDescriptionID = (String) yaml.get(StandardDatasetMetaData.personS);
			String contactinfodataID = (String) yaml.get(OntologyKeys.contactInfoData);

			org = new IndividualInformation(datastructure,
					contactLocationInformationID, 
					personalDescriptionID,
					contactinfodataID);
			
			return org;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {

			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			IndividualInformation individual = (IndividualInformation) object;
			map.put(StandardDatasetMetaData.locationKeyS, individual.getContactLocationInformationID());
			map.put(StandardDatasetMetaData.personS, individual.getPersonalDescriptionID());
			map.put(OntologyKeys.contactInfoData, individual.getContactInfoData());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(IndividualInformation.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return IndividualInformation.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject indobj = new DatabaseObject(obj);
			indobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.individualInformation);
			String indid = createSuffix(obj, element);
			indobj.setIdentifier(indid);

			DatabaseObjectHierarchy compoundhier = InterpretData.ChemConnectDataStructure.createEmptyObject(indobj);
			ChemConnectDataStructure structure = (ChemConnectDataStructure) compoundhier.getObject();

			DatabaseObjectHierarchy contacthier = InterpretData.ChemConnectCompoundMultiple.createEmptyObject(indobj);
			setChemConnectCompoundMultipleType(contacthier,OntologyKeys.contactInfoData);

			DatabaseObjectHierarchy location = InterpretData.ContactLocationInformation.createEmptyObject(indobj);
			DatabaseObjectHierarchy personalhier = InterpretData.PersonalDescription.createEmptyObject(indobj);
			IndividualInformation info = new IndividualInformation(structure, 
					location.getObject().getIdentifier(), 
					personalhier.getObject().getIdentifier(),
					contacthier.getObject().getIdentifier());
			info.setIdentifier(indid);
			DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(info);
			top.addSubobject(location);
			top.addSubobject(personalhier);
			top.addSubobject(contacthier);
			top.transferSubObjects(compoundhier);

			return top;
		}

	},
	Organization {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			Organization org = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			ChemConnectDataStructure datastructure = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml,
					sourceID);

			String contactInfoData = (String) 
					yaml.get(OntologyKeys.contactInfoData);
			String contactLocationInformationID = (String) 
					yaml.get(StandardDatasetMetaData.locationKeyS);
			String organizationDescriptionID = (String) 
					yaml.get(StandardDatasetMetaData.orginfoKeyS);
		
			org = new Organization(datastructure,
					contactLocationInformationID, 
					organizationDescriptionID,
					contactInfoData
					);

			return org;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {

			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			Organization org = (Organization) object;
			map.put(OntologyKeys.contactInfoData, org.getContactInfoData());
			map.put(StandardDatasetMetaData.locationKeyS, org.getContactLocationInformationID());
			map.put(StandardDatasetMetaData.orginfoKeyS, org.getOrganizationDescriptionID());
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(Organization.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return Organization.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject compobj = new DatabaseObject(obj);
			compobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.organization);
			String compid = createSuffix(obj, element);
			compobj.setIdentifier(compid);
			DatabaseObjectHierarchy location = InterpretData.ContactLocationInformation.createEmptyObject(compobj);
			DatabaseObjectHierarchy orgdescr = InterpretData.OrganizationDescription.createEmptyObject(compobj);
			DatabaseObjectHierarchy contacthier = InterpretData.ChemConnectCompoundMultiple.createEmptyObject(compobj);
			setChemConnectCompoundMultipleType(contacthier,OntologyKeys.contactInfoData);

			DatabaseObjectHierarchy compoundhier = InterpretData.ChemConnectDataStructure.createEmptyObject(compobj);
			ChemConnectDataStructure structure = (ChemConnectDataStructure) compoundhier.getObject();
			Organization org = new Organization(structure,
					location.getObject().getIdentifier(), 
					orgdescr.getObject().getIdentifier(),
					contacthier.getObject().getIdentifier());
			org.setIdentifier(compid);
			DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(org);
			top.addSubobject(location);
			top.addSubobject(orgdescr);
			top.addSubobject(contacthier);
			top.transferSubObjects(compoundhier);

			return top;
		}

	},
	UserAccountInformation {
		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			UserAccountInformation account = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String password = (String) yaml.get(StandardDatasetMetaData.password);
			String emailS = (String) yaml.get(StandardDatasetMetaData.emailKeyS);
			String userrole = (String) yaml.get(StandardDatasetMetaData.userrole);

			account = new UserAccountInformation(objdata.getIdentifier(), objdata.getAccess(), 
					objdata.getOwner(), sourceID, 
					emailS, password, userrole);
			return account;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			UserAccountInformation account = (UserAccountInformation) object;

			map.put(StandardDatasetMetaData.password, account.getPassword());
			map.put(StandardDatasetMetaData.emailKeyS, account.getEmail());
			map.put(StandardDatasetMetaData.userrole, account.getUserrole());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			DatabaseObject obj = QueryBase
					.getDatabaseObjectFromIdentifier(UserAccountInformation.class.getCanonicalName(), identifier);
			return obj;
		}

		@Override
		public String canonicalClassName() {
			return UserAccountInformation.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject userobj = new DatabaseObject(obj);
			userobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.useraccountid);
			String userid = createSuffix(obj, element);
			userobj.setIdentifier(userid);
			
			DatabaseObjectHierarchy compoundhier = InterpretData.ChemConnectDataStructure.createEmptyObject(userobj);
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) compoundhier.getObject();
			UserAccountInformation useraccount = new UserAccountInformation(structure,
					"email@comp.com", "password",MetaDataKeywords.accessTypeQuery);
			DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(useraccount);
			return top;
		}

	},
	UserAccount {
		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			UserAccount account = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			ChemConnectDataStructure datastructure = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml,
					sourceID);

			String DatabaseUserID = (String) yaml.get(StandardDatasetMetaData.databaseUserS);
			String UserAccountInformationID = (String) yaml.get(StandardDatasetMetaData.UserAccountInformation);

			account = new UserAccount(datastructure,
					DatabaseUserID, UserAccountInformationID);
			return account;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			UserAccount account = (UserAccount) object;

			map.put(StandardDatasetMetaData.databaseUserS, account.getDatabaseUser());
			map.put(StandardDatasetMetaData.UserAccountInformation, account.getUserAccountInformation());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(UserAccount.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return UserAccount.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			DatabaseObject userobj = new DatabaseObject(obj);
			userobj.nullKey();
			DataElementInformation element = DatasetOntologyParsing
					.getSubElementStructureFromIDObject(OntologyKeys.useraccount);
			String userid = createSuffix(obj, element);
			userobj.setIdentifier(userid);
			
			DatabaseObjectHierarchy usraccid = InterpretData.UserAccountInformation.createEmptyObject(userobj);
			//DatabaseObjectHierarchy personid = InterpretData.DatabasePerson.createEmptyObject(userobj);
			
			DatabaseObjectHierarchy compoundhier = InterpretData.ChemConnectDataStructure.createEmptyObject(userobj);
			ChemConnectDataStructure structure = (ChemConnectDataStructure) compoundhier.getObject();
			UserAccount useraccount = new UserAccount(structure,
					"",
					usraccid.getObject().getIdentifier());
			DatabaseObjectHierarchy userhier = new DatabaseObjectHierarchy(useraccount);
			userhier.addSubobject(usraccid);
			userhier.transferSubObjects(compoundhier);
			return userhier;
		}

	},
	
	Consortium {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			Consortium consortium = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			ChemConnectDataStructure datastructure = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml,
					sourceID);

			HashSet<String> DatabaseUserIDReadAccess 
				= interpretMultipleYaml(StandardDatasetMetaData.DatabaseUserIDReadAccess,yaml);
			HashSet<String> DatabaseUserIDWriteAccess
				= interpretMultipleYaml(StandardDatasetMetaData.DatabaseUserIDWriteAccess,yaml);
			HashSet<String> DataSetCatalogID
				= interpretMultipleYaml(StandardDatasetMetaData.parameterSetDescriptionsS,yaml);
			HashSet<String> OrganizationID
			= interpretMultipleYaml(StandardDatasetMetaData.orginfoKeyS,yaml);

			consortium = new Consortium(datastructure, 
					DatabaseUserIDReadAccess, DatabaseUserIDWriteAccess,
					DataSetCatalogID, OrganizationID);

			return consortium;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			Consortium consortium = (Consortium) object;

			putMultipleInYaml(StandardDatasetMetaData.DatabaseUserIDReadAccess, 
					map,consortium.getDatabaseUserIDReadAccess());
			putMultipleInYaml(StandardDatasetMetaData.DatabaseUserIDWriteAccess, 
					map,consortium.getDatabaseUserIDWriteAccess());
			putMultipleInYaml(StandardDatasetMetaData.parameterSetDescriptionsS, 
					map,consortium.getDataSetCatalogID());
			putMultipleInYaml(StandardDatasetMetaData.orginfoKeyS, 
					map,consortium.getOrganizationID());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(Consortium.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return Consortium.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			return null;
		}

	}, ConvertInputDataBase {
		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);
			
			ConvertInputDataBase convert = null;
			String inputType = (String) yaml.get(StandardDatasetMetaData.inputTypeS);
			String outputType = (String) yaml.get(StandardDatasetMetaData.outputTypeS);

			convert = new ConvertInputDataBase(objdata,inputType,outputType);
			return convert;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {

			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			ConvertInputDataBase base = (ConvertInputDataBase) object;
			map.put(StandardDatasetMetaData.inputTypeS, base.getInputType());
			map.put(StandardDatasetMetaData.outputTypeS, base.getOutputType());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ConvertInputDataBase.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return ConvertInputDataBase.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}, ConvertToMatrixOfObjects {
		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			InterpretData interpret = InterpretData.valueOf("ConvertInputDataBase");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);
			
			ConvertToMatrixOfObjects convert = null;
			String fileType = (String) yaml.get(StandardDatasetMetaData.fileTypeS);
			String delimitor = (String) yaml.get(StandardDatasetMetaData.delimitorS);

			convert = new ConvertToMatrixOfObjects(objdata,fileType,delimitor);
			return convert;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {

			InterpretData interpret = InterpretData.valueOf("ConvertInputDataBase");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			ConvertToMatrixOfObjects convert = (ConvertToMatrixOfObjects ) object;
			map.put(StandardDatasetMetaData.fileTypeS, convert.getFileType());
			map.put(StandardDatasetMetaData.delimitorS, convert.getDelimitor());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ConvertToMatrixOfObjects.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return ConvertToMatrixOfObjects.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject obj) {
			// TODO Auto-generated method stub
			return null;
		}
		
	};
	

	
	public abstract DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj);
	
	public abstract DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
			throws IOException;

	public abstract Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException;

	public abstract DatabaseObject readElementFromDatabase(String identifier) throws IOException;

	public abstract String canonicalClassName();

	public HashSet<String> parseKeywords(String keywordset) {
		HashSet<String> keywords = new HashSet<String>();
		if (keywordset != null) {
			StringTokenizer tok = new StringTokenizer(keywordset, ",");
			while (tok.hasMoreTokens()) {
				keywords.add(tok.nextToken().trim());
			}
		}
		return keywords;
	}

	public Date parseDate(String dateS) throws IOException {
		Date dateD = null;
		dateD = parseDateWithFormat(dateS, "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		if (dateD == null) {
			dateD = parseDateWithFormat(dateS, "yyyy-MM-dd");
		}
		if (dateD == null) {
			dateD = parseDateWithFormat(dateS, "yyyyMMdd");
		}
		if (dateD == null) {
			throw new IOException("Date parse exception");
		}
		return dateD;
	}

	public Date parseDateWithFormat(String dateS, String formatS) {
		Date dateD = null;
		try {
			DateFormat format = new SimpleDateFormat(formatS);
			dateD = format.parse(dateS);
		} catch (ParseException e) {
		}
		return dateD;

	}

	public String dateToString(Date dateD) {
		String formatS = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
		DateFormat format = new SimpleDateFormat(formatS);
		String dateS = format.format(dateD);
		return dateS;
	}

	public String keywordsToString(HashSet<String> keywords) {
		StringBuilder build = new StringBuilder();
		boolean notfirstKey = false;
		for (String key : keywords) {
			if (notfirstKey) {
				build.append(", ");
			} else {
				notfirstKey = true;
			}
			build.append(key);
		}
		return build.toString();
	}

	public void putMultipleInYaml(String key, Map<String, Object> yaml, HashSet<String> set) {
		ArrayList<String> arr = new ArrayList<String>();
		if(set != null) {
		for(String name : set) {
			arr.add(name);
		}
		}
		yaml.put(key, arr);
	}
	public void putMultipleInYamlList(String key, Map<String, Object> yaml, ArrayList<String> lst) {
		if(lst != null) {
			yaml.put(key, lst);
		} else {
			yaml.put(key, new ArrayList<String>());
		}
		
	}
	
	public ArrayList<String> interpretMultipleYamlList(String key, Map<String, Object> yaml) {
		//System.out.println("interpretMultipleYamlList: \n" + yaml.toString());
		ArrayList<String> answers = new ArrayList<String>();
		Object yamlobj = yaml.get(key);
		//System.out.println("interpretMultipleYamlList: " + key);
		//System.out.println("interpretMultipleYamlList: \n" + yamlobj);
		//System.out.println("interpretMultipleYamlList: \n" + yaml.toString());
		@SuppressWarnings("unchecked")
		List<String> lst = (List<String>) yamlobj;
		for (String answer : lst) {
			answers.add(answer);
		}
		return answers;
	}
	
	@SuppressWarnings("unchecked")
	public HashSet<String> interpretMultipleYaml(String key, Map<String, Object> yaml) {
		HashSet<String> answers = new HashSet<String>();
		Object yamlobj = yaml.get(key);
	if (yamlobj != null) {
			if (yamlobj.getClass().getCanonicalName().compareTo("java.util.ArrayList") == 0) {
				List<String> lst = (List<String>) yamlobj;
				for (String answer : lst) {
					answers.add(answer);
				}
			} else if (yamlobj.getClass().getCanonicalName().compareTo("java.util.HashMap") == 0) {
				HashMap<String, Object> map = (HashMap<String, Object>) yamlobj;
				Set<String> keyset = map.keySet();
				for(String mapkey : keyset) {
					HashMap<String, Object> submap = (HashMap<String, Object>) map.get(mapkey);
					String identifier = (String) submap.get(StandardDatasetMetaData.identifierKeyS);
					answers.add(identifier);
				}
			} else {
				String answer = (String) yamlobj;
				answers.add(answer);
			}
		}
		return answers;
	}
	String createSuffix(DatabaseObject obj, String elementname, Map<String, DataElementInformation> elementmap) {
		DataElementInformation element = elementmap.get(elementname);
		return createSuffix(obj, element);
	}

	public static String createSuffix(DatabaseObject obj, DataElementInformation element) {
		return obj.getIdentifier() + "-" + element.getSuffix();
	}

	Map<String, DataElementInformation> createElementMap(String dataelement) {
		info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure structures = DatasetOntologyParsing
				.subElementsOfStructure(dataelement);
		return createElementMap(structures);
	}

	Map<String, DataElementInformation> createElementMap(info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure structures) {
		Map<String, DataElementInformation> elementmap = new HashMap<String, DataElementInformation>();
		for (DataElementInformation element : structures) {
			elementmap.put(element.getDataElementName(), element);
		}
		return elementmap;
	}
	public String removeNamespace(String name) {
		int pos = name.indexOf(":");
		String ans = name;
		if(pos >= 0) {
			ans = name.substring(pos+1);
		}
		return ans;
	}
	
	public static void setChemConnectCompoundMultipleType(DatabaseObjectHierarchy hierarchy, String dataType) {
		ChemConnectCompoundMultiple multiple = (ChemConnectCompoundMultiple) hierarchy.getObject();
		multiple.setType(dataType);
		DataElementInformation refelement = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(dataType);
		String refid = createSuffix(multiple, refelement);
		multiple.setIdentifier(refid);
	}
	public static String noUnitClassS = "no unit class";
	public static String noValueUnitsS = "no value units";
	public static String noPurposeS = "no purpose";
	public static String noConceptS = "no concept";

}
