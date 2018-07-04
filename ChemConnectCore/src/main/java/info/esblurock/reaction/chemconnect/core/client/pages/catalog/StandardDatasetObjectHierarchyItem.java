package info.esblurock.reaction.chemconnect.core.client.pages.catalog;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class StandardDatasetObjectHierarchyItem extends Composite {

	private static StandardDatasetObjectHierarchyItemUiBinder uiBinder = GWT
			.create(StandardDatasetObjectHierarchyItemUiBinder.class);

	interface StandardDatasetObjectHierarchyItemUiBinder extends UiBinder<Widget, StandardDatasetObjectHierarchyItem> {
	}

	public StandardDatasetObjectHierarchyItem() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialCollapsibleBody topbody;
	@UiField
	MaterialCollapsibleItem infoitem;
	@UiField
	MaterialCollapsibleHeader header;
	@UiField
	MaterialCollapsible subinfo;
	@UiField
	MaterialTooltip id;
	@UiField
	MaterialLink infotitle;
	@UiField
	MaterialCollapsible infocollapsible;
	@UiField
	MaterialCollapsibleBody body;

	DatabaseObject object;
	DatabaseObjectHierarchy hierarchy;
	MaterialPanel modalpanel;
	ArrayList<StandardDatasetObjectHierarchyItem> subitems;
	ArrayList<StandardDatasetObjectHierarchyItem> infosubitems;
	ArrayList<StandardDatasetRecord> records;
	Composite headerObject;

	public StandardDatasetObjectHierarchyItem(DatabaseObjectHierarchy hierarchy, MaterialPanel modalpanel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.hierarchy = hierarchy;
		this.object = hierarchy.getObject();
		this.modalpanel = modalpanel;
		init();
		id.setText(object.getIdentifier());
		SetUpCollapsibleItem setup = getSetup(object);
		infoitem.setVisible(false);
		topbody.setVisible(false);
		if (setup != null) {
			setup.addInformation(this);
			if (setup.addSubitems()) {
				addSubItems(hierarchy);
			} else {
				subinfo.setVisible(false);
			}
		} else {
			StandardDatasetRecord record = new StandardDatasetRecord(hierarchy);
			infoitem.setVisible(true);
			infocollapsible.add(record);
		}
	}

	public void addSubItems(DatabaseObjectHierarchy hierarchy) {
		for (DatabaseObjectHierarchy sub : hierarchy.getSubobjects()) {
			SetUpCollapsibleItem setup = getSetup(sub.getObject());
			if (setup != null) {
				StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(sub, modalpanel);
				if (setup.isInformation()) {
					addInfoItem(item);
				} else {
					addSubItem(item);
				}
			} else {
				StandardDatasetRecord record = new StandardDatasetRecord(sub);
				records.add(record);
				infoitem.setVisible(true);
				infocollapsible.add(record);
			}
		}
	}

	private SetUpCollapsibleItem getSetup(DatabaseObject object) {
		String structure = object.getClass().getSimpleName();
		SetUpCollapsibleItem setup = null;
		try {
			setup = SetUpCollapsibleItem.valueOf(structure);
		} catch (Exception ex) {
			Window.alert(structure + " not found: " + ex.getClass().getSimpleName());
		}
		return setup;
	}

	void init() {
		infotitle.setText("Info");
		infosubitems = new ArrayList<StandardDatasetObjectHierarchyItem>();
		subitems = new ArrayList<StandardDatasetObjectHierarchyItem>();
		records = new ArrayList<StandardDatasetRecord>();
		infoitem.setVisible(false);
	}

	public void insertInformation() {

	}

	public void addHeader(Composite composite) {
		header.add(composite);
		headerObject = composite;
	}

	public Composite getHeader() {
		return headerObject;
	}

	public void addInfoItem(StandardDatasetObjectHierarchyItem item) {
		infoitem.setVisible(true);
		infosubitems.add(item);
		infocollapsible.add(item);
	}

	public void addSubItem(StandardDatasetObjectHierarchyItem item) {
		topbody.setVisible(false);
		subitems.add(item);
		subinfo.add(item);
	}
	
	public void writeDatabaseObjectHierarchy() {
		updateDatabaseObjectHierarchy();
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		WriteDatasetObjectHierarchyCallback callback = new WriteDatasetObjectHierarchyCallback(this);
		async.writeDatabaseObjectHierarchy(hierarchy,callback);
	}
	
	public void updateDatabaseObjectHierarchy() {
		SetUpCollapsibleItem setup = getSetup(object);
		boolean includesubs = true;
		if(setup != null) {
			includesubs = setup.update(this);
		}
		if(includesubs) {
			for(StandardDatasetObjectHierarchyItem sub : subitems) {
				sub.updateDatabaseObjectHierarchy();
			}
			for(StandardDatasetObjectHierarchyItem sub : infosubitems) {
				sub.updateDatabaseObjectHierarchy();
			}
		}
		updateRecords();
	}
	private void updateRecords() {
		for(StandardDatasetRecord record: records) {
			record.updateFromRecords();
		}
	}

	public DatabaseObject getObject() {
		return object;
	}
	public DatabaseObjectHierarchy getHierarchy() {
		return hierarchy;
	}

	public MaterialPanel getModalpanel() {
		return modalpanel;
	}

	public ArrayList<StandardDatasetObjectHierarchyItem> getSubitems() {
		return subitems;
	}
	public ArrayList<StandardDatasetObjectHierarchyItem> getInfoSubitems() {
		return infosubitems;
	}

	public void setBodyVisible(boolean visible) {
		body.setVisible(visible);
	}

	public void updateHierarchy(DatabaseObjectHierarchy hierarchy2) {
		MaterialCollapsible parent = (MaterialCollapsible) this.getParent();
		this.removeFromParent();
		StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(hierarchy2,modalpanel);
		parent.add(item);
	}
}
