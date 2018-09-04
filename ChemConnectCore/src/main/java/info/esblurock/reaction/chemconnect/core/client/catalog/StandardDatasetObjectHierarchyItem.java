package info.esblurock.reaction.chemconnect.core.client.catalog;

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
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.GeneralVoidReturnCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.multiple.ChemConnectCompoundMultipleHeader;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
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
	MaterialCollapsibleHeader header;
	@UiField
	MaterialPanel headerpanel;
	@UiField
	MaterialPanel infopanel;
	@UiField
	MaterialLink seeinfo;
	@UiField
	MaterialCollapsible topbodypanel;


	DatabaseObject object;
	DatabaseObjectHierarchy hierarchy;
	MaterialPanel modalpanel;
	ArrayList<StandardDatasetObjectHierarchyItem> subitems;
	ArrayList<StandardDatasetObjectHierarchyItem> infosubitems;
	ArrayList<StandardDatasetRecord> records;
	Composite headerObject;
	boolean infovisible;

	public StandardDatasetObjectHierarchyItem(DatabaseObjectHierarchy hierarchy, MaterialPanel modalpanel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.hierarchy = hierarchy;
		this.object = hierarchy.getObject();
		this.modalpanel = modalpanel;
		init();
		addSubObjects(this.object);
	}

	void init() {
		infosubitems = new ArrayList<StandardDatasetObjectHierarchyItem>();
		subitems = new ArrayList<StandardDatasetObjectHierarchyItem>();
		records = new ArrayList<StandardDatasetRecord>();
		topbody.setVisible(true);
		infopanel.setVisible(false);
		infovisible = false;
	}

	@UiHandler("seeinfo")
	public void seeInfo(ClickEvent event) {
		if (infovisible) {
			infopanel.setVisible(false);
			infovisible = false;
		} else {
			infopanel.setVisible(true);
			infovisible = true;
		}
	}

	private void addSubObjects(DatabaseObject object) {
		SetUpCollapsibleItem setup = DatasetHierarchyStaging.getSetup(object);
		if (setup != null) {
			setup.addInformation(this);
			if (setup.addSubitems()) {
				addSubItems(hierarchy);
			} else {
			}
		} else {
			StandardDatasetRecord record = new StandardDatasetRecord(hierarchy);
			infopanel.add(record);
		}

	}

	public void addSubItems(DatabaseObjectHierarchy hierarchy) {
		boolean multipleB = hierarchy.getObject().getClass().getCanonicalName()
				.compareTo(ChemConnectCompoundMultiple.class.getCanonicalName()) == 0;
		boolean multinfoB = false;
		if (multipleB) {
			ChemConnectCompoundMultiple multiple = (ChemConnectCompoundMultiple) hierarchy.getObject();
			String subtype = TextUtilities.removeNamespace(multiple.getType());
			SetUpCollapsibleItem setup = SetUpCollapsibleItem.valueOf(subtype);
			multinfoB = multipleB & setup.isInformation();
		}
		ArrayList<DatasetHierarchyStaging> staginglist = DatasetHierarchyStaging.computeStaging(hierarchy);
		for (DatasetHierarchyStaging substage : staginglist) {
			DatabaseObjectHierarchy sub = substage.getHierarchy();
			if (multinfoB) {
				addMultipleInfoItem(sub);
			} else {
				SetUpCollapsibleItem setup = substage.getSetup();
				StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(sub, modalpanel);
				if (setup != null) {
					if (setup.isInformation()) {
						addInfoItem(item);
					} else {
						boolean submultipleB = sub.getObject().getClass().getCanonicalName()
								.compareTo(ChemConnectCompoundMultiple.class.getCanonicalName()) == 0;
						if(submultipleB) {
							ChemConnectCompoundMultiple m = (ChemConnectCompoundMultiple) sub.getObject();
							String subtype = TextUtilities.removeNamespace(m.getType());
							SetUpCollapsibleItem subsetup = SetUpCollapsibleItem.valueOf(subtype);
							if(subsetup.isInformation()) {
								addInfoItem(item);
							} else {
								addSubItem(item);
							}
						} else {
							addSubItem(item);
						}
					}
				} else {
					StandardDatasetRecord record = new StandardDatasetRecord(sub);
					records.add(record);
					infopanel.add(record);
				}
			}
		}
	}

	public void addHeader(Composite composite) {
		headerpanel.add(composite);
		headerObject = composite;
	}

	public Composite getHeader() {
		return headerObject;
	}

	public void addMultipleInfoItem(DatabaseObjectHierarchy subhierarchy) {
		ChemConnectCompoundMultipleHeader header = (ChemConnectCompoundMultipleHeader) headerObject;
		header.addMultipleObject(subhierarchy);
	}

	public void addInfoItem(StandardDatasetObjectHierarchyItem item) {
		infopanel.add(item.getHeader());
	}

	public void addSubItem(StandardDatasetObjectHierarchyItem item) {
		topbody.setVisible(true);
		topbodypanel.add(item);
		subitems.add(item);
	}

	public void writeDatabaseObjectHierarchy() {
		updateDatabaseObjectHierarchy();
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		WriteDatasetObjectHierarchyCallback callback = new WriteDatasetObjectHierarchyCallback(this);
		async.writeDatabaseObjectHierarchy(hierarchy, callback);
	}
	public void writeYamlObjectHierarchy() {
		updateDatabaseObjectHierarchy();
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		GeneralVoidReturnCallback callback = new GeneralVoidReturnCallback("Successful YAML save");
		async.writeYamlObjectHierarchy(hierarchy, callback);
	}

	public void updateDatabaseObjectHierarchy() {
		SetUpCollapsibleItem setup = DatasetHierarchyStaging.getSetup(object);
		boolean includesubs = true;
		if (setup != null) {
			includesubs = setup.update(this);
		}
		if (includesubs) {
			for (StandardDatasetObjectHierarchyItem sub : subitems) {
				sub.updateDatabaseObjectHierarchy();
			}
			for (StandardDatasetObjectHierarchyItem sub : infosubitems) {
				sub.updateDatabaseObjectHierarchy();
			}
		}
		updateRecords();
	}

	private void updateRecords() {
		for (StandardDatasetRecord record : records) {
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
	
	public MaterialPanel getInfoPanel() {
		return infopanel;
	}

	public void updateHierarchy(DatabaseObjectHierarchy hierarchy2) {
		MaterialCollapsible parent = (MaterialCollapsible) this.getParent();
		this.removeFromParent();
		StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(hierarchy2, modalpanel);
		parent.add(item);
	}
}
