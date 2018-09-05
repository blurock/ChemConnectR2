package info.esblurock.reaction.chemconnect.core.client.catalog.multiple;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.catalog.SetUpCollapsibleItem;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

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
	@UiField
	MaterialPanel multiobjects;
	
	ArrayList<StandardDatasetObjectHierarchyItem> multipleItems;
	
	StandardDatasetObjectHierarchyItem item;
	ChemConnectCompoundMultiple multiple;
	DatabaseObjectHierarchy multipleHier;
	
	String dataType;
	public ChemConnectCompoundMultipleHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		multiple = (ChemConnectCompoundMultiple) item.getObject();
		multipleHier = item.getHierarchy();
		dataType = multiple.getType();
		init();
		typename.setText(TextUtilities.removeNamespace(dataType));
	}

	private void init() {
		multipletitle.setText("Objects:");
		multipleItems = new ArrayList<StandardDatasetObjectHierarchyItem>();
	}

	private DatabaseObject determineSubObjectID() {
		DatabaseObject obj = new DatabaseObject(multiple);
		int elementNumber = multiple.getIds().size();
		String elementNumberS = String.valueOf(elementNumber);
		String id = obj.getIdentifier() + "-link" + elementNumberS;
		obj.setIdentifier(id);
		return obj;
	}
	
	@UiHandler("add")
	public void onAddClick(ClickEvent event) {
		ChemConnectCompoundMultipleCallback callback = new ChemConnectCompoundMultipleCallback(this);
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		DatabaseObject obj = determineSubObjectID();
		async.createEmptyMultipleObject(multiple,callback);
	}
	public void addMultipleObject(DatabaseObjectHierarchy obj) {
		multipleHier.addSubobject(obj);
		multiple.addID(obj.getObject().getIdentifier());
		String type = obj.getObject().getClass().getSimpleName();
		SetUpCollapsibleItem setup = SetUpCollapsibleItem.valueOf(type);
		if(setup != null) {
			MaterialPanel modalpanel = item.getModalpanel();
			StandardDatasetObjectHierarchyItem itemobj = new StandardDatasetObjectHierarchyItem(obj,modalpanel);
			multipleItems.add(itemobj);
			if(setup.isInformation()) {
				multiobjects.add(itemobj.getHeader());
			} else {
				item.addSubItem(itemobj);
			}
		} else {
			Window.alert("addMultipleObject:  not setup found");
		}
	}

	public void updateObject() {
		for(StandardDatasetObjectHierarchyItem item: multipleItems) {
			item.updateDatabaseObjectHierarchy();
		}
	}
	
}
