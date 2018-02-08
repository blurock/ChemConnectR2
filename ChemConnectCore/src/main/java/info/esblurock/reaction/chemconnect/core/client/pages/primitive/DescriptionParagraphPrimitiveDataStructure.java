package info.esblurock.reaction.chemconnect.core.client.pages.primitive;

import gwt.material.design.client.ui.MaterialTextArea;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class DescriptionParagraphPrimitiveDataStructure extends PrimitiveDataStructureBase {

	public DescriptionParagraphPrimitiveDataStructure(PrimitiveDataStructureInformation primitiveinfo) {
		super(primitiveinfo);
		MaterialTextArea paragraph = new MaterialTextArea();
		paragraph.setText(primitiveinfo.getValue());
		
	}
	public DescriptionParagraphPrimitiveDataStructure() {
		super();
		MaterialTextArea paragraph = new MaterialTextArea();
		paragraph.setText("");
		
	}
}
