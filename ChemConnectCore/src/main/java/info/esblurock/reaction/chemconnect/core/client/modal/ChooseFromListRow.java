package info.esblurock.reaction.chemconnect.core.client.modal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;

public class ChooseFromListRow extends Composite {

	private static ChooseFromListRowUiBinder uiBinder = GWT.create(ChooseFromListRowUiBinder.class);

	interface ChooseFromListRowUiBinder extends UiBinder<Widget, ChooseFromListRow> {
	}

	public ChooseFromListRow() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiField
	MaterialColumn labelCountRow;
	@UiField
	MaterialLink labelCount;
	@UiField
	MaterialLink specificationLabel;
	@UiField
	MaterialTooltip uncertaintytooltip;
	@UiField
	MaterialCheckBox uncertainty;
	
	ChooseFromListInterface choose;
	String label;
	boolean includeCount;

	public ChooseFromListRow(int count, String label, String rowString, boolean includeCount, ChooseFromListInterface choose) {
		initWidget(uiBinder.createAndBindUi(this));
		specificationLabel.setText(rowString);
		this.label = label;
		labelCount.setText(String.valueOf(count));
		labelCountRow.setVisible(includeCount);
		this.choose = choose;
		this.uncertaintytooltip.setText("Check if value is error/uncertainty");
	}

	@UiHandler("specificationLabel")
	void onClick(ClickEvent e) {
		choose.chosenLabel(label, uncertainty.getValue());
	}

}
