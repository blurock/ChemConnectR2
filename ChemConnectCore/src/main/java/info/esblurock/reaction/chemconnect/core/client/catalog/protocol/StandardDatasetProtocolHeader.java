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

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
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
	MaterialLink save;
	@UiField
	MaterialLink delete;
	@UiField
	MaterialPanel measureitems;
	@UiField
	MaterialPanel dimensionitems;
	@UiField
	MaterialLink dimensionlink;
	@UiField
	MaterialLink measurelink;
	
	StandardDatasetObjectHierarchyItem item;
	DatabaseObjectHierarchy hierarchy;
	ChemConnectProtocol methodology;
	DatabaseObjectHierarchy catidhierarchy;
	DataCatalogID catid;
	
	public StandardDatasetProtocolHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		hierarchy = item.getHierarchy();
		
		dimensionlink.setText("Dimension Specifications");
		measurelink.setText("Measure Specifications");
		
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

	
	
	@UiHandler("save")
	void onClickSave(ClickEvent event) {
		Window.alert("Save Object");
		item.writeDatabaseObjectHierarchy();
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
