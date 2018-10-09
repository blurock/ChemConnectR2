package info.esblurock.reaction.chemconnect.core.client.device.observations;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.UpdateDataObjectHeaderInterface;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.dataset.ObservationSpecification;

public class StandardDatasetObservationSpecificationHeader extends Composite
	implements UpdateDataObjectHeaderInterface {

	private static StandardDatasetObservationSpecificationHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetObservationSpecificationHeaderUiBinder.class);

	interface StandardDatasetObservationSpecificationHeaderUiBinder
			extends UiBinder<Widget, StandardDatasetObservationSpecificationHeader> {
	}

	public StandardDatasetObservationSpecificationHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink observationhead;
	@UiField
	MaterialTooltip observationtooltip;
	@UiField
	MaterialLink observationtype;

	public StandardDatasetObservationSpecificationHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		ObservationSpecification observation = (ObservationSpecification) item.getObject();
		observationhead.setText(TextUtilities.removeNamespace(observation.getSpecificationLabel()));
		observationtooltip.setText(observation.getIdentifier());
		observationtype.setText(TextUtilities.removeNamespace(observation.getObservationParameterType()));
	}

	@Override
	public boolean updateData() {
		return true;
	}

}
