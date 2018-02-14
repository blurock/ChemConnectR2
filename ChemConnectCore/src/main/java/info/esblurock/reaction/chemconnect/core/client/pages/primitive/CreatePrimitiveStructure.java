package info.esblurock.reaction.chemconnect.core.client.pages.primitive;


import com.google.gwt.user.client.Window;

import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.PrimitiveParameterSpecification;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.SetOfObservationsField;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.value.PrimitiveParameterValue;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.value.PrimitiveParameterValueRow;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterSpecificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterValueInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.SetOfObservationsInformation;

public enum CreatePrimitiveStructure {
	
	ParameterValue {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			PrimitiveDataStructureBase base = new PrimitiveDataStructureBase(info);
			PrimitiveParameterValue value = new PrimitiveParameterValue(info);
			base.add(value);
			return base;
		}

		@Override
		public PrimitiveDataStructureBase createEmptyStructure() {
			PrimitiveParameterValueInformation info = new PrimitiveParameterValueInformation();
			PrimitiveDataStructureBase base = new PrimitiveDataStructureBase(info);
			PrimitiveParameterValue value = new PrimitiveParameterValue(info);
			base.add(value);
			return base;
		}

		@Override
		public String getStructureName() {
			return "ParameterValue";
		}
		
	}, ParameterSpecification {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			PrimitiveParameterSpecification spec = new PrimitiveParameterSpecification(info);
			return spec;
		}

		@Override
		public PrimitiveDataStructureBase createEmptyStructure() {
			PrimitiveParameterSpecificationInformation info = new PrimitiveParameterSpecificationInformation();
			PrimitiveParameterSpecification spec = new PrimitiveParameterSpecification(info);
			return spec;
		}

		@Override
		public String getStructureName() {
			return "ParameterSpecification";
		}
		
	}, SetOfObservationsSpecification {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			Window.alert("CreatePrimitiveStructure --> SetOfObservationsSpecification: ");
			SetOfObservationsInformation obs = (SetOfObservationsInformation) info;
			SetOfObservationsField set = new SetOfObservationsField(obs);
			return set;
		}

		@Override
		public PrimitiveDataStructureBase createEmptyStructure() {
			SetOfObservationsInformation obs = new SetOfObservationsInformation();
			SetOfObservationsField set = new SetOfObservationsField(obs);
			return set;
		}

		@Override
		public String getStructureName() {
			return "SetOfObservationsSpecification";
		}
		
	},
	datasetClassification {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			ClassificationPrimitiveDataStructure base = new ClassificationPrimitiveDataStructure(info);
			return base;
		}

		@Override
		public PrimitiveDataStructureBase createEmptyStructure() {
			ClassificationPrimitiveDataStructure base = new ClassificationPrimitiveDataStructure();
			return base;
		}

		@Override
		public String getStructureName() {
			return "datasetClassification";
		}
	}, dctermsdescription {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			DescriptionParagraphPrimitiveDataStructure base = new DescriptionParagraphPrimitiveDataStructure(info);
			return base;
		}

		@Override
		public PrimitiveDataStructureBase createEmptyStructure() {
			DescriptionParagraphPrimitiveDataStructure base = new DescriptionParagraphPrimitiveDataStructure();
			return base;
		}

		@Override
		public String getStructureName() {
			return "dctermsdescription";
		}
		
	};
	
	public abstract PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info);
	public abstract PrimitiveDataStructureBase createEmptyStructure();
	public abstract String getStructureName();
	
	public static CreatePrimitiveStructure getStructureType(PrimitiveDataStructureInformation primitive) {
		CreatePrimitiveStructure create = null;
		String identifier = primitive.getPropertyType();
		int pos = identifier.indexOf(':');
		if (pos >= 0) {
			String key = identifier.substring(0, pos) + identifier.substring(pos + 1);
			try {
				create = CreatePrimitiveStructure.valueOf(key);
			} catch (Exception ex) {
			}
		} else {
			try {
				create = CreatePrimitiveStructure.valueOf(identifier);
			} catch (Exception ex) {
			}
		}
		return create;
	}
	
}
