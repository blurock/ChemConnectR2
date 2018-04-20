package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.base.ResetDatabaseObjects;
import info.esblurock.reaction.chemconnect.core.data.dataset.device.DeviceSubsystemElement;

public class RegistrerDataset {
		public static void register() {
			ObjectifyService.register(Consortium.class);
			ObjectifyService.register(ChemConnectObjectLink.class);
			ObjectifyService.register(AttributeInDataset.class);
			ObjectifyService.register(ParameterValue.class);
			ObjectifyService.register(DataObjectLink.class);
			ObjectifyService.register(ChemConnectObjectLink.class);
			ObjectifyService.register(PurposeConceptPair.class);
			ObjectifyService.register(ParameterSpecification.class);
			ObjectifyService.register(ParameterDescriptionSet.class);
			ObjectifyService.register(DataSpecification.class);
			ObjectifyService.register(DatasetCatalogHierarchy.class);
			ObjectifyService.register(DeviceSubsystemElement.class);
			ObjectifyService.register(SetOfKeywords.class);
			
		}
		
		public static void reset() {
			ResetDatabaseObjects.resetClass(Consortium.class);
			ResetDatabaseObjects.resetClass(ChemConnectObjectLink.class);
			ResetDatabaseObjects.resetClass(AttributeInDataset.class);
			ResetDatabaseObjects.resetClass(ParameterValue.class);
			ResetDatabaseObjects.resetClass(DataObjectLink.class);
			ResetDatabaseObjects.resetClass(ChemConnectObjectLink.class);
			ResetDatabaseObjects.resetClass(ParameterSpecification.class);
			ResetDatabaseObjects.resetClass(ParameterDescriptionSet.class);
			ResetDatabaseObjects.resetClass(DataSpecification.class);
			ResetDatabaseObjects.resetClass(DatasetCatalogHierarchy.class);
			ResetDatabaseObjects.resetClass(DeviceSubsystemElement.class);
			ResetDatabaseObjects.resetClass(SetOfKeywords.class);
		}	
}
