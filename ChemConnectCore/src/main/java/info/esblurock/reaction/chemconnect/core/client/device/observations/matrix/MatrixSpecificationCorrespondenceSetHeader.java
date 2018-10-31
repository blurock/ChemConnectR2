package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;

import java.util.ArrayList;
import java.util.HashMap;

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
import info.esblurock.reaction.chemconnect.core.client.UpdateDataObjectHeaderInterface;
import info.esblurock.reaction.chemconnect.core.client.catalog.SetUpCollapsibleItem;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.dataset.ObservationSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.ParameterSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.ValueUnits;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.MatrixSpecificationCorrespondenceSet;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRowTitle;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class MatrixSpecificationCorrespondenceSetHeader extends Composite 
	implements  UpdateDataObjectHeaderInterface {

	private static MatrixSpecificationCorrespondenceSetHeaderUiBinder uiBinder = GWT
			.create(MatrixSpecificationCorrespondenceSetHeaderUiBinder.class);

	interface MatrixSpecificationCorrespondenceSetHeaderUiBinder
			extends UiBinder<Widget, MatrixSpecificationCorrespondenceSetHeader> {
	}

	public MatrixSpecificationCorrespondenceSetHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink headername;
	/*
	@UiField
	MaterialLink startrow;
	@UiField
	MaterialLink endrow;
	@UiField
	MaterialLink startcolumn;
	@UiField
	MaterialLink endcolumn;
	*/
	@UiField
	MaterialLink corrspecheader;
	@UiField
	MaterialLink addcorr;
	@UiField
	MaterialPanel corrspecpanel;
	@UiField
	MaterialLink spreadsheetname;
	/*
	boolean startrowB;
	boolean endrowB;
	boolean startcolumnB;
	boolean endcolumnB;
*/
	MatrixSpecificationCorrespondenceSet speccorrset;
	DatabaseObjectHierarchy hierarchy;
	//MatrixBlockDefinition blockdef;
	DatabaseObjectHierarchy matspechierarchy;
	ChemConnectCompoundMultiple specmult;
	StandardDatasetObjectHierarchyItem topitem;
	InputLineModal linemodal;
	ArrayList<String> orderedlist;
	HashMap<String, String> listmap;
	DatabaseObjectHierarchy attachedObservationsFromSpreadSheet;

	public MatrixSpecificationCorrespondenceSetHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		this.hierarchy = item.getHierarchy();
		initMatrixSpecificationCorrespondenceSet();
		this.topitem = item;
		//displayMatrixBlockDefinition();
		displayCorrespondences();
	}
	/*
	 * MatrixSpecificationCorrespondenceSet hierarchy
	 */
	private void initMatrixSpecificationCorrespondenceSet() {
		speccorrset = (MatrixSpecificationCorrespondenceSet) hierarchy.getObject();
		matspechierarchy = hierarchy
				.getSubObject(speccorrset.getMatrixSpecificationCorrespondence());
		specmult = (ChemConnectCompoundMultiple) matspechierarchy.getObject();		
	}
	
	private void init() {
		headername.setText("Matrix Specification Correspondence Set");
		corrspecheader.setText("Specification Correspondence Set");
		spreadsheetname.setText("Spreadsheet Matrix");
		//startrowB = false;
		//endrowB = false;
		//startcolumnB = false;
		//endcolumnB = false;
		
		orderedlist = null;
		listmap = null;
		attachedObservationsFromSpreadSheet = null;
	}
	
	public void displayCorrespondences() {
		for(DatabaseObjectHierarchy mathier : matspechierarchy.getSubobjects()) {
			StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(mathier,
					topitem.getModalpanel());
			SetUpCollapsibleItem.MatrixSpecificationCorrespondence.addInformation(item);
			MatrixSpecificationCorrespondenceHeader header = (MatrixSpecificationCorrespondenceHeader) item.getHeader();
			
			if(orderedlist != null) {
				header.addChoices("Select Specification for Column", orderedlist, listmap, false);
			}
			corrspecpanel.add(header);
		}
	}
