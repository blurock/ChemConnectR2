package info.esblurock.reaction.chemconnect.core.client.pages.primitive;

import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextArea;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class DescriptionParagraphPrimitiveDataStructure extends PrimitiveDataStructureBase {

	public DescriptionParagraphPrimitiveDataStructure(PrimitiveDataStructureInformation primitiveinfo) {
		super(primitiveinfo);
		MaterialPanel panel = this.getPanel();
		MaterialTextArea paragraph = new MaterialTextArea();
		paragraph.setText(primitiveinfo.getValue());
		panel.add(paragraph);
		
	}
	public DescriptionParagraphPrimitiveDataStructure() {
		super();
		MaterialPanel panel = this.getPanel();
		MaterialTextArea paragraph = new MaterialTextArea();
		paragraph.setText("");
		panel.add(paragraph);
		
	}
}
