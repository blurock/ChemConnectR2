package info.esblurock.reaction.chemconnect.core.client.pages.catalog.hierarchy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.client.cards.CardModal;
import info.esblurock.reaction.chemconnect.core.client.catalog.SubCatagoryHierarchyCallback;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.SetUpCollapsibleItem;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class StandardDatasetCatalogHierarchyHeader extends Composite {

	private static StandardDatasetCatalogHierarchyHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetCatalogHierarchyHeaderUiBinder.class);

	interface StandardDatasetCatalogHierarchyHeaderUiBinder
			extends UiBinder<Widget, StandardDatasetCatalogHierarchyHeader> {
	}

	public StandardDatasetCatalogHierarchyHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink cataloghead;
	@UiField
	MaterialIcon delete;
	@UiField
	MaterialIcon add;

	StandardDatasetObjectHierarchyItem item;
	NewSubCatalogWizard wizard;
	MaterialPanel modal;
	
	public StandardDatasetCatalogHierarchyHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		this.modal = item.getModalpanel();
		DatasetCatalogHierarchy hierarchy = (DatasetCatalogHierarchy) item.getObject();
		cataloghead.setText(hierarchy.getSimpleCatalogName());
	}

	private void editCatagory() {

	}

	@UiHandler("delete")
	public void onDeleteClick(ClickEvent event) {
		
		if (item.getSubCatagories().size() > 0) {
			MaterialToast.fireToast("Delete subcatagories first");
		}
		
	}

	@UiHandler("add")
	public void onAddClick(ClickEvent event) {
		addCatagory();
	}

	private void addCatagory() {
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
		/*
		for(CatalogHierarchyNode cat : subcatagories) {
			DatasetCatalogHierarchy hierarchy = cat.getHierarchyElement();
			String name = hierarchy.getSimpleCatalogName();
			if(name.compareTo(id) == 0) {
				addsub = false;
			}
		}
		*/
		if(addsub) {
			/*
			DatabaseObject subobj = new DatabaseObject(item.getObject());
			UserImageServiceAsync async = UserImageService.Util.getInstance();
			SubCatagoryHierarchyCallback callback = new SubCatagoryHierarchyCallback(this);
			async.getNewCatalogHierarchy(subobj,id,onelinedescription,callback);	
			*/
		} else {
			MaterialToast.fireToast("Name already being used in another sub-catagory");
		}
	}
	public void insertSubCatalog(DatabaseObjectHierarchy subs) {
		StandardDatasetObjectHierarchyItem subcat = new StandardDatasetObjectHierarchyItem(subs,modal);
		SetUpCollapsibleItem setup = SetUpCollapsibleItem.valueOf(DatasetCatalogHierarchy.class.getSimpleName());
		setup.addInformation(subcat);
		Window.alert("insertSubCatalog: " );
		item.addSubItem(subcat);
	}


}
