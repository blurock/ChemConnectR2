package info.esblurock.reaction.chemconnect.core.client.pages.primitive;


import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.PrimitiveParameterSpecification;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.value.PrimitiveParameterValue;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.value.PrimitiveParameterValueRow;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterSpecificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterValueInformation;

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
		
	}, ParameterSpecification {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			PrimitiveDataStructureBase base = new PrimitiveDataStructureBase(info);
			PrimitiveParameterSpecification spec = new PrimitiveParameterSpecification(info);
			base.add(spec);
			return base;
		}

		@Override
		public PrimitiveDataStructureBase createEmptyStructure() {
			PrimitiveParameterSpecificationInformation info = new PrimitiveParameterSpecificationInformation();
			PrimitiveDataStructureBase base = new PrimitiveDataStructureBase(info);
			PrimitiveParameterSpecification spec = new PrimitiveParameterSpecification(info);
			base.add(spec);
			return base;
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
		
	};
	
	public abstract PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info);
	public abstract PrimitiveDataStructureBase createEmptyStructure();
	
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
