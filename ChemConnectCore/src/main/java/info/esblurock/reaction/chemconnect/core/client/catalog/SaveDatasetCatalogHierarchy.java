package info.esblurock.reaction.chemconnect.core.client.catalog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;

public class SaveDatasetCatalogHierarchy extends Composite {

	private static SaveDatasetCatalogHierarchyUiBinder uiBinder = GWT.create(SaveDatasetCatalogHierarchyUiBinder.class);

	interface SaveDatasetCatalogHierarchyUiBinder extends UiBinder<Widget, SaveDatasetCatalogHierarchy> {
	}

	public SaveDatasetCatalogHierarchy() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialDialog modal;
	@UiField
	MaterialCheckBox databasecheckbox;
	@UiField
	MaterialCheckBox yamlcheckbox;
	@UiField
	MaterialLabel databasetextbox;
	@UiField
	MaterialLabel yamltextbox;
	@UiField
	MaterialLink close;
	@UiField
	MaterialLink done;
	
	StandardDatasetObjectHierarchyItem topitem;
	
	public SaveDatasetCatalogHierarchy(StandardDatasetObjectHierarchyItem topitem) {
		initWidget(uiBinder.createAndBindUi(this));
		this.topitem = topitem;
		init();
	}
	
	void init() {
		databasetextbox.setText("Save to database");
		yamltextbox.setText("Save as yaml string");
	}

	public void openModal() {
		Window.alert("SaveDatasetCatalogHierarchy");
		boolean vInput = Cookies.getCookie(MetaDataKeywords.accessDataInput).compareTo(Boolean.TRUE.toString()) == 0;
		Window.alert("SaveDatasetCatalogHierarchy accessDataInput " + vInput);
		boolean vUserInput = Cookies.getCookie(MetaDataKeywords.accessUserDataInput).compareTo(Boolean.TRUE.toString()) == 0;
		Window.alert("SaveDatasetCatalogHierarchy accessUserDataInput " + vUserInput);
		boolean allowed = false;
		if(vInput) {
			allowed = Boolean.TRUE;
		}
		if(vUserInput) {
			String owner = topitem.getHierarchy().getObject().getOwner();
			String account = Cookies.getCookie("account_name");
			if(owner.compareTo(account) == 0) {
				allowed = Boolean.TRUE;
			}
		}
		Window.alert("SaveDatasetCatalogHierarchy accessUserDataInput " + allowed);
		if(!allowed) {
			MaterialToast.fireToast("Have to be signed in (and have authorization) to save");
		} else {
			modal.open();
		}
	}
	
	@UiHandler("close")
	void onClickClose(ClickEvent e) {
		MaterialToast.fireToast("Cancel Save");
		modal.close();
	}

	@UiHandler("done")
	void onClickDone(ClickEvent e) {
		modal.close();
		if(databasecheckbox.getValue()) {
			MaterialToast.fireToast("Save into database");
			topitem.writeDatabaseObjectHierarchy();
		}
		if(yamlcheckbox.getValue()) {
			MaterialToast.fireToast("Save into Yaml");
			topitem.writeYamlObjectHierarchy();
		}
	}
	
	
}
