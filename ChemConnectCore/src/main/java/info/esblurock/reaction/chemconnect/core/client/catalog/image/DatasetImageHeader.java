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
import info.esblurock.reaction.chemconnect.core.client.GeneralVoidReturnCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.SaveDatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.modal.OKAnswerInterface;
import info.esblurock.reaction.chemconnect.core.client.modal.OKModal;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.image.DatasetImage;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class DatasetImageHeader extends Composite implements OKAnswerInterface {

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
	void deleteClick(ClickEvent event) {
		OKModal askifok = new OKModal("askifOK","Are you sure you want to delete catalog obj","Delete",this);	
		item.getModalpanel().clear();
		item.getModalpanel().add(askifok);
		askifok.openModal();
	}
	@Override
	public void answeredOK(String answer) {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		GeneralVoidReturnCallback callback = new GeneralVoidReturnCallback("Specification deletion successful");
		async.deleteObject(image.getIdentifier(),
				MetaDataKeywords.datasetImage,
				callback);
	}
	@UiHandler("header")
	void onClickHeader(ClickEvent e) {
		Window.alert("Header!");
	}

}
