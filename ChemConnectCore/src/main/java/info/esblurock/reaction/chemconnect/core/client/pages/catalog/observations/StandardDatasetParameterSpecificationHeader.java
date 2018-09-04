package info.esblurock.reaction.chemconnect.core.client.pages.catalog.observations;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.data.dataset.ParameterSpecification;

public class StandardDatasetParameterSpecificationHeader extends Composite {

	private static StandardDatasetParameterSpecificationHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetParameterSpecificationHeaderUiBinder.class);

	interface StandardDatasetParameterSpecificationHeaderUiBinder
			extends UiBinder<Widget, StandardDatasetParameterSpecificationHeader> {
	}

	@UiField
	MaterialLink measuredimension;
	@UiField
	MaterialTooltip parametertooltip;
	@UiField
	MaterialLink parameterhead;
	ParameterSpecification specification;
	public StandardDatasetParameterSpecificationHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public StandardDatasetParameterSpecificationHeader(StandardDatasetObjectHierarchyItem item, boolean measure) {
		initWidget(uiBinder.createAndBindUi(this));
		specification = (ParameterSpecification) item.getObject();
		parameterhead.setText(specification.getParameterLabel());
		parametertooltip.setText(specification.getIdentifier());
		if(measure) {
			measuredimension.setText("Measure");
		} else {
			measuredimension.setText("Dimension");			
		}
	}
	public boolean updateObject() {
		specification.setParameterLabel(parameterhead.getText());
		return true;
	}

}
