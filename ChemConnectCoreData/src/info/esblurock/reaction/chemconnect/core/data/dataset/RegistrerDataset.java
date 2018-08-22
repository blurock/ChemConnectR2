package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.base.ResetDatabaseObjects;
import info.esblurock.reaction.chemconnect.core.data.dataset.device.SubSystemDescription;

public class RegistrerDataset {
		public static void register() {
			ObjectifyService.register(Consortium.class);
			ObjectifyService.register(ChemConnectObjectLink.class);
			ObjectifyService.register(ParameterValue.class);
			ObjectifyService.register(DataObjectLink.class);
			ObjectifyService.register(ChemConnectObjectLink.class);
			ObjectifyService.register(PurposeConceptPair.class);
			ObjectifyService.register(ParameterSpecification.class);
			ObjectifyService.register(MeasureParameterSpecification.class);
			ObjectifyService.register(DimensionParameterSpecification.class);
			ObjectifyService.register(DataSpecification.class);
			ObjectifyService.register(DatasetCatalogHierarchy.class);
			ObjectifyService.register(SubSystemDescription.class);
			ObjectifyService.register(SetOfKeywords.class);
			ObjectifyService.register(SetOfObservationValues.class);
			ObjectifyService.register(DimensionParameterValue.class);
			ObjectifyService.register(MeasurementParameterValue.class);
			ObjectifyService.register(ValueUnits.class);
			ObjectifyService.register(ObservationSpecification.class);
			ObjectifyService.register(DataCatalogID.class);
		}
		
		public static void reset() {
			ResetDatabaseObjects.resetClass(Consortium.class);
			ResetDatabaseObjects.resetClass(ChemConnectObjectLink.class);
			ResetDatabaseObjects.resetClass(ParameterValue.class);
			ResetDatabaseObjects.resetClass(DataObjectLink.class);
			ResetDatabaseObjects.resetClass(ChemConnectObjectLink.class);
			ResetDatabaseObjects.resetClass(PurposeConceptPair.class);
			ResetDatabaseObjects.resetClass(ParameterSpecification.class);
			ResetDatabaseObjects.resetClass(MeasureParameterSpecification.class);
			ResetDatabaseObjects.resetClass(DimensionParameterSpecification.class);
			ResetDatabaseObjects.resetClass(DataSpecification.class);
			ResetDatabaseObjects.resetClass(DatasetCatalogHierarchy.class);
			ResetDatabaseObjects.resetClass(SubSystemDescription.class);
			ResetDatabaseObjects.resetClass(SetOfKeywords.class);
			ResetDatabaseObjects.resetClass(SetOfObservationValues.class);
			ResetDatabaseObjects.resetClass(DimensionParameterValue.class);
			ResetDatabaseObjects.resetClass(MeasurementParameterValue.class);
			ResetDatabaseObjects.resetClass(ValueUnits.class);
			ResetDatabaseObjects.resetClass(ObservationSpecification.class);
			ResetDatabaseObjects.resetClass(DataCatalogID.class);
		}	
}
