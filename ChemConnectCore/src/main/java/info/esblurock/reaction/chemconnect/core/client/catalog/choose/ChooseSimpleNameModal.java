package info.esblurock.reaction.chemconnect.core.client.catalog.choose;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.tree.MaterialTree;
import gwt.material.design.addins.client.tree.MaterialTreeItem;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import info.esblurock.reaction.chemconnect.core.client.catalog.HierarchyNodeCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.HierarchyNodeCallbackInterface;
import info.esblurock.reaction.chemconnect.core.client.graph.hierarchy.ConvertToMaterialTree;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.transfer.CompoundDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class ChooseSimpleNameModal extends Composite implements HierarchyNodeCallbackInterface {

	private static ChooseSimpleNameModalUiBinder uiBinder = GWT.create(ChooseSimpleNameModalUiBinder.class);

	interface ChooseSimpleNameModalUiBinder extends UiBinder<Widget, ChooseSimpleNameModal> {
	}

	public ChooseSimpleNameModal() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialModal modal;
	@UiField
	MaterialTextBox textbox;
	@UiField
	MaterialLink close;
	@UiField
	MaterialLink done;
	@UiField
	MaterialLink newname;
	@UiField
	MaterialTree tree;

	String basecatalog;
	String catalog;
	DatabaseObject obj;
	ChooseSimpleNameInterface answer;
	
	public ChooseSimpleNameModal(ChooseSimpleNameInterface answer, DatabaseObject obj, String basecatalog, String catalog) {
		initWidget(uiBinder.createAndBindUi(this));
		this.basecatalog = basecatalog;
		this.catalog = catalog;
		this.obj = obj;
		this.answer = answer;
		findSimpleNameFromHierarchy();
	}
	
	
	private void findSimpleNameFromHierarchy() {
		Window.alert("findSimpleNameFromHierarchy()");
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		HierarchyNodeCallback callback = new HierarchyNodeCallback(this);
		async.getIDHierarchyFromDataCatalogID(basecatalog,catalog,callback);
	}
	
	@UiHandler("newname")
	void onClickNewName(ClickEvent e) {
		answer.newNameChosen(textbox.getText());
		modal.close();
	}
	@UiHandler("done")
	void onClickDone(ClickEvent e) {
		modal.close();
	}
	@UiHandler("close")
	void onClickClose(ClickEvent e) {
		modal.close();
	}

	public String getLabel() {
		return textbox.getLabel().toString();
	}
	
	public void openModal() {
		modal.open();
	}
	public String getText() {
		return textbox.getText();
	}
	@UiHandler("tree")
	public void onSelected(SelectionEvent<MaterialTreeItem> event) {
		MaterialTreeItem item = (MaterialTreeItem) event.getSelectedItem();
		if (item.getTreeItems().size() == 0) {
			answer.objectChosen(item.getText());
		}
		modal.close();
	}


	@Override
	public void insertTree(HierarchyNode topnode) {
		tree.clear();
		ConvertToMaterialTree.addHierarchyTop(topnode, tree);
	}


}
