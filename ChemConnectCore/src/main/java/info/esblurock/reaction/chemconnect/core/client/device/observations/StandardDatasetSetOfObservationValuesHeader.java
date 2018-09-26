package info.esblurock.reaction.chemconnect.core.client.device.observations;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.catalog.DatasetStandardDataCatalogIDHeader;
import info.esblurock.reaction.chemconnect.core.client.catalog.HierarchyNodeCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.HierarchyNodeCallbackInterface;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.catalog.SubCatagoryHierarchyCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.choose.SubCatagoryHierarchyCallbackInterface;
import info.esblurock.reaction.chemconnect.core.client.device.observations.matrix.MatrixSpecificationCorrespondenceSetHeader;
import info.esblurock.reaction.chemconnect.core.client.graph.hierarchy.ConvertToMaterialTree;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.observations.StandardDatasetObservationSpecificationHeader;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.dataset.ObservationSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.SetOfObservationValues;
import info.esblurock.reaction.chemconnect.core.data.description.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.MatrixSpecificationCorrespondenceSet;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.io.metadata.StandardDatasetMetaData;

public class StandardDatasetSetOfObservationValuesHeader extends Composite 
	implements HierarchyNodeCallbackInterface, ChooseFromHierarchyTreeInterface, SubCatagoryHierarchyCallbackInterface {

	private static StandardDatasetSetOfObservationValuesHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetSetOfObservationValuesHeaderUiBinder.class);

	interface StandardDatasetSetOfObservationValuesHeaderUiBinder
			extends UiBinder<Widget, StandardDatasetSetOfObservationValuesHeader> {
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
	SetOfObservationValues value;
	MatrixSpecificationCorrespondenceSetHeader matspecheader;
	DatasetStandardDataCatalogIDHeader catidheader;
	StandardDatasetObservationSpecificationHeader specheader;
	DataCatalogID catid;
	ObservationSpecification obsspec;
	MatrixSpecificationCorrespondenceSet matspec;
	
	ChooseFromHiearchyTree chooseSheet;

	public StandardDatasetSetOfObservationValuesHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public StandardDatasetSetOfObservationValuesHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		DatabaseObjectHierarchy hierarchy = item.getHierarchy();
		value = (SetOfObservationValues) hierarchy.getObject();
		observationtooltip.setText(value.getIdentifier());
		DatabaseObjectHierarchy spechier = hierarchy.getSubObject(value.getObservationSpecification());
		obsspec = (ObservationSpecification) spechier.getObject();
		observationtype.setText(TextUtilities.removeNamespace(obsspec.getObservationParameterType()));
		String descID = value.getDescriptionDataData();
		DatabaseObjectHierarchy  deschier = hierarchy.getSubObject(descID);
		DescriptionDataData description = (DescriptionDataData) deschier.getObject();
		observationhead.setText(description.getOnlinedescription());
		DatabaseObjectHierarchy cathier = hierarchy.getSubObject(value.getCatalogDataID());
		catid = (DataCatalogID) cathier.getObject();
		titletooltip.setText(TextUtilities.removeNamespace(catid.getCatalogBaseName()));
		attachtooltip.setText("Attach a observations from spreadsheet");
		matspecheader = null;
		catidheader = null;
		specheader = null;
		matspec = null;
	}
	
	@UiHandler("attach")
	void saveClick(ClickEvent event) {
		ArrayList<StandardDatasetObjectHierarchyItem> subs = item.getInfoSubitems();
		subs.addAll(item.getSubitems());
		for(StandardDatasetObjectHierarchyItem i: subs) {
			String classname = i.getObject().getClass().getSimpleName();
			if(classname.compareTo("MatrixSpecificationCorrespondenceSet") == 0) {
				matspecheader = (MatrixSpecificationCorrespondenceSetHeader) i.getHeader();
				matspec = (MatrixSpecificationCorrespondenceSet) i.getObject();
			} else if(classname.compareTo("DataCatalogID") == 0) {
				catidheader = (DatasetStandardDataCatalogIDHeader) i.getHeader();
			} else if(classname.compareTo("ObservationSpecification") == 0) {
				specheader = (StandardDatasetObservationSpecificationHeader) i.getHeader();
			}
		}
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		HierarchyNodeCallback callback = new HierarchyNodeCallback(this);
		async.getIDHierarchyFromDataCatalogIDAndClassType(catid.getCatalogBaseName(),"dataset:ObservationsFromSpreadSheet",callback);
		
	}

	@Override
	public void insertTree(HierarchyNode topnode) {
		chooseSheet = new ChooseFromHiearchyTree("",topnode,this);
		chooseSheet.open();
		item.getModalpanel().clear();
		item.getModalpanel().add(chooseSheet);
	}

	@Override
	public void treeNodeChosen(String id, ArrayList<String> path) {
		chooseSheet.close();
		Window.alert("ID of Spreadsheet: " + id);
		Window.alert("Dir path of Spreadsheet: " + path);
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		SubCatagoryHierarchyCallback callback = new SubCatagoryHierarchyCallback(this);
		async.getCatalogObject(id, "dataset:ObservationsFromSpreadSheet",callback);
	}

	@Override
	public void setInHierarchy(DatabaseObjectHierarchy subs) {
		matspecheader.setupMatrix(catid,obsspec,subs);
		
	}

}
