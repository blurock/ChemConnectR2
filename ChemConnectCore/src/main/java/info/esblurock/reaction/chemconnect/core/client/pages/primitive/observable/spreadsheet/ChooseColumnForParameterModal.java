package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialDialog;

public class ChooseColumnForParameterModal extends Composite {

	private static ChooseColumnForParameterModalUiBinder uiBinder = GWT
			.create(ChooseColumnForParameterModalUiBinder.class);

	interface ChooseColumnForParameterModalUiBinder extends UiBinder<Widget, ChooseColumnForParameterModal> {
	}

	public ChooseColumnForParameterModal() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink button;
	@UiField
	MaterialLink parameter;
	@UiField
	MaterialLink close;
	@UiField
	MaterialDropDown column;
	@UiField
	MaterialDialog modal;
	
	ColumnToParameterCorrespondenceRow top;
	int colcount;
	boolean error;
	ArrayList<String> columns;
	
	
	public ChooseColumnForParameterModal(String parameterS,
			ArrayList<String> columns, 
			ColumnToParameterCorrespondenceRow top) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		parameter.setText(parameterS);
		this.top = top;
		for(String param : columns) {
			MaterialLink link = new MaterialLink(param);
			column.add(link);
		}		
	}	
	
	void init() {
		
	}
		
	@UiHandler("column")
	void onClick(SelectionEvent<Widget>  callback) {
		Window.alert("SelectionEvent");
		String selected = callback.getSelectedItem().getClass().getCanonicalName();
		Window.alert("SelectionEvent  " + selected);
		boolean notdone = true;
		int count = 0;
		while(notdone) {
			if(columns.get(count).compareTo(selected) == 0) {
				notdone = false;
			}
			count++;
		}
		modal.close();
	}
	
	@UiHandler("close")
	void onClickClose(ClickEvent e) {
		modal.close();
	}

}
