package info.esblurock.reaction.chemconnect.core.client.catalog.reference;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.http.client.Response;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.description.AuthorInformation;
import info.esblurock.reaction.chemconnect.core.data.description.DataSetReference;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class StandardDatasetDataSetReferenceHeader extends Composite {

	private static StandardDatasetDataSetReferenceHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetDataSetReferenceHeaderUiBinder.class);

	interface StandardDatasetDataSetReferenceHeaderUiBinder
			extends UiBinder<Widget, StandardDatasetDataSetReferenceHeader> {
	}

	@UiField
	MaterialButton doilookupbutton;
	@UiField
	MaterialLink doi;
	@UiField
	MaterialLink title;
	@UiField
	MaterialLink referenceText;
	@UiField
	MaterialPanel authorpanel;
	
	DatabaseObjectHierarchy hierarchy;
	DataSetReference reference;
	DatabaseObjectHierarchy authorhier;
	ChemConnectCompoundMultiple authors;
	StandardDatasetObjectHierarchyItem item;
	
	public StandardDatasetDataSetReferenceHeader() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public StandardDatasetDataSetReferenceHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		reference = (DataSetReference) item.getObject();
		hierarchy = item.getHierarchy();
		authorhier = hierarchy.getSubObject(reference.getAuthors());
		authors = (ChemConnectCompoundMultiple) authorhier.getObject();
		
		init();
		doilookupbutton.setText("DOI");
		//doi.setText(reference.getDOI());
		title.setText(reference.getTitle());
		referenceText.setText(reference.getReferenceString());
	}
	private void init() {
		doilookupbutton.setText("Look up DOI (using CrossRef)");
		doi.setText("10.1016/j.energy.2012.01.072");
		//doi.setPlaceholder("Enter DOI (of the form 10.1016/j.energy.2012.01.072)");
		title.setText("title");
		//title.setPlaceholder("title of article (can be filled in by DOI)");
		referenceText.setText("reference");
		//referenceText.setPlaceholder("reference of article (free form)");
	}
	
	public void updateReference() {
		reference.setDOI(doi.getText());
		reference.setReferenceString(referenceText.getText());
		reference.setTitle(title.getText());
	}
	public void doPost(String doi) throws RequestException {
		String url = "http://api.crossref.org/works/" + doi;
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

		builder.sendRequest(null, new RequestCallback() {

			@Override
			public void onResponseReceived(com.google.gwt.http.client.Request request, Response response) {
				MaterialToast.fireToast("DOI response: '" + response.getStatusText() + "'");
				if (response.getStatusText().matches("OK")) {
					JSONObject obj = JSONParser.parseStrict(response.getText()).isObject();
					JSONValue messageV = obj.get("message");
					if (messageV != null) {
						JSONObject message = messageV.isObject();
						JSONValue authorsV = message.get("author");
						if (authorsV != null) {
							JSONArray authors = authorsV.isArray();
							for (int i = 0; i < authors.size(); i++) {
								JSONObject author = authors.get(i).isObject();
								String name = null;
								JSONValue nameV = author.get("given");
								if (nameV != null) {
									name = nameV.isString().stringValue();
								}
								String last = null;
								JSONValue lastV = author.get("family");
								if (lastV != null) {
									last = lastV.isString().stringValue();
								}
								addNewAuthor("", name, last, "no link");
							}
						} else {
							MaterialToast.fireToast("No authors specified");
						}
						JSONValue titlesV = message.get("title");
						if (titlesV != null) {
							JSONArray titles = titlesV.isArray();
							if (titles.size() > 0) {
								String jsontitle = titles.get(0).isString().stringValue();
								title.setText(jsontitle);
							} else {
								MaterialToast.fireToast("No Title");
							}
						} else {
							MaterialToast.fireToast("No Title");
						}
					} else {
						MaterialToast.fireToast("No Message found in JSON object");
					}
				} else {
					MaterialToast.fireToast("DOI lookup unsuccessful: '" + response.getStatusText() + "'");
					MaterialToast.fireToast("DOI lookup unsuccessful: '" + response.getText() + "'");
				}
			}

			@Override
			public void onError(com.google.gwt.http.client.Request request, Throwable exception) {
				Window.alert(exception.toString());
			}

		});
	}

	@UiHandler("doilookupbutton")
	public void onDOILookup(ClickEvent event) {
		String doiS = doi.getText();
		try {
			doPost(doiS);
		} catch (RequestException e) {
			Window.alert(e.toString());
		}
	}

	public void addNewAuthor(String title, String name, String lastname, String link ) {
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(reference,reference.getIdentifier());
		NameOfPerson person = new NameOfPerson(structure,title,name,lastname);
		AuthorInformation author = new AuthorInformation(person,link);
		DatabaseObjectHierarchy newauthorhier = new DatabaseObjectHierarchy(author);
		authorhier.addSubobject(newauthorhier);
		authors.setNumberOfElements(authorhier.getSubObjectKeys().size());
		AuthorInformationHeader auheader = new AuthorInformationHeader(author,item.getModalpanel());
		authorpanel.add(auheader);
	}
}
