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

public class ManageCatalogHierarchy extends Composite {

	private static ManageCatalogHierarchyUiBinder uiBinder = GWT.create(ManageCatalogHierarchyUiBinder.class);

	interface ManageCatalogHierarchyUiBinder extends UiBinder<Widget, ManageCatalogHierarchy> {
	}

	
	@UiField
	MaterialTooltip identifiertip;
	@UiField
	MaterialTitle title;
	@UiField
	MaterialLink cataloghead;
	@UiField
	MaterialCollapsibleBody bodycollapsible;
	@UiField
	MaterialCollapsible contentcollapsible;
	
	String userName;
	
	public ManageCatalogHierarchy() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		userName = Cookies.getCookie("user");
		
		identifiertip.setText(userName);
		title.setTitle("Manage User Catalag Hierarchy");
		
	}


}
