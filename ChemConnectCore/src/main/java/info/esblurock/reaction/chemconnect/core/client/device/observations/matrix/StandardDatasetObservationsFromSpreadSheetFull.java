package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheetFull;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class StandardDatasetObservationsFromSpreadSheetFull extends Composite  {

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
	
	ObservationsFromSpreadSheetFull observation;
	DatabaseObjectHierarchy hierarchy;
	
	public StandardDatasetObservationsFromSpreadSheetFull(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		
		hierarchy = item.getHierarchy();
		observation = (ObservationsFromSpreadSheetFull) item.getObject();
		
		String id = observation.getCatalogDataID();
		DatabaseObjectHierarchy catidhierarchy = hierarchy.getSubObject(id);
		DataCatalogID catid = (DataCatalogID) catidhierarchy.getObject();
		
		titletooltip.setText(catid.getFullName());
		title.setText(catid.getSimpleCatalogName());
	}

}
