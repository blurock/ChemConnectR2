package info.esblurock.reaction.chemconnect.core.client.pages.reference;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.shell.remoteui.MessageTransport.RequestException;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.description.DataSetReference;

public class StandardDatasetDataSetReferenceHeader extends Composite {

	private static StandardDatasetDataSetReferenceHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetDataSetReferenceHeaderUiBinder.class);

	interface StandardDatasetDataSetReferenceHeaderUiBinder
			extends UiBinder<Widget, StandardDatasetDataSetReferenceHeader> {
	}

	@UiField
	MaterialButton doilookupbutton;
	@UiField
	MaterialTextBox doi;
	@UiField
	MaterialTextBox title;
	@UiField
	MaterialTextBox referenceText;
	
	DataSetReference reference;

	public StandardDatasetDataSetReferenceHeader() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public StandardDatasetDataSetReferenceHeader(DatabaseObject object) {
		initWidget(uiBinder.createAndBindUi(this));
		reference = (DataSetReference) object;
		init();
		doilookupbutton.setText("DOI");
		doi.setText(reference.getDOI());
		doi.setPlaceholder("DOI of data set");
		title.setText(reference.getTitle());
		title.setPlaceholder("title");
		referenceText.setText(reference.getReferenceString());
		referenceText.setPlaceholder("reference of article (free form)");
	}
	private void init() {
		doilookupbutton.setText("Look up DOI (using CrossRef)");
		doi.setText("10.1016/j.energy.2012.01.072");
		doi.setPlaceholder("Enter DOI (of the form 10.1016/j.energy.2012.01.072)");
		title.setText("title");
		title.setPlaceholder("title of article (can be filled in by DOI)");
		referenceText.setText("reference");
		referenceText.setPlaceholder("reference of article (free form)");
	}
	
	public void updateReference() {
		reference.setDOI(doi.getText());
		reference.setReferenceString(referenceText.getText());
		reference.setTitle(title.getText());
	}


}
