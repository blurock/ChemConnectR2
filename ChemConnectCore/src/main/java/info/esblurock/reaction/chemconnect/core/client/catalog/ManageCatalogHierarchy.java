package info.esblurock.reaction.chemconnect.core.client.catalog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class ManageCatalogHierarchy extends Composite {

	private static ManageCatalogHierarchyUiBinder uiBinder = GWT.create(ManageCatalogHierarchyUiBinder.class);

	interface ManageCatalogHierarchyUiBinder extends UiBinder<Widget, ManageCatalogHierarchy> {
	}

	public static String catalogS = "dataset:DatasetCatalogHierarchy";
	
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
		//String id = "Catalog-Administration";
		//findHierarchyInformation(userName);
	}
/*
	private void findHierarchyInformation(String id) {
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		MapOfCatalogObjectsCallback callback = new MapOfCatalogObjectsCallback();
		if(userName != null) {
			async.getElementsOfCatalogObject(id,catalogS,callback);
		} else {
			Window.alert("No User");
		}
	}
	*/
	/*
	 * Retrieve the tree of CatalogHierarchyNode for the user....
	 * getUserDatasetCatalogHierarchy just retrieves the nodes (not underlying info)
	 * Callback calls insertCatalog
	 */
	public void setUpHierarchyFromDatabase() {
		if(userName != null) {
			UserImageServiceAsync async = UserImageService.Util.getInstance();
			SetUpUserCatalogCallback callback = new SetUpUserCatalogCallback(this);
			async.getUserDatasetCatalogHierarchy(userName,callback);	
		} else {
			Window.alert("Not logged in");
		}
	}
	
	/*
	 *  The call from SetUpUserCatalogCallback 
	 *  CatalogHierarchyNode the tree of nodes
	 *  Within CatalogHierarchyNode, the underlying information is filled in
	 */
	public void insertCatalog(DatabaseObjectHierarchy transfer) {
		Window.alert("insertCatalog\n" + transfer.getObject().toString());
		StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(transfer,modalpanel);
		panel.add(item);
	}
	
	@UiHandler("refresh")
	public void onClickRefresh(ClickEvent event) {
		setUpHierarchyFromDatabase();
	}
	
	public void setIdentifer(DatabaseObject obj) {
		this.obj = obj;
	}

	public MaterialPanel getModalPanel() {
		return modalpanel;
	}

}
