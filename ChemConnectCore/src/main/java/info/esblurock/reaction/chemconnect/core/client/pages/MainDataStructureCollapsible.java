package info.esblurock.reaction.chemconnect.core.client.pages;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.administration.GetMainStructureSubElementsCallback;
import info.esblurock.reaction.chemconnect.core.client.cards.CardModal;
import info.esblurock.reaction.chemconnect.core.client.cards.ClassificationInformationCard;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.CreatePrimitiveStructure;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.DefaultPrimiiveDataStructure;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.MapToChemConnectCompoundDataStructure;

public class MainDataStructureCollapsible extends Composite {

	private static MainDataStructureCollapsibleUiBinder uiBinder = GWT
			.create(MainDataStructureCollapsibleUiBinder.class);

	interface MainDataStructureCollapsibleUiBinder extends UiBinder<Widget, MainDataStructureCollapsible> {
	}
	@UiField
	MaterialLabel datatype;
	@UiField
	MaterialPanel content;
	@UiField
	MaterialLink info;
	@UiField
	MaterialCollapsibleItem body;
	@UiField
	MaterialColumn infocolumn;
	@UiField
	MaterialPanel modalpanal;
	
	String identifier;
	
	CardModal card;
	ClassificationInformation clsinfo;
	ChemConnectCompoundDataStructure subelements;
	ArrayList<DataElementInformation> records;
	
	String parentId;
	String suffix;
	
	public MainDataStructureCollapsible() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	public MainDataStructureCollapsible(ClassificationInformation info) {
		initWidget(uiBinder.createAndBindUi(this));
		clsinfo = info;
		datatype.setText(info.getDataType());
		parentId = "ChemConnect";
		suffix = "0";
		setIdenifier(false);
		init(clsinfo.getIdName());
	}
	
	
	public MainDataStructureCollapsible(ChemConnectCompoundDataStructure compound, ChemConnectDataStructure totalstructure) {
		initWidget(uiBinder.createAndBindUi(this));
		parentId = "ChemConnect";
		String type = compound.getRecordType();
		this.subelements = compound;
		clsinfo = new ClassificationInformation(null,null,null,parentId,type);
		if(subelements != null) { 
			datatype.setText(TextUtilities.removeNamespace(type));
			suffix = "0";
			setIdenifier(false);
			init(type);
			setRecords(compound,totalstructure);
		} else {
			Window.alert("MainDataStructureCollapsible: can't find element: " + type);
		}
	}
	
	private void setRecords(ArrayList<DataElementInformation> records, ChemConnectDataStructure datastructure) {
		this.records = records;
		String id = parentId +"-" + suffix;
		for(DataElementInformation record : records) {
			setRecord(id, record,datastructure);
		}
	}
	
	
	private void setRecord(String rootID, DataElementInformation element, ChemConnectDataStructure datastructure) {
		MapToChemConnectCompoundDataStructure mapping = datastructure.getMapping();
		String id = rootID + "-" + element.getSuffix();
		this.identifier = id;
		ChemConnectCompoundDataStructure structure = mapping.getStructure(datatype.getText());
		if (structure != null) {
			//setUpStructureElements(this.structure);
		} else {
			primitive(element, id);
		}
	}
	
	private void setUpStructureElements(ChemConnectCompoundDataStructure struct,
			MapToChemConnectCompoundDataStructure mapping) {
		for (DataElementInformation element : struct) {
			String structurename = element.getDataElementName();
				ChemConnectCompoundDataStructure sub = mapping.getStructure(structurename);
				if (sub != null) {
					setUpStructureElements(sub,mapping);
				} else {
					PrimitiveDataStructureInformation info = new PrimitiveDataStructureInformation(structurename,
							element.getIdentifier(), "");
					PrimitiveDataStructureBase base = new PrimitiveDataStructureBase(info);
					content.add(base);
				}
		}
	}

	
	private void primitive(DataElementInformation element, String identifier) {
		String structurename = element.getDataElementName();
		try {
			CreatePrimitiveStructure create = CreatePrimitiveStructure.valueOf(structurename);
			PrimitiveDataStructureInformation info = new PrimitiveDataStructureInformation(
					element.getDataElementName(), element.getIdentifier(), "");
			PrimitiveDataStructureBase base = create.createEmptyStructure(info);
			content.add(base);
		} catch (Exception ex) {
			PrimitiveDataStructureInformation info = new PrimitiveDataStructureInformation(structurename,
					identifier, "");
			DefaultPrimiiveDataStructure base = new DefaultPrimiiveDataStructure(info);
			content.add(base);
			
		}
	}

	public MainDataStructureCollapsible(String idName) {
		initWidget(uiBinder.createAndBindUi(this));
		clsinfo = null;
		datatype.setText(idName);
		init(idName);
	}
	private void init(String name) {
		card = new CardModal();
		body.add(card);
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		GetMainStructureSubElementsCallback callback = new GetMainStructureSubElementsCallback(this);
		async.getChemConnectCompoundDataStructure(name,callback);
	}
	
	public void setIdenifier(boolean textbox) {
		if(textbox) {
			
		} else {
			
		}
	}
	
	
	
	@UiHandler("info")
	public void onInfoClick(ClickEvent event) {
		ClassificationInformationCard infocard = new ClassificationInformationCard(clsinfo);
		card.setContent(infocard, false);
		card.open();
		modalpanal.add(card);
	}
	/*
	 * The definition of expand has changed
	 * 
	 * 
	@UiHandler("expand")
	public void onExpand(ClickEvent event) {
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		ListOfMainDataObjectCallback callback = new ListOfMainDataObjectCallback(this);
		async.getMainObjects(clsinfo, callback);
	}
	*/
	
	public void setStructureSubElements(ChemConnectCompoundDataStructure subelements) {
		this.subelements = subelements;
	}
	public ClassificationInformation getClsinfo() {
		return clsinfo;
	}
	public ChemConnectCompoundDataStructure getSubelements() {
		return subelements;
	}
	/*
	 * The concept of expand has changed... this will be moved somewhere else
	public void addObjectCollapsible(MainDataStructureInstanceCollapsible collapsible) {
		contentcollapsible.add(collapsible);
	}
*/
}
