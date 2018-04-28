package info.esblurock.reaction.chemconnect.core.client.pages.primitive.text;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class PrimitiveParagraphRow extends Composite implements HasText {

	private static PrimitiveParagraphRowUiBinder uiBinder = GWT.create(PrimitiveParagraphRowUiBinder.class);

	interface PrimitiveParagraphRowUiBinder extends UiBinder<Widget, PrimitiveParagraphRow> {
	}

	public PrimitiveParagraphRow() {
		initWidget(uiBinder.createAndBindUi(this));
		identifier = "id";
		paragraph.setLabel("Type");
		paragraph.setText("Set text here");		
	}


	@UiField
	MaterialTextArea paragraph;
	@UiField
	MaterialIcon info;
	@UiField
	MaterialTooltip tip;
	
	String identifier;
	String typeWithNamespace;
		
	public PrimitiveParagraphRow(PrimitiveDataStructureInformation info) {
		initWidget(uiBinder.createAndBindUi(this));
		fill(info);
	}

	public void fill(PrimitiveDataStructureInformation info) {
		identifier = info.getIdentifier();
		tip.setText(identifier);
		typeWithNamespace = info.getType();
		paragraph.setLabel(TextUtilities.removeNamespace(typeWithNamespace));
		paragraph.setText(info.getValue());
		paragraph.getLabel().setTextColor(Color.BLACK);
	}

	@UiHandler("info")
	void onClick(ClickEvent e) {
		MaterialToast.fireToast("ID: " + identifier);
	}
	
	
	public void setText(String text) {
		paragraph.setLabel(text);
	}

	public String getText() {
		return paragraph.getLabel().toString();
	}

}
