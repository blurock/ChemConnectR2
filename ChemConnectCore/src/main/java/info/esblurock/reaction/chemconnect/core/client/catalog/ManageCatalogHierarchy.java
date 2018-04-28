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
import info.esblurock.reaction.chemconnect.core.client.administration.ChemConnectDataStructureInterface;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.TransferDatabaseCatalogHierarchy;

public class ManageCatalogHierarchy extends Composite implements ChemConnectDataStructureInterface {

	private static ManageCatalogHierarchyUiBinder uiBinder = GWT.create(ManageCatalogHierarchyUiBinder.class);

	interface ManageCatalogHierarchyUiBinder extends UiBinder<Widget, ManageCatalogHierarchy> {
	}

	public static String catalogS = "dataset:DatasetCatalogHierarchy";
	
	@UiField
	MaterialTooltip identifiertip;
	@UiField
	MaterialTitle title;
	@UiField
	MaterialCollapsible catalog;
	@UiField
	MaterialIcon refresh;
	@UiField
	MaterialPanel modalpanel;
	
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

	public void setUpHierarchyFromDatabase() {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		SetUpUserCatalogCallback callback = new SetUpUserCatalogCallback(this);
		async.getUserDatasetCatalogHierarchy(userName,callback);		
	}
	
	public void insertCatalog(TransferDatabaseCatalogHierarchy transfer) {
		CatalogHierarchyNode topnode = new CatalogHierarchyNode(transfer.getTop(),modalpanel);
		catalog.add(topnode);
	}
	
	@UiHandler("refresh")
	public void onClickRefresh(ClickEvent event) {
		setUpHierarchyFromDatabase();
	}
	
	@Override
	public void setIdentifer(DatabaseObject obj) {
		this.obj = obj;
	}

	@Override
	public MaterialPanel getModalPanel() {
		return modalpanel;
	}

	@Override
	public MaterialCollapsible getInfoContentCollapisble() {
		return catalog;
	}
}
