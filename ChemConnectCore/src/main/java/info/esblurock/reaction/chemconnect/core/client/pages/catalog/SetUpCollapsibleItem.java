package info.esblurock.reaction.chemconnect.core.client.pages.catalog;


import com.google.gwt.user.client.Window;

import info.esblurock.reaction.chemconnect.core.client.contact.StandardDatasetContactInfoHeader;
import info.esblurock.reaction.chemconnect.core.client.contact.StandardDatasetContactLocationInformationHeader;
import info.esblurock.reaction.chemconnect.core.client.contact.StandardDatasetOrganizationDescriptionHeader;
import info.esblurock.reaction.chemconnect.core.client.contact.StandardDatasetOrganizationHeader;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.hierarchy.StandardDatasetCatalogHierarchyHeader;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.link.PrimitiveDataObjectLinkRow;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.methodology.StandardDatasetMethodologyHeader;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.multiple.ChemConnectCompoundMultipleHeader;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.observations.PrimitiveParameterValueRow;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.observations.StandardDatasetObservationSpecificationHeader;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.observations.StandardDatasetParameterSpecificationHeader;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.observations.StandardDatasetSetOfObservationValuesHeader;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.observations.StandardDatasetValueUnitsHeader;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.subsystems.StandardDatasetSubSystemHeader;
import info.esblurock.reaction.chemconnect.core.client.pages.description.StandardDatasetDescriptionDataDataHeader;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.concept.PrimitiveConceptRow;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.gps.PrimitiveGPSLocationRow;
import info.esblurock.reaction.chemconnect.core.client.pages.reference.StandardDatasetDataSetReferenceHeader;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.OrganizationDescription;
import info.esblurock.reaction.chemconnect.core.data.dataset.PurposeConceptPair;

public enum SetUpCollapsibleItem {
	
	DatasetCatalogHierarchy {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetCatalogHierarchyHeader header = new StandardDatasetCatalogHierarchyHeader(item);
			item.addHeader(header);
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
		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
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
			return false;
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
		
	},
	SubSystemDescription {

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
		
	}, SetOfObservationValues {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetSetOfObservationValuesHeader header = new StandardDatasetSetOfObservationValuesHeader(item);
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
		
	}, ChemConnectMethodology {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetMethodologyHeader header = new StandardDatasetMethodologyHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetMethodologyHeader header = (StandardDatasetMethodologyHeader) item.getHeader();
			return header.updateMethodology();
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
		
	}, ParameterValue {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			PrimitiveParameterValueRow header = new PrimitiveParameterValueRow(item.getHierarchy());
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
			PrimitiveParameterValueRow header = new PrimitiveParameterValueRow(item.getHierarchy());
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
			return false;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return true;
		}
		
	}, MeasurementParameterValue {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			PrimitiveParameterValueRow header = new PrimitiveParameterValueRow(item.getHierarchy());
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
			return false;
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
			return header.updateObject();
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
			return header.updateObject();
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
			return header.updateObject();
		}
		
	}, ChemConnectCompoundMultiple {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			ChemConnectCompoundMultiple multiple = (ChemConnectCompoundMultiple) item.getObject();
			ChemConnectCompoundMultipleHeader header = new ChemConnectCompoundMultipleHeader(multiple.getType());
			item.addHeader(header);
		}

		@Override
		public int priority() {
			return 50;
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

	}, DataObjectLink {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			item.setBodyVisible(false);
			PrimitiveDataObjectLinkRow row = new PrimitiveDataObjectLinkRow(item.getObject());
			item.addHeader(row);
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
			item.setBodyVisible(false);
			PrimitiveConceptRow concept = new PrimitiveConceptRow(item.getObject());
			item.addHeader(concept);
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
			return false;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			PrimitiveConceptRow row = (PrimitiveConceptRow) item.getHeader();
			PurposeConceptPair pair = (PurposeConceptPair) item.getObject();
			pair.setPurpose(row.getPurpose());
			pair.setConcept(row.getConcept());
			return true;
		}

	}, GPSLocation {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			Window.alert("GPSLocation insert");
			PrimitiveGPSLocationRow row = new PrimitiveGPSLocationRow(item.getObject());
			Window.alert("GPSLocation: " + item.getObject());
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
		
	}, DescriptionDataData {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetDescriptionDataDataHeader header = new StandardDatasetDescriptionDataDataHeader(item.getObject());
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetDescriptionDataDataHeader header = (StandardDatasetDescriptionDataDataHeader) item.getHeader();
			return header.updateData();
		}

		@Override
		public int priority() {
			return 10;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return true;
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
	},ContactLocationInformation {

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
		
	},
	DataSetReference {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetDataSetReferenceHeader header = new StandardDatasetDataSetReferenceHeader(item.getObject());
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
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
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
			return false;
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
