package info.esblurock.reaction.chemconnect.core.client.catalog.methodology;

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
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.catalog.SetUpDatabaseObjectHierarchyCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataObjectLink;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.methodology.ChemConnectProtocol;
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
	MaterialLink attach;
	@UiField
	MaterialLink save;
	@UiField
	MaterialLink delete;
	@UiField
	MaterialCollapsible correspondences;
	
	StandardDatasetObjectHierarchyItem item;
	DatabaseObjectHierarchy hierarchy;
	ChemConnectProtocol methodology;
	DatabaseObjectHierarchy catidhierarchy;
	DataCatalogID catid;
	
	public StandardDatasetProtocolHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		hierarchy = item.getHierarchy();
		ChemConnectProtocol methodology = (ChemConnectProtocol) item.getObject();
		
		catidhierarchy = hierarchy.getSubObject(methodology.getCatalogDataID());
		catid = (DataCatalogID) catidhierarchy.getObject();
		
		title.setText(TextUtilities.removeNamespace(catid.getDataCatalog()));
		devicetooltip.setText(methodology.getIdentifier());
		save.setEnabled(true);
		
		findObservationCorrespondenceSpecificationLinks();
	}
	
	public void findObservationCorrespondenceSpecificationLinks() {
		DatabaseObjectHierarchy multhierarchy = hierarchy.getSubObject(methodology.getChemConnectObjectLink());
		ArrayList<DatabaseObjectHierarchy> lst = multhierarchy.getSubobjects();
		for(DatabaseObjectHierarchy multhier : lst) {
			DataObjectLink link = (DataObjectLink) multhier.getObject();
			if(link.getLinkConcept().compareTo(MetaDataKeywords.observationCorrespondenceSpecification) == 0) {
				String id = link.getDataStructure();
				UserImageServiceAsync async = UserImageService.Util.getInstance();
				SetUpDatabaseObjectHierarchyCallback callback = new SetUpDatabaseObjectHierarchyCallback(correspondences,item.getModalpanel());
				async.getCatalogObject(id,MetaDataKeywords.observationCorrespondenceSpecification,callback);	
			}
		}
	}
	
	@UiHandler("attach")
	void onClickAttach(ClickEvent event) {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		SetUpDatabaseObjectHierarchyCallback callback 
			= new SetUpDatabaseObjectHierarchyCallback(correspondences,item.getModalpanel());
		
		DatabaseObject obj = new DatabaseObject(methodology);
		obj.nullKey();
		String observation =""; 
		String title ="";
		
		async.getSetOfObservations(obj, observation, title,catid,callback);			
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
