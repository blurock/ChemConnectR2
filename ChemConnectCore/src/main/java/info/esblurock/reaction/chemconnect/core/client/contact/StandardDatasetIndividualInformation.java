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
import info.esblurock.reaction.chemconnect.core.client.catalog.SaveDatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.data.contact.IndividualInformation;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.contact.PersonalDescription;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class StandardDatasetIndividualInformation extends Composite implements QueryNameOfPersonInterface {

	private static StandardDatasetIndividualInformationUiBinder uiBinder = GWT
			.create(StandardDatasetIndividualInformationUiBinder.class);

	interface StandardDatasetIndividualInformationUiBinder
			extends UiBinder<Widget, StandardDatasetIndividualInformation> {
	}

	public StandardDatasetIndividualInformation() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialTooltip persontooltip;
	@UiField
	MaterialLink personhead;
	@UiField
	MaterialLink save;
	@UiField
	MaterialLink delete;
	
	String descrclassname;
	StandardDatasetObjectHierarchyItem item;
	IndividualInformation person;
	PersonalDescription persondescr;
	NameOfPerson personname;
	
	public StandardDatasetIndividualInformation(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		//descrclassname = StandardDatasetOrganizationDescriptionHeader.class.getCanonicalName();
		person = (IndividualInformation) item.getObject();
		String persondescrID = person.getPersonalDescriptionID();
		DatabaseObjectHierarchy hierarchy = item.getHierarchy();
		DatabaseObjectHierarchy descrhier = hierarchy.getSubObject(persondescrID);
		persondescr = (PersonalDescription) descrhier.getObject();
		String nameID = persondescr.getNameOfPersonIdentifier();
		DatabaseObjectHierarchy namehier = descrhier.getSubObject(nameID);
		personname = (NameOfPerson) namehier.getObject();
		persontooltip.setText(person.getIdentifier());
		personhead.setText(personname.nameAsString());
		save.setEnabled(true);
	}
	@UiHandler("personhead")
	public void onHeadClick(ClickEvent event) {
		QueryNameOfPersonModal modal = new QueryNameOfPersonModal(personname, this);
		item.getModalpanel().clear();
		item.getModalpanel().add(modal);
		modal.open();
		
	}

	@UiHandler("save")
	public void clickSave(ClickEvent event) {
		SaveDatasetCatalogHierarchy savemodal = new SaveDatasetCatalogHierarchy(item);
		item.getModalpanel().clear();
		item.getModalpanel().add(savemodal);
		savemodal.openModal();
	}
	
	@Override
	public void insertNameOfPerson(NameOfPerson person) {
		this.personname = person;
		personhead.setText(personname.nameAsString());
	}


}
