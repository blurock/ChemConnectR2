package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.catalog.HierarchyNodeCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.HierarchyNodeCallbackInterface;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.catalog.SubCatagoryHierarchyCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.choose.SubCatagoryHierarchyCallbackInterface;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.modal.ChooseFromHierarchyNode;
import info.esblurock.reaction.chemconnect.core.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServices;
import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServicesAsync;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheetFull;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockIsolation;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class SpreadSheetMatrixBlockIsolateHeader extends Composite 
	implements ChooseFromConceptHeirarchy, HierarchyNodeCallbackInterface, 
	SubCatagoryHierarchyCallbackInterface, SetLineContentInterface {

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
	MaterialLink titlechoose;
	@UiField
	MaterialTooltip originaltooltip;
	@UiField
	MaterialLink originalmatrix;
	@UiField
	MaterialTooltip applytooltip;
	@UiField
	MaterialLink apply;
	boolean startrowB;
	boolean endrowB;
	boolean startcolumnB;
	boolean endcolumnB;
	boolean titlesB;
	ArrayList<String> startrowChoice;
	ArrayList<String> endrowChoice;
	ArrayList<String> startcolumnChoice;
	ArrayList<String> endcolumnChoice;
	ArrayList<String> titlesChoice;
	
	boolean readoriginalmatrix;
	
	boolean originalmatrixB;
	
	SpreadSheetBlockIsolation spread;
	StandardDatasetObjectHierarchyItem item;
	DatabaseObjectHierarchy observationsFromSpreadSheet;
	DatabaseObjectHierarchy observationMatrixValues;
	
	String noValue;
	
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
		titlechoose.setText(TextUtilities.removeNamespace(spread.getTitleIncluded()));

		TextUtilities.setText(startrowtext, spread.getStartRowInfo(), noValue);
		TextUtilities.setText(endrowtext, spread.getEndRowInfo(), noValue);
		TextUtilities.setText(startcolumntext, spread.getStartColumnInfo(), noValue);
		TextUtilities.setText(endcolumntext, spread.getEndColumnInfo(), noValue);
	}
	
	void init() {
		startrowB = false;
		endrowB = false;
		startcolumnB = false;
		endcolumnB = false;
		originalmatrixB = false;
		titlesB = false;

		noValue = "no identifier";
		
		startrowChoice = new ArrayList<String>();
		startrowChoice.add("dataset:MatrixBlockBeginClassification");
		endrowChoice = new ArrayList<String>();
		endrowChoice.add("dataset:MatrixBlockEndClassification");
		startcolumnChoice = new ArrayList<String>();
		startcolumnChoice.add("dataset:MatrixBlockColumnBeginClassification");
		endcolumnChoice = new ArrayList<String>();
		endcolumnChoice.add("dataset:MatrixBlockColumnEndClassification");
		titlesChoice = new ArrayList<String>();
		titlesChoice.add("dataset:MatrixBlockTitleClassification");
		
		originaltooltip.setText("The reference matrix (the pattern on which to base the block)");
		originalmatrix.setText("Choose Reference Matrix");
		applytooltip.setText("Apply the current block definition to the reference matrix");
		apply.setText("Apply Block Definition");
		apply.setEnabled(false);
	}
	
	@UiHandler("startrowtext")
	public void onStartRowText(ClickEvent event) {
		startrowB = true;
		InputLineModal inmodal = new InputLineModal("Start Row Identifier","",this);
		item.getModalpanel().clear();
		item.getModalpanel().add(inmodal);
		inmodal.openModal();		
	}
	@UiHandler("endrowtext")
	public void onEndRowText(ClickEvent event) {
		endrowB = true;
		InputLineModal inmodal = new InputLineModal("End Row Identifier","",this);
		item.getModalpanel().clear();
		item.getModalpanel().add(inmodal);
		inmodal.openModal();		
	}
	@UiHandler("startcolumntext")
	public void onStartColumnText(ClickEvent event) {
		startcolumnB = true;
		InputLineModal inmodal = new InputLineModal("Start Column Identifier","",this);
		item.getModalpanel().clear();
		item.getModalpanel().add(inmodal);
		inmodal.openModal();		
	}
	@UiHandler("endcolumntext")
	public void onEndColumnText(ClickEvent event) {
		endcolumnB = true;
		InputLineModal inmodal = new InputLineModal("End Column Identifier","",this);
		item.getModalpanel().clear();
		item.getModalpanel().add(inmodal);
		inmodal.openModal();		
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
	
	@UiHandler("titlechoose")
	public void onTitlesClick(ClickEvent event) {
		titlesB = true;
		ChooseFromConceptHierarchies choose = new ChooseFromConceptHierarchies(titlesChoice,this);
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
			spread.setEndRowType(concept);
			endrow.setText(TextUtilities.removeNamespace(concept));			
		} else if(startcolumnB) {
			spread.setStartColumnType(concept);
			startcolumn.setText(TextUtilities.removeNamespace(concept));			
		} else if(endcolumnB) {
			spread.setEndColumnType(concept);
			endcolumn.setText(TextUtilities.removeNamespace(concept));			
		}  else if(titlesB) {
			spread.setTitleIncluded(concept);
			titlechoose.setText(TextUtilities.removeNamespace(concept));			
		} else if(originalmatrixB) {
			UserImageServiceAsync async = UserImageService.Util.getInstance();
			SubCatagoryHierarchyCallback callback = new SubCatagoryHierarchyCallback(this);
			readoriginalmatrix = true;
			async.getCatalogObject(concept,MetaDataKeywords.observationsFromSpreadSheetFull,callback);
		}
		startrowB = false;
		endrowB = false;
		startcolumnB = false;
		endcolumnB = false;
		originalmatrixB = false;
		titlesB = false;
	}

	@Override
	public void setInHierarchy(DatabaseObjectHierarchy subs) {
		if(readoriginalmatrix) {
			observationsFromSpreadSheet = subs;
			StandardDatasetObjectHierarchyItem matrixitem = new StandardDatasetObjectHierarchyItem(item,subs,
					item.getModalpanel());
			item.addSubItem(matrixitem);
			apply.setEnabled(true);
			item.addLinkToCatalogItem(MetaDataKeywords.conceptLinkReferenceMatrix, subs.getObject().getIdentifier());
		} else {
			StandardDatasetObjectHierarchyItem matrixitem = new StandardDatasetObjectHierarchyItem(item,subs,
					item.getModalpanel());
			item.addSubItem(matrixitem);
			StandardDatasetObservationsFromSpreadSheet header = (StandardDatasetObservationsFromSpreadSheet) matrixitem.getHeader();
			header.addTitles();
			apply.setEnabled(true);
			readoriginalmatrix= false;
			item.addLinkToCatalogItem(MetaDataKeywords.conceptLinkReferenceMatrixIsolatedBlock, subs.getObject().getIdentifier());
		}
	}

	@UiHandler("apply")
	public void onBlockClick(ClickEvent event) {
		ObservationsFromSpreadSheetFull obs = (ObservationsFromSpreadSheetFull) observationsFromSpreadSheet.getObject();
		DatabaseObjectHierarchy catidhier = observationsFromSpreadSheet.getSubObject(obs.getCatalogDataID());
		DataCatalogID catid = (DataCatalogID) catidhier.getObject();
		String id = "IsolateMatrixFrom" + catid.getSimpleCatalogName();
		InputLineModal inmodal = new InputLineModal("Base name of Isolated matrix",id,this);
		item.getModalpanel().clear();
		item.getModalpanel().add(inmodal);
		inmodal.openModal();
	}
	@Override
	public void setLineContent(String line) {
		if(startrowB) {
			startrowtext.setText(line);
			spread.setStartRowInfo(line);
		} else if(endrowB) {
			endrowtext.setText(line);
			spread.setEndRowInfo(line);
		} else if(startcolumnB) {
			startcolumntext.setText(line);
			spread.setStartColumnInfo(line);
		} else if(endcolumnB) {
			endcolumntext.setText(line);
			spread.setEndColumnInfo(line);
		} else {
			applyMatrixIsolation(line.trim());
		}
		startrowB = false;
		endrowB = false;
		startcolumnB = false;
		endcolumnB = false;
	}

	private void applyMatrixIsolation(String basename) {
		ObservationsFromSpreadSheetFull obs = (ObservationsFromSpreadSheetFull) observationsFromSpreadSheet.getObject();
		DatabaseObjectHierarchy catidhier = observationsFromSpreadSheet.getSubObject(obs.getCatalogDataID());
		DataCatalogID catid = (DataCatalogID) catidhier.getObject();
		DataCatalogID isolatedcatid = new DataCatalogID(catid);
		isolatedcatid.nullKey();
		isolatedcatid.setSimpleCatalogName(basename);
		String id = isolatedcatid.getFullName();
		isolatedcatid.setIdentifier(id);
		SpreadSheetServicesAsync async = SpreadSheetServices.Util.getInstance();
		SubCatagoryHierarchyCallback callback = new SubCatagoryHierarchyCallback(this);
		async.isolateFromMatrix(isolatedcatid, observationsFromSpreadSheet, spread, callback);
		readoriginalmatrix= false;		
	}

	public void updateData() {
		
	}
}
