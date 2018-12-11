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

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.modal.ChooseFromHierarchyNode;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class ProtocolObservationSpecificationChoice extends Composite implements ChooseFromConceptHeirarchy {

	private static ProtocolObservationSpecificationChoiceUiBinder uiBinder = GWT
			.create(ProtocolObservationSpecificationChoiceUiBinder.class);

	interface ProtocolObservationSpecificationChoiceUiBinder
			extends UiBinder<Widget, ProtocolObservationSpecificationChoice> {
	}

	public ProtocolObservationSpecificationChoice() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	String defaultChoose = "Choose Specification";
	@UiField
	MaterialLink measure;
	@UiField
	MaterialLink measurespec;
	@UiField
	MaterialLink viewspec;
	@UiField
	MaterialLink deletespec;
	@UiField
	MaterialTooltip measuretooltip;
	
	HierarchyNode measurenodes;
	String measureParameter;
	String measureSpec;
	MaterialPanel modalpanel;
	StandardDatasetObjectHierarchyItem item;
	StandardDatasetObjectHierarchyItem specificationitem;
	boolean specChosen;
	

	public ProtocolObservationSpecificationChoice(String measurename, HierarchyNode measurenodes, MaterialPanel modalpanel,
			StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		measurespec.setText(defaultChoose);
		measure.setText(measurename);
		this.item = item;
		this.modalpanel = modalpanel;
		this.measurenodes = measurenodes;
		this.measureParameter = measurename;
		this.viewspec.setVisible(false);
		this.deletespec.setVisible(false);
		specChosen = false;
		specificationitem = null;
	}

	@UiHandler("measurespec")
	public void measureClick(ClickEvent event) {
		if(specChosen) {
			changeCurrent();
		}
		specChosen = true;
		this.viewspec.setVisible(true);
		this.deletespec.setVisible(true);
		String title = "Choose the specification: " + measureParameter;
		ChooseFromHierarchyNode choose = new ChooseFromHierarchyNode(measureParameter, title, measurenodes,this);
		modalpanel.clear();
		modalpanel.add(choose);
		choose.open();
	}
	@UiHandler("deletespec")
	public void deleteClick(ClickEvent event) {
		deleteCurrent();
	}
	
	@Override
	public void conceptChosen(String topconcept, String concept, ArrayList<String> path) {
		measuretooltip.setText(concept);
		measurespec.setText(TextUtilities.extractSimpleNameFromCatalog(concept));
		this.viewspec.setVisible(true);
	}
	
	void changeCurrent() {
		Window.alert("Changing Specification");
		if(specificationitem != null) {
			Window.alert("Deleting current specification from list");
		}
	}
	void deleteCurrent() {
		Window.alert("Deleting current specification from list");
		measurespec.setText(defaultChoose);
	}
	
	/*
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
*/
}