/*
	@UiHandler("startrow")
	public void onStartRowClock(ClickEvent event) {
		startrowB = true;
		linemodal = new InputLineModal("Starting row in matrix", "0",InputLineModal.integerField, true, this);
		topitem.getModalpanel().clear();
		topitem.getModalpanel().add(linemodal);
		linemodal.openModal();
	}
	@UiHandler("endrow")
	public void onEndRowClock(ClickEvent event) {
		endrowB = true;
		linemodal = new InputLineModal("Last row in matrix", "0",InputLineModal.integerField, true, this);
		topitem.getModalpanel().clear();
 		topitem.getModalpanel().add(linemodal);
		linemodal.openModal();
	}
	@UiHandler("startcolumn")
	public void onStartColumnClock(ClickEvent event) {
		startcolumnB = true;
		linemodal = new InputLineModal("Starting column in matrix", "0",InputLineModal.integerField, true, this);
		topitem.getModalpanel().clear();
		topitem.getModalpanel().add(linemodal);
		linemodal.openModal();
	}
	@UiHandler("endcolumn")
	public void onEndColumnClock(ClickEvent event) {
		endcolumnB = true;
		linemodal = new InputLineModal("last column in matrix","0",InputLineModal.integerField, true, this);
		topitem.getModalpanel().clear();
		topitem.getModalpanel().add(linemodal);
		linemodal.openModal();
	}
	@Override
	public void setLineContent(String line) {
		if(startrowB) {
				startrow.setText(line);
		} else if(endrowB) {
				endrow.setText(line);
		} else if(startcolumnB) {
				startcolumn.setText(line);
		} else if(endcolumnB) {
				endcolumn.setText(line);
		}
		startrowB = false;
		startcolumnB = false;
		endrowB = false;
		endcolumnB = false;			
	}
	*/

	@UiHandler("spreadsheetname")
	void addClickEvent(ClickEvent event) {
		Window.alert("Add Spreadsheet");
	}

	/**
	 * @param catid  Catalog ID
	 * @param spechier Hierarchy for ObservationSpecification
	 * @param subs Hierachy for ObservationsFromSpreadSheet
	 */
	public void setupMatrix(DataCatalogID catid, DatabaseObjectHierarchy subs) {
		attachedObservationsFromSpreadSheet = subs;
		ObservationsFromSpreadSheet observations = (ObservationsFromSpreadSheet) attachedObservationsFromSpreadSheet.getObject();
		Window.alert("StandardDatasetSetOfObservationValuesHeader: setupMatrix 1");
		Window.alert("StandardDatasetSetOfObservationValuesHeader: setupMatrix\n" + observations.toString());
		DatabaseObjectHierarchy matrixhierarchy = attachedObservationsFromSpreadSheet.getSubObject(observations.getObservationValueRowTitle());
		Window.alert("StandardDatasetSetOfObservationValuesHeader: setupMatrix 2");
		setInColumnCorrespondences(matrixhierarchy);
		Window.alert("StandardDatasetSetOfObservationValuesHeader: setupMatrix 3");
	}
	
	public void setUpListOfSpecifications(DatabaseObjectHierarchy spechier) {
		ObservationSpecification obsspec = (ObservationSpecification) spechier.getObject();
		Window.alert("StandardDatasetSetOfObservationValuesHeader: setUpListOfSpecifications\n" + obsspec.toString());
		String dimensionid = obsspec.getDimensionSpecifications();
		String measureid = obsspec.getMeasureSpecifications();
		DatabaseObjectHierarchy dimensionhier = spechier.getSubObject(dimensionid);
		DatabaseObjectHierarchy measurehier = spechier.getSubObject(measureid);
		orderedlist = new ArrayList<String>();
		listmap = new HashMap<String, String>();
		Window.alert("StandardDatasetSetOfObservationValuesHeader: setUpListOfSpecifications 1");
		fillSpec(dimensionhier.getSubobjects());
		Window.alert("StandardDatasetSetOfObservationValuesHeader: setUpListOfSpecifications 2");
		fillSpec(measurehier.getSubobjects());
		Window.alert("StandardDatasetSetOfObservationValuesHeader: setUpListOfSpecifications 3");
		
	}

	private void fillSpec(ArrayList<DatabaseObjectHierarchy> specs) {
		for (DatabaseObjectHierarchy dimelement : specs) {
			ParameterSpecification spec = (ParameterSpecification) dimelement.getObject();
			
			DatabaseObjectHierarchy unithier = dimelement.getSubObject(spec.getUnits());
			
			ValueUnits units = (ValueUnits) unithier.getObject();
			String label = spec.getParameterLabel();
			orderedlist.add(label);
			String title = TextUtilities.removeNamespace(spec.getParameterLabel())
					+ ": (" 
					+ TextUtilities.removeNamespace(spec.getObservationParameterType())
					+ ", "
					+ TextUtilities.removeNamespace(units.getUnitsOfValue())
					+ ")";
			listmap.put(label, title);
		}
	}

	private void setInColumnCorrespondences(DatabaseObjectHierarchy titlehier) {
		//ObservationsFromSpreadSheet sheet = (ObservationsFromSpreadSheet) matrixhierarchy.getObject();
		//DatabaseObjectHierarchy titlehier = matrixhierarchy.getSubObject(sheet.getObservationValueRowTitle());
		Window.alert("setInColumnCorrespondences: should be ObservationValueRowTitle: " + titlehier.getObject().getClass().getSimpleName());
		ObservationValueRowTitle titles = (ObservationValueRowTitle) titlehier.getObject();
		ArrayList<String> coltitles = titles.getParameterLabel();
		Window.alert("Eliminated Block Information: displayMatrixBlockDefinition();");
		/*
		DatabaseObjectHierarchy interprethier = matrixhierarchy.getSubObject(sheet.getSpreadSheetInterpretation());
		SpreadSheetBlockIsolation interpret = (SpreadSheetBlockIsolation) interprethier.getObject();
			blockdef.setStartRowInMatrix(interpret.getStartRow());
			blockdef.setLastRowInMatrix(interpret.getEndRow());
			blockdef.setStartColumnInMatrix(interpret.getStartColumn());
			blockdef.setLastColumnInMatrix(interpret.getEndColumn());
		displayMatrixBlockDefinition();
		*/
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		FillMatrixSpecificationCorrespondenceCallback callback = new FillMatrixSpecificationCorrespondenceCallback(this);
		async.fillMatrixSpecificationCorrespondence(hierarchy,coltitles,callback);
	}
	
	public void fillMatrixSpecificationCorrespondence(DatabaseObjectHierarchy matspechierarchy) {
		hierarchy.replaceInfo(matspechierarchy);
		initMatrixSpecificationCorrespondenceSet();
		displayCorrespondences();
	}
	
	
	public boolean updateData() {
		for(Widget widget: corrspecpanel.getChildren()) {
			MatrixSpecificationCorrespondenceHeader header = (MatrixSpecificationCorrespondenceHeader) widget;
			header.updateData();
		}
		/*
		if(startcolumn.getText().compareTo(MetaDataKeywords.undefined) != 0) {
			blockdef.setStartColumnInMatrix(startcolumn.getText());
			blockdef.setLastColumnInMatrix(endcolumn.getText());
		} else {
			blockdef.setStartColumnInMatrix(null);
			blockdef.setLastColumnInMatrix(null);			
		}
		if(startrow.getText().compareTo(MetaDataKeywords.undefined) != 0) {		
			blockdef.setStartRowInMatrix(startrow.getText());
			blockdef.setLastRowInMatrix(endrow.getText());
		} else {
			blockdef.setStartRowInMatrix(null);
			blockdef.setLastRowInMatrix(null);			
		}
		*/
		return true;
	}
	
	/*
	
	public void displayMatrixBlockDefinition() {
		if(blockdef.getStartRowInMatrix() != null) {
			startrow.setText(blockdef.getStartRowInMatrix());
			endrow.setText(blockdef.getLastRowInMatrix());
		} else {
			startrow.setText(MetaDataKeywords.undefined);
			endrow.setText(MetaDataKeywords.undefined);
			
		}
		if(blockdef.getStartColumnInMatrix() != null) {
			startcolumn.setText(blockdef.getStartColumnInMatrix());
			endcolumn.setText(blockdef.getLastColumnInMatrix());
		} else {
			startcolumn.setText(MetaDataKeywords.undefined);
			endcolumn.setText(MetaDataKeywords.undefined);
			
		}
	}
	*/

}
