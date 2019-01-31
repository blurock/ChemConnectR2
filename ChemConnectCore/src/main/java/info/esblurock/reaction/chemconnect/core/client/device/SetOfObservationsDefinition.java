package info.esblurock.reaction.chemconnect.core.client.device;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.catalog.SetUpDatabaseObjectHierarchyCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.catalog.choose.ChooseFullNameFromCatagoryRow;
import info.esblurock.reaction.chemconnect.core.client.catalog.choose.ObjectVisualizationInterface;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ChemConnectObservationView;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class SetOfObservationsDefinition extends Composite implements ChemConnectObservationView, ObjectVisualizationInterface {

	private static SetOfObservationsDefinitionUiBinder uiBinder = GWT.create(SetOfObservationsDefinitionUiBinder.class);

	interface SetOfObservationsDefinitionUiBinder extends UiBinder<Widget, SetOfObservationsDefinition> {
	}

	Presenter listener;
	
	String defaultCatagory;
	String enterkeyS;
	String keynameS;
	@UiField
	MaterialCollapsible contentcollapsible;
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialPanel topPanel;
	
	ChooseFullNameFromCatagoryRow choose;
	String access;

	
	public SetOfObservationsDefinition() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public SetOfObservationsDefinition(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		refresh();
	}

	@Override
	public void createCatalogObject(DatabaseObject obj,DataCatalogID datid) {
		SetUpDatabaseObjectHierarchyCallback callback = new SetUpDatabaseObjectHierarchyCallback(contentcollapsible,modalpanel);
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		String observation = choose.getObjectType();
		String title = choose.getObjectName();
		async.getSetOfObservations(obj,observation,title,datid,callback);
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
	public void refresh() {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(MetaDataKeywords.chemConnectObservable);
		String user = Cookies.getCookie("user");
		String object = MetaDataKeywords.observationCorrespondenceSpecification;
		choose = new ChooseFullNameFromCatagoryRow(this,user,object,choices,modalpanel);
		topPanel.clear();
		topPanel.add(choose);
	}


}
