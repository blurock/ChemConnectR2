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
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.client.administration.GetMainStructureSubElementsCallback;
import info.esblurock.reaction.chemconnect.core.client.cards.CardModal;
import info.esblurock.reaction.chemconnect.core.client.cards.ClassificationInformationCard;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.CreatePrimitiveStructure;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.DefaultPrimiiveDataStructure;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.value.MultipleRecordsPrimitive;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
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
	SubsystemInformation subsysteminfo;

	boolean isParameterDescriptionSet;
	boolean isObservationSpecification;

	public MainDataStructureCollapsible() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public MainDataStructureCollapsible(ClassificationInformation info) {
		initWidget(uiBinder.createAndBindUi(this));
		clsinfo = info;
		datatype.setText(info.getDataType());
		setIdenifier(false);
		init(clsinfo.getIdName());
	}

	public MainDataStructureCollapsible(String parentId, DataElementInformation element,
			ChemConnectDataStructure totalstructure, SubsystemInformation subsysteminfo) {
		initWidget(uiBinder.createAndBindUi(this));
		ChemConnectCompoundDataStructure compound = totalstructure.getMapping()
				.getStructure(element.getDataElementName());
		this.subsysteminfo = subsysteminfo;
		String type = compound.getRecordType();
		isParameterDescriptionSet = type.compareTo(parameterDescriptionSetS) == 0;
		isObservationSpecification = type.compareTo(observationSpecificationS) == 0;
		this.subelements = compound;
		clsinfo = new ClassificationInformation(null, null, null, parentId, type);
		if (subelements != null) {
			datatype.setText(TextUtilities.removeNamespace(type));
			setIdenifier(false);
			init(type);
			MapToChemConnectCompoundDataStructure mapping = totalstructure.getMapping();
			setUpStructureElements(parentId, element, compound, mapping);
		} else {
			Window.alert("MainDataStructureCollapsible: can't find element: " + type);
		}
	}

	private void setUpStructureElements(String id, DataElementInformation element,
			ChemConnectCompoundDataStructure struct, MapToChemConnectCompoundDataStructure mapping) {
		try {
			CreatePrimitiveStructure create = CreatePrimitiveStructure.valueOf(element.getChemconnectStructure());
			structureWithPrimitiveStructure(id,element,create);			
		} catch (Exception ex) {
			for (DataElementInformation subelement : struct) {
				compound(id,subelement,mapping);
			}
		}
	}

	private void compound(String id, DataElementInformation subelement,MapToChemConnectCompoundDataStructure mapping) {
		String structurename = subelement.getDataElementName();
		ChemConnectCompoundDataStructure sub = mapping.getStructure(structurename);
		String subid = id + "-" + subelement.getSuffix();
		if (sub != null) {
			setUpStructureElements(subid, subelement, sub, mapping);
		} else {
			try {
				CreatePrimitiveStructure create = CreatePrimitiveStructure.valueOf(subelement.getChemconnectStructure());
				structureWithPrimitiveStructure(subid,subelement,create);
			}  catch (Exception ex) {
				PrimitiveDataStructureInformation info = new PrimitiveDataStructureInformation(structurename,
					subid, subelement.getIdentifier());
				DefaultPrimiiveDataStructure defaultbase = new DefaultPrimiiveDataStructure(info);
				content.add(defaultbase);
			}
		}
		
	}
	
	private void structureWithPrimitiveStructure(String id, DataElementInformation element,CreatePrimitiveStructure create ) {
		if (element.isSinglet()) {
			String type = element.getDataElementName();
			String structure = element.getChemconnectStructure();
			PrimitiveDataStructureInformation info = new PrimitiveDataStructureInformation(type,id,structure);
			PrimitiveDataStructureBase base = create.createStructure(info);
			content.add(base);
		} else {
			String elementname = element.getDataElementName();
			String subid = id + "-" + element.getSuffix();
			MultipleRecordsPrimitive multiple = new MultipleRecordsPrimitive(elementname, create);
			if (isParameterDescriptionSet && elementname.compareTo(parameterValueS) == 0) {
				multiple.fillInParameters(subid, subsysteminfo);
			}
			if (isObservationSpecification && elementname.compareTo(observationSpecificationS) == 0) {
				multiple.fillInSpecifications(subid, subsysteminfo);
			}
			content.add(multiple);
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
		async.getChemConnectCompoundDataStructure(name, callback);
	}

	public void setIdenifier(boolean textbox) {
		if (textbox) {

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

	public void setStructureSubElements(ChemConnectCompoundDataStructure subelements) {
		this.subelements = subelements;
	}

	public ClassificationInformation getClsinfo() {
		return clsinfo;
	}

	public ChemConnectCompoundDataStructure getSubelements() {
		return subelements;
	}
}
