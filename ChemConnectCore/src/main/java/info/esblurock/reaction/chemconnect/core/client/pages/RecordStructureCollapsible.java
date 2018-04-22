package info.esblurock.reaction.chemconnect.core.client.pages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.CreatePrimitiveStructure;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.DefaultPrimiiveDataStructure;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.CompoundDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.MapToChemConnectCompoundDataStructure;

public class RecordStructureCollapsible extends Composite {

	private static RecordStructureCollapsibleUiBinder uiBinder = GWT.create(RecordStructureCollapsibleUiBinder.class);

	interface RecordStructureCollapsibleUiBinder extends UiBinder<Widget, RecordStructureCollapsible> {
	}

	@UiField
	MaterialLabel datatype;
	@UiField
	MaterialLabel identifier;
	@UiField
	MaterialCollapsibleItem body;
	@UiField
	MaterialLink expand;
	@UiField
	MaterialCollapsibleHeader infoheader;
	@UiField
	MaterialPanel panel;

	DatabaseObject obj;
	CompoundDataStructureInformation compound;
	ChemConnectCompoundDataStructure structure;
	MapToChemConnectCompoundDataStructure mapping;

	public RecordStructureCollapsible() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public RecordStructureCollapsible(DatabaseObject obj, DataElementInformation element,
			MapToChemConnectCompoundDataStructure mapping) {
		initWidget(uiBinder.createAndBindUi(this));
		this.mapping = mapping;
		datatype.setText(element.getDataElementName());
		String id = obj.getIdentifier() + "-" + element.getSuffix();
		identifier.setText(id);
		DatabaseObject subobj = new DatabaseObject(obj);
		subobj.setIdentifier(id);
		this.structure = mapping.getStructure(datatype.getText());
		if (structure != null) {
			//setUpStructureElements(this.structure);
		} else {
			primitive(element, subobj);
		}
	}
/*
	private void setUpStructureElements(ChemConnectCompoundDataStructure struct) {
		for (DataElementInformation element : struct) {
			String structurename = element.getDataElementName();
				ChemConnectCompoundDataStructure sub = mapping.getStructure(structurename);
				if (sub != null) {
					setUpStructureElements(sub);
				} else {
					PrimitiveDataStructureInformation info = new PrimitiveDataStructureInformation(structurename,
							element.getIdentifier(), "");
					PrimitiveDataStructureBase base = new PrimitiveDataStructureBase(info);
					infoheader.add(base);
				}
		}
	}
	*/
	private void primitive(DataElementInformation element, DatabaseObject obj) {
		this.obj = obj;
		String structurename = element.getDataElementName();
		try {
			CreatePrimitiveStructure create = CreatePrimitiveStructure.valueOf(structurename);
			PrimitiveDataStructureInformation info = new PrimitiveDataStructureInformation(obj,
					structurename, element.getChemconnectStructure(), "");
			Window.alert("RecordStructureCollapsible: primitive:  " + obj.getIdentifier());
			PrimitiveDataStructureBase base = create.createStructure(info);
			base.setIdentifier(obj);
			infoheader.add(base);
		} catch (Exception ex) {
			Window.alert("RecordStructureCollapsible: primitive: empty ");
			PrimitiveDataStructureInformation info = new PrimitiveDataStructureInformation(obj,structurename, element.getChemconnectStructure(), "");
			DefaultPrimiiveDataStructure base = new DefaultPrimiiveDataStructure(info);
			base.setIdentifier(obj);
			panel.add(base);
			
		}
	}

	public RecordStructureCollapsible(DatabaseObject obj, CompoundDataStructureInformation compound) {
		initWidget(uiBinder.createAndBindUi(this));
		this.obj = obj;
		this.compound = compound;
		datatype.setText(TextUtilities.removeNamespace(compound.getPropertyType()));
		// info.setText(compound.getChemconnectcompound());
		datatype.setTextColor(Color.BLACK);
		// info.setTextColor(Color.BLACK);
		for (PrimitiveDataStructureInformation primitive : compound.getPrimitiveelements()) {
			Window.alert("RecordStructureCollapsible: " + primitive.toString());
			CreatePrimitiveStructure create = CreatePrimitiveStructure.getStructureType(primitive);
			DatabaseObject subobj = new DatabaseObject(primitive);
			String id = obj.getIdentifier() + "-" + primitive.getPropertyType();
			subobj.setIdentifier(id);
			if (create != null) {
				PrimitiveDataStructureBase element = create.createStructure(primitive);
				element.setIdentifier(subobj);
				panel.add(element);
			} else {
				DefaultPrimiiveDataStructure base = new DefaultPrimiiveDataStructure(primitive);
				base.setIdentifier(primitive);
				base.setIdentifier(subobj);
				panel.add(base);
			}
		}
		for (CompoundDataStructureInformation primitive : compound.getCompoundelements()) {
			DatabaseObject subobj = new DatabaseObject(obj);
			
			Window.alert(primitive.toString());
		}
	}

}
