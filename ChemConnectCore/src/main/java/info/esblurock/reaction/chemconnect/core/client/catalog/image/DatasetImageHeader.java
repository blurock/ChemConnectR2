package info.esblurock.reaction.chemconnect.core.client.catalog.image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.catalog.SaveDatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.image.DatasetImage;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class DatasetImageHeader extends Composite {

	private static DatasetImageHeaderUiBinder uiBinder = GWT.create(DatasetImageHeaderUiBinder.class);

	interface DatasetImageHeaderUiBinder extends UiBinder<Widget, DatasetImageHeader> {
	}


	@UiField
	MaterialTooltip headertooltip;
	@UiField
	MaterialLink header;
	@UiField
	MaterialLink delete;
	@UiField
	MaterialLink save;
	
	StandardDatasetObjectHierarchyItem item;
	DatabaseObjectHierarchy hierarchy;
	DatasetImage image;
	DataCatalogID catid;
	
	public DatasetImageHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public DatasetImageHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		hierarchy = item.getHierarchy();
		init();
		image = (DatasetImage) hierarchy.getObject();
		DatabaseObjectHierarchy catidhierarchy = hierarchy.getSubObject(image.getCatalogDataID());
		catid = (DataCatalogID) catidhierarchy.getObject();
		header.setText(catid.getSimpleCatalogName());
	}
	
	void init() {
		headertooltip.setText("Simple Name");
		header.setText("Simple");
	}

	@UiHandler("save")
	void onClickSave(ClickEvent e) {
		SaveDatasetCatalogHierarchy savemodal = new SaveDatasetCatalogHierarchy(item);
		item.getModalpanel().clear();
		item.getModalpanel().add(savemodal);
		savemodal.openModal();		
	}
	
	@UiHandler("delete")
	void onClickDelete(ClickEvent e) {
		Window.alert("Delete!");
	}
	
	@UiHandler("header")
	void onClickHeader(ClickEvent e) {
		Window.alert("Header!");
	}

}