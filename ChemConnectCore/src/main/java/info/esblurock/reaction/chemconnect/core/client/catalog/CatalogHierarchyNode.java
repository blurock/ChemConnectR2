package info.esblurock.reaction.chemconnect.core.client.catalog;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.client.administration.ChemConnectDataStructureCallback;
import info.esblurock.reaction.chemconnect.core.client.administration.ChemConnectDataStructureInterface;
import info.esblurock.reaction.chemconnect.core.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.client.cards.CardModal;
import info.esblurock.reaction.chemconnect.core.client.cards.CardModalInterface;
import info.esblurock.reaction.chemconnect.core.client.catalog.NewSubCatalogWizard;

public class CatalogHierarchyNode extends Composite implements ChemConnectDataStructureInterface {

	private static CatalogHierarchyNodeUiBinder uiBinder = GWT.create(CatalogHierarchyNodeUiBinder.class);

	interface CatalogHierarchyNodeUiBinder extends UiBinder<Widget, CatalogHierarchyNode> {
	}

	public static String catalogS = "dataset:DatasetCatalogHierarchy";

	public CatalogHierarchyNode() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink cataloghead;
	@UiField
	MaterialIcon edit;
	@UiField
	MaterialIcon add;
	@UiField
	MaterialIcon delete;
	@UiField
	MaterialCollapsibleBody bodycollapsible;
	@UiField
	MaterialCollapsible contentcollapsible;
	@UiField
	MaterialCollapsible infocollapsible;

	DatasetCatalogHierarchy hierarchy;
	DatabaseObject obj; 
	MaterialPanel modal;
	ArrayList<CatalogHierarchyNode> subcatagories;
	NewSubCatalogWizard wizard;
	
	/**
	 * @param top This is the current node of the Catalog hiearachy 
	 * @param modal Modal panel from top level
	 * 
	 * This recursively adds CatalogHierarchyNode for each DatabaseObjectHierarchy in the tree
	 * 1. Setup
	 * 2. Insert the information associated with this DatabaseObjectHierarchy  (insertInfo())
	 * 3. For each DatabaseObjectHierarchy:
	 * 3.1 Make sure it is a DatasetCatalogHierarchy
	 * 3.1.1 create the next CatalogHierarchyNode (recursive)
	 * 3.1.2 Add under this node.
	 * 
	 */
	public CatalogHierarchyNode(DatabaseObjectHierarchy top, MaterialPanel modal) {
		initWidget(uiBinder.createAndBindUi(this));
		subcatagories = new ArrayList<CatalogHierarchyNode>();
		this.modal = modal;
		hierarchy = (DatasetCatalogHierarchy) top.getObject();
		this.obj = new DatabaseObject(hierarchy);
		cataloghead.setText(hierarchy.getIdentifier());
		insertInfo();
		for(DatabaseObjectHierarchy obj: top.getSubobjects()) {
			DatabaseObject subobj = obj.getObject();
			if(subobj.getClass().getCanonicalName().compareTo(DatasetCatalogHierarchy.class.getCanonicalName()) == 0) {
				CatalogHierarchyNode subnode = new CatalogHierarchyNode(obj,modal);
				contentcollapsible.add(subnode);
			} else {
				//Window.alert("CatalogHierarchyNode: subobject: " + subobj.getClass().getName());
			}
		}
	}

	@UiHandler("add")
	public void onAddClick(ClickEvent event) {
		wizard = new NewSubCatalogWizard(this);
		CardModal cardmodal = new CardModal();
		cardmodal.setContent(wizard, true);
		modal.clear();
		modal.add(cardmodal);
		cardmodal.open();
	}

	public void insertInitialSubCatagoryInformation() {
		String name = wizard.getSimpleName();
		String oneline = wizard.getOneLineDescription();
		addSubCatagory(name,oneline);
	}

	private void addSubCatagory(String id, String onelinedescription) {
		boolean addsub = true;
		for(CatalogHierarchyNode cat : subcatagories) {
			DatasetCatalogHierarchy hierarchy = cat.getHierarchyElement();
			String name = hierarchy.getSimpleCatalogName();
			if(name.compareTo(id) == 0) {
				addsub = false;
			}
		}
		if(addsub) {
			DatabaseObject subobj = new DatabaseObject(hierarchy);
			UserImageServiceAsync async = UserImageService.Util.getInstance();
			SubCatagoryHierarchyCallback callback = new SubCatagoryHierarchyCallback(this);
			async.getNewCatalogHierarchy(subobj,id,onelinedescription,callback);	
		} else {
			MaterialToast.fireToast("Name already being used in another sub-catagory");
		}
	}
	
	
	@UiHandler("edit")
	public void onEditClick(ClickEvent event) {
		editCatagory();
	}
	
	private void editCatagory() {
		
	}
	
	@UiHandler("delete")
	public void onDeleteClick(ClickEvent event) {
		if(subcatagories.size() > 0) {
			MaterialToast.fireToast("Delete subcatagories first");
		}
	}
	
	
	public void insertSubCatalog(DatabaseObjectHierarchy subs) {
		CatalogHierarchyNode node = new CatalogHierarchyNode(subs,modal);
		addSubCatagoryNode(node);
	}

	public void addSubCatagoryNode(CatalogHierarchyNode node) {
		contentcollapsible.add(node);
		subcatagories.add(node);
	}
	
	/* This inserts the underlying information for the node (under the Info node)
	 * 1. Set up the top object information (with DatabaseObject subobj)
	 * 2. Async call getChemConnectDataStructure
	 * 3. ChemConnectDataStructureCallback creates (MainDataStructureCollapsible) and sets info in info panel
	 *         ModalPanel:   top.getModalPanel();
	 *         Content panel (info panel)  top.getInfoContentCollapisble();
	 */
	private void insertInfo() {
		DatabaseObject subobj = new DatabaseObject(hierarchy);
		ContactDatabaseAccessAsync conasync = ContactDatabaseAccess.Util.getInstance();
		ChemConnectDataStructureCallback callback = new ChemConnectDataStructureCallback(subobj,this);
		Window.alert("CatalogHierarchyNode  insertInfo(): " + obj.getIdentifier());
		conasync.getChemConnectDataStructure(obj.getIdentifier(), catalogS, callback);
	}

	public DatabaseObject getDatabaseObject() {
		return obj;
	}
	
	@Override
	public void setIdentifer(DatabaseObject obj) {
		this.obj = obj;
	}

	public DatasetCatalogHierarchy getHierarchyElement() {
		return hierarchy;
	}
	
	@Override
	public MaterialPanel getModalPanel() {
		return modal;
	}

	@Override
	public MaterialCollapsible getInfoContentCollapisble() {
		return infocollapsible;
	}


}
