package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetTitleRowCorrespondence;

public class AssignParameterToColumnPanel extends Composite {

	private static AssignParameterToColumnPanelUiBinder uiBinder = GWT
			.create(AssignParameterToColumnPanelUiBinder.class);

	interface AssignParameterToColumnPanelUiBinder extends UiBinder<Widget, AssignParameterToColumnPanel> {
	}

	@UiField
	MaterialPanel parameterpanel;
	
	ArrayList<String> parameters;
	ArrayList<String> columns;
	boolean error;
	int colcount;
	ArrayList<ColumnToParameterCorrespondenceRow> rows;
	
	
	public AssignParameterToColumnPanel(ArrayList<String> parameters, boolean error, ArrayList<String> columns) {
		initWidget(uiBinder.createAndBindUi(this));
		this.parameters = parameters;
		this.error = error;
		this.columns = columns;
		this.colcount = columns.size();
		rows = new ArrayList<ColumnToParameterCorrespondenceRow>();
		parameterpanel.clear();
		fillTitle();
	}
	public AssignParameterToColumnPanel(ArrayList<String> parameters, boolean error, int colcolumn) {
		initWidget(uiBinder.createAndBindUi(this));
		this.parameters = parameters;
		this.colcount = colcolumn;
		this.error = error;
		this.columns = new ArrayList<String>();
		for(int i=0; i<colcount;i++) {
			String param = "Column:" + i;
			columns.add(param);
		}
		rows = new ArrayList<ColumnToParameterCorrespondenceRow>();
		parameterpanel.clear();
		fillTitle();
	}
	
	void fillTitle() {
		int count = 0;
		for(String parameter : parameters) {
			ColumnToParameterCorrespondenceRow row = new ColumnToParameterCorrespondenceRow(parameter, count, false,
					columns, error, this,parameterpanel);
			parameterpanel.add(row);
			rows.add(row);		
			if(error) {
				String errorParameter = createErrorName(parameter);
				ColumnToParameterCorrespondenceRow erow = new ColumnToParameterCorrespondenceRow(errorParameter, count, true,
						columns, error, this,parameterpanel);
				parameterpanel.add(erow);
				rows.add(erow);
				
			}
			count++;
		}
	}
	private String createErrorName(String parameter) {
		return "d(" + parameter + ")";
	}


	public void columnSelected(String parameter, String selected) {
		for(ColumnToParameterCorrespondenceRow row : rows) {
			if(row.getParameter().compareTo(parameter) != 0) {
				if(row.getSelected().compareTo(selected) == 0) {
					MaterialToast.fireToast("Warning: Column already selected for parameter: " + row.getParameter());
				}
			}
		}
	}

	public ArrayList<SpreadSheetTitleRowCorrespondence> getCorrespondences(DatabaseObject obj, String parent) {
		ArrayList<SpreadSheetTitleRowCorrespondence> lst = new ArrayList<SpreadSheetTitleRowCorrespondence>();
		for(ColumnToParameterCorrespondenceRow row : rows) {
			SpreadSheetTitleRowCorrespondence corr = row.getCorrespondence(obj, parent);
			lst.add(corr);
		}
		return lst;
	}

}
