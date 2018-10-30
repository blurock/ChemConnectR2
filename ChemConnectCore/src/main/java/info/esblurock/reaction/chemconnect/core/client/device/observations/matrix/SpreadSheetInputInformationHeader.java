package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;

public class SpreadSheetInputInformationHeader extends Composite {

	private static SpreadSheetInputInformationHeaderUiBinder uiBinder = GWT
			.create(SpreadSheetInputInformationHeaderUiBinder.class);

	interface SpreadSheetInputInformationHeaderUiBinder extends UiBinder<Widget, SpreadSheetInputInformationHeader> {
	}

	public SpreadSheetInputInformationHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink source;
	@UiField
	MaterialLink type;
	@UiField
	MaterialLink sourceType;

	public SpreadSheetInputInformationHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		SpreadSheetInputInformation input = (SpreadSheetInputInformation) item.getObject();
		source.setText(input.getSource());
		type.setText(TextUtilities.removeNamespace(input.getType()));
		sourceType.setText(TextUtilities.removeNamespace(input.getSourceType()));
		
	}

}
