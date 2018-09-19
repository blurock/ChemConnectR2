package info.esblurock.reaction.chemconnect.core.data;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;

public class SpreadSheetInputInterpretationHeader extends Composite {

	private static SpreadSheetInputInterpretationHeaderUiBinder uiBinder = GWT
			.create(SpreadSheetInputInterpretationHeaderUiBinder.class);

	interface SpreadSheetInputInterpretationHeaderUiBinder
			extends UiBinder<Widget, SpreadSheetInputInterpretationHeader> {
	}

	public SpreadSheetInputInterpretationHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink fileidentifier;
	@UiField
	MaterialTooltip typetooltip;
	@UiField
	MaterialLink type;
	@UiField
	MaterialLink sourceType;
	
	SpreadSheetInputInformation interpret;

	public SpreadSheetInputInterpretationHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		
		interpret = (SpreadSheetInputInformation) item.getObject();
		
		fileidentifier.setText(interpret.getSourceID());
		sourceType.setText(interpret.getSourceType());
		type.setText(interpret.getType());
	}

}
