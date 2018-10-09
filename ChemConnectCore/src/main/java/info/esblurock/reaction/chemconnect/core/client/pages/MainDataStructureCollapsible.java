package info.esblurock.reaction.chemconnect.core.client.pages;

import java.util.ArrayList;
import java.util.Map;

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
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveInterpretedInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.SubsystemInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.MapToChemConnectCompoundDataStructure;

/**
 * @author edwardblurock
 *
 */
public class MainDataStructureCollapsible extends Composite {

	private static MainDataStructureCollapsibleUiBinder uiBinder = GWT
			.create(MainDataStructureCollapsibleUiBinder.class);

	interface MainDataStructureCollapsibleUiBinder extends UiBinder<Widget, MainDataStructureCollapsible> {
	}

	String parameterDescriptionSetS = MetaDataKeywords.parameterDescriptionSet;
	String observationSpecificationS = MetaDataKeywords.setOfObservationsSpecification;
	String parameterSpecificationS = MetaDataKeywords.parameterSpecification;
	String parameterValueS = MetaDataKeywords.parameterValue;
	String specandvalues = MetaDataKeywords.observationValuesWithSpecification;

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
	boolean findStructures;

	boolean isParameterDescriptionSet;
	boolean isObservationSpecification;
	String rootID;
	// SetOfObservationsInformation observations;

	public MainDataStructureCollapsible() {
		initWidget(uiBinder.createAndBindUi(this));
		findStructures = true;
		Window.alert("MainDataStructureCollapsible" + "   MainDataStructureCollapsible()");
	}

	public MainDataStructureCollapsible(ClassificationInformation info) {
		initWidget(uiBinder.createAndBindUi(this));
		findStructures = true;
		clsinfo = info;
		datatype.setText(info.getDataType());
		Window.alert("MainDataStructureCollapsible" + "   MainDataStructureCollapsible(ClassificationInformation info)");
		init(clsinfo.getIdName());
	}

	public MainDataStructureCollapsible(ClassificationInformation info, boolean findStructures) {
		initWidget(uiBinder.createAndBindUi(this));
		this.findStructures = findStructures;
		clsinfo = info;
		datatype.setText(info.getDataType());
		Window.alert("MainDataStructureCollapsible" + "   MainDataStructureCollapsible(ClassificationInformation info, boolean findStructures)");
		init(clsinfo.getIdName());
	}

	/**
	 * @param obj
	 *            This has the parent object information, this subobject inheirits
	 *            this info
	 * @param element
	 *            This is the element information from the ontology
	 * @param totalstructure:
	 *            This has all the information from the ontology (to be used for sub
	 *            elements of this)
	 * @param modalpanel:
	 *            Modal panel to use.
	 * 
	 *            This call insert
	 */
	public MainDataStructureCollapsible(DatabaseObject obj, DataElementInformation element,
			ChemConnectDataStructure totalstructure, MaterialPanel modalpanel) {
		initWidget(uiBinder.createAndBindUi(this));
		findStructures = true;
		insert(obj, element, totalstructure);
		this.modalpanel = modalpanel;
	}

	public MainDataStructureCollapsible(DatabaseObject obj, DataElementInformation element,
			ChemConnectDataStructure totalstructure, SubsystemInformation subsysteminfo, MaterialPanel modalpanel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.subsysteminfo = subsysteminfo;
		findStructures = true;
		insert(obj, element, totalstructure);
		this.modalpanel = modalpanel;
	}

	/**
	 * @param obj
	 *            This has the parent object information, this subobject inheirits
	 *            this info
	 * @param element
	 *            This is the element information from the ontology
	 * @param totalstructure:
	 *            This has all the information from the ontology (to be used for sub
	 *            elements of this)
	 *
	 *            1. From the mapping, get the element information
	 *            (ChemConnectCompoundDataStructure) 2. Identify if parameter node
	 *            (is a hack.. will disappear later) 3. If the element information
	 *            was found (system error if not): 3.1 Set node title 3.2 3.3 Get
	 *            total mapping 3.4 Call setUpStructureElements (fills in
	 *            information)
	 */
	private void insert(DatabaseObject obj, DataElementInformation element, ChemConnectDataStructure totalstructure) {
		ChemConnectCompoundDataStructure compound = totalstructure.getMapping()
				.getStructure(element.getDataElementName());
		Map<String, DatabaseObject> objectmap = totalstructure.getObjectMap();
		String type = compound.getRecordType();
		isParameterDescriptionSet = type.compareTo(parameterDescriptionSetS) == 0;
		isObservationSpecification = type.compareTo(observationSpecificationS) == 0;
		this.subelements = compound;
		clsinfo = new ClassificationInformation(null, null, null, obj.getIdentifier(),
				element.getChemconnectStructure());
		if (subelements != null) {
			datatype.setText(TextUtilities.removeNamespace(type));
			// init(type);
			MapToChemConnectCompoundDataStructure mapping = totalstructure.getMapping();
			setUpStructureElements(obj, element, compound, mapping, objectmap);
		} else {
			Window.alert("MainDataStructureCollapsible: can't find element: " + type);
		}
	}

