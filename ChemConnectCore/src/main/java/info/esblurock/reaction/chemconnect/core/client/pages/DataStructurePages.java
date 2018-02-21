package info.esblurock.reaction.chemconnect.core.client.pages;

import java.io.Serializable;

import com.google.gwt.user.client.ui.Widget;

import info.esblurock.reaction.chemconnect.core.client.device.DeviceWithSubystemsDefinition;
import info.esblurock.reaction.chemconnect.core.client.device.MethodologyDefinition;
import info.esblurock.reaction.chemconnect.core.client.device.SetOfObservationsDefinition;
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
			return new MainDataStructureVisualization();
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
			return new VisualizeGraphicalObjects();
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
			return new DeviceWithSubystemsDefinition();
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
			return new SetOfObservationsDefinition();
		}
		
	}, Methodology {

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
			return new MethodologyDefinition();
		}
		
	};
	
	public abstract String getTitle();
	public abstract String getDescription();
	public abstract Widget getContent();
}
