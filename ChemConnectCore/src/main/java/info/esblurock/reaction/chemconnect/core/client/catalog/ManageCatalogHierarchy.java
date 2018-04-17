package info.esblurock.reaction.chemconnect.core.client.catalog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Cookies;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.TransferDatabaseCatalogHierarchy;

public class ManageCatalogHierarchy extends Composite {

	private static ManageCatalogHierarchyUiBinder uiBinder = GWT.create(ManageCatalogHierarchyUiBinder.class);

	interface ManageCatalogHierarchyUiBinder extends UiBinder<Widget, ManageCatalogHierarchy> {
	}

	
	@UiField
	MaterialTooltip identifiertip;
	@UiField
	MaterialTitle title;
	@UiField
	MaterialCollapsible catalog;
	
	
	String userName;
	
	public ManageCatalogHierarchy() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		userName = Cookies.getCookie("user");
		
		identifiertip.setText(userName);
		title.setTitle("Manage User Catalog Hierarchy");
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		SetUpUserCatalogCallback callback = new SetUpUserCatalogCallback(this);
		async.getUserDatasetCatalogHierarchy(userName,callback);
	}

	public void insertCatalog(TransferDatabaseCatalogHierarchy transfer) {
		CatalogHierarchyNode topnode = new CatalogHierarchyNode(transfer.getTop(), transfer);
		catalog.add(topnode);
	}


}
