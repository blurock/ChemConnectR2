package info.esblurock.reaction.chemconnect.core.client.pages.catalog.multiple;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;

public class ChemConnectCompoundMultipleHeader extends Composite {

	private static ChemConnectCompoundMultipleHeaderUiBinder uiBinder = GWT
			.create(ChemConnectCompoundMultipleHeaderUiBinder.class);

	interface ChemConnectCompoundMultipleHeaderUiBinder extends UiBinder<Widget, ChemConnectCompoundMultipleHeader> {
	}

	public ChemConnectCompoundMultipleHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink typename;
	@UiField
	MaterialLink multipletitle;
	
	public ChemConnectCompoundMultipleHeader(String type) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		typename.setText(TextUtilities.removeNamespace(type));
	}

	private void init() {
		multipletitle.setText("Objects:");
		
	}

}
