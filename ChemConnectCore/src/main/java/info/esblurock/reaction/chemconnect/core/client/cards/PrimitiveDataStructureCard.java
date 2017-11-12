package info.esblurock.reaction.chemconnect.core.client.cards;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialLabel;
import info.esblurock.reaction.chemconnect.core.client.resources.DataTypeText;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class PrimitiveDataStructureCard extends Composite {

	private static PrimitiveDataStructureCardUiBinder uiBinder = GWT.create(PrimitiveDataStructureCardUiBinder.class);

	interface PrimitiveDataStructureCardUiBinder extends UiBinder<Widget, PrimitiveDataStructureCard> {
	}

	DataTypeText datatypetext = GWT.create(DataTypeText.class);

	
	@UiField
	MaterialCardTitle dataType;
	@UiField
	MaterialLabel idNameTitle;
	@UiField
	MaterialLabel idNameElement;
	@UiField
	MaterialLabel valueTitle;
	@UiField
	MaterialLabel valueElement;

	PrimitiveDataStructureInformation primitiveinfo;
	
	public PrimitiveDataStructureCard(PrimitiveDataStructureInformation primitiveinfo) {
		initWidget(uiBinder.createAndBindUi(this));
		this.primitiveinfo = primitiveinfo;
		dataType.setText(primitiveinfo.getPropertyType());
		idNameElement.setText(primitiveinfo.getPropertyType());
		valueElement.setText(primitiveinfo.getIdentifier());		
		init();
	}
	
	private void init() {
		idNameTitle.setText(datatypetext.idNameTitle());
		valueTitle.setText(datatypetext.valueTitle());
	}

}
