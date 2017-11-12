package info.esblurock.reaction.chemconnect.core.client.pages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.CreatePrimitiveStructure;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.data.transfer.CompoundDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class RecordStructureCollapsible extends Composite {

	private static RecordStructureCollapsibleUiBinder uiBinder = GWT.create(RecordStructureCollapsibleUiBinder.class);

	interface RecordStructureCollapsibleUiBinder extends UiBinder<Widget, RecordStructureCollapsible> {
	}

	@UiField
	MaterialLink title;
	@UiField
	MaterialCollapsibleItem body;
	@UiField
	MaterialLink expand;

	@UiField
	MaterialPanel panel;

	CompoundDataStructureInformation compound;

	public RecordStructureCollapsible() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public RecordStructureCollapsible(CompoundDataStructureInformation compound) {
		initWidget(uiBinder.createAndBindUi(this));
		this.compound = compound;
		//title.setText(compound.getPropertyType());
		title.setText("Text");
		// info.setText(compound.getChemconnectcompound());
		title.setTextColor(Color.BLACK);
		// info.setTextColor(Color.BLACK);
		for (PrimitiveDataStructureInformation primitive : compound.getPrimitiveelements()) {
			CreatePrimitiveStructure create = CreatePrimitiveStructure.getStructureType(primitive);
			if (create != null) {
				PrimitiveDataStructureBase element = create.createStructure(primitive);
				Window.alert(primitive.getPropertyType() + ": " + primitive.getValue());
				panel.add(element);
			} else {
				PrimitiveDataStructureBase element = new PrimitiveDataStructureBase(primitive);
				element.addAsStringLabel();
				panel.add(element);
			}
		}
		/*
		for (CompoundDataStructureInformation primitive : compound.getCompoundelements()) {

		}
*/
	}

}
