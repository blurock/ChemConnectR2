package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterSpecificationInformation;

public class SetOfObservationsRow extends Composite implements HasText {

	private static SetOfObservationsRowUiBinder uiBinder = GWT.create(SetOfObservationsRowUiBinder.class);

	interface SetOfObservationsRowUiBinder extends UiBinder<Widget, SetOfObservationsRow> {
	}

	public SetOfObservationsRow() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialTooltip identifiertip;
	@UiField
	MaterialLink topconcept;
	@UiField
	MaterialLink valuetype;
	@UiField
	MaterialColumn dimensions;
	@UiField
	MaterialColumn measures;
	
	public SetOfObservationsRow(String id, String top, String type) {
		initWidget(uiBinder.createAndBindUi(this));
		topconcept.setText(TextUtilities.removeNamespace(top));
		valuetype.setText(TextUtilities.removeNamespace(type));
		identifiertip.setText(id);
	}

	public void addParameter(PrimitiveParameterSpecificationInformation info) {
		PrimitiveParameterSpecification spec = new PrimitiveParameterSpecification(info);
		if(info.isDimension()) {
			dimensions.add(spec);
		} else {
			measures.add(spec);
		}
	}
	public void setText(String text) {
		topconcept.setText(text);
	}

	public String getText() {
		return topconcept.getText();
	}

}
