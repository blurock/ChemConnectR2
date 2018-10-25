package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;

public class ObservationTitleRow extends Composite {

	private static ObservationTitleRowUiBinder uiBinder = GWT.create(ObservationTitleRowUiBinder.class);

	interface ObservationTitleRowUiBinder extends UiBinder<Widget, ObservationTitleRow> {
	}

	public ObservationTitleRow() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink arrowup;
	@UiField
	MaterialLink arrowdown;
	@UiField
	MaterialLink title;
	@UiField
	MaterialLink delete;

	public ObservationTitleRow(String title) {
		initWidget(uiBinder.createAndBindUi(this));
		this.title.setText(title);
	}
}
