package info.esblurock.reaction.chemconnect.core.client.firstpage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;

public class StandardFooter extends Composite {

	private static StandardFooterUiBinder uiBinder = GWT.create(StandardFooterUiBinder.class);

	interface StandardFooterUiBinder extends UiBinder<Widget, StandardFooter> {
	}


	@UiField
	MaterialButton btnPartners;
	@UiField
	MaterialButton btnContact;

	public StandardFooter() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("btnPartners")
	void onClickPartners(ClickEvent e) {
		Window.alert("Partner Search");
	}
	@UiHandler("btnContact")
	void onClickContact(ClickEvent e) {
		Window.alert("Contact Information");
	}
}
