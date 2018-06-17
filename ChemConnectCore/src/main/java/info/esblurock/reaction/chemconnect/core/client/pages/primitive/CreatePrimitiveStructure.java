package info.esblurock.reaction.chemconnect.core.client.pages.primitive;



import com.google.gwt.user.client.Window;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.PrimitiveObservationValuesWithSpecification;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.reference.PrimitiveDateObject;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.reference.PrimitivePersonName;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.reference.PrimitiveReference;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.text.PrimitiveOneLine;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.text.PrimitiveParagraph;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.text.PrimitiveSetOfKeys;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.text.PrimitiveShortString;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveInterpretedInformation;

public enum CreatePrimitiveStructure {

/*	
	ParameterSpecification {

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

		@Override
		public void updateStructure(DatabaseObject object, PrimitiveDataStructureBase base) {
			// TODO Auto-generated method stub
			
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

		@Override
		public void updateStructure(DatabaseObject object, PrimitiveDataStructureBase base) {
			// TODO Auto-generated method stub
			
		}

	}, PurposeConceptPair {

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
			return "PurposeConceptPair";
		}

		@Override
		public void updateStructure(DatabaseObject object, PrimitiveDataStructureBase base) {
			// TODO Auto-generated method stub
			
		}
		
	}*/
	Paragraph {

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

		@Override
		public void updateStructure(DatabaseObject object, PrimitiveDataStructureBase base) {
			// TODO Auto-generated method stub
			
		}

	}, ShortStringLabel {

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

		@Override
		public void updateStructure(DatabaseObject object, PrimitiveDataStructureBase base) {
			// TODO Auto-generated method stub
			
		}
		
	}, LinkToDataStructure {

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
			return "LinkToDataStructure";
		}

		@Override
		public void updateStructure(DatabaseObject object, PrimitiveDataStructureBase base) {
			// TODO Auto-generated method stub
			
		}
		
	}, Classification {

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
			return "Classification";
		}

		@Override
		public void updateStructure(DatabaseObject object, PrimitiveDataStructureBase base) {
			// TODO Auto-generated method stub
			
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

		@Override
		public void updateStructure(DatabaseObject object, PrimitiveDataStructureBase base) {
			// TODO Auto-generated method stub
			
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

		@Override
		public void updateStructure(DatabaseObject object, PrimitiveDataStructureBase base) {
			// TODO Auto-generated method stub
			
		}
		
	}, NameOfPerson {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			Window.alert("Create NameOfPerson");
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

		@Override
		public void updateStructure(DatabaseObject object, PrimitiveDataStructureBase base) {
			// TODO Auto-generated method stub
			
		}
		
	}, DateObject {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			PrimitiveDateObject date = new PrimitiveDateObject(info);
			return date;
		}

		@Override
		public PrimitiveDataStructureBase createEmptyStructure() {
			PrimitiveDateObject date = new PrimitiveDateObject();
			return date;
		}

		@Override
		public String getStructureName() {
			return "Date";
		}

		@Override
		public void updateStructure(DatabaseObject object, PrimitiveDataStructureBase base) {
			// TODO Auto-generated method stub
			
		}
		
	},DataSetReference {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			PrimitiveInterpretedInformation information = (PrimitiveInterpretedInformation) info;
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

		@Override
		public void updateStructure(DatabaseObject object, PrimitiveDataStructureBase base) {
			// TODO Auto-generated method stub
			
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

		@Override
		public void updateStructure(DatabaseObject object, PrimitiveDataStructureBase base) {
			// TODO Auto-generated method stub
			
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

		@Override
		public void updateStructure(DatabaseObject object, PrimitiveDataStructureBase base) {
			// TODO Auto-generated method stub
			
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

		@Override
		public void updateStructure(DatabaseObject object, PrimitiveDataStructureBase base) {
			// TODO Auto-generated method stub
			
		}
		
	}
	/*, GPSLocation {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			PrimitiveGPSLocation location = new PrimitiveGPSLocation(info);
			return location;
		}

		@Override
		public PrimitiveDataStructureBase createEmptyStructure() {
			PrimitiveGPSLocation location = new PrimitiveGPSLocation();
			return location;
		}

		@Override
		public String getStructureName() {
			return "GPSLocation";
		}

		@Override
		public void updateStructure(DatabaseObject object, PrimitiveDataStructureBase base) {
			// TODO Auto-generated method stub
			
		}
		
	}
	*/
	;
	
	public abstract PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info);
	public abstract PrimitiveDataStructureBase createEmptyStructure();
	public abstract String getStructureName();
	public abstract void updateStructure(DatabaseObject object, PrimitiveDataStructureBase base);
	
	public static CreatePrimitiveStructure getStructureType(PrimitiveDataStructureInformation primitive) {
		CreatePrimitiveStructure create = null;
		String identifier = primitive.getPropertyType();
		int pos = identifier.indexOf(':');
		if (pos >= 0) {
			String key = identifier.substring(0, pos) + identifier.substring(pos + 1);
			try {
				create = CreatePrimitiveStructure.valueOf(key);
			} catch (Exception ex) {
				Window.alert("CreatePrimitiveStructure (id): " + ex.toString());
			}
		} else {
			try {
				create = CreatePrimitiveStructure.valueOf(identifier);
			} catch (Exception ex) {
				Window.alert("CreatePrimitiveStructure (type): " + ex.toString());
			}
		}
		return create;
	}
	
}
