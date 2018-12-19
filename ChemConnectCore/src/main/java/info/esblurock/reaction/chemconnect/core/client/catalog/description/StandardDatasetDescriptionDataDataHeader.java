package info.esblurock.reaction.chemconnect.core.client.catalog.description;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.concept.PrimitiveConceptRow;
import info.esblurock.reaction.chemconnect.core.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.data.description.DescriptionDataData;

public class StandardDatasetDescriptionDataDataHeader extends Composite implements SetLineContentInterface {

	private static StandardDatasetDescriptionDataDataHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetDescriptionDataDataHeaderUiBinder.class);

	interface StandardDatasetDescriptionDataDataHeaderUiBinder
			extends UiBinder<Widget, StandardDatasetDescriptionDataDataHeader> {
	}

	public StandardDatasetDescriptionDataDataHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialLink titlelabel;
	@UiField
	MaterialTooltip titletooltip;
	@UiField
	MaterialLink title;
	//@UiField
	//MaterialLink save;
	@UiField
	MaterialTextArea abstractText;
	@UiField
	MaterialTooltip ownertooltip;
	@UiField
	MaterialLink owner;
	@UiField
	MaterialTooltip accessibiltytooltip;
	@UiField
	MaterialLink accessibilty;
	@UiField
	MaterialTooltip datetooltip;
	@UiField
	MaterialDatePicker date;
	@UiField
	MaterialRow purposeconceptrow;
	
	DescriptionDataData description;
	PrimitiveConceptRow conceptrow;

	public StandardDatasetDescriptionDataDataHeader(DescriptionDataData description, PrimitiveConceptRow conceptrow) {
		initWidget(uiBinder.createAndBindUi(this));
		this.description = description;
		init();
		fill(description);
		this.conceptrow = conceptrow;
		purposeconceptrow.add(conceptrow);
	}

	@UiHandler("title")
	void onTitleClick(ClickEvent event) {
		InputLineModal linemodal = new InputLineModal("Title", "Type new title here", this);
		modalpanel.add(linemodal);
		linemodal.openModal();
	}
	
	@Override
	public void setLineContent(String line) {
		title.setText(line);
	}

	void init() {
		ownertooltip.setText("Owner");
		accessibiltytooltip.setText("Accessibility");
		datetooltip.setText("Date source created");
		abstractText.setTitle("Abstract");
		titlelabel.setText("Title: ");
	}

	public void fill(DescriptionDataData description) {
		titletooltip.setText(description.getIdentifier());
		title.setText(description.getOnlinedescription());
		abstractText.setLabel("Abstract");
		abstractText.setText(description.getDescriptionAbstract());
		owner.setText(description.getOwner());
		accessibilty.setText(description.getAccess());
		date.setDate(description.getSourceDate());
	}
	public boolean updateData() {
		conceptrow.updateData();
		description.setOnlinedescription(title.getText());
		description.setDescriptionAbstract(abstractText.getText());
		description.setSourceDate(date.getDate());
		return true;
	}

}
