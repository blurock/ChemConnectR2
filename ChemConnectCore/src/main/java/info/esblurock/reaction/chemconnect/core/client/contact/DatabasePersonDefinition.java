package info.esblurock.reaction.chemconnect.core.client.contact;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.catalog.SetOfObjectsCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.SetOfObjectsCallbackInterface;
import info.esblurock.reaction.chemconnect.core.client.catalog.SetUpDatabaseObjectHierarchyCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.choose.ChooseFullNameFromCatagoryRow;
import info.esblurock.reaction.chemconnect.core.client.catalog.choose.ObjectVisualizationInterface;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class DatabasePersonDefinition extends Composite  implements ObjectVisualizationInterface, SetOfObjectsCallbackInterface, QueryNameOfPersonInterface {

	private static DatabasePersonDefinitionUiBinder uiBinder = GWT.create(DatabasePersonDefinitionUiBinder.class);

	interface DatabasePersonDefinitionUiBinder extends UiBinder<Widget, DatabasePersonDefinition> {
	}

	@UiField
	MaterialCollapsible contentcollapsible;
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialPanel topPanel;
	@UiField
	MaterialLink peopleExisingHeader;
	@UiField
	MaterialCollapsible existingPeople;
	@UiField
	MaterialLink peopleCreatedHeader;
	@UiField
	MaterialCollapsible createdPeople;

	ChooseFullNameFromCatagoryRow choose;
	String access;
	NameOfPerson person;
	DatabaseObject obj;

	public DatabasePersonDefinition() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		
	}

	void init() {
		peopleExisingHeader.setText("People visible to User");
		peopleCreatedHeader.setText("New person definitions");
		
		person = new NameOfPerson();
		
		String peopleType = "dataset:PeopleTypes";
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(peopleType);
		String user = Cookies.getCookie("user");
		String object = "Role in catagory";
		choose = new ChooseFullNameFromCatagoryRow(this,user,object,choices,modalpanel);
		topPanel.add(choose);

		UserImageServiceAsync async = UserImageService.Util.getInstance();
		SetOfObjectsCallback callback = new SetOfObjectsCallback(this);
		async.getSetOfDatabaseObjectHierarchyForUser("dataset:DatabasePerson",callback);
	}

	@Override
	public void setInOjbects(ArrayList<DatabaseObjectHierarchy> objects) {
		for(DatabaseObjectHierarchy object : objects) {
			StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(object,modalpanel);			
			existingPeople.add(item);
		}		
	}

	@Override
	public void createCatalogObject(DatabaseObject obj) {
		person = new NameOfPerson(obj,"title","name", "familyname");
		QueryNameOfPersonModal modal = new QueryNameOfPersonModal(person, this);
		modalpanel.clear();
		modalpanel.add(modal);
		modal.open();
	}

	@Override
	public void insertNameOfPerson(NameOfPerson person) {
		String catagory = choose.getCatagory();
		SetUpDatabaseObjectHierarchyCallback callback = new SetUpDatabaseObjectHierarchyCallback(createdPeople,modalpanel);
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		async.createDatabasePerson(person, catagory, person, callback);
	}
}
