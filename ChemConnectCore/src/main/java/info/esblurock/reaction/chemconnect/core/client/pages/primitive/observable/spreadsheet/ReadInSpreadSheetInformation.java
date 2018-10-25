package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet;

import java.util.ArrayList;
import java.util.function.Predicate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.overlay.MaterialOverlay;
import gwt.material.design.addins.client.stepper.MaterialStep;
import gwt.material.design.addins.client.stepper.MaterialStepper;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.ReadInSpreadSheetCallback;
import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServices;
import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServicesAsync;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
//import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInterpretation;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class ReadInSpreadSheetInformation extends Composite implements ObservationsFromSpreadSheetInterface {

	private static ReadInSpreadSheetInformationUiBinder uiBinder = GWT
			.create(ReadInSpreadSheetInformationUiBinder.class);

	interface ReadInSpreadSheetInformationUiBinder extends UiBinder<Widget, ReadInSpreadSheetInformation> {
	}

	@UiField
	MaterialCheckBox nonblank;
	@UiField
	MaterialTooltip nonblacktooltip;
	@UiField
	MaterialTooltip uptooltip;
	@UiField
	MaterialTooltip downtooltip;
	@UiField
	MaterialTextBox beginrows;
	@UiField
	MaterialTextBox endrows;
	@UiField
	MaterialIcon up;
	@UiField
	MaterialIcon down;
	@UiField
	MaterialPanel interpret;
	@UiField
	MaterialOverlay overlay;
	@UiField
	MaterialTooltip sourcetooltip;
	@UiField
	MaterialLink sourcetype;
	@UiField
	MaterialTooltip typetooltip;
	@UiField
	MaterialLink filetype;
	@UiField
	MaterialTooltip delimitortooltip;
	@UiField
	MaterialLink delimitor;
	@UiField
	MaterialLink readhttp;
	@UiField
	MaterialTextBox httpsourceline;
	@UiField
	MaterialPanel columnpanel;

	@UiField
	MaterialTooltip rowstooltip;
	@UiField
	MaterialTextBox numberrows;

	@UiField
	MaterialCheckBox title;
	@UiField
	MaterialTooltip titletooltip;
	@UiField
	MaterialTooltip searchtooltip;
	@UiField
	MaterialTextBox searchbegin;
	@UiField
	ScrollPanel tablepanel;
	@UiField
	MaterialButton close;
	@UiField
	MaterialStepper stepper;
	@UiField
	MaterialButton btnContinue1, btnContinue2, btnContinue3;
	@UiField
	MaterialButton btnPrev1, btnPrev2, btnPrev3;
	@UiField
	MaterialStep step1, step2, step3;

	ArrayList<String> parameterNames;
	SpreadSheetMatrix spreadsheet;
	SpreadSheetInformationExtractionInterface top;
	int numberOfVisibleRows;
	int beginningRow;
	int endingRow;
	ArrayList<ObservationValueRow> origmatrix;
	ArrayList<ObservationValueRow> matrix;
	ArrayList<ObservationValueRow> visiblematrix;
	//SpreadSheetInterpretation interpretation;
	String obstitle;
	int maxcount;
	AssignParameterToColumnPanel assign;
	DatabaseObject obj;
	String parent;
	public ReadInSpreadSheetInformation() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public ReadInSpreadSheetInformation(
			DatabaseObject obj, String parent,
			String obstitle, ArrayList<String> parameterNames, SpreadSheetInformationExtractionInterface top) {
		initWidget(uiBinder.createAndBindUi(this));
		this.obj = obj;
		this.parent = parent;
		this.parameterNames = parameterNames;
		this.obstitle = obstitle;
		spreadsheet = null;
		this.top = top;
		init();
	}

	void init() {
		httpsourceline.setLabel("HTTP Source");
		// httpsourceline.setText("http://combdiaglab.engr.uconn.edu/wp-content/uploads/Laminar_Flame_Speed_database/High-pressure-flame-speed.xlsx");
		httpsourceline
				.setText("http://cms.heatfluxburner.org/wp-content/uploads/Bosschaart_CH4_Air_1atm_Tu_295K_thesis.xls");
		readhttp.setText("Read URL");

		filetype.setText("XSL");
		typetooltip.setText("Type of file");

		sourcetype.setText("URL");
		sourcetooltip.setText("Source of Text");

		delimitor.setText(",");
		delimitortooltip.setText("Column element delimitor");

		searchbegin.setLabel("Search");
		searchbegin.setText("");
		searchtooltip.setText("Search for first column entry");

		beginningRow = 0;
		uptooltip.setText("Shift up");
		downtooltip.setText("Shift down");
		beginrows.setLabel("Begin");
		endrows.setLabel("End");
		numberrows.setLabel("Visible Rows");
		numberOfVisibleRows = 200;
		beginningRow = 0;
		endingRow = 0;
		fieldUpdate();
		titletooltip.setText("Check if title is first row");
		title.setText("Title");

		step1.setTitle("Read");
		step1.setDescription("Read in the spreadsheet file");
		step2.setTitle("Block");
		step2.setDescription("Find the corresponding block of information");
		step3.setTitle("Assign");
		step3.setDescription("Assign the columns to the specification");

	}

	public void open() {
		overlay.open();
	}

	@UiHandler("close")
	public void closeClick(ClickEvent event) {
		MaterialToast.fireToast("All done.");
		overlay.close();
		this.removeFromParent();
	}

	@UiHandler("btnContinue1")
	void onNextStep1(ClickEvent e) {
		stepper.nextStep();
	}
	
	@UiHandler("btnContinue2")
	void onNextStep2(ClickEvent e) {
		stepper.nextStep();
		columnpanel.clear();
		columnpanel.setVerticalAlign(VerticalAlign.TOP);
		boolean error = true;
		ObservationValueRow spreadrow = matrix.get(beginningRow);
		if(title.getValue().booleanValue()) {
			ArrayList<String> columns = spreadrow.getRow();
			assign = new AssignParameterToColumnPanel(parameterNames,error,columns);
			columnpanel.add(assign);
		} else {
			int colcount = spreadrow.getRow().size();
			assign = new AssignParameterToColumnPanel(parameterNames,error,colcount);
			columnpanel.add(assign);
		}
	}

	@UiHandler("btnContinue3")
	void doneProcessing(ClickEvent event) {
		MaterialToast.fireToast("Done Processing matrix");
	}
	@UiHandler({ "btnPrev1", "btnPrev2", "btnPrev3" })
	void onPrevStep(ClickEvent e) {
		stepper.prevStep();
	}

	@UiHandler("up")
	void shiftUpClick(ClickEvent event) {
		beginningRow++;
		setUpMatrix();
	}

	@UiHandler("down")
	void shiftDownClick(ClickEvent event) {
		beginningRow--;
		setUpMatrix();
	}

	@UiHandler("btnContinue3")
	void onFinish(ClickEvent e) {
		MaterialToast.fireToast("All done.");
		top.setCorrespondences(assign.getCorrespondences(obj, parent));
		
		ArrayList<ObservationValueRow> visible = new ArrayList<ObservationValueRow>();
		for(int i=beginningRow; i<endingRow;i++) {
			visible.add(matrix.get(i));
		}
		top.setIsolatedMatrix(visible);
		/*
		int titlerow = -1;
		if(title.getValue().booleanValue()) {
			titlerow = beginningRow;
		}
		*/
		/*
		String beginningColumn = "0";
		String endingColumn = "0";
		boolean titleGiven = title.getValue().booleanValue();
		*/
		Window.alert("Should not be here: ReadInSpreadSheetInformation (eliminated insert to SpreadSheetInterpretation");
		/*
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj,"");
		interpretation = new SpreadSheetInterpretation(structure,
				String.valueOf(beginningRow),String.valueOf(endingRow),
				beginningColumn, endingColumn,
				searchbegin.getText(),
				String.valueOf(titleGiven));
		top.setMatrixInterpretation(interpretation);
		*/
		stepper.reset();
		overlay.close();
	}

	@UiHandler("searchbegin")
	void onSearchClick(KeyUpEvent e) {
		if (e.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			String s = searchbegin.getText();
			int i = beginningRow;
			boolean notdone = true;
			boolean found = false;
			while (notdone) {
				if (i++ < matrix.size()) {
					ObservationValueRow spreadrow = matrix.get(i);
					ArrayList<String> row = spreadrow.getRow();
					if (row.size() > 0) {
						String first = row.get(0).trim();
						if (first.startsWith(s)) {
							beginningRow = i;
							notdone = false;
							found = true;
						}
					}
				} else {
					notdone = false;
				}
			}
			if(found) {
				setUpMatrix();
			} else {
				MaterialToast.fireToast("Search text not found");
			}
		}
	}

	@UiHandler("numberrows")
	void onNumberClick(KeyUpEvent e) {
		if (e.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			Integer numberI = Integer.valueOf(numberrows.getText());
			numberOfVisibleRows = numberI.intValue();
			int total = endingRow - beginningRow;
			if (numberOfVisibleRows >= total) {
				numberOfVisibleRows = total;
			}
			setUpMatrix();
		}
	}
	
	@UiHandler("beginrows")
	void onBeginClick(KeyUpEvent e) {
		if (e.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			Integer numberI = Integer.valueOf(beginrows.getText());
			beginningRow = numberI.intValue();
			if (beginningRow >= matrix.size() || beginningRow < 0) {
				beginningRow = 0;
			}
			setUpMatrix();
		}
	}
	
	@UiHandler("endrows")
	void onEndClick(KeyUpEvent e) {
		if (e.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			Integer numberI = Integer.valueOf(endrows.getText());
			endingRow = numberI.intValue();
			if (endingRow >= matrix.size() || endingRow < 0) {
				endingRow = matrix.size();
			}
			setUpMatrix();
		}
	}
	@UiHandler("nonblank")
	void nonBlankClick(ClickEvent event) {
		if(nonblank.getValue().booleanValue()) {
			matrix.removeIf(new Predicate<ObservationValueRow>() {
				@Override
				public boolean test(ObservationValueRow t) {
					ArrayList<String> lst = t.getRow();
					return lst.size() == 0;
				}
			});
		} else {
			matrix = origmatrix;
		}
		beginningRow = 0;
		endingRow = matrix.size();
		setUpMatrix();
	}
	
	@UiHandler("readhttp")
	void onClickReadInput(ClickEvent e) {
		String sourceType = SpreadSheetInputInformation.URL;
		String source = httpsourceline.getText();
		String type = SpreadSheetInputInformation.CSV;
		if (source.endsWith("xls")) {
			type = SpreadSheetInputInformation.XLS;
		}
		DatabaseObject obj = new DatabaseObject();
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj,"");
		DataCatalogID catid = new DataCatalogID();
		SpreadSheetInputInformation input = new SpreadSheetInputInformation(structure,type, sourceType, source);
		readInSpreadSheet(input,catid);
	}
	
	public void setUpResultMatrix(DatabaseObjectHierarchy results) {
		Window.alert("ReadInSpreadSheetInformation  setUpResultMatrix(");
		/*
		origmatrix = results.getMatrix();
		matrix = new ArrayList<SpreadSheetRow>(origmatrix);
		beginningRow = 0;
		endingRow = matrix.size();
		numberOfVisibleRows = 200;
		setUpMatrix();
		stepper.nextStep();
		*/
	}

	public void fieldUpdate() {
		numberrows.setText(String.valueOf(numberOfVisibleRows));
		beginrows.setText(String.valueOf(beginningRow));
		endrows.setText(String.valueOf(endingRow));
	}

	void setUpMatrix() {
		int number = endingRow - beginningRow;
		if(numberOfVisibleRows > number) {
			numberOfVisibleRows = number;
		}
		fieldUpdate();
		ArrayList<ObservationValueRow> visible = new ArrayList<ObservationValueRow>();
		int top = numberOfVisibleRows+beginningRow;
		for(int i=beginningRow; i<top;i++) {
			visible.add(matrix.get(i));
		}
		tablepanel.clear();
		Window.alert("ReadInSpreadSheetInformation   setUpMatrix()");
		//spreadsheet = new SpreadSheetMatrix(obstitle,visible);
		//tablepanel.add(spreadsheet);
	}
	
	private void readInSpreadSheet(SpreadSheetInputInformation input, DataCatalogID catid) {
		SpreadSheetServicesAsync async = SpreadSheetServices.Util.getInstance();
		ReadInSpreadSheetCallback callback = new ReadInSpreadSheetCallback(this);
		async.interpretSpreadSheet(input,catid,true, callback);
	}



}
