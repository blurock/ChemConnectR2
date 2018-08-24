package info.esblurock.reaction.chemconnect.core.client.pages.catalog.observations;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.dataset.ObservationSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.SetOfObservationValues;
import info.esblurock.reaction.chemconnect.core.data.description.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class StandardDatasetSetOfObservationValuesHeader extends Composite {

	private static StandardDatasetSetOfObservationValuesHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetSetOfObservationValuesHeaderUiBinder.class);

	interface StandardDatasetSetOfObservationValuesHeaderUiBinder
			extends UiBinder<Widget, StandardDatasetSetOfObservationValuesHeader> {
	}

	@UiField
	MaterialTooltip observationtooltip;
	@UiField
	MaterialTooltip titletooltip;
	@UiField
	MaterialLink observationhead;
	@UiField
	MaterialLink observationtype;
	@UiField
	MaterialIcon save;
	@UiField
	MaterialIcon delete;

	public StandardDatasetSetOfObservationValuesHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public StandardDatasetSetOfObservationValuesHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		DatabaseObjectHierarchy hierarchy = item.getHierarchy();
		SetOfObservationValues value = (SetOfObservationValues) hierarchy.getObject();
		observationtooltip.setText(value.getIdentifier());
		DatabaseObjectHierarchy spechier = hierarchy.getSubObject(value.getObservationSpecification());
		ObservationSpecification spec = (ObservationSpecification) spechier.getObject();
		observationtype.setText(TextUtilities.removeNamespace(spec.getObservationParameterType()));
		String descID = value.getDescriptionDataData();
		DatabaseObjectHierarchy  deschier = hierarchy.getSubObject(descID);
		DescriptionDataData description = (DescriptionDataData) deschier.getObject();
		observationhead.setText(description.getOnlinedescription());
		DatabaseObjectHierarchy cathier = hierarchy.getSubObject(value.getCatalogDataID());
		DataCatalogID catid = (DataCatalogID) cathier.getObject();
		titletooltip.setText(TextUtilities.removeNamespace(catid.getCatalogBaseName()));
	}

}
