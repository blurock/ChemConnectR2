package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRowTitle;

public class ObservationValueRowTitleHeader extends Composite {

	private static ObservationValueRowTitleHeaderUiBinder uiBinder = GWT
			.create(ObservationValueRowTitleHeaderUiBinder.class);

	interface ObservationValueRowTitleHeaderUiBinder extends UiBinder<Widget, ObservationValueRowTitleHeader> {
	}

	@UiField
	MaterialLink header;
	@UiField
	MaterialTooltip headertooltip;
	
	StandardDatasetObjectHierarchyItem item;
	ObservationValueRowTitle titles;
	
	public ObservationValueRowTitleHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public ObservationValueRowTitleHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		titles = (ObservationValueRowTitle) item.getObject();
		header.setText("Observation Value Row Title");
		headertooltip.setText(titles.getIdentifier());
	}

}
