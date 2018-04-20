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

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.administration.GetMainStructureSubElementsCallback;
import info.esblurock.reaction.chemconnect.core.client.administration.ListOfMainDataObjectCallback;
import info.esblurock.reaction.chemconnect.core.client.cards.CardModal;
import info.esblurock.reaction.chemconnect.core.client.cards.ClassificationInformationCard;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.CreatePrimitiveStructure;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.DefaultPrimiiveDataStructure;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.value.MultipleRecordsPrimitive;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.SubsystemInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.MapToChemConnectCompoundDataStructure;

public class MainDataStructureCollapsible extends Composite {

	private static MainDataStructureCollapsibleUiBinder uiBinder = GWT
			.create(MainDataStructureCollapsibleUiBinder.class);

	interface MainDataStructureCollapsibleUiBinder extends UiBinder<Widget, MainDataStructureCollapsible> {
	}

	String parameterDescriptionSetS = "dataset:ParameterDescriptionSet";
	String observationSpecificationS = "dataset:SetOfObservationsSpecification";
	String parameterSpecificationS = "dataset:ParameterSpecification";
	String parameterValueS = "dataset:ParameterValue";
	String specandvalues = "dataset:ObservationValuesWithSpecification";
	
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

	
	MaterialPanel modalpanel;
	DatabaseObject baseobj;

	CardModal card;
	ClassificationInformation clsinfo;
	ChemConnectCompoundDataStructure subelements;
	ArrayList<DataElementInformation> records;
	SubsystemInformation subsysteminfo;

	boolean isParameterDescriptionSet;
	boolean isObservationSpecification;
	String rootID; 
	//SetOfObservationsInformation observations;

	public MainDataStructureCollapsible() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public MainDataStructureCollapsible(ClassificationInformation info) {
		initWidget(uiBinder.createAndBindUi(this));
		clsinfo = info;
		datatype.setText(info.getDataType());
		init(clsinfo.getIdName());
	}
	
	public MainDataStructureCollapsible(DatabaseObject obj, DataElementInformation element,
			ChemConnectDataStructure totalstructure, 
			MaterialPanel modalpanel) {
		initWidget(uiBinder.createAndBindUi(this));
		insert(obj,element,totalstructure);
		this.modalpanel = modalpanel;
	}
	
	public MainDataStructureCollapsible(DatabaseObject obj, DataElementInformation element,
			ChemConnectDataStructure totalstructure, 
			SubsystemInformation subsysteminfo,
			MaterialPanel modalpanel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.subsysteminfo = subsysteminfo;		
		insert(obj,element,totalstructure);
		this.modalpanel = modalpanel;
	}
	private void insert(DatabaseObject obj, DataElementInformation element,
			ChemConnectDataStructure totalstructure) {
		ChemConnectCompoundDataStructure compound = totalstructure.getMapping()
				.getStructure(element.getDataElementName());
		String type = compound.getRecordType();
		isParameterDescriptionSet = type.compareTo(parameterDescriptionSetS) == 0;
		isObservationSpecification = type.compareTo(observationSpecificationS) == 0;
		this.subelements = compound;
		clsinfo = new ClassificationInformation(null, null, null, obj.getIdentifier(), type);
		if (subelements != null) {
			datatype.setText(TextUtilities.removeNamespace(type));
			init(type);
			MapToChemConnectCompoundDataStructure mapping = totalstructure.getMapping();
			setUpStructureElements(obj, element, compound, mapping);
		} else {
			Window.alert("MainDataStructureCollapsible: can't find element: " + type);
		}
	}

	private void setUpStructureElements(DatabaseObject obj, DataElementInformation element,
			ChemConnectCompoundDataStructure struct, MapToChemConnectCompoundDataStructure mapping) {
		try {
			CreatePrimitiveStructure create = CreatePrimitiveStructure.valueOf(element.getChemconnectStructure());
			structureWithPrimitiveStructure(obj,element,create);
		} catch (Exception ex) {
			for (DataElementInformation subelement : struct) {
				compound(obj,subelement,mapping);
			}
		}
	}

	private void compound(DatabaseObject obj, DataElementInformation subelement,MapToChemConnectCompoundDataStructure mapping) {
		String structurename = subelement.getDataElementName();
		ChemConnectCompoundDataStructure sub = mapping.getStructure(structurename);
		String subid = obj.getIdentifier() + "-" + subelement.getSuffix();
		DatabaseObject subobj = new DatabaseObject(obj);
		subobj.setIdentifier(subid);
		if (sub != null) {
			setUpStructureElements(subobj, subelement, sub, mapping);
		} else {
			try {
				CreatePrimitiveStructure create = CreatePrimitiveStructure.valueOf(subelement.getChemconnectStructure());
				structureWithPrimitiveStructure(subobj,subelement,create);
			}  catch (Exception ex) {
				
				PrimitiveDataStructureInformation info = new PrimitiveDataStructureInformation(subobj,
						subelement.getDataElementName(), structurename,
					subelement.getIdentifier());
				DefaultPrimiiveDataStructure defaultbase = new DefaultPrimiiveDataStructure(info);
				content.add(defaultbase);
			}
		}
	}
	
	private void structureWithPrimitiveStructure(DatabaseObject obj, DataElementInformation element,CreatePrimitiveStructure create ) {
		if (element.isSinglet()) {
			PrimitiveDataStructureBase base;
				base = create.createEmptyStructure();
				base.setIdentifier(obj);
			content.add(base);
		} else {
			try {
			String elementname = element.getDataElementName();
			MultipleRecordsPrimitive multiple = new MultipleRecordsPrimitive(elementname, create);
			multiple.setIdentifier(obj);
			if (isParameterDescriptionSet && elementname.compareTo(parameterValueS) == 0) {
				multiple.fillInParameters(obj, subsysteminfo);
			}
			content.add(multiple);
			} catch(Exception ex) {
				Window.alert(ex.toString());
			}
		}
		
	}
	public MainDataStructureCollapsible(String idName) {
		initWidget(uiBinder.createAndBindUi(this));
		clsinfo = null;
		datatype.setText(idName);
		init(idName);
	}

	private void init(String name) {
		rootID = TextUtilities.removeNamespace(name);
		card = new CardModal();
		body.add(card);
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		GetMainStructureSubElementsCallback callback = new GetMainStructureSubElementsCallback(this);
		async.getChemConnectCompoundDataStructure(name, callback);
	}

	public void setIdenifier(DatabaseObject obj) {
		baseobj = new DatabaseObject(obj);
		String id = obj.getIdentifier() + "-" + rootID;
		baseobj.setIdentifier(id);
		datatype.setText(id);
	}

	@UiHandler("info")
	public void onInfoClick(ClickEvent event) {
		ClassificationInformationCard infocard = new ClassificationInformationCard(clsinfo);
		card.setContent(infocard, false);
		card.open();
		modalpanel.add(card);
	}

	public void setStructureSubElements(ChemConnectCompoundDataStructure subelements) {
		this.subelements = subelements;
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		ListOfMainDataObjectCallback callback = new ListOfMainDataObjectCallback(this);
		async.getMainObjects(clsinfo, callback);
	}

	public ClassificationInformation getClsinfo() {
		return clsinfo;
	}

	public ChemConnectCompoundDataStructure getSubelements() {
		return subelements;
	}

	public void addObjectCollapsible(MainDataStructureInstanceCollapsible collapsible) {
		MaterialCollapsible object = new MaterialCollapsible();
		object.add(collapsible);
		content.add(object);
	}
	
}
