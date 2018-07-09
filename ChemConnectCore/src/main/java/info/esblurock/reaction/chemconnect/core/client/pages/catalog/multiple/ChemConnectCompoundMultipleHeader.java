package info.esblurock.reaction.chemconnect.core.client.pages.catalog.multiple;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.catalog.SetUpDatabaseObjectHierarchyCallback;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

public class ChemConnectCompoundMultipleHeader extends Composite {

	private static ChemConnectCompoundMultipleHeaderUiBinder uiBinder = GWT
			.create(ChemConnectCompoundMultipleHeaderUiBinder.class);

	interface ChemConnectCompoundMultipleHeaderUiBinder extends UiBinder<Widget, ChemConnectCompoundMultipleHeader> {
	}

	@UiField
	MaterialLink typename;
	@UiField
	MaterialLink multipletitle;
	@UiField
	MaterialLink add;
	
	StandardDatasetObjectHierarchyItem item;
	ChemConnectCompoundMultiple multiple;
	String dataType;
	public ChemConnectCompoundMultipleHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		multiple = (ChemConnectCompoundMultiple) item.getObject();
		dataType = multiple.getType();
		init();
		typename.setText(TextUtilities.removeNamespace(dataType));
	}

	private void init() {
		multipletitle.setText("Objects:");
	}

	private DatabaseObject determineSubObjectID() {
		DatabaseObject obj = new DatabaseObject(multiple);
		int elementNumber = item.getSubitems().size() + 1;
		String elementNumberS = String.valueOf(elementNumber);
		String id = "Element" + elementNumberS;
		obj.setIdentifier(id);
		return obj;
	}
	
	@UiHandler("add")
	public void onAddClick(ClickEvent event) {
		Window.alert("Add item: " + dataType);
		MaterialPanel modalpanel = item.getModalpanel();
		MaterialCollapsible contentcollapsible = item.getSubElementCollapsible();
		SetUpDatabaseObjectHierarchyCallback callback = new SetUpDatabaseObjectHierarchyCallback(contentcollapsible,modalpanel);
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		DatabaseObject obj = determineSubObjectID();
		async.createEmptyObject(obj,dataType,callback);
	}
}
