package info.esblurock.reaction.chemconnect.core.client.catalog.protocol;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.overlay.MaterialOverlay;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.pages.DataStructurePages;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.methodology.ChemConnectProtocol;
import info.esblurock.reaction.chemconnect.core.data.transfer.ProtocolSetupTransfer;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class StandardDatasetProtocolHeader extends Composite {

	private static StandardDatasetProtocolHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetProtocolHeaderUiBinder.class);

	interface StandardDatasetProtocolHeaderUiBinder extends UiBinder<Widget, StandardDatasetProtocolHeader> {
	}

	public StandardDatasetProtocolHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialTooltip devicetooltip;
	@UiField
	MaterialLink title;
	@UiField
	MaterialTooltip savetooltip;
	@UiField
	MaterialLink save;
	@UiField
	MaterialTooltip deletetooltip;
	@UiField
	MaterialLink delete;
	@UiField
	MaterialLink add;
	@UiField
	MaterialTooltip addtooltip;
	@UiField
	MaterialPanel measureitems;
	@UiField
	MaterialPanel dimensionitems;
	@UiField
	MaterialLink dimensionlink;
	@UiField
	MaterialLink measurelink;
	
	@UiField
	MaterialOverlay overlay;
	@UiField
	MaterialButton btnCloseOverlay;
	@UiField
	MaterialPanel overlaypanel;
	
	StandardDatasetObjectHierarchyItem item;
	DatabaseObjectHierarchy hierarchy;
	ChemConnectProtocol methodology;
	DatabaseObjectHierarchy catidhierarchy;
	DataCatalogID catid;
	
	public StandardDatasetProtocolHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		hierarchy = item.getHierarchy();
		init();
		
		
		ChemConnectProtocol methodology = (ChemConnectProtocol) item.getObject();
		
		catidhierarchy = hierarchy.getSubObject(methodology.getCatalogDataID());
		catid = (DataCatalogID) catidhierarchy.getObject();
		
		title.setText(TextUtilities.removeNamespace(catid.getDataCatalog()));
		devicetooltip.setText(methodology.getIdentifier());
		save.setEnabled(true);
		
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		ProtocolDefinitionSetupCallback callback = new ProtocolDefinitionSetupCallback(this);
		async.protocolDefinitionSetup(catid.getDataCatalog(), methodology.getOwner(), callback);
	}
	
	void init() {
		overlaypanel.add(DataStructurePages.setofobservations);
		dimensionlink.setText("Dimension Specifications");
		measurelink.setText("Measure Specifications");
		addtooltip.setText("Create a observation specification to be used in this protocol");
		savetooltip.setText("Save changes to this protocol specification");
		deletetooltip.setText("Delete this protocol specification");
	}
	
	public void protocolSetup(ProtocolSetupTransfer transfer) {
		for(String name: transfer.getMeasurenodes().keySet()) {
			HierarchyNode measurenodes = transfer.getMeasurenodes().get(name);
			ProtocolObservationSpecificationChoice choice = new ProtocolObservationSpecificationChoice(name,
					measurenodes,item.getModalpanel(), item);
			dimensionitems.add(choice);
		}
		for(String name: transfer.getDimensionnodes().keySet()) {
			HierarchyNode measurenodes = transfer.getDimensionnodes().get(name);
			ProtocolObservationSpecificationChoice choice = new ProtocolObservationSpecificationChoice(name,measurenodes,item.getModalpanel(),item);
			measureitems.add(choice);
		}
	}

	@UiHandler("btnCloseOverlay")
	public void onCloseOverlay(ClickEvent event) {
		overlay.close();
	}
	
	@UiHandler("save")
	void onClickSave(ClickEvent event) {
		Window.alert("Save Object");
		item.writeDatabaseObjectHierarchy();
	}
	@UiHandler("add")
	void onClickAdd(ClickEvent event) {
		overlay.open();
	}
	@UiHandler("delete")
	void onClickDelete(ClickEvent event) {
		Window.alert("Delete Object not implemented");
	}

	public boolean updateProtocol() {
		//ChemConnectProtocol methodology = (ChemConnectProtocol) item.getObject();
		return true;
	}

}