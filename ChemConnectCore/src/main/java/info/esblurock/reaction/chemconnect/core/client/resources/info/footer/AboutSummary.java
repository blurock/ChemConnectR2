package info.esblurock.reaction.chemconnect.core.client.resources.info.footer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialTitle;
import info.esblurock.reaction.chemconnect.core.client.ui.view.AboutSummaryView;

public class AboutSummary extends Composite implements AboutSummaryView {

	Presenter listener;

	private static AboutSummaryUiBinder uiBinder = GWT.create(AboutSummaryUiBinder.class);

	interface AboutSummaryUiBinder extends UiBinder<Widget, AboutSummary> {
	}
	
	@UiField
	MaterialTitle title;

	public AboutSummary() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		title.setTitle("About Summary");
	}
	
	@Override
	public void setName(String helloName) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;	
	}


}
