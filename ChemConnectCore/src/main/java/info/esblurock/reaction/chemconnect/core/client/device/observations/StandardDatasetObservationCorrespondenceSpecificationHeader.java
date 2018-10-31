package info.esblurock.reaction.chemconnect.core.client.device.observations;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.UpdateDataObjectHeaderInterface;
import info.esblurock.reaction.chemconnect.core.client.catalog.DatasetStandardDataCatalogIDHeader;
import info.esblurock.reaction.chemconnect.core.client.catalog.HierarchyNodeCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.HierarchyNodeCallbackInterface;
import info.esblurock.reaction.chemconnect.core.client.catalog.SaveDatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.catalog.SubCatagoryHierarchyCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.choose.SubCatagoryHierarchyCallbackInterface;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.device.observations.matrix.MatrixSpecificationCorrespondenceSetHeader;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.dataset.ObservationSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.ObservationCorrespondenceSpecification;
import info.esblurock.reaction.chemconnect.core.data.description.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class StandardDatasetObservationCorrespondenceSpecificationHeader extends Composite 
	implements HierarchyNodeCallbackInterface, ChooseFromHierarchyTreeInterface, 
		SubCatagoryHierarchyCallbackInterface, UpdateDataObjectHeaderInterface,
		ChooseFromConceptHeirarchy {

	private static StandardDatasetObservationCorrespondenceSpecificationHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetObservationCorrespondenceSpecificationHeaderUiBinder.class);

	interface StandardDatasetObservationCorrespondenceSpecificationHeaderUiBinder
			extends UiBinder<Widget, StandardDatasetObservationCorrespondenceSpecificationHeader> {
	}

	@UiField
	MaterialTooltip observationtooltip;
	@UiField
	MaterialTooltip titletooltip;
	@UiField
	MaterialLink observationhead;
	@UiField
	MaterialLink observationtype;
	@UiField
	MaterialIcon save;
	@UiField
	MaterialIcon delete;
	@UiField
	MaterialTooltip attachtooltip;
	@UiField
	MaterialIcon attach;
	
	
	StandardDatasetObjectHierarchyItem item;
	ObservationCorrespondenceSpecification value;
	MatrixSpecificationCorrespondenceSetHeader matspecheader;
	DatasetStandardDataCatalogIDHeader catidheader;
	DataCatalogID catid;
	DescriptionDataData description;
	ObservationSpecification obsspec;
	DatabaseObjectHierarchy attachedObservationsFromSpreadSheet;
	
	String observationType; 
	
	ChooseFromHiearchyTree chooseSheet;
	DatabaseObjectHierarchy spechier;
	
	public StandardDatasetObservationCorrespondenceSpecificationHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public StandardDatasetObservationCorrespondenceSpecificationHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		extractFromItem(item);
		fillInValues();
	}
	private void fillInValues() {
		setObservationType();
		observationtooltip.setText(value.getIdentifier());
		observationhead.setText(description.getOnlinedescription());
		titletooltip.setText(TextUtilities.removeNamespace(catid.getCatalogBaseName()));
		attachtooltip.setText("Attach a observations from spreadsheet");
		
	}
	private void extractFromItem(StandardDatasetObjectHierarchyItem item) {
		this.item = item;
		DatabaseObjectHierarchy hierarchy = item.getHierarchy();
		value = (ObservationCorrespondenceSpecification) hierarchy.getObject();
		spechier = hierarchy.getSubObject(value.getObservationSpecification());
		obsspec = (ObservationSpecification) spechier.getObject();
		String descID = value.getDescriptionDataData();
		DatabaseObjectHierarchy  deschier = hierarchy.getSubObject(descID);
		description = (DescriptionDataData) deschier.getObject();
		DatabaseObjectHierarchy cathier = hierarchy.getSubObject(value.getCatalogDataID());
		catid = (DataCatalogID) cathier.getObject();		
	}
	
	private void init() {
		attachedObservationsFromSpreadSheet = null;
		matspecheader = null;
		catidheader = null;		
	}
	private void setObservationType() {
		observationType = obsspec.getObservationParameterType();
		observationtype.setText(TextUtilities.removeNamespace(observationType));
	}

	@UiHandler("observationtype")
	void observationtypeClicked(ClickEvent event) {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(MetaDataKeywords.observationStructureChoice);
		ChooseFromConceptHierarchies choose = new ChooseFromConceptHierarchies(choices,this);
		item.getModalpanel().clear();
		item.getModalpanel().add(choose);
	}
	@Override
	public void conceptChosen(String topconcept, String concept, ArrayList<String> path) {
		obsspec.setObservationParameterType(concept);
		setObservationType();
	}

	@UiHandler("save")
	void saveClick(ClickEvent event) {
		SaveDatasetCatalogHierarchy saveit = new SaveDatasetCatalogHierarchy(item);
		item.getModalpanel().clear();
		item.getModalpanel().add(saveit);
		saveit.openModal();
	}
	
	private void fillInSubItems() {
		catidheader = (DatasetStandardDataCatalogIDHeader) item.getItemHeaderFromID(value.getCatalogDataID());
		matspecheader = (MatrixSpecificationCorrespondenceSetHeader) item.getItemHeaderFromID(value.getMatrixSpecificationCorrespondenceSet());
		matspecheader.setUpListOfSpecifications(spechier);
		matspecheader.displayCorrespondences();
	}
	
	
	@UiHandler("attach")
	void attachClick(ClickEvent event) {
		fillInSubItems();
		if(attachedObservationsFromSpreadSheet == null) {
			UserImageServiceAsync async = UserImageService.Util.getInstance();
			HierarchyNodeCallback callback = new HierarchyNodeCallback(this);
			async.getIDHierarchyFromDataCatalogIDAndClassType(catid.getCatalogBaseName(),
					MetaDataKeywords.observationsFromSpreadSheet,callback);
		}
	}

	@Override
	public void insertTree(HierarchyNode topnode) {
		chooseSheet = new ChooseFromHiearchyTree("Choose reference matrix for set of observations",topnode,this);
		chooseSheet.open();
		item.getModalpanel().clear();
		item.getModalpanel().add(chooseSheet);
	}

	@Override
	public void treeNodeChosen(String id, ArrayList<String> path) {
		chooseSheet.close();
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		SubCatagoryHierarchyCallback callback = new SubCatagoryHierarchyCallback(this);
		Window.alert("treeNodeChosen: " + id);
		async.getCatalogObject(id,MetaDataKeywords.observationsFromSpreadSheet,callback);
	}

	@Override
	public void setInHierarchy(DatabaseObjectHierarchy subs) {
		Window.alert("StandardDatasetSetOfObservationValuesHeader  setInHierarchy 1");
		matspecheader.setUpListOfSpecifications(spechier);
		Window.alert("StandardDatasetSetOfObservationValuesHeader  setInHierarchy 2");
		matspecheader.setupMatrix(catid,subs);
		Window.alert("StandardDatasetSetOfObservationValuesHeader  setInHierarchy 3");
	}
	
	@Override
	public boolean updateData() {
		matspecheader.updateData();
		obsspec.setObservationParameterType(observationType);
		return true;
	}
}
