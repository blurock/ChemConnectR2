package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetTitleRowCorrespondence;

public class SpreadSheetColumnInterpretationRow extends Composite {

	private static SpreadSheetColumnInterpretationRowUiBinder uiBinder = GWT
			.create(SpreadSheetColumnInterpretationRowUiBinder.class);

	interface SpreadSheetColumnInterpretationRowUiBinder extends UiBinder<Widget, SpreadSheetColumnInterpretationRow> {
	}

	public SpreadSheetColumnInterpretationRow() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink columnnumber;
	@UiField
	MaterialLink error;
	@UiField
	MaterialLink parametername;
	@UiField
	MaterialLink columnname;
			
	public SpreadSheetColumnInterpretationRow(SpreadSheetTitleRowCorrespondence corr) {
		initWidget(uiBinder.createAndBindUi(this));
		columnnumber.setText(String.valueOf(corr.getColumnNumber()));
		if(corr.isErrorParameter()) {
			error.setText("Error");
		} else {
			error.setText("Parameter  ");
		}
		parametername.setText(corr.getCorrespondingParameternName());
		columnname.setText(corr.getOriginalColumnTitle());
	}

}
