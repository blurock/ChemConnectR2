package info.esblurock.reaction.chemconnect.core.client.pages.catalog;


import info.esblurock.reaction.chemconnect.core.client.pages.catalog.hierarchy.StandardDatasetCatalogHierarchyHeader;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.link.PrimitiveDataObjectLinkRow;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.multiple.ChemConnectCompoundMultipleHeader;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.observations.PrimitiveParameterValueRow;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.observations.StandardDatasetObservationSpecificationHeader;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.observations.StandardDatasetParameterSpecificationHeader;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.observations.StandardDatasetSetOfObservationValuesHeader;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.subsystems.StandardDatasetSubSystemHeader;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.concept.PrimitiveConceptRow;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

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
		
	}, ParameterSpecification {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetParameterSpecificationHeader header = new StandardDatasetParameterSpecificationHeader(item);
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
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}

	};

	public abstract void addInformation(StandardDatasetObjectHierarchyItem item);
	public abstract int priority();
	public abstract boolean isInformation();
	public abstract boolean addSubitems();
	public static void addGenericInformation(DatabaseObject object,
			StandardDatasetObjectHierarchyItem item) {
		StandardDatasetGenericHeader header = new StandardDatasetGenericHeader(object.getClass().getSimpleName());
		item.addHeader(header);
		
	}
}
