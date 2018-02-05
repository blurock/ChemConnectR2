package info.esblurock.reaction.chemconnect.core.client.cards;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialLabel;
import info.esblurock.reaction.chemconnect.core.client.resources.DataTypeText;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;

public class ClassificationInformationCard extends CardModalInterface {

	private static ClassificationInformationCardUiBinder uiBinder = GWT
			.create(ClassificationInformationCardUiBinder.class);

	interface ClassificationInformationCardUiBinder extends UiBinder<Widget, ClassificationInformationCard> {
	}

	DataTypeText datatypetext = GWT.create(DataTypeText.class);
	@UiField
	MaterialCardTitle dataType;
	@UiField
	MaterialLabel idNameTitle;
	@UiField
	MaterialLabel idNameElement;
	@UiField
	MaterialLabel identifierTitle;
	@UiField
	MaterialLabel identifierElement;
	
	public ClassificationInformationCard() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	public ClassificationInformationCard(ClassificationInformation info) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		if(info.getDataType() != null) {
		dataType.setText(TextUtilities.removeNamespace(info.getDataType()));
		}
		if(info.getIdName() != null) {
		idNameElement.setText(info.getIdName());
		}
		if(info.getIdentifier() != null) {
		identifierElement.setText(info.getIdentifier());
		}
		dataType.setIconType(IconType.ARROW_FORWARD);
		if(info.getLink() != null) {
			if(info.getLink().compareTo("dcat:record") == 0) {
				dataType.setIconType(IconType.TOC);
			}
		} else {
			
		}
	}

	private void init() {
		idNameTitle.setText(datatypetext.idNameTitle());
		identifierTitle.setText(datatypetext.identifierTitle());
	}
	@Override
	public void actionOnOK() {
	}
}
