package info.esblurock.reaction.chemconnect.core.client.pages.catalog.subsystems;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.cards.CardModal;
import info.esblurock.reaction.chemconnect.core.client.modal.OKAnswerInterface;
import info.esblurock.reaction.chemconnect.core.client.modal.OKModal;
import info.esblurock.reaction.chemconnect.core.client.pages.MainDataStructureCollapsible;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;

public class SubsystemsAndDeviceCollapsible extends Composite implements OKAnswerInterface {

	private static SubsystemsAndDeviceCollapsibleUiBinder uiBinder = GWT
			.create(SubsystemsAndDeviceCollapsibleUiBinder.class);

	interface SubsystemsAndDeviceCollapsibleUiBinder extends UiBinder<Widget, SubsystemsAndDeviceCollapsible> {
	}

	@UiField
	MaterialLabel typetitle;
	@UiField
	MaterialTooltip idtip;
	@UiField
	MaterialCollapsible contentcollapsible;
	@UiField
	MaterialCollapsible infocollapsible;
	@UiField
	MaterialLink info;
	@UiField
	MaterialLink clear;
	
	CardModal card;
	OKModal okmodal;
	String clearConcept;
	String clearText;
	MaterialPanel modalpanel;
	
	ArrayList<SubsystemsAndDeviceCollapsible> subList;
	String catagory;
	String suffix;
	DatabaseObject object;
	String identifier;
	boolean readOnly;

	public SubsystemsAndDeviceCollapsible() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public SubsystemsAndDeviceCollapsible(String name, DatabaseObject object, MaterialPanel modalpanel) {
		initWidget(uiBinder.createAndBindUi(this));
		typetitle.setText(eliminateNamespace(name));
		this.modalpanel = modalpanel;
		init();
		idtip.setText(object.getIdentifier());
	}

	void init() {
		subList = new ArrayList<SubsystemsAndDeviceCollapsible>();
		catagory = "";
		suffix = null;
		this.readOnly = true;
		clearText = "Are you sure you want delete";
		clearConcept = "Delete";
	}

	MaterialCollapsible getCollapsible() {
		return contentcollapsible;
	}

	MaterialCollapsible getInfoCollapsible() {
		return infocollapsible;
	}

	public void add(MainDataStructureCollapsible collapsible) {
		infocollapsible.add(collapsible);
	}

	public void setText(String text) {
		typetitle.setText(text);
	}

	public String getText() {
		return typetitle.getText();
	}

	public void setCatagory(String catagory) {
		if (suffix != null) {
			this.catagory = catagory;
			setIdentifier();
		}
		for (SubsystemsAndDeviceCollapsible sub : subList) {
			sub.setCatagory(catagory);
		}
	}

	private void setTopCatagory() {
		identifier = catagory;
	}

	private void setIdentifier() {
		identifier = catagory + suffix;
	}

	public void setCatagory() {
		if (suffix == null) {
			setTopCatagory();
		} else {
			setIdentifier();
		}
	}

	public void addHierarchialModal(ChemConnectDataStructure infoStructure, String catagory, String suffix) {
		this.catagory = catagory;
		this.suffix = suffix;
		setCatagory();
		/*
		int count = 0;
		String newsuffix = suffix;
		if (suffix == null) {
			newsuffix = "";
		}
		*/
		Window.alert("addHierarchialModal: # " + infoStructure.getRecords().size());
		for (DataElementInformation element : infoStructure.getRecords()) {
			Window.alert("addHierarchialModal: element:  " + element.toString());
			String type = element.getDataElementName();
			ChemConnectCompoundDataStructure compound = infoStructure.getMapping().getStructure(type);
			if (compound != null) {
				Window.alert("addHierarchialModal:compound:   " + compound.toString());
				//MainDataStructureCollapsible main = new MainDataStructureCollapsible(compound, infoStructure,null);
				//add(main);
			} else {
				Window.alert("Compound element not found: " + type);
			}
		}
	}

	private String eliminateNamespace(String fullname) {
		int pos = fullname.indexOf(":");
		String ans = fullname;
		if (pos >= 0) {
			ans = fullname.substring(pos + 1);
		}
		return ans;
	}
	@UiHandler("info")
	void onInfoClick(ClickEvent ev) {
		
	}
	@UiHandler("clear")
	void onClearClick(ClickEvent ev) {
		MaterialToast.fireToast("Clear");
		okmodal = new OKModal(clearConcept, clearText, this);
		modalpanel.clear();
		modalpanel.add(okmodal);
		okmodal.openModal();
		MaterialToast.fireToast("Clear");
	}

	@Override
	public void answeredOK(String answer) {
		this.removeFromParent();
	}
	
}
