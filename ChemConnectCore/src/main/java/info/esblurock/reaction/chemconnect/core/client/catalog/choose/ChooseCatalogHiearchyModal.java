package info.esblurock.reaction.chemconnect.core.client.catalog.choose;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.tree.MaterialTree;
import gwt.material.design.addins.client.tree.MaterialTreeItem;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialDialogContent;
import gwt.material.design.client.ui.MaterialDialogFooter;
import info.esblurock.reaction.chemconnect.core.client.catalog.SubCatagoryHierarchyCallback;
import info.esblurock.reaction.chemconnect.core.client.graph.hierarchy.ConvertToMaterialTree;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.dataset.DatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class ChooseCatalogHiearchyModal extends Composite implements SubCatagoryHierarchyCallbackInterface {

	private static ChooseCatalogHiearchyModalUiBinder uiBinder = GWT.create(ChooseCatalogHiearchyModalUiBinder.class);

	interface ChooseCatalogHiearchyModalUiBinder extends UiBinder<Widget, ChooseCatalogHiearchyModal> {
	}

	public ChooseCatalogHiearchyModal() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink title;
	@UiField
	MaterialDialog modal;
	@UiField
	MaterialDialogContent modalcontent;
	@UiField
	MaterialLink close;
	@UiField
	MaterialTree tree;
	@UiField
	MaterialDialogFooter footer;
	
	Map<String, String> mapping;
	ChooseCatagoryHierarchyInterface chosen;

	public ChooseCatalogHiearchyModal(String userName, ChooseCatagoryHierarchyInterface chosen) {
		initWidget(uiBinder.createAndBindUi(this));
		this.chosen = chosen;
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		SubCatagoryHierarchyCallback callback = new SubCatagoryHierarchyCallback(this);
		async.getUserDatasetCatalogHierarchy(userName,callback);	
	}
	
	@UiHandler("close")
	public void closeClick(ClickEvent event) {
		this.close();
	}

	@UiHandler("tree")
	public void onClose(CloseEvent<MaterialTreeItem> event) {
		MaterialTreeItem item = (MaterialTreeItem) event.getTarget();
		selected(item);
	}

	@UiHandler("tree")
	public void onOpen(OpenEvent<MaterialTreeItem> event) {
		MaterialTreeItem item = (MaterialTreeItem) event.getTarget();
		selected(item);
	}

	@UiHandler("tree")
	public void onSelected(SelectionEvent<MaterialTreeItem> event) {
		MaterialTreeItem item = (MaterialTreeItem) event.getSelectedItem();
		selected(item);
	}

	/*
	 * Grab selected information from tree and call 'catagoryChosen' in the 
	 * class that called this modal class
	 */
	private void selected(MaterialTreeItem item) {
		if (item.getTreeItems().size() == 0) {
			String id = item.getText();
			chosen.catagoryChosen(mapping.get(id), item);
			close();
		} else {
			item.expand();
		}
		
	}
	public void open() {
		modal.open();
	}

	public void close() {
		modal.close();
	}
/* Reply of selection of type of hierarchy
 * 
 * Draw out the hierarchy as a MaterialTree
 * 
 */
	public void setInHierarchy(DatabaseObjectHierarchy hierarchy) {
		mapping = new HashMap<String,String>();
		HierarchyNode node = nodeFromDatabaseObjectHierarchy(hierarchy);
		recursiveHierarchy(hierarchy,node);
		ConvertToMaterialTree.addHierarchyTop(node,tree);
	}
	
	private void recursiveHierarchy(DatabaseObjectHierarchy hierarchy, HierarchyNode node) {
		for(DatabaseObjectHierarchy subhier : hierarchy.getSubobjects()) {
			String classID = subhier.getObject().getClass().getSimpleName();
			if(classID.compareTo(DatasetCatalogHierarchy.class.getSimpleName()) == 0) {
				HierarchyNode subnode = nodeFromDatabaseObjectHierarchy(subhier);
				node.addSubNode(subnode);
				recursiveHierarchy(subhier,subnode);
			}
		}		
	}
	
	private HierarchyNode nodeFromDatabaseObjectHierarchy(DatabaseObjectHierarchy hierarchy) {
		DatasetCatalogHierarchy sub = (DatasetCatalogHierarchy) hierarchy.getObject();
		String descrID = sub.getCatalogDataID();
		DatabaseObjectHierarchy descrhier = hierarchy.getSubObject(descrID);
		DataCatalogID catid = (DataCatalogID) descrhier.getObject();
		String link = sub.getIdentifier();
		String simplename = catid.getSimpleCatalogName();
		mapping.put(simplename,link);
		HierarchyNode node = new HierarchyNode(simplename);
		return node;
	}

}
