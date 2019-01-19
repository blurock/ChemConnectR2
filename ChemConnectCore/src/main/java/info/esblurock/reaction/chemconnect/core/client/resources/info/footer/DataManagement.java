package info.esblurock.reaction.chemconnect.core.client.resources.info.footer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialTitle;
import info.esblurock.reaction.chemconnect.core.client.ui.view.DataManagementView;

public class DataManagement extends Composite implements DataManagementView {

	Presenter listener;

	private static DataManagmentUiBinder uiBinder = GWT.create(DataManagmentUiBinder.class);

	interface DataManagmentUiBinder extends UiBinder<Widget, DataManagement> {
	}

	@UiField
	MaterialTitle title;

	public DataManagement() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		title.setTitle("Data Management");
	}
	
	@Override
	public void setName(String helloName) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

}
