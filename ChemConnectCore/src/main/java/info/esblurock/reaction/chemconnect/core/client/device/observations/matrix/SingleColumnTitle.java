package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;

public class SingleColumnTitle extends Composite {

	private static SingleColumnTitleUiBinder uiBinder = GWT.create(SingleColumnTitleUiBinder.class);

	interface SingleColumnTitleUiBinder extends UiBinder<Widget, SingleColumnTitle> {
	}

	public SingleColumnTitle() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink columnnumber;
	@UiField
	MaterialLink title;

	public SingleColumnTitle(int index, String title) {
		initWidget(uiBinder.createAndBindUi(this));
		this.title.setText(title);
		this.columnnumber.setText(String.valueOf(index));
	}

}
