package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.overlay.MaterialOverlay;
import gwt.material.design.addins.client.stepper.MaterialStep;
import gwt.material.design.addins.client.stepper.MaterialStepper;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.ReadInSpreadSheetCallback;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.SpreadSheetBlockMatrix;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.SpreadSheetBlockTitle;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;

public class ReadInSpreadSheetInformation extends Composite implements ObservationsFromSpreadSheetInterface {

	private static ReadInSpreadSheetInformationUiBinder uiBinder = GWT
			.create(ReadInSpreadSheetInformationUiBinder.class);

	interface ReadInSpreadSheetInformationUiBinder extends UiBinder<Widget, ReadInSpreadSheetInformation> {
	}
	@UiField
	MaterialPanel parameters;
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
	MaterialTextBox linebegin;
	@UiField
	MaterialCheckBox title;
	@UiField
	MaterialTooltip linetooltip;
	@UiField
	MaterialTooltip titletooltip;
	@UiField
	MaterialCheckBox linecheck;
	@UiField
	MaterialCheckBox searchcheck;
	@UiField
	MaterialTooltip searchtooltip;
	@UiField
	MaterialTextBox searchbegin;
	@UiField
	MaterialPanel tablepanel;
	@UiField
	MaterialButton close;
	@UiField
	MaterialStepper stepper;
	@UiField
	MaterialButton btnContinue1, btnContinue2, btnContinue3;
	@UiField
	MaterialButton btnPrev1, btnPrev2, btnPrev3;
	@UiField
	MaterialStep step1,step2,step3;
	
	MaterialPanel toptablepanel;
	
	public ReadInSpreadSheetInformation() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	public ReadInSpreadSheetInformation(MaterialPanel toptablepanel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.toptablepanel = toptablepanel;
		init();
	}

	
	void init() {
		httpsourceline.setLabel("HTTP Source");
		httpsourceline.setText("http://cms.heatfluxburner.org/wp-content/uploads/Bosschaart_CH4_Air_1atm_Tu_295K_thesis.xls");
		readhttp.setText("Read URL");
		
		filetype.setText("XSL");
		typetooltip.setText("Type of file");
		
		sourcetype.setText("URL");
		sourcetooltip.setText("Source of Text");
		
		delimitor.setText(",");
		delimitortooltip.setText("Column element delimitor");
		
		searchbegin.setText("");
		searchtooltip.setText("Search for first column entry");

		linebegin.setLabel("Start");
		linebegin.setText("0");
		linetooltip.setText("Line to start interpretation (from search if checked)");
		 
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
	}

	@UiHandler({"btnContinue1", "btnContinue2", "btnContinue3"})
	void onNextStep(ClickEvent e){
		stepper.nextStep();
		}

	@UiHandler({"btnPrev1", "btnPrev2", "btnPrev3"})
	void onPrevStep(ClickEvent e){
		stepper.prevStep();
		}
	
	@UiHandler("btnContinue3")
	void onFinish(ClickEvent e){
		MaterialToast.fireToast("All done.");
		stepper.reset();
		overlay.close();
		}
	
	
	@UiHandler("readhttp")
	void onClickReadInput(ClickEvent e) {
		String sourceType = SpreadSheetInputInformation.URL;
		String source = httpsourceline.getText();
		String type = SpreadSheetInputInformation.CVS;
		if(source.endsWith("xls")) {
			type = SpreadSheetInputInformation.XLS;
		}
		SpreadSheetInputInformation input = new SpreadSheetInputInformation(type, sourceType, source);
		readInSpreadSheet(input);
	}

	private void readInSpreadSheet(SpreadSheetInputInformation input) {
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		ReadInSpreadSheetCallback callback = new ReadInSpreadSheetCallback(this);
		async.interpretSpreadSheet(input,callback);
	}
	public void setUpResultMatrix(ObservationsFromSpreadSheet results) {
		ArrayList<SpreadSheetBlockInformation> blocks = results.getBlocks();
		for(SpreadSheetBlockInformation block : blocks) {
			Window.alert(block.toString());
			/*
			if(block.isJustTitle()) {
				SpreadSheetBlockTitle titleblock = new SpreadSheetBlockTitle(block,this);
				tablepanel.add(titleblock);
			} else {
				SpreadSheetBlockMatrix matrixblock = new SpreadSheetBlockMatrix(block, this);
				tablepanel.add(matrixblock);
			}
			*/
		}
	}
}
