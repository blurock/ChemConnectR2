package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInterpretation;

public class SpreadSheetInterpretationHeader extends Composite {

	private static SpreadSheetInterpretationHeaderUiBinder uiBinder = GWT
			.create(SpreadSheetInterpretationHeaderUiBinder.class);

	interface SpreadSheetInterpretationHeaderUiBinder extends UiBinder<Widget, SpreadSheetInterpretationHeader> {
	}

	public SpreadSheetInterpretationHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink startrow;
	@UiField
	MaterialLink endrow;
	@UiField
	MaterialLink startcolumn;
	@UiField
	MaterialLink endcolumn;
	
	SpreadSheetInterpretation spread;

	public SpreadSheetInterpretationHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		
		spread = (SpreadSheetInterpretation) item.getObject();
		
		startrow.setText(String.valueOf(spread.getStartRow()));
		endrow.setText(String.valueOf(spread.getEndRow()));
		startrow.setText(String.valueOf(spread.getStartColumn()));
		startrow.setText(String.valueOf(spread.getEndColumn()));
	}

}
