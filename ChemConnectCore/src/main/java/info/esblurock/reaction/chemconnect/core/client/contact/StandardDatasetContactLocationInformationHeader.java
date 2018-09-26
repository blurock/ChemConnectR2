package info.esblurock.reaction.chemconnect.core.client.contact;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.contact.ContactLocationInformation;

public class StandardDatasetContactLocationInformationHeader extends Composite implements SetLineContentInterface {

	private static StandardDatasetContactLocationInformationHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetContactLocationInformationHeaderUiBinder.class);

	interface StandardDatasetContactLocationInformationHeaderUiBinder
			extends UiBinder<Widget, StandardDatasetContactLocationInformationHeader> {
	}

	public StandardDatasetContactLocationInformationHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialTooltip addresstooltip;
	@UiField
	MaterialTooltip citytooltip;
	@UiField
	MaterialTooltip countrytooltip;
	@UiField
	MaterialTooltip postcodetooltip;
	@UiField
	MaterialLink address;
	@UiField
	MaterialLink city;
	@UiField
	MaterialLink country;
	@UiField
	MaterialLink postcode;
	
	StandardDatasetObjectHierarchyItem item;
	ContactLocationInformation location;
	InputLineModal line;
	String linequery;
	
	public StandardDatasetContactLocationInformationHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		this.item = item;
		location = (ContactLocationInformation) item.getObject();
		addresstooltip.setText(location.getIdentifier());
		TextUtilities.setText(address, location.getAddressAddress(), "Street Address");
		TextUtilities.setText(city, location.getCity(), "City");
		TextUtilities.setText(country, location.getCountry(), "Country");
		TextUtilities.setText(postcode, location.getPostcode(), "Postcode");
	}

	void init() {
		citytooltip.setText("City");
		countrytooltip.setText("Country");
		postcodetooltip.setText("Postcode");
	}
	@UiHandler("address")
	void onClickAddress(ClickEvent event) {
		line = new InputLineModal("Input Street Address","Street Address",this);
		linequery = "Address";
		item.getModalpanel().add(line);
		line.openModal();
	}
	@UiHandler("city")
	void onClickCity(ClickEvent event) {
		line = new InputLineModal("Input City","City",this);
		linequery = "City";
		item.getModalpanel().add(line);
		line.openModal();
	}
	@UiHandler("country")
	void onClickCountry(ClickEvent event) {
		line = new InputLineModal("Input Country","Country",this);
		linequery = "Country";
		item.getModalpanel().add(line);
		line.openModal();
	}
	@UiHandler("postcode")
	void onClickPostcode(ClickEvent event) {
		line = new InputLineModal("Input Postcode","Postcode",this);
		linequery = "Postcode";
		item.getModalpanel().add(line);
		line.openModal();
	}

	@Override
	public void setLineContent(String line) {
		if(linequery.compareTo("Address") == 0) {
			address.setText(line);
		} else if(linequery.compareTo("City") == 0) {
			city.setText(line);
		} else if(linequery.compareTo("Country") == 0) {
			country.setText(line);
		} else if(linequery.compareTo("Postcode") == 0) {
			postcode.setText(line);
		}
	}

	public boolean updateData() {
		location.setAddressAddress(address.getText());
		location.setCity(city.getText());
		location.setCountry(country.getText());
		location.setPostcode(postcode.getText());
		return true;
	}
}
