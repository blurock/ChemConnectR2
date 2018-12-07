package info.esblurock.reaction.chemconnect.core.client.contact;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.catalog.SetOfObjectsCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.SetOfObjectsCallbackInterface;
import info.esblurock.reaction.chemconnect.core.client.catalog.SetUpDatabaseObjectHierarchyCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.catalog.choose.ChooseFullNameFromCatagoryRow;
import info.esblurock.reaction.chemconnect.core.client.catalog.choose.ObjectVisualizationInterface;
import info.esblurock.reaction.chemconnect.core.client.ui.view.OrganizationDefinitionView;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class OrganizationDefinition extends Composite implements ObjectVisualizationInterface, SetOfObjectsCallbackInterface, OrganizationDefinitionView {

	private static OrganizationDefinitionUiBinder uiBinder = GWT.create(OrganizationDefinitionUiBinder.class);

	interface OrganizationDefinitionUiBinder extends UiBinder<Widget, OrganizationDefinition> {
	}


	String defaultCatagory;
	String enterkeyS;
	String keynameS;
	@UiField
	MaterialCollapsible contentcollapsible;
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialPanel topPanel;
	@UiField
	MaterialLink organizationExisingHeader;
	@UiField
	MaterialCollapsible existingOrgs;
	@UiField
	MaterialLink organizationCreatedHeader;
	@UiField
	MaterialCollapsible createdOrgs;
	
	Presenter listener;
	ChooseFullNameFromCatagoryRow choose;
	String access;

	public OrganizationDefinition() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		String orgType = MetaDataKeywords.organizationClassifications;
		
		organizationExisingHeader.setText("Existing Organization Definitions");
		organizationCreatedHeader.setText("New Organizations");
		
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(orgType);
		String user = Cookies.getCookie("user");
		String organization = MetaDataKeywords.organization;
		choose = new ChooseFullNameFromCatagoryRow(this,user,organization,choices,modalpanel);
		topPanel.add(choose);
		
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		SetOfObjectsCallback callback = new SetOfObjectsCallback(this);
		async.getSetOfDatabaseObjectHierarchyForUser(organization,callback);
		
	}
	@Override
	public void createCatalogObject(DatabaseObject obj,DataCatalogID datid) {
		SetUpDatabaseObjectHierarchyCallback callback = new SetUpDatabaseObjectHierarchyCallback(createdOrgs,modalpanel);
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		String title = choose.getObjectName();
		String shortname = title;
		async.createOrganization(obj,shortname, title,datid,callback);
	}

	@Override
	public void setInOjbects(ArrayList<DatabaseObjectHierarchy> objects) {
		for(DatabaseObjectHierarchy object : objects) {
			StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(null,object,modalpanel);			
			existingOrgs.add(item);
		}
		
	}

	@Override
	public void insertCatalogObject(DatabaseObjectHierarchy subs) {
		StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(null,subs,modalpanel);
		contentcollapsible.add(item);
	}
	@Override
	public void setName(String titleName) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

}
