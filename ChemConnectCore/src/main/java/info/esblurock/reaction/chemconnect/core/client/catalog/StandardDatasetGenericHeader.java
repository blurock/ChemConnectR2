package info.esblurock.reaction.chemconnect.core.client.catalog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;

public class StandardDatasetGenericHeader extends Composite {

	private static StandardDatasetGenericHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetGenericHeaderUiBinder.class);

	interface StandardDatasetGenericHeaderUiBinder extends UiBinder<Widget, StandardDatasetGenericHeader> {
	}

	public StandardDatasetGenericHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink genericTitle;

	public StandardDatasetGenericHeader(String genericTitle) {
		initWidget(uiBinder.createAndBindUi(this));
		this.genericTitle.setText(genericTitle);
	}


}
