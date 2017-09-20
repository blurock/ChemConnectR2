package info.esblurock.reaction.chemconnect.core.client.administration;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import info.esblurock.reaction.chemconnect.core.client.ui.view.ChemConnectAdministrationView;

public class ChemConnectAdministrationImpl extends Composite implements ChemConnectAdministrationView {

	private static ChemConnectAdministrationImplUiBinder uiBinder = GWT
			.create(ChemConnectAdministrationImplUiBinder.class);

	interface ChemConnectAdministrationImplUiBinder extends UiBinder<Widget, ChemConnectAdministrationImpl> {
	}

	Presenter listener;
	String name;

	public ChemConnectAdministrationImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		Window.alert("Here I am");
	}

	@Override
	public void setName(String titleName) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

}
