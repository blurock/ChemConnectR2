package info.esblurock.reaction.chemconnect.core.client.pages.primitive;


import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public enum CreatePrimitiveStructure {
	datasetClassification {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			ClassificationPrimitiveDataStructure base = new ClassificationPrimitiveDataStructure(info);
			return base;
		}
	}, dctermsdescription {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			DescriptionParagraphPrimitiveDataStructure base = new DescriptionParagraphPrimitiveDataStructure(info);
			return base;
		}
		
	};
	
	public abstract PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info);
	
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
