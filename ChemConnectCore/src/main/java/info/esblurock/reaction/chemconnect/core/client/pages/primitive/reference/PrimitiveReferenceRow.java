package info.esblurock.reaction.chemconnect.core.client.pages.primitive.reference;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.CreatePrimitiveStructure;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.value.MultipleRecordsPrimitiveRow;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.description.DataSetReference;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveInterpretedInformation;

public class PrimitiveReferenceRow extends Composite implements SetLineContentInterface {

	private static PrimitiveReferenceRowUiBinder uiBinder = GWT.create(PrimitiveReferenceRowUiBinder.class);

	interface PrimitiveReferenceRowUiBinder extends UiBinder<Widget, PrimitiveReferenceRow> {
	}

	@UiField
	MaterialTooltip identifiertip;
	@UiField
	MaterialPanel toppanel;
	@UiField
	MaterialIcon info;
	@UiField
	MaterialTooltip doitip;
	@UiField
	MaterialLink title;
	@UiField
	MaterialSwitch toggleinfo;
	@UiField
	MaterialRow  extrarow1;
	@UiField
	MaterialLink referenceTitle;
	@UiField
	MaterialLink referenceString;
	@UiField
	MaterialRow extrarow2;
	@UiField
	MaterialPanel namespanel;
	@UiField
	MaterialRow extrarow3;
	@UiField
	MaterialPanel parametervaluespanel;

	PrimitivePersonNameRow person;
	
	boolean extra;
	DatabaseObject obj;
	String doi;
	String labelS;
	String referenceS;
	InputLineModal line;
	String titleS;
	String titlePlaceholderS;
	String referenceStringS;
	String referenceStringPlaceholderS;
	PrimitiveInterpretedInformation referenceinfo;
	DataSetReference reference;
	
	public PrimitiveReferenceRow() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public PrimitiveReferenceRow(PrimitiveInterpretedInformation referenceinfo) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		this.referenceinfo = referenceinfo;
		fill(referenceinfo);
	}

	void init() {
		extrarow1.setVisible(false);
		extrarow2.setVisible(false);
		extrarow3.setVisible(false);
		extra = false;
		
		String parametervalue = "ParameterValue";
		CreatePrimitiveStructure create = CreatePrimitiveStructure.valueOf(parametervalue);
		MultipleRecordsPrimitiveRow multiple = new MultipleRecordsPrimitiveRow(parametervalue,create);
		parametervaluespanel.add(multiple);
		
		person = new PrimitivePersonNameRow();
		namespanel.add(person);
		
		labelS = "";
		referenceString.setText("Reference as String");
		title.setText("Title of paper");
		doi = "DOI";
		doitip.setText(doi);
		obj = new DatabaseObject();
		
		referenceTitle.setText("Reference");
		referenceTitle.setTextColor(Color.BLACK);
		titleS = "Title";
		titlePlaceholderS = "Set in title";
		referenceStringS = "Reference String";
		referenceStringPlaceholderS = "Reference in any format";
		setFullIdentifier();
	}
	
	public void fill(PrimitiveInterpretedInformation referenceinfo) {
		reference = (DataSetReference) referenceinfo.getObj();
		if(reference.getTitle() != null) {
			title.setText(reference.getTitle());
		}
		if(reference.getReferenceString() != null) {
			referenceString.setText(reference.getReferenceString());
		}
		if(reference.getDOI() != null) {
			doi = reference.getDOI();
		}
		setFullIdentifier();
	}
	@UiHandler("info")
	void onClickAdd(ClickEvent e) {
		MaterialToast.fireToast("Click Add: " + obj.getIdentifier());
	}
	
	@UiHandler("title")
	void onClickTitle(ClickEvent e) {
		line = new InputLineModal(titleS, titlePlaceholderS, this);
		labelS = titleS;
		toppanel.add(line);
		line.openModal();
	}
	@UiHandler("referenceString")
	void onClickReferenceString(ClickEvent e) {
		line = new InputLineModal(referenceStringS, referenceStringPlaceholderS, this);
		labelS = referenceStringS;
		toppanel.add(line);
		line.openModal();
	}
	
	@UiHandler("toggleinfo")
	void onClickToggle(ClickEvent e) {
		if(extra) {
			extrarow1.setVisible(false);
			extrarow2.setVisible(false);
			extrarow3.setVisible(false);
			extra = false;		
		} else {
			extrarow1.setVisible(true);
			extrarow2.setVisible(true);
			extrarow3.setVisible(true);
			extra = true;			
		}
	}

	@Override
	public void setLineContent(String linetext) {
		if(labelS.compareTo(titleS) == 0) {
			title.setText(linetext);
		} else if(labelS.compareTo(referenceStringS) == 0) {
			referenceString.setText(linetext);
		}
	}

	public String getIdentifier() {
		return obj.getIdentifier();
	}
	public void setIdentifier(DatabaseObject obj) {
		this.obj = obj;
		setFullIdentifier();
	}
	public String setFullIdentifier() {
		String id = obj.getIdentifier() + "-" + doi;
		obj.setIdentifier(id);
		identifiertip.setText(id);
		return id;
		
	}
}
