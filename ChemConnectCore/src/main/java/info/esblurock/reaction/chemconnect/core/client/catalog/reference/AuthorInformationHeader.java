package info.esblurock.reaction.chemconnect.core.client.catalog.reference;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.catalog.VisualizeCatalogObjectWindow;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.reference.ChoiceOfNamesModal;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.contact.PersonalDescription;
import info.esblurock.reaction.chemconnect.core.data.description.AuthorInformation;

public class AuthorInformationHeader extends Composite {

	private static AuthorInformationHeaderUiBinder uiBinder = GWT.create(AuthorInformationHeaderUiBinder.class);

	interface AuthorInformationHeaderUiBinder extends UiBinder<Widget, AuthorInformationHeader> {
	}

	public AuthorInformationHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialTooltip title;
	@UiField
	MaterialLink givenname;
	@UiField
	MaterialLink familyname;
	@UiField
	MaterialTooltip contactLink;
	
	AuthorInformation info;
	MaterialPanel modal;
	boolean contactSetB;

	public AuthorInformationHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.info = (AuthorInformation) item.getObject();
		init();
	}
	public AuthorInformationHeader(AuthorInformation info, MaterialPanel modal) {
		initWidget(uiBinder.createAndBindUi(this));
		this.info = info;
		this.modal = modal;
		init();
		init(info);
	}
	
	public void init() {
		
		contactSetB = false;
	}
	
	public void init(AuthorInformation info) {
		String titleS = info.getTitle();
		if(titleS == null) {
			
		} else {
			if(titleS.length() == 0) {
				titleS = "";
			}
		}
		title.setText(titleS);
		givenname.setText(info.getGivenName());
		familyname.setText(info.getFamilyName());	
		String linkS = info.getLinkToContact();
		if(linkS == null) {
			linkS = "no contact";
		} else {
			if(linkS.length() == 0) {
				linkS = "no contact";
			}
		}
		contactLink.setText(linkS);
	}
	
	@UiHandler("familyname")
	public void clickFamily(ClickEvent event) {
		if(!contactSetB) {
			UserImageServiceAsync async = UserImageService.Util.getInstance();
			FamilyNameCallback callback = new FamilyNameCallback(this);
			async.getIDHierarchyFromFamilyNameAndUser(familyname.getText(),callback);
		} else {
			VisualizeCatalogObjectWindow window = new VisualizeCatalogObjectWindow(contactLink.getText(),
					PersonalDescription.class.getCanonicalName());
			modal.add(window);
			window.open();
		}
	}
	public void setInNames(ArrayList<NameOfPerson> names) {
		ChoiceOfNamesModal choice = new ChoiceOfNamesModal(names,this);
		modal.clear();
		modal.add(choice);
		choice.open();
	}
	public void setFamilyName(NameOfPerson nameOfPerson) {
		contactLink.setText(nameOfPerson.getParentLink());
		contactSetB = true;
	}
	public boolean isContactLinkSet() {
		return contactSetB;
	}
}
