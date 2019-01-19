package info.esblurock.reaction.chemconnect.core.client.resources.info.footer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialTitle;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ChemConnectPartnersView;

public class ChemConnectPartners extends Composite implements ChemConnectPartnersView {

	Presenter listener;

	private static ChemConnectPartnersUiBinder uiBinder = GWT.create(ChemConnectPartnersUiBinder.class);

	interface ChemConnectPartnersUiBinder extends UiBinder<Widget, ChemConnectPartners> {
	}

	@UiField
	MaterialTitle title;

	public ChemConnectPartners() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		title.setTitle("ChemConnect Partners");
	}
	
	@Override
	public void setName(String helloName) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;	
	}
}
