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
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockIsolation;

public class SpreadSheetMatrixBlockIsolateHeader extends Composite {

	private static SpreadSheetMatrixBlockIsolateHeaderUiBinder uiBinder = GWT
			.create(SpreadSheetMatrixBlockIsolateHeaderUiBinder.class);

	interface SpreadSheetMatrixBlockIsolateHeaderUiBinder
			extends UiBinder<Widget, SpreadSheetMatrixBlockIsolateHeader> {
	}

	@UiField
	MaterialLink startrow;
	@UiField
	MaterialLink endrow;
	@UiField
	MaterialLink startcolumn;
	@UiField
	MaterialLink endcolumn;
	
	SpreadSheetBlockIsolation spread;
	public SpreadSheetMatrixBlockIsolateHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public SpreadSheetMatrixBlockIsolateHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		spread = (SpreadSheetBlockIsolation) item.getObject();
		
		startrow.setText(String.valueOf(spread.getStartRow()));
		endrow.setText(String.valueOf(spread.getEndRow()));
		startcolumn.setText(String.valueOf(spread.getStartColumn()));
		endcolumn.setText(String.valueOf(spread.getEndColumn()));
	}


}
