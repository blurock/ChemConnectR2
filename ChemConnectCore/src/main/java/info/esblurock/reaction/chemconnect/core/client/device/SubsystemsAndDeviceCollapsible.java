package info.esblurock.reaction.chemconnect.core.client.device;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.client.cards.CardModal;
import info.esblurock.reaction.chemconnect.core.client.pages.MainDataStructureCollapsible;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.TotalSubsystemInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.MapToChemConnectCompoundDataStructure;

public class SubsystemsAndDeviceCollapsible extends Composite implements HasText {

	private static SubsystemsAndDeviceCollapsibleUiBinder uiBinder = GWT
			.create(SubsystemsAndDeviceCollapsibleUiBinder.class);

	interface SubsystemsAndDeviceCollapsibleUiBinder extends UiBinder<Widget, SubsystemsAndDeviceCollapsible> {
	}

	@UiField
	MaterialLabel typetitle;
	@UiField
	MaterialTextBox changeableIdentifier;
	@UiField
	MaterialLabel identifier;
	@UiField
	MaterialColumn texboxcolumn;
	@UiField
	MaterialColumn labelcolumn;
	@UiField
	MaterialCollapsible contentcollapsible;
	@UiField
	MaterialCollapsible infocollapsible;
	@UiField
	MaterialLink info;
	@UiField
	MaterialLink expand;
	CardModal card;
	
	ArrayList<SubsystemsAndDeviceCollapsible> subList;
	String catagory;
	String suffix;
	String delimiter;

	public SubsystemsAndDeviceCollapsible() {
		initWidget(uiBinder.createAndBindUi(this));
		init();		
	}

	public SubsystemsAndDeviceCollapsible(String name) {
		initWidget(uiBinder.createAndBindUi(this));
		typetitle.setText(eliminateNamespace(name));
		init();
	}
	 void init() {
		 subList = new ArrayList<SubsystemsAndDeviceCollapsible>();
		 catagory = "";
		 suffix = null;
		 setReadOnly(false);
		 delimiter = ".";
	 }
	
	 
	 public void setReadOnly(boolean readonly) {
		 changeableIdentifier.setReadOnly(readonly);
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
		if(suffix != null) {
			this.catagory = catagory;
			setIdentifier();
		}
		for(SubsystemsAndDeviceCollapsible sub : subList) {
			sub.setCatagory(catagory);
		}
	}
	private void setTopCatagory() {
		texboxcolumn.setVisible(true);
		labelcolumn.setVisible(false);
		changeableIdentifier.setText(catagory);		
	}
	private void setIdentifier() {
		texboxcolumn.setVisible(false);
		labelcolumn.setVisible(true);
		identifier.setText(catagory+suffix);					
	}
	public void setCatagory() {
		if(suffix == null) {
			setTopCatagory();
		} else {
			setIdentifier();
		}
	}
	
	
	public void addHierarchialModal(ChemConnectDataStructure infoStructure, 
			String catagory, String suffix) {
		this.catagory = catagory;
		this.suffix = suffix;
		setCatagory();
		int count = 0;
		String newsuffix = suffix;
		if(suffix == null) {
			newsuffix = "";
		}
		for(DataElementInformation element : infoStructure.getRecords()) {
			Window.alert("DeviceWithSubystemsDefinition: DataElementInformation\n" + element.toString());
			String type = element.getDataElementName();
			ChemConnectCompoundDataStructure compound = infoStructure.getMapping().getStructure(type);
			if(compound != null) {
				MainDataStructureCollapsible main = new MainDataStructureCollapsible(compound,infoStructure);
				add(main);
			} else {
				Window.alert("Compound element not found: " + type);
			}
		}
	}
	private String eliminateNamespace(String fullname) {
		int pos = fullname.indexOf(":");
		String ans = fullname;
		if(pos >= 0) {
			ans = fullname.substring(pos+1);
		}
		return ans;
	}
	
	@UiHandler("changeableIdentifier")
	public void typed(KeyUpEvent ev) {
		String catagoryS = changeableIdentifier.getText();
		setCatagory(catagoryS);
	}
}
