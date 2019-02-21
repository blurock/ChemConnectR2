package info.esblurock.reaction.chemconnect.core.client.catalog;

import info.esblurock.reaction.chemconnect.core.client.catalog.description.StandardDatasetDescriptionDataDataHeader;
import info.esblurock.reaction.chemconnect.core.client.catalog.hierarchy.StandardDatasetCatalogHierarchyHeader;
import info.esblurock.reaction.chemconnect.core.client.catalog.image.DatasetImageHeader;
import info.esblurock.reaction.chemconnect.core.client.catalog.image.ImageInformationHeader;
import info.esblurock.reaction.chemconnect.core.client.catalog.link.PrimitiveDataObjectLinkRow;
import info.esblurock.reaction.chemconnect.core.client.catalog.multiple.ChemConnectCompoundMultipleHeader;
import info.esblurock.reaction.chemconnect.core.client.catalog.protocol.StandardDatasetProtocolHeader;
import info.esblurock.reaction.chemconnect.core.client.catalog.reference.AuthorInformationHeader;
import info.esblurock.reaction.chemconnect.core.client.catalog.reference.StandardDatasetDataSetReferenceHeader;
import info.esblurock.reaction.chemconnect.core.client.concept.PrimitiveConceptRow;
import info.esblurock.reaction.chemconnect.core.client.contact.StandardDatabasePersonalDescriptionHeader;
import info.esblurock.reaction.chemconnect.core.client.contact.StandardDatasetContactHasSiteHeader;
import info.esblurock.reaction.chemconnect.core.client.contact.StandardDatasetContactInfoHeader;
import info.esblurock.reaction.chemconnect.core.client.contact.StandardDatasetContactLocationInformationHeader;
import info.esblurock.reaction.chemconnect.core.client.contact.StandardDatasetIndividualInformation;
import info.esblurock.reaction.chemconnect.core.client.contact.StandardDatasetNameOfPersonHeader;
import info.esblurock.reaction.chemconnect.core.client.contact.StandardDatasetOrganizationDescriptionHeader;
import info.esblurock.reaction.chemconnect.core.client.contact.StandardDatasetOrganizationHeader;
import info.esblurock.reaction.chemconnect.core.client.contact.gps.PrimitiveGPSLocationRow;
import info.esblurock.reaction.chemconnect.core.client.device.StandardDatasetSubSystemHeader;
import info.esblurock.reaction.chemconnect.core.client.device.observations.ParameterValueHeader;
import info.esblurock.reaction.chemconnect.core.client.device.observations.PrimitiveParameterValueRow;
import info.esblurock.reaction.chemconnect.core.client.device.observations.StandardDatasetObservationSpecificationHeader;
import info.esblurock.reaction.chemconnect.core.client.device.observations.StandardDatasetParameterSpecificationHeader;
import info.esblurock.reaction.chemconnect.core.client.device.observations.StandardDatasetObservationCorrespondenceSpecificationHeader;
import info.esblurock.reaction.chemconnect.core.client.device.observations.matrix.MatrixSpecificationCorrespondenceHeader;
import info.esblurock.reaction.chemconnect.core.client.device.observations.matrix.MatrixSpecificationCorrespondenceSetHeader;
import info.esblurock.reaction.chemconnect.core.client.device.observations.matrix.ObservationBlockFromSpreadSheetHeader;
import info.esblurock.reaction.chemconnect.core.client.device.observations.matrix.SpreadSheetBlockMatrix;
import info.esblurock.reaction.chemconnect.core.client.device.observations.matrix.SpreadSheetInputInformationHeader;
import info.esblurock.reaction.chemconnect.core.client.device.observations.matrix.SpreadSheetMatrixBlockIsolateHeader;
import info.esblurock.reaction.chemconnect.core.client.device.observations.matrix.StandardDatasetObservationValueRowTitle;
import info.esblurock.reaction.chemconnect.core.client.device.observations.matrix.StandardDatasetObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.client.device.observations.matrix.StandardDatasetObservationsFromSpreadSheetFull;
import info.esblurock.reaction.chemconnect.core.client.device.observations.units.StandardDatasetValueUnitsHeader;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.description.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public enum SetUpCollapsibleItem {
	
	DatasetCatalogHierarchy {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetCatalogHierarchyHeader header = new StandardDatasetCatalogHierarchyHeader(item);
			item.addHeader(header);
		}
		@Override
		public int priority() {
			return 100;
		}
		@Override
		public boolean isInformation() {
			return false;
		}
		@Override
		public boolean addSubitems() {
			return true;
		}
		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			//item.updateDatabaseObjectHierarchy();
			UserImageServiceAsync async = UserImageService.Util.getInstance();
			WriteDatasetObjectHierarchyCallback callback = new WriteDatasetObjectHierarchyCallback(item);
			async.writeDatabaseObjectHierarchy(item.getHierarchy(), callback);
			return true;
		}
	}, Organization {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetOrganizationHeader header = new StandardDatasetOrganizationHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetOrganizationHeader header = (StandardDatasetOrganizationHeader) item.getHeader();
			header.updateData();
			return true;
		}

		@Override
		public int priority() {
			return 610;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}
		
	}, OrganizationDescription {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetOrganizationDescriptionHeader header = new StandardDatasetOrganizationDescriptionHeader(item);
			item.addHeader(header);			
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetOrganizationDescriptionHeader header = (StandardDatasetOrganizationDescriptionHeader)item.getHeader();
			header.updateInfo();
			return false;
		}

		@Override
		public int priority() {
			return 609;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	}, SubSystemDescription {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetSubSystemHeader header = new StandardDatasetSubSystemHeader(item);
			item.addHeader(header);
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return true;
		}
		
	}, IndividualInformation {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetIndividualInformation header = new StandardDatasetIndividualInformation(item);
			item.addHeader(header);
			
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return true;
		}

		@Override
		public int priority() {
			return 610;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}
		
	}, PersonalDescription {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatabasePersonalDescriptionHeader header = new StandardDatabasePersonalDescriptionHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatabasePersonalDescriptionHeader header = (StandardDatabasePersonalDescriptionHeader) item.getHeader();
			return header.updateData();
		}

		@Override
		public int priority() {
			return 609;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
	}, ObservationsFromSpreadSheet  {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetObservationsFromSpreadSheet header = new StandardDatasetObservationsFromSpreadSheet(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return true;
		}

		@Override
		public int priority() {
			return 800;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}
		
	}, ObservationsFromSpreadSheetFull  {
		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetObservationsFromSpreadSheetFull header = new StandardDatasetObservationsFromSpreadSheetFull(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return true;
		}

		@Override
		public int priority() {
			return 800;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}
		
	}, ObservationValueRowTitle {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetObservationValueRowTitle title = new StandardDatasetObservationValueRowTitle(item);
			item.addHeader(title);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return false;
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	}, ObservationBlockFromSpreadSheet {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			ObservationBlockFromSpreadSheetHeader header = new ObservationBlockFromSpreadSheetHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			ObservationBlockFromSpreadSheetHeader header = (ObservationBlockFromSpreadSheetHeader) item.getHeader();
			return header.updateData();
		}

		@Override
		public int priority() {
			return 500;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}
		
	},
	
	SpreadSheetBlockIsolation {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			SpreadSheetMatrixBlockIsolateHeader header = new SpreadSheetMatrixBlockIsolateHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return false;
		}

		@Override
		public int priority() {
			return 400;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	}, ObservationSpecification {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetObservationSpecificationHeader header = new StandardDatasetObservationSpecificationHeader(item);
			item.addHeader(header);
			
		}

		@Override
		public int priority() {
			return 10;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return true;
		}
		
	}, ObservationCorrespondenceSpecification {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetObservationCorrespondenceSpecificationHeader header = new StandardDatasetObservationCorrespondenceSpecificationHeader(item);
			item.addHeader(header);
			
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetObservationCorrespondenceSpecificationHeader header = (StandardDatasetObservationCorrespondenceSpecificationHeader) item.getHeader();
			header.updateData();
			return true;
		}
		
	}, SpreadSheetInputInformation {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			SpreadSheetInputInformationHeader header = new SpreadSheetInputInformationHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return true;
		}

		@Override
		public int priority() {
			return 200;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}
		
	}, ObservationMatrixValues {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			SpreadSheetBlockMatrix header = new SpreadSheetBlockMatrix(item);	
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return false;
		}

		@Override
		public int priority() {
			return 200;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	}, MatrixSpecificationCorrespondenceSet {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			MatrixSpecificationCorrespondenceSetHeader header = new MatrixSpecificationCorrespondenceSetHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			MatrixSpecificationCorrespondenceSetHeader header = 
						(MatrixSpecificationCorrespondenceSetHeader) item.getHeader();
			header.updateData();
			return true;
		}

		@Override
		public int priority() {
			// TODO Auto-generated method stub
			return 500;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	}, MatrixSpecificationCorrespondence {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			MatrixSpecificationCorrespondenceHeader header = new MatrixSpecificationCorrespondenceHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			MatrixSpecificationCorrespondenceHeader header = (MatrixSpecificationCorrespondenceHeader)
					item.getHeader();
			header.updateData();
			return false;
		}

		@Override
		public int priority() {
			return 500;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	}, DatasetImage {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			DatasetImageHeader header = new DatasetImageHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return true;
		}

		@Override
		public int priority() {
			return 0;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}
		
	}, ImageInformation {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			ImageInformationHeader header = new ImageInformationHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			ImageInformationHeader image = (ImageInformationHeader) item.getHeader();
			image.updateData();
			return false;
		}

		@Override
		public int priority() {
			return 700;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	}, ChemConnectProtocol {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetProtocolHeader header = new StandardDatasetProtocolHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetProtocolHeader header = (StandardDatasetProtocolHeader) item.getHeader();
			return header.updateProtocol();
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	}, ParameterValue {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			ParameterValueHeader header = new ParameterValueHeader(item.getHierarchy());
			item.addHeader(header);
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			PrimitiveParameterValueRow header = (PrimitiveParameterValueRow) item.getHeader();
			return header.updateObject();
		}
		
	}, DimensionParameterValue {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			ParameterValueHeader header = new ParameterValueHeader(item.getHierarchy());
			item.addHeader(header);
			
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			ParameterValueHeader header = (ParameterValueHeader) item.getHeader();
			return header.updateObject();
		}
		
	}, MeasurementParameterValue {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			ParameterValueHeader header = new ParameterValueHeader(item.getHierarchy());
			item.addHeader(header);
			
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			PrimitiveParameterValueRow header = (PrimitiveParameterValueRow) item.getHeader();
			return header.updateObject();
		}
		
	}, ParameterSpecification {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetParameterSpecificationHeader header = new StandardDatasetParameterSpecificationHeader(item, true);
			item.addHeader(header);
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetParameterSpecificationHeader header = (StandardDatasetParameterSpecificationHeader) item.getHeader();
			return header.updateData();
		}
		
	}, MeasureParameterSpecification {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetParameterSpecificationHeader header = new StandardDatasetParameterSpecificationHeader(item,true);
			item.addHeader(header);
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetParameterSpecificationHeader header = (StandardDatasetParameterSpecificationHeader) item.getHeader();
			return header.updateData();
		}
		
	}, DimensionParameterSpecification {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetParameterSpecificationHeader header = new StandardDatasetParameterSpecificationHeader(item,false);
			item.addHeader(header);
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetParameterSpecificationHeader header = (StandardDatasetParameterSpecificationHeader) item.getHeader();
			return header.updateData();
		}
		
	}, ChemConnectCompoundMultiple {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			ChemConnectCompoundMultipleHeader header = new ChemConnectCompoundMultipleHeader(item);
			item.addHeader(header);
		}

		@Override
		public int priority() {
			return 1;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			ChemConnectCompoundMultipleHeader header = (ChemConnectCompoundMultipleHeader) item.getHeader();
			header.updateObject();
			return true;
		}

	}, DataObjectLink {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			PrimitiveDataObjectLinkRow row = new PrimitiveDataObjectLinkRow(item);
			item.addHeader(row);
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			PrimitiveDataObjectLinkRow header = (PrimitiveDataObjectLinkRow) item.getHeader();
			return header.updateObject();
		}

	}, PurposeConceptPair {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			PrimitiveConceptRow concept = new PrimitiveConceptRow(item.getObject());
			item.addHeader(concept);
		}

		@Override
		public int priority() {
			return 90;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			PrimitiveConceptRow row = (PrimitiveConceptRow) item.getHeader();
			return row.updateData();
		}

	}, DataCatalogID {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			DatasetStandardDataCatalogIDHeader header = new DatasetStandardDataCatalogIDHeader(item);
			item.addHeader(header);
			
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			DatasetStandardDataCatalogIDHeader header = (DatasetStandardDataCatalogIDHeader) item.getHeader();
			header.updateData();
			return false;
		}

		@Override
		public int priority() {
			return 1000;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	},
	
	
	GPSLocation {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			PrimitiveGPSLocationRow row = new PrimitiveGPSLocationRow(item.getObject());
			item.addHeader(row);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			PrimitiveGPSLocationRow header = (PrimitiveGPSLocationRow) item.getHeader();
			header.update();
			return false;
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	}, NameOfPerson {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetNameOfPersonHeader header = new StandardDatasetNameOfPersonHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return false;
		}

		@Override
		public int priority() {
			return 400;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	}, DescriptionDataData {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			DescriptionDataData description = (DescriptionDataData) item.getObject();
			String purposeconceptID = description.getSourceConcept();
			DatabaseObjectHierarchy hierarchy = item.getHierarchy();
			DatabaseObjectHierarchy phierarchy = hierarchy.getSubObject(purposeconceptID);
			StandardDatasetObjectHierarchyItem subitem = new StandardDatasetObjectHierarchyItem(item,phierarchy,item.getModalpanel());
			SetUpCollapsibleItem setup = SetUpCollapsibleItem.valueOf("PurposeConceptPair");
			setup.addInformation(subitem);
			PrimitiveConceptRow conceptrow = (PrimitiveConceptRow) subitem.getHeader();
			StandardDatasetDescriptionDataDataHeader header 
				= new StandardDatasetDescriptionDataDataHeader(description,conceptrow);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetDescriptionDataDataHeader header = (StandardDatasetDescriptionDataDataHeader) item.getHeader();
			return header.updateData();
		}

		@Override
		public int priority() {
			return 900;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	}, ContactInfoData {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetContactInfoHeader header = new StandardDatasetContactInfoHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetContactInfoHeader header = (StandardDatasetContactInfoHeader) item.getHeader();
			return header.updateData();
		}

		@Override
		public int priority() {
			return 500;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
	}, AuthorInformation {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			AuthorInformationHeader header = new AuthorInformationHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return false;
		}

		@Override
		public int priority() {
			return 0;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	},
	
	
	ContactLocationInformation {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetContactLocationInformationHeader header = new StandardDatasetContactLocationInformationHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetContactLocationInformationHeader header = (StandardDatasetContactLocationInformationHeader) item.getHeader();
			return header.updateData();
		}

		@Override
		public int priority() {
			return 500;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}
		
	}, ContactHasSite {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetContactHasSiteHeader header = new StandardDatasetContactHasSiteHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return false;
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	},
	DataSetReference {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetDataSetReferenceHeader header = new StandardDatasetDataSetReferenceHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetDataSetReferenceHeader header = (StandardDatasetDataSetReferenceHeader) item.getHeader();
			header.updateReference();
			return true;
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	}, ValueUnits {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetValueUnitsHeader header = new StandardDatasetValueUnitsHeader(item.getObject());
			item.addHeader(header);
			
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetValueUnitsHeader header = (StandardDatasetValueUnitsHeader) item.getHeader();
			header.updateValues();
			return false;
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	};

	public abstract void addInformation(StandardDatasetObjectHierarchyItem item);
	public abstract boolean update(StandardDatasetObjectHierarchyItem item);
	public abstract int priority();
	public abstract boolean isInformation();
	public abstract boolean addSubitems();
	public static void addGenericInformation(DatabaseObject object,
			StandardDatasetObjectHierarchyItem item) {
		StandardDatasetGenericHeader header = new StandardDatasetGenericHeader(object.getClass().getSimpleName());
		item.addHeader(header);
		
	}
}
