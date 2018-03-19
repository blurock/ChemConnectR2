package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet;

import java.util.ArrayList;
import java.util.function.Predicate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
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
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetRow;

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

	SpreadSheetMatrix spreadsheet;
	MaterialPanel toptablepanel;
	int numberOfVisibleRows;
	int beginningRow;
	int endingRow;
	ArrayList<SpreadSheetRow> origmatrix;
	ArrayList<SpreadSheetRow> matrix;
	ArrayList<SpreadSheetRow> visiblematrix;
	String obstitle;
	int maxcount;

	public ReadInSpreadSheetInformation() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public ReadInSpreadSheetInformation(String obstitle, MaterialPanel toptablepanel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.obstitle = obstitle;
		spreadsheet = null;
		this.toptablepanel = toptablepanel;
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

	@UiHandler({ "btnContinue1", "btnContinue2" })
	void onNextStep(ClickEvent e) {
		stepper.nextStep();
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
					SpreadSheetRow spreadrow = matrix.get(i);
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
			matrix.removeIf(new Predicate<SpreadSheetRow>() {
				@Override
				public boolean test(SpreadSheetRow t) {
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
		String type = SpreadSheetInputInformation.CVS;
		if (source.endsWith("xls")) {
			type = SpreadSheetInputInformation.XLS;
		}
		DatabaseObject obj = new DatabaseObject();
		SpreadSheetInputInformation input = new SpreadSheetInputInformation(obj,type, sourceType, source);
		readInSpreadSheet(input);
	}
	
	public void setUpResultMatrix(ObservationsFromSpreadSheet results) {
		origmatrix = results.getMatrix();
		matrix = new ArrayList<SpreadSheetRow>(origmatrix);
		beginningRow = 0;
		endingRow = matrix.size();
		numberOfVisibleRows = 200;
		setUpMatrix();
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
		ArrayList<SpreadSheetRow> visible = new ArrayList<SpreadSheetRow>();
		int top = numberOfVisibleRows+beginningRow;
		for(int i=beginningRow; i<top;i++) {
			visible.add(matrix.get(i));
		}
		tablepanel.clear();
		spreadsheet = new SpreadSheetMatrix(obstitle,visible);
		tablepanel.add(spreadsheet);
	}
	
	private void readInSpreadSheet(SpreadSheetInputInformation input) {
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		ReadInSpreadSheetCallback callback = new ReadInSpreadSheetCallback(this);
		async.interpretSpreadSheet(input, callback);
	}



}
