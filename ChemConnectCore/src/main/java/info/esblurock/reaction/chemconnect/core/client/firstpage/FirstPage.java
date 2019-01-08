package info.esblurock.reaction.chemconnect.core.client.firstpage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.ui.view.FirstPageView;

public class FirstPage extends Composite implements FirstPageView {

	private static FirstPageUiBinder uiBinder = GWT.create(FirstPageUiBinder.class);

	interface FirstPageUiBinder extends UiBinder<Widget, FirstPage> {
	}
	
	Presenter listener;

	@UiField
	MaterialPanel modalPanel;
	@UiField
	MaterialPanel footerpanel;
	@UiField
	MaterialPanel mainPanel;
	@UiField
	MaterialCollapsible mainCollapsible;

	public FirstPage() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	
	void init() {
		StandardFooter footer = new StandardFooter();
		footerpanel.add(footer);
	}

	@Override
	public void setName(String helloName) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}
	
	public void asNewUser() {
		CreateNewUserWizard wizard = new CreateNewUserWizard(mainPanel,mainCollapsible,modalPanel);
		mainPanel.clear();
		mainPanel.add(wizard);
	}
	public void asExistingUser() {
		
	}


	MaterialPanel getMainPanel() {
		return mainPanel;
	}


}
