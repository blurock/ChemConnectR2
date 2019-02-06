package info.esblurock.reaction.chemconnect.core.client.device.observations;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.dataset.ParameterSpecification;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;

public class StandardDatasetParameterSpecificationHeader extends Composite 
	implements ChooseFromConceptHeirarchy {

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
	
	String parameter;
	StandardDatasetObjectHierarchyItem item;
	ParameterSpecification specification;
	
	public StandardDatasetParameterSpecificationHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public StandardDatasetParameterSpecificationHeader(StandardDatasetObjectHierarchyItem item, boolean measure) {
		initWidget(uiBinder.createAndBindUi(this));
		specification = (ParameterSpecification) item.getObject();
		parameterhead.setText(specification.getParameterLabel());
		parametertooltip.setText(specification.getIdentifier());
		parameter = specification.getParameterLabel();
		if(measure) {
			measuredimension.setText("Measure");
		} else {
			measuredimension.setText("Dimension");			
		}
	}
	
	@UiHandler("parameterhead")
	void parameterHead(ClickEvent event) {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(MetaDataKeywords.chemConnectParameter);
		ChooseFromConceptHierarchies choice = new ChooseFromConceptHierarchies(choices, this);
		item.getModalpanel().clear();
		item.getModalpanel().add(choice);
		choice.open();
	}
	
	
	public boolean updateData() {
		specification.setParameterLabel(parameter);
		return true;
	}

	@Override
	public void conceptChosen(String topconcept, String concept, ArrayList<String> path) {
		parameter = concept;
		parameterhead.setText(TextUtilities.removeNamespace(parameter));
	}

}
