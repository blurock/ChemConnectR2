package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRowTitle;

public class StandardDatasetObservationValueRowTitle extends Composite {

	private static StandardDatasetObservationValueRowTitleUiBinder uiBinder = GWT
			.create(StandardDatasetObservationValueRowTitleUiBinder.class);

	interface StandardDatasetObservationValueRowTitleUiBinder
			extends UiBinder<Widget, StandardDatasetObservationValueRowTitle> {
	}

	@UiField
	MaterialPanel titlepanel;
	@UiField
	MaterialLink toptitle;
	
	ObservationValueRowTitle titles;
	
	public StandardDatasetObservationValueRowTitle(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		titles = (ObservationValueRowTitle) item.getObject();
		int count = 0;
		for(String coltitle: titles.getParameterLabel()) {
			SingleColumnTitle title = new SingleColumnTitle(count++,coltitle);
			titlepanel.add(title);
		}
	}
	private void init() {
		toptitle.setText("Column Titles");
	}

}
