package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;

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
import info.esblurock.reaction.chemconnect.core.client.modal.OKAnswerInterface;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheetFull;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class StandardDatasetObservationsFromSpreadSheetFull extends Composite  implements OKAnswerInterface {

	private static StandardDatasetObservationsFromSpreadSheetFullUiBinder uiBinder = GWT
			.create(StandardDatasetObservationsFromSpreadSheetFullUiBinder.class);

	interface StandardDatasetObservationsFromSpreadSheetFullUiBinder
			extends UiBinder<Widget, StandardDatasetObservationsFromSpreadSheetFull> {
	}

	public StandardDatasetObservationsFromSpreadSheetFull() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink title;
	@UiField
	MaterialTooltip titletooltip;
	@UiField
	MaterialLink save;
	@UiField
	MaterialLink delete;
	
	ObservationsFromSpreadSheetFull observation;
	DatabaseObjectHierarchy hierarchy;
	StandardDatasetObjectHierarchyItem item;
	
	public StandardDatasetObservationsFromSpreadSheetFull(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		hierarchy = item.getHierarchy();
		observation = (ObservationsFromSpreadSheetFull) item.getObject();
		
		String id = observation.getCatalogDataID();
		DatabaseObjectHierarchy catidhierarchy = hierarchy.getSubObject(id);
		DataCatalogID catid = (DataCatalogID) catidhierarchy.getObject();
		
		titletooltip.setText(catid.getFullName());
		title.setText(catid.getSimpleCatalogName());
	}
	@UiHandler("save")
	public void clickSave(ClickEvent event) {
		SaveDatasetCatalogHierarchy savemodal = new SaveDatasetCatalogHierarchy(item);
		item.getModalpanel().clear();
		item.getModalpanel().add(savemodal);
		savemodal.openModal();
		/*
		OKModal ok = new OKModal("OK", "Ask", this);
		item.getModalpanel().clear();
		item.getModalpanel().add(ok);
		ok.openModal();
		*/
	}
	@Override
	public void answeredOK(String answer) {
		Window.alert("StandardDatasetObservationsFromSpreadSheetFull");
		
	}
}
