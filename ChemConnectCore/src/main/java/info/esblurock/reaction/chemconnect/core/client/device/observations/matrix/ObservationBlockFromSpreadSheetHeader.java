package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationBlockFromSpreadSheet;

public class ObservationBlockFromSpreadSheetHeader extends Composite {

	private static ObservationBlockFromSpreadSheetHeaderUiBinder uiBinder = GWT
			.create(ObservationBlockFromSpreadSheetHeaderUiBinder.class);

	interface ObservationBlockFromSpreadSheetHeaderUiBinder
			extends UiBinder<Widget, ObservationBlockFromSpreadSheetHeader> {
	}

	public ObservationBlockFromSpreadSheetHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialTooltip headertooltip;
	@UiField
	MaterialLink header;
	@UiField
	MaterialLink save;
	@UiField
	MaterialLink delete;
	@UiField
	MaterialLink info;

	ObservationBlockFromSpreadSheet obs;
	public ObservationBlockFromSpreadSheetHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.obs = (ObservationBlockFromSpreadSheet) item.getObject();
		header.setText("Observation Block From Spread Sheet");
		headertooltip.setText(obs.getIdentifier());
	}

	public boolean updateData() {
		return true;
	}

}
