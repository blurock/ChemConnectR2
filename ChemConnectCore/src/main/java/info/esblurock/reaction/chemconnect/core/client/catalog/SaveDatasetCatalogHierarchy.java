package info.esblurock.reaction.chemconnect.core.client.catalog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialToast;

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
		modal.open();
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
