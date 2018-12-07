package info.esblurock.reaction.chemconnect.core.client.pages;

import java.io.Serializable;

import com.google.gwt.user.client.ui.Widget;

import info.esblurock.reaction.chemconnect.core.client.catalog.ManageCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.client.catalog.protocol.ProtocolDefinition;
import info.esblurock.reaction.chemconnect.core.client.contact.DatabasePersonDefinition;
import info.esblurock.reaction.chemconnect.core.client.contact.OrganizationDefinition;
import info.esblurock.reaction.chemconnect.core.client.device.DeviceWithSubystemsDefinition;
import info.esblurock.reaction.chemconnect.core.client.device.SetOfObservationsDefinition;
import info.esblurock.reaction.chemconnect.core.client.device.observations.matrix.IsolateMatrixBlock;
import info.esblurock.reaction.chemconnect.core.client.gcs.UploadFileToBlobStorage;

public enum DataStructurePages implements Serializable {
	
	
	

	DataStructures {

		@Override
		public String getTitle() {
			return "Main Data Structures";
		}
		@Override
		public String getDescription() {
			return "The set of main data structures";
		}
		@Override
		public Widget getContent() {
			return dataStructures;
		}
	}, DeviceDefinition {

		@Override
		public String getTitle() {
			return "Device Description";
		}

		@Override
		public String getDescription() {
			return "Fields for defining a device with subsystems";
		}

		@Override
		public Widget getContent() {
			return subsystems;
		}
		
	}, Observations {

		@Override
		public String getTitle() {
			return "Observations";
		}

		@Override
		public String getDescription() {
			return "Fields for defining a set of observations";
		}

		@Override
		public Widget getContent() {
			return setofobservations;
		}
		
	}, BlobStorage {

		@Override
		public String getTitle() {
			return "BlobStorage";
		}

		@Override
		public String getDescription() {
			return "Read in and set in blob storage";
		}

		@Override
		public Widget getContent() {
			return blobstorage;
		}
		
	},
	
	Protocol {

		@Override
		public String getTitle() {
			return "Protocol";
		}

		@Override
		public String getDescription() {
			return "Fields for defining a protocol";
		}

		@Override
		public Widget getContent() {
			return protocol;
		}
		
	}, ManageCatalog {

		@Override
		public String getTitle() {
			return "Manage Catalog Hierarchy";
		}

		@Override
		public String getDescription() {
			return "Manage the catalog hierarchy";
		}

		@Override
		public Widget getContent() {
			return managecatalog;
		}
		
	}, Organization {

		@Override
		public String getTitle() {
			return "Organizations";
		}

		@Override
		public String getDescription() {
			return "Manage Organizations";
		}

		@Override
		public Widget getContent() {
			return organizations;
		}
		
	}, DatabasePerson {

		@Override
		public String getTitle() {
			return "People";
		}

		@Override
		public String getDescription() {
			return "Manage the people profiles available to user";
		}

		@Override
		public Widget getContent() {
			return people;
		}
		
	}, IsolateMatrixBlock {

		@Override
		public String getTitle() {
			return "Isolate Matrix Block";
		}

		@Override
		public String getDescription() {
			return "From a full matrix file, isolate a single block of information";
		}

		@Override
		public Widget getContent() {
			return block;
		}
		
	};
	
	public abstract String getTitle();
	public abstract String getDescription();
	public abstract Widget getContent();
	
	public static MainDataStructureVisualization dataStructures = new MainDataStructureVisualization();
	public static IsolateMatrixBlock block = new IsolateMatrixBlock();
	public static DeviceWithSubystemsDefinition subsystems = new DeviceWithSubystemsDefinition();
	public static SetOfObservationsDefinition setofobservations = new SetOfObservationsDefinition();
	public static ProtocolDefinition protocol = new ProtocolDefinition();
	public static UploadFileToBlobStorage blobstorage = new UploadFileToBlobStorage();
	public static ManageCatalogHierarchy managecatalog = new ManageCatalogHierarchy();
	public static OrganizationDefinition organizations = new OrganizationDefinition();
	public static DatabasePersonDefinition people = new DatabasePersonDefinition();
	
}
