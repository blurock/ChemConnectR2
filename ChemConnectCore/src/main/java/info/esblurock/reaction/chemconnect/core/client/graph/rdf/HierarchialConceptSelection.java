package info.esblurock.reaction.chemconnect.core.client.graph.rdf;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.tree.MaterialTree;
import gwt.material.design.addins.client.tree.MaterialTreeItem;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModalContent;
import info.esblurock.reaction.chemconnect.core.client.device.DeviceWithSubystemsDefinition;
import info.esblurock.reaction.chemconnect.core.client.graph.hierarchy.ConvertToMaterialTree;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class HierarchialConceptSelection extends Composite implements HasText {

	private static HierarchialConceptSelectionUiBinder uiBinder = GWT.create(HierarchialConceptSelectionUiBinder.class);

	interface HierarchialConceptSelectionUiBinder extends UiBinder<Widget, HierarchialConceptSelection> {
	}

	@UiField
	MaterialModal modal;
	@UiField
	MaterialModalContent modalcontent;
	@UiField
	MaterialLink close;	
	@UiField
	MaterialTree tree;
	
	DeviceWithSubystemsDefinition top;
	
	public HierarchialConceptSelection() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public HierarchialConceptSelection(HierarchyNode hierarchy, DeviceWithSubystemsDefinition top) {
		this.top = top;
		initWidget(uiBinder.createAndBindUi(this));
		ConvertToMaterialTree.addHierarchyTop(hierarchy, tree);
		modalcontent.add(tree);
	}
	
	public void setText(String text) {
	}

	public String getText() {
		return null;
	}
	
	@UiHandler("close")
	public void closeClick(ClickEvent event) {
		this.close();
	}
	@UiHandler("tree")
	public void onClose(CloseEvent<MaterialTreeItem> event) {
	}
	
	@UiHandler("tree")
	public void onOpen(OpenEvent<MaterialTreeItem> event) {
	}
	
	@UiHandler("tree")
	public void onSelected(SelectionEvent<MaterialTreeItem> event) {
		MaterialTreeItem item = (MaterialTreeItem) event.getSelectedItem();
		if(item.getTreeItems().size() == 0) {
			top.async(item.getText());
			close();
		}
	}
	
	public void open() {
		modal.open();
	}
	public void close() {
		modal.close();
	}
}
