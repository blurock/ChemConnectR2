package info.esblurock.reaction.chemconnect.core.client.catalog.protocol;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.overlay.MaterialOverlay;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.GeneralVoidReturnCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.modal.OKAnswerInterface;
import info.esblurock.reaction.chemconnect.core.client.modal.OKModal;
import info.esblurock.reaction.chemconnect.core.client.pages.DataStructurePages;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.methodology.ChemConnectProtocol;
import info.esblurock.reaction.chemconnect.core.data.transfer.ProtocolSetupTransfer;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class StandardDatasetProtocolHeader extends Composite implements OKAnswerInterface {

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
	void deleteClick(ClickEvent event) {
		OKModal askifok = new OKModal("askifOK","Are you sure you want to delete catalog obj","Delete",this);	
		item.getModalpanel().clear();
		item.getModalpanel().add(askifok);
		askifok.openModal();
	}
	@Override
	public void answeredOK(String answer) {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		GeneralVoidReturnCallback callback = new GeneralVoidReturnCallback("Protocol deletion successful");
		async.deleteObject(item.getObject().getIdentifier(),
				MetaDataKeywords.chemConnectProtocol,
				callback);
	}

	public boolean updateProtocol() {
		//ChemConnectProtocol methodology = (ChemConnectProtocol) item.getObject();
		return true;
	}

}
