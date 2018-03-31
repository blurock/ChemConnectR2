package info.esblurock.reaction.chemconnect.core.client.pages;

import java.io.Serializable;

import com.google.gwt.user.client.ui.Widget;

import info.esblurock.reaction.chemconnect.core.client.device.DeviceWithSubystemsDefinition;
import info.esblurock.reaction.chemconnect.core.client.device.MethodologyDefinition;
import info.esblurock.reaction.chemconnect.core.client.device.SetOfObservationsDefinition;
import info.esblurock.reaction.chemconnect.core.client.gcs.UploadFileToBlobStorage;
import info.esblurock.reaction.chemconnect.core.client.graph.pages.VisualizeGraphicalObjects;

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
	}, GraphicalStructures {

		@Override
		public String getTitle() {
			return "Graphical Structures";
		}

		@Override
		public String getDescription() {
			return "The concepts represented as graph";
		}

		@Override
		public Widget getContent() {
			return graphicalObjects;
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
	
	Methodology {

		@Override
		public String getTitle() {
			return "Methodology";
		}

		@Override
		public String getDescription() {
			return "Fields for defining methodologies";
		}

		@Override
		public Widget getContent() {
			return methodology;
		}
		
	};
	
	public abstract String getTitle();
	public abstract String getDescription();
	public abstract Widget getContent();
	
	static MainDataStructureVisualization dataStructures = new MainDataStructureVisualization();
	static VisualizeGraphicalObjects graphicalObjects = new VisualizeGraphicalObjects();
	static DeviceWithSubystemsDefinition subsystems = new DeviceWithSubystemsDefinition();
	static SetOfObservationsDefinition setofobservations = new SetOfObservationsDefinition();
	static MethodologyDefinition methodology = new MethodologyDefinition();
	static UploadFileToBlobStorage blobstorage = new UploadFileToBlobStorage();
}
