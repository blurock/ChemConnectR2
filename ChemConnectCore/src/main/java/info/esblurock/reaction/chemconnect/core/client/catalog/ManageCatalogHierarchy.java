package info.esblurock.reaction.chemconnect.core.client.catalog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Cookies;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ManageCatalogHierarchyView;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;

public class ManageCatalogHierarchy extends Composite implements ManageCatalogHierarchyView {

	private static ManageCatalogHierarchyUiBinder uiBinder = GWT.create(ManageCatalogHierarchyUiBinder.class);

	interface ManageCatalogHierarchyUiBinder extends UiBinder<Widget, ManageCatalogHierarchy> {
	}

	public static String catalogS = MetaDataKeywords.datasetCatalogHierarchy;
	
	@UiField
	MaterialTooltip identifiertip;
	@UiField
	MaterialTitle title;
	@UiField
	MaterialIcon refresh;
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialCollapsible panel;
	@UiField
	MaterialIcon save;
	
	Presenter listener;
	String userName;
	ChemConnectDataStructure infoStructure;
	DatabaseObject obj;
	
	public ManageCatalogHierarchy() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		userName = Cookies.getCookie("user");
		identifiertip.setText(userName);
		title.setTitle("Manage User Catalog Hierarchy");
		setUpHierarchyFromDatabase();
	}

	/*
	 * Retrieve the tree of CatalogHierarchyNode for the user....
	 * getUserDatasetCatalogHierarchy just retrieves the nodes (not underlying info)
	 * Callback calls insertCatalog
	 */
	public void setUpHierarchyFromDatabase() {
		userName = Cookies.getCookie("user");
		panel.clear();
		if(userName != null) {
			UserImageServiceAsync async = UserImageService.Util.getInstance();
			SetUpDatabaseObjectHierarchyCallback callback = new SetUpDatabaseObjectHierarchyCallback(panel,modalpanel);
			async.getUserDatasetCatalogHierarchy(userName,callback);	
		}
	}
	@UiHandler("refresh")
	public void onClickRefresh(ClickEvent event) {
		panel.clear();
		setUpHierarchyFromDatabase();
	}
	@UiHandler("save")
	public void onClickSave(ClickEvent event) {
		StandardDatasetObjectHierarchyItem item = (StandardDatasetObjectHierarchyItem) panel.getChildrenList().get(0);
		SaveDatasetCatalogHierarchy savemodal = new SaveDatasetCatalogHierarchy(item);
		modalpanel.clear();
		modalpanel.add(savemodal);
		savemodal.openModal();
	}
	
	public void setIdentifer(DatabaseObject obj) {
		this.obj = obj;
	}

	public MaterialPanel getModalPanel() {
		return modalpanel;
	}
	@Override
	public void setName(String titleName) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

}
