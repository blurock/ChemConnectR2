package info.esblurock.reaction.chemconnect.core.client.device.observations;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialDialog;
import info.esblurock.reaction.chemconnect.core.client.device.observations.units.SetUpParameterValue;
import info.esblurock.reaction.chemconnect.core.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.data.concepts.SetOfUnitProperties;

public class AskForUnitsModal extends Composite {

	private static AskForUnitsModalUiBinder uiBinder = GWT.create(AskForUnitsModalUiBinder.class);

	interface AskForUnitsModalUiBinder extends UiBinder<Widget, AskForUnitsModal> {
	}

	public AskForUnitsModal() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialComboBox<String> unitschoice;
	@UiField
	MaterialDialog modal;
	@UiField
	MaterialLink close;
	@UiField
	MaterialLink done;
	
	SetLineContentInterface setline;

	public AskForUnitsModal(String chosenUnit, SetOfUnitProperties set, SetLineContentInterface setline) {
		initWidget(uiBinder.createAndBindUi(this));
		 SetUpParameterValue.setup(unitschoice,chosenUnit,set);
		this.setline = setline;
	}
	@UiHandler("done")
	void onClickDone(ClickEvent e) {
		setline.setLineContent(unitschoice.getSingleValue());
		modal.close();
	}
	@UiHandler("close")
	void onClickClose(ClickEvent e) {
		modal.close();
	}
	
	public void openModal() {
		modal.open();
	}

}
