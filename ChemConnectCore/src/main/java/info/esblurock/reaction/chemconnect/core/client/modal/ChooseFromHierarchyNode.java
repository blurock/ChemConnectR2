package info.esblurock.reaction.chemconnect.core.client.modal;

import java.util.ArrayList;

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
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.graph.hierarchy.ConvertToMaterialTree;
import info.esblurock.reaction.chemconnect.core.client.graph.hierarchy.MaterialTreeItemWithPath;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class ChooseFromHierarchyNode extends Composite {

	private static ChooseFromHierarchyNodeUiBinder uiBinder = GWT.create(ChooseFromHierarchyNodeUiBinder.class);

	interface ChooseFromHierarchyNodeUiBinder extends UiBinder<Widget, ChooseFromHierarchyNode> {
	}

	public ChooseFromHierarchyNode() {
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
	String topconcept;
	ChooseFromConceptHeirarchy chosen;

	public ChooseFromHierarchyNode(String topconcept, String title, HierarchyNode topnode,ChooseFromConceptHeirarchy chosen) {
		initWidget(uiBinder.createAndBindUi(this));
		tree.clear();
		ArrayList<String> path = new ArrayList<String>();
		path.add("First");
		ConvertToMaterialTree.addHierarchyTop(topnode, tree);
		tree.collapse();
		this.title.setText(title);
		this.topconcept = topconcept;
		this.chosen = chosen;
	}

	@UiHandler("close")
	public void closeClick(ClickEvent event) {
		modal.close();
	}

	@UiHandler("tree")
	public void onClose(CloseEvent<MaterialTreeItem> event) {
	}

	@UiHandler("tree")
	public void onOpen(OpenEvent<MaterialTreeItem> event) {
	}

	@UiHandler("tree")
	public void onSelected(SelectionEvent<MaterialTreeItem> event) {
		MaterialTreeItemWithPath item = (MaterialTreeItemWithPath) event.getSelectedItem();
		if (item.getTreeItems().size() == 0) {
			chosen.conceptChosen(topconcept, item.getIdentifier(),item.getPath());
			modal.close();
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

	
	
}
