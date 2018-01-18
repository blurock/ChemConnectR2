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
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.TextAlign;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModalContent;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.client.graph.pages.VisualizeGraphicalObjects;
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
	
	VisualizeGraphicalObjects top;
	
	public HierarchialConceptSelection() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public HierarchialConceptSelection(HierarchyNode hierarchy, VisualizeGraphicalObjects top) {
		this.top = top;
		initWidget(uiBinder.createAndBindUi(this));
		addHierarchyTop(hierarchy);
		modalcontent.add(tree);
	}

	public MaterialTree addHierarchyTop(HierarchyNode hierarchy) {
		MaterialTreeItem item = new MaterialTreeItem(hierarchy.getIdentifier());
		item.setTextColor(Color.BLACK);
		tree.add(item);
		for(HierarchyNode sub : hierarchy.getSubNodes()) {
			addHierarchy(sub,item);
		}
		return tree;
	}
		
	public void addHierarchy(HierarchyNode hierarchy, MaterialTreeItem item) {
		for(HierarchyNode sub : hierarchy.getSubNodes() ) {
			MaterialTreeItem subitem = new MaterialTreeItem(sub.getIdentifier());
			subitem.setIconType(IconType.FOLDER);
			subitem.setTextAlign(TextAlign.LEFT);
			subitem.setTextColor(Color.BLACK);
			item.addItem(subitem);
			addHierarchy(sub,subitem);
		}
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
		MaterialToast.fireToast("Closed : " + event.getTarget().getText());
	}
	
	@UiHandler("tree")
	public void onOpen(OpenEvent<MaterialTreeItem> event) {
		MaterialToast.fireToast("Open : " + event.getTarget().getText());
	}
	
	@UiHandler("tree")
	public void onSelected(SelectionEvent<MaterialTreeItem> event) {
		MaterialTreeItem item = (MaterialTreeItem) event.getSelectedItem();
		top.async(item.getText());
		close();
	}
	
	public void open() {
		modal.open();
	}
	public void close() {
		modal.close();
	}
}