	/**
	 * @param obj
	 *            This has the parent object information, this subobject inheirits
	 *            this info
	 * @param element
	 *            This is the element information from the ontology
	 * @param struct
	 *            This has the information from the ontology for this element
	 * @param mapping
	 *            The mapping from the ontology from this level
	 * 
	 *            1. If there is a CreatePrimitiveStructure, then the node will be
	 *            created using structureWithPrimitiveStructure 2. Otherwise: loop
	 *            through the subelements 2.1 Call compound with subelement
	 * 
	 */
	private void setUpStructureElements(DatabaseObject obj, DataElementInformation element,
			ChemConnectCompoundDataStructure struct, MapToChemConnectCompoundDataStructure mapping,
			Map<String, DatabaseObject> objectmap) {
		Window.alert("setUpStructureElements: ID: " + obj.getIdentifier() + "   " + isParameterDescriptionSet);
		DatabaseObject currentobject = null;
		if(objectmap != null) {
			currentobject = objectmap.get(obj.getIdentifier());
		}
		try {
			CreatePrimitiveStructure create = CreatePrimitiveStructure.valueOf(element.getChemconnectStructure());
			structureWithPrimitiveStructure(obj, currentobject, element, create, objectmap);
		} catch (Exception ex) {
			for (DataElementInformation subelement : struct) {
				compound(obj, subelement, mapping, objectmap);
			}
		}
	}

	private void compound(DatabaseObject obj, DataElementInformation subelement,
			MapToChemConnectCompoundDataStructure mapping, Map<String, DatabaseObject> objectmap) {
		String structurename = subelement.getDataElementName();
		ChemConnectCompoundDataStructure sub = mapping.getStructure(structurename);
		String subid = obj.getIdentifier() + "-" + subelement.getSuffix();
		DatabaseObject subobj = new DatabaseObject(obj);
		subobj.setIdentifier(subid);
		if (sub != null) {
			setUpStructureElements(subobj, subelement, sub, mapping, objectmap);
		} else {
			try {
				CreatePrimitiveStructure create = CreatePrimitiveStructure
						.valueOf(subelement.getChemconnectStructure());
				DatabaseObject currentobject = objectmap.get(subid);
				structureWithPrimitiveStructure(subobj, currentobject, subelement, create, objectmap);
			} catch (Exception ex) {
				PrimitiveDataStructureInformation info = new PrimitiveDataStructureInformation(subobj,
						subelement.getDataElementName(), structurename, subelement.getIdentifier());
				DefaultPrimiiveDataStructure defaultbase = new DefaultPrimiiveDataStructure(info);
				content.add(defaultbase);
			}
		}
	}

	/**
	 * @param obj
	 *            This has the parent object information, this subobject inheirits
	 *            this info
	 * @param element
	 *            This is the element information from the ontology
	 * @param create
	 *            How the node is to be built, from CreatePrimitiveStructure
	 * 
	 *            1. This makes a distinction between 1.1 Singlet:
	 */
	private void structureWithPrimitiveStructure(DatabaseObject obj, DatabaseObject currentobject,
			DataElementInformation element, CreatePrimitiveStructure create, Map<String, DatabaseObject> objectmap) {
		if (element.isSinglet()) {
			String type = element.getChemconnectStructure();
			String propertyType = element.getDataElementName();
			String value = element.getIdentifier();
			PrimitiveDataStructureInformation primitive = new PrimitiveDataStructureInformation(currentobject, type,
					propertyType, value);
			PrimitiveInterpretedInformation interpreted = new PrimitiveInterpretedInformation(primitive, currentobject);
			PrimitiveDataStructureBase base = create.createStructure(interpreted);
			base.setIdentifier(obj);
			content.add(base);
		} else {
			Window.alert("structureWithPrimitiveStructure");
			try {
				String elementname = element.getDataElementName();
				MultipleRecordsPrimitive multiple = new MultipleRecordsPrimitive(elementname, create);
				if(currentobject != null) {
				//ChemConnectCompoundMultiple compound = (ChemConnectCompoundMultiple) currentobject;
				//ArrayList<String> lst = compound.getIds();
				//multiple.setIdentifier(currentobject);
				//multiple.addPrimitive(lst, objectmap);
				} else {
					Window.alert("Test: " + isParameterDescriptionSet + "    " + elementname);
					if (isParameterDescriptionSet && elementname.compareTo(parameterValueS) == 0) {
						Window.alert("fill");
						multiple.fillInParameters(obj, subsysteminfo);
					}
				content.add(multiple);
				}
			} catch (Exception ex) {
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
		Window.alert("MainDataStructureCollapsible");
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
		if (findStructures) {
			this.subelements = subelements;
			ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
			ListOfMainDataObjectCallback callback = new ListOfMainDataObjectCallback(this);
			async.getMainObjects(clsinfo, callback);
		} else {

		}
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
