package info.esblurock.reaction.chemconnect.core.client.concepts;

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
import info.esblurock.reaction.chemconnect.core.client.graph.hierarchy.ConvertToMaterialTree;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class ChooseFromConceptHierarchies extends Composite implements HasText {

	private static ChooseFromConceptHierarchiesUiBinder uiBinder = GWT.create(ChooseFromConceptHierarchiesUiBinder.class);

	interface ChooseFromConceptHierarchiesUiBinder extends UiBinder<Widget, ChooseFromConceptHierarchies> {
	}


	ChooseFromConceptHeirarchy chosen;
	
	@UiField
	MaterialLink title;
	@UiField
	MaterialModal modal;
	@UiField
	MaterialModalContent modalcontent;
	@UiField
	MaterialLink close;	
	@UiField
	MaterialTree tree;
	@UiField
	MaterialLink choosedevice;
	@UiField
	MaterialLink choosesubsystem;
	@UiField
	MaterialLink choosecomponent;

	public ChooseFromConceptHierarchies(ChooseFromConceptHeirarchy chosen) {
		initWidget(uiBinder.createAndBindUi(this));
		this.chosen = chosen;
	}
	public ChooseFromConceptHierarchies(String firstName,ChooseFromConceptHeirarchy chosen ) {
		initWidget(uiBinder.createAndBindUi(this));
		this.chosen = chosen;
		title.setText(firstName);
	}

	@UiHandler("choosedevice")
	public void setupDevice(ClickEvent event) {
		treeHierarchyCall("dataset:DataTypeDevice");
	}
	@UiHandler("choosesubsystem")
	public void setupSubsystem(ClickEvent event) {
		treeHierarchyCall("dataset:DataTypeSubSystem");		
	}
	@UiHandler("choosecomponent")
	public void setupComponent(ClickEvent event) {
		treeHierarchyCall("dataset:DataTypeComponent");		
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
			chosen.conceptChosen(item.getText());
			close();
		}
	}
	
	public void open() {
		modal.open();
	}
	public void close() {
		modal.close();
	}
	public void treeHierarchyCall(String topconcept) {
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		ConceptHierarchyCallback callback = new ConceptHierarchyCallback(this);
		async.hierarchyOfConcepts(topconcept,callback);
		
	}

	public void setupTree(HierarchyNode hierarchy) {
		tree.clear();
		ConvertToMaterialTree.addHierarchyTop(hierarchy, tree);
	}
	
	public void setText(String text) {
		title.setText(text);
	}

	public String getText() {
		return title.getText();
	}

	public void conceptChosen(String concept) {
		
	}
}
