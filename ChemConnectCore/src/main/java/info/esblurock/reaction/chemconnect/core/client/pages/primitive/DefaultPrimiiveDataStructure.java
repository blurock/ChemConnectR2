package info.esblurock.reaction.chemconnect.core.client.pages.primitive;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialTextBox;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class DefaultPrimiiveDataStructure extends PrimitiveDataStructureBase {
	
	MaterialTextBox textbox;

	public DefaultPrimiiveDataStructure() {
		super();
	}

	public DefaultPrimiiveDataStructure(PrimitiveDataStructureInformation primitiveinfo) {
		super(primitiveinfo);
		MaterialColumn column = new MaterialColumn();
		row.add(column);
		column.setGrid("s12");
		textbox = new MaterialTextBox();
		column.add(textbox);
		textbox.setTextColor(Color.BLACK);
		textbox.setLabel(primitiveinfo.getPropertyType());
		textbox.setText(primitiveinfo.getValue());
		textbox.setPlaceholder(primitiveinfo.getIdentifier());
	}
	
	public PrimitiveDataStructureInformation getInfo() {
		
		primitiveinfo.setValue(textbox.getText());
		return primitiveinfo;
	}

}
