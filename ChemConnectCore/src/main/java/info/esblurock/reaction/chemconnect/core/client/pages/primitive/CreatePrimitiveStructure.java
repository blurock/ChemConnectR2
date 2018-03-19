package info.esblurock.reaction.chemconnect.core.client.pages.primitive;



import info.esblurock.reaction.chemconnect.core.client.pages.primitive.concept.PrimitiveConcept;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.PrimitiveObservationValuesWithSpecification;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.PrimitiveParameterSpecification;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.SetOfObservationsField;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.reference.PrimitivePersonName;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.reference.PrimitiveReference;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.text.PrimitiveOneLine;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.text.PrimitiveParagraph;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.text.PrimitiveSetOfKeys;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.text.PrimitiveShortString;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.value.PrimitiveParameterValue;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterSpecificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterValueInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveReferenceInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.SetOfObservationsInformation;

public enum CreatePrimitiveStructure {
	
	ParameterValue {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			PrimitiveParameterValue value = new PrimitiveParameterValue(info);
			return value;
		}

		@Override
		public PrimitiveDataStructureBase createEmptyStructure() {
			PrimitiveParameterValueInformation info = new PrimitiveParameterValueInformation();
			PrimitiveParameterValue value = new PrimitiveParameterValue(info);
			return value;
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
		
	}, Paragraph {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			PrimitiveParagraph paragraph = new PrimitiveParagraph(info);
			return paragraph;
		}

		@Override
		public PrimitiveDataStructureBase createEmptyStructure() {
			PrimitiveParagraph paragraph = new PrimitiveParagraph();
			return paragraph;
		}

		@Override
		public String getStructureName() {
			return "Paragraph";
		}
		
	}, ShortString {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			PrimitiveShortString shortstring = new PrimitiveShortString(info);
			return shortstring;
		}

		@Override
		public PrimitiveDataStructureBase createEmptyStructure() {
			PrimitiveShortString shortstring = new PrimitiveShortString();
			return shortstring;
		}

		@Override
		public String getStructureName() {
			return "ShortString";
		}
		
	}, OneLine {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			PrimitiveOneLine oneline = new PrimitiveOneLine(info);
			return oneline;
		}

		@Override
		public PrimitiveDataStructureBase createEmptyStructure() {
			PrimitiveOneLine oneline = new PrimitiveOneLine();
			return oneline;
		}

		@Override
		public String getStructureName() {
			return "Oneline";
		}
		
	}, Concept {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			PrimitiveConcept concept = new PrimitiveConcept(info);
			return concept;
		}

		@Override
		public PrimitiveDataStructureBase createEmptyStructure() {
			PrimitiveConcept concept = new PrimitiveConcept();
			return concept;
		}

		@Override
		public String getStructureName() {
			return "Concept";
		}
		
	}, SetOfKeywords {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			PrimitiveSetOfKeys keys = new PrimitiveSetOfKeys(info);
			return keys;
		}

		@Override
		public PrimitiveDataStructureBase createEmptyStructure() {
			PrimitiveSetOfKeys keys = new PrimitiveSetOfKeys();
			return keys;
		}

		@Override
		public String getStructureName() {
			return "SetOfKeywords";
		}
		
	}, NameOfPerson {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			PrimitivePersonName person = new PrimitivePersonName(info);
			return person;
		}

		@Override
		public PrimitiveDataStructureBase createEmptyStructure() {
			PrimitivePersonName person = new PrimitivePersonName();
			return person;
		}

		@Override
		public String getStructureName() {
			return "NameOfPerson";
		}
		
	}, DataSetReference {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			PrimitiveReferenceInformation information = (PrimitiveReferenceInformation) info;
			PrimitiveReference reference = new PrimitiveReference(information);
			return reference;
		}

		@Override
		public PrimitiveDataStructureBase createEmptyStructure() {
			PrimitiveReference reference = new PrimitiveReference();
			return reference;
		}

		@Override
		public String getStructureName() {
			return "DataSetReference";
		}
		
	}, ObservationValuesWithSpecification {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			PrimitiveObservationValuesWithSpecification spec = new PrimitiveObservationValuesWithSpecification(info);
			return spec;
		}

		@Override
		public PrimitiveDataStructureBase createEmptyStructure() {
			PrimitiveObservationValuesWithSpecification spec = new PrimitiveObservationValuesWithSpecification();
			return spec;
		}

		@Override
		public String getStructureName() {
			return "ObservationVauesWithSpecification";
		}
		
	}, datasetClassification {

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
