package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.GeneralVoidReturnCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.SaveDatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.modal.OKAnswerInterface;
import info.esblurock.reaction.chemconnect.core.client.modal.OKModal;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationBlockFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class ObservationBlockFromSpreadSheetHeader extends Composite implements OKAnswerInterface{

	private static ObservationBlockFromSpreadSheetHeaderUiBinder uiBinder = GWT
			.create(ObservationBlockFromSpreadSheetHeaderUiBinder.class);

	interface ObservationBlockFromSpreadSheetHeaderUiBinder
			extends UiBinder<Widget, ObservationBlockFromSpreadSheetHeader> {
	}

	public ObservationBlockFromSpreadSheetHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialTooltip blocktooltip;
	@UiField
	MaterialLink block;
	@UiField
	MaterialLink header;
	@UiField
	MaterialLink save;
	@UiField
	MaterialLink delete;

	ObservationBlockFromSpreadSheet obs;
	StandardDatasetObjectHierarchyItem item;
	public ObservationBlockFromSpreadSheetHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		this.obs = (ObservationBlockFromSpreadSheet) item.getObject();
		block.setText("Block");
		blocktooltip.setText(obs.getIdentifier());
		DatabaseObjectHierarchy hierarchy = item.getHierarchy();
		DatabaseObjectHierarchy catidhier = hierarchy.getSubObject(obs.getCatalogDataID());
		DataCatalogID catid = (DataCatalogID) catidhier.getObject();
		TextUtilities.setText(header, catid.getSimpleCatalogName(), obs.getIdentifier());
	}

	public boolean updateData() {
		return true;
	}

	@UiHandler("save")
	void onClickSave(ClickEvent event) {
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
		GeneralVoidReturnCallback callback = new GeneralVoidReturnCallback("Isolate Block Definition deletion successful");
		async.deleteObject(item.getObject().getIdentifier(),
				MetaDataKeywords.observationBlockFromSpreadSheet,
				callback);
	}

}
