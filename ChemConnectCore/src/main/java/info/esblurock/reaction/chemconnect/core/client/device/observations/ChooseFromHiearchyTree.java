package info.esblurock.reaction.chemconnect.core.client.device.observations;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.tree.MaterialTree;
import gwt.material.design.addins.client.tree.MaterialTreeItem;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.chemconnect.core.client.graph.hierarchy.ConvertToMaterialTree;
import info.esblurock.reaction.chemconnect.core.client.graph.hierarchy.MaterialTreeItemWithPath;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class ChooseFromHiearchyTree extends Composite {

	private static ChooseFromHiearchyTreeUiBinder uiBinder = GWT.create(ChooseFromHiearchyTreeUiBinder.class);

	interface ChooseFromHiearchyTreeUiBinder extends UiBinder<Widget, ChooseFromHiearchyTree> {
	}

	public ChooseFromHiearchyTree() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialDialog modal;
	@UiField
	MaterialLink title;
	@UiField
	MaterialTree tree;
	@UiField
	MaterialLink close;
	
	ChooseFromHierarchyTreeInterface source;

	public ChooseFromHiearchyTree(String title, HierarchyNode topnode, ChooseFromHierarchyTreeInterface source) {
		initWidget(uiBinder.createAndBindUi(this));
		this.source = source;
		this.title.setText(title);
		ConvertToMaterialTree.addHierarchyTop(topnode, tree);
	}
	
	@UiHandler("tree")
	public void onSelected(SelectionEvent<MaterialTreeItem> event) {
		MaterialTreeItemWithPath item = (MaterialTreeItemWithPath) event.getSelectedItem();
		if (item.getTreeItems().size() == 0) {
			source.treeNodeChosen(item.getIdentifier(), item.getPath());
		} else {
			item.expand();
		}
	}
	@UiHandler("close")
	public void onCloseClick(ClickEvent click) {
		modal.close();
	}
	public void open() {
		modal.open();
	}
	public void close() {
		modal.close();
	}
}
