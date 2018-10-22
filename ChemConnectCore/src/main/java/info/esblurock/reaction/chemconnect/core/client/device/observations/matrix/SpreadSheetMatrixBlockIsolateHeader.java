package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;

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
import info.esblurock.reaction.chemconnect.core.client.catalog.HierarchyNodeCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.HierarchyNodeCallbackInterface;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.catalog.SubCatagoryHierarchyCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.choose.SubCatagoryHierarchyCallbackInterface;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.modal.ChooseFromHierarchyNode;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockIsolation;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationMatrixValues;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class SpreadSheetMatrixBlockIsolateHeader extends Composite 
	implements ChooseFromConceptHeirarchy, HierarchyNodeCallbackInterface, SubCatagoryHierarchyCallbackInterface {

	private static SpreadSheetMatrixBlockIsolateHeaderUiBinder uiBinder = GWT
			.create(SpreadSheetMatrixBlockIsolateHeaderUiBinder.class);

	interface SpreadSheetMatrixBlockIsolateHeaderUiBinder
			extends UiBinder<Widget, SpreadSheetMatrixBlockIsolateHeader> {
	}

	@UiField
	MaterialLink startrow;
	@UiField
	MaterialLink endrow;
	@UiField
	MaterialLink startcolumn;
	@UiField
	MaterialLink endcolumn;
	@UiField
	MaterialLink startrowtext;
	@UiField
	MaterialLink endrowtext;
	@UiField
	MaterialLink startcolumntext;
	@UiField
	MaterialLink endcolumntext;
	@UiField
	MaterialTooltip originaltooltip;
	@UiField
	MaterialLink originalmatrix;
	@UiField
	MaterialPanel originalmatrixpanel;
	
	boolean startrowB;
	boolean endrowB;
	boolean startcolumnB;
	boolean endcolumnB;
	ArrayList<String> startrowChoice;
	ArrayList<String> endrowChoice;
	ArrayList<String> startcolumnChoice;
	ArrayList<String> endcolumnChoice;
	
	boolean originalmatrixB;
	
	SpreadSheetBlockIsolation spread;
	StandardDatasetObjectHierarchyItem item;
	DatabaseObjectHierarchy observationsFromSpreadSheet;
	DatabaseObjectHierarchy observationMatrixValues;
	
	public SpreadSheetMatrixBlockIsolateHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public SpreadSheetMatrixBlockIsolateHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		
		init();
		spread = (SpreadSheetBlockIsolation) item.getObject();
		this.item = item;
		observationsFromSpreadSheet = null;
		startrow.setText(TextUtilities.removeNamespace(spread.getStartRowType()));
		endrow.setText(TextUtilities.removeNamespace(spread.getEndRowType()));
		startcolumn.setText(TextUtilities.removeNamespace(spread.getStartColumnType()));
		endcolumn.setText(TextUtilities.removeNamespace(spread.getEndColumnType()));

		startrowtext.setText(spread.getStartRowInfo());
		endrowtext.setText(spread.getEndRowInfo());
		startcolumntext.setText(spread.getStartColumnInfo());
		endcolumntext.setText(spread.getEndColumnInfo());

	}
	
	void init() {
		startrowB = false;
		endrowB = false;
		startcolumnB = false;
		endcolumnB = false;
		originalmatrixB = false;
		
		startrowChoice = new ArrayList<String>();
		startrowChoice.add("dataset:MatrixBlockBeginClassification");
		endrowChoice = new ArrayList<String>();
		endrowChoice.add("dataset:MatrixBlockEndClassification");
		startcolumnChoice = new ArrayList<String>();
		startcolumnChoice.add("dataset:MatrixBlockColumnBeginClassification");
		endcolumnChoice = new ArrayList<String>();
		endcolumnChoice.add("dataset:MatrixBlockColumnEndClassification");
		
		originaltooltip.setText("The reference matrix (the pattern on which to base the block)");
		originalmatrix.setText("Choose Reference Matrix");
	}
	
	
	@UiHandler("startrow")
	public void onStartRowClick(ClickEvent event) {
		startrowB = true;
		ChooseFromConceptHierarchies choose = new ChooseFromConceptHierarchies(startrowChoice,this);
		item.getModalpanel().clear();
		item.getModalpanel().add(choose);
		choose.open();
	}
	
	@UiHandler("endrow")
	public void onEndRowClick(ClickEvent event) {
		endrowB = true;
		ChooseFromConceptHierarchies choose = new ChooseFromConceptHierarchies(endrowChoice,this);
		item.getModalpanel().clear();
		item.getModalpanel().add(choose);
		choose.open();				
	}
	
	@UiHandler("startcolumn")
	public void onStartColumnClick(ClickEvent event) {
		startcolumnB = true;
		ChooseFromConceptHierarchies choose = new ChooseFromConceptHierarchies(startcolumnChoice,this);
		item.getModalpanel().clear();
		item.getModalpanel().add(choose);
		choose.open();				
	}
	
	@UiHandler("endcolumn")
	public void onEndColumnClick(ClickEvent event) {
		endcolumnB = true;
		ChooseFromConceptHierarchies choose = new ChooseFromConceptHierarchies(endcolumnChoice,this);
		item.getModalpanel().clear();
		item.getModalpanel().add(choose);
		choose.open();				
	}
	
	@UiHandler("originalmatrix")
	public void originalmatrixClick(ClickEvent event) {
		String datacatalog = MetaDataKeywords.dataFileMatrixStructure;
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		HierarchyNodeCallback callback = new HierarchyNodeCallback(this);
		async.getIDHierarchyFromDataCatalogAndUser(datacatalog,callback);	
		
	}
	@Override
	public void insertTree(HierarchyNode topnode) {
		Window.alert(topnode.toString());
		ChooseFromHierarchyNode choose = new ChooseFromHierarchyNode(MetaDataKeywords.dataFileMatrixStructure,
				"Choose a reference Matrix to test isolation", topnode, this);
		item.getModalpanel().clear();
		item.getModalpanel().add(choose);
		choose.open();
		originalmatrixB = true;
	}

	@Override
	public void conceptChosen(String topconcept, String concept, ArrayList<String> path) {
		if(startrowB) {
			spread.setStartRowType(concept);
			startrow.setText(TextUtilities.removeNamespace(concept));
		} else if(endrowB) {
			spread.setEndRowInfo(concept);
			endrow.setText(TextUtilities.removeNamespace(concept));			
		} else if(startcolumnB) {
			spread.setStartColumnType(concept);
			startcolumn.setText(TextUtilities.removeNamespace(concept));			
		} else if(endcolumnB) {
			spread.setEndColumnType(concept);
			endcolumn.setText(TextUtilities.removeNamespace(concept));			
		} else if(originalmatrixB) {
			Window.alert("conceptChosen- originalmatrix: " + concept);
			
			UserImageServiceAsync async = UserImageService.Util.getInstance();
			SubCatagoryHierarchyCallback callback = new SubCatagoryHierarchyCallback(this);
			async.getCatalogObject(concept,MetaDataKeywords.observationsFromSpreadSheet,callback);
			
		}
		startrowB = false;
		endrowB = false;
		startcolumnB = false;
		endcolumnB = false;
		originalmatrixB = false;
	}

	private void setInfoRelativeToOriginal() {
		
	}

	@Override
	public void setInHierarchy(DatabaseObjectHierarchy subs) {
		observationsFromSpreadSheet = subs;
		ObservationsFromSpreadSheet obs = (ObservationsFromSpreadSheet) subs.getObject();
		observationMatrixValues = subs.getSubObject(obs.getObservationMatrixValues());
		StandardDatasetObjectHierarchyItem matrixitem = new StandardDatasetObjectHierarchyItem(observationMatrixValues,item.getModalpanel());
		originalmatrixpanel.add(matrixitem.getHeader());
	}


}
