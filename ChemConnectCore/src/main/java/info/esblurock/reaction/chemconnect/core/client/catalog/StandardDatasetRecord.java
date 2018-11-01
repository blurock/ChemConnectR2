package info.esblurock.reaction.chemconnect.core.client.catalog;

import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.CreatePrimitiveStructure;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.DefaultPrimitiveStructureRow;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.ChemConnectRecordInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class StandardDatasetRecord extends Composite {

	private static StandardDatasetRecordUiBinder uiBinder = GWT.create(StandardDatasetRecordUiBinder.class);

	interface StandardDatasetRecordUiBinder extends UiBinder<Widget, StandardDatasetRecord> {
	}

	public StandardDatasetRecord() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialCollapsibleItem body;
	@UiField
	MaterialLink datatype;
	@UiField
	MaterialColumn infocolumn;
	@UiField
	MaterialIcon info;
	@UiField
	MaterialPanel content;
	@UiField
	MaterialCollapsible collapsible;

	DatabaseObjectHierarchy hierarchy;
	ChemConnectRecordInformation recordInfo;
	ArrayList<PrimitiveDataStructureBase> records;
	Map<String, PrimitiveDataStructureBase> primitives;
	ArrayList<StandardDatasetObjectHierarchyItem> items;
	StandardDatasetObjectHierarchyItem parent;
	
	public StandardDatasetRecord(StandardDatasetObjectHierarchyItem parent, DatabaseObjectHierarchy hierarchy) {
		initWidget(uiBinder.createAndBindUi(this));
		this.hierarchy = hierarchy;
		records = new ArrayList<PrimitiveDataStructureBase>();
		items = new ArrayList<StandardDatasetObjectHierarchyItem>();
		getRecordInformation(hierarchy);
		this.parent = parent;
	}

	void getRecordInformation(DatabaseObjectHierarchy hierarchy) {
		DatabaseObject obj = hierarchy.getObject();
		String structurename = obj.getClass().getSimpleName();
		datatype.setText(structurename);
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		GetChemConnectRecordInformationCallback callback = new GetChemConnectRecordInformationCallback(this);
		async.getChemConnectRecordInformation(obj, callback);
	}

	public void insertRecords(ChemConnectRecordInformation info) {
		recordInfo = info;
		ChemConnectCompoundDataStructure structure = info.getStructure();
		Map<String, Object> mapping = info.getMapping();
		for (DataElementInformation element : structure) {
			String structurename = element.getChemconnectStructure();
			String id = element.getIdentifier();
			String value = (String) mapping.get(id);
			DatabaseObjectHierarchy subhierarchy = hierarchy.getSubObject(value);
			if (subhierarchy == null) {
				PrimitiveDataStructureInformation elementinfo = new PrimitiveDataStructureInformation(info.getObject(),
						element.getDataElementName(), element.getChemconnectStructure(), value);
				try {
					CreatePrimitiveStructure create = CreatePrimitiveStructure.valueOf(structurename);
					PrimitiveDataStructureBase primitive = create.createStructure(elementinfo);
					content.add(primitive);
					records.add(primitive);
				} catch (IllegalArgumentException ex) {
					DefaultPrimitiveStructureRow row = new DefaultPrimitiveStructureRow(elementinfo);
					PrimitiveDataStructureBase base = new PrimitiveDataStructureBase(elementinfo);
					base.add(row);
					content.add(base);
					primitives.put(id,base);
				}
			} else {
				StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(parent,subhierarchy,null);
				collapsible.add(item);
				items.add(item);
			}

		}
	}
	public void updateFromRecords() {
		for(StandardDatasetObjectHierarchyItem item : items) {
			item.updateDatabaseObjectHierarchy();
		}
		/*
		for(PrimitiveDataStructureBase base : records) {
			PrimitiveDataStructureInformation info = base.getPrimitiveDataStructureInformation();
			DatabaseObject object = info.get
			
		}
		*/
		/*
		ChemConnectCompoundDataStructure structure = recordInfo.getStructure();
		Map<String, Object> mapping = recordInfo.getMapping();
		for (DataElementInformation element : structure) {
			String structurename = element.getChemconnectStructure();
			String id = element.getIdentifier();
			String value = (String) mapping.get(id);
			DatabaseObjectHierarchy subhierarchy = hierarchy.getSubObject(value);
			if (subhierarchy == null) {
				try {
					CreatePrimitiveStructure create = CreatePrimitiveStructure.valueOf(structurename);
					//create.updateObject(recordInfo.getObject());
				} catch (IllegalArgumentException ex) {
					PrimitiveDataStructureBase base = primitives.get(id);
					PrimitiveDataStructureInformation info = base.getPrimitiveDataStructureInformation();
					value = info.getValue();
					mapping.put(id, value);
				}
			}
		}
		*/
	}
}
