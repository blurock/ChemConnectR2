package info.esblurock.reaction.chemconnect.core.client.modal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;

public class ChooseFromListDialog extends Composite {

	private static ChooseFromListDialogUiBinder uiBinder = GWT.create(ChooseFromListDialogUiBinder.class);

	interface ChooseFromListDialogUiBinder extends UiBinder<Widget, ChooseFromListDialog> {
	}

	@UiField
	MaterialLink header;
	@UiField
	MaterialDialog modal;
	@UiField
	MaterialPanel rowpanel;
	@UiField
	MaterialLink close;
			
	Map<String,String> maplst;
	ChooseFromListInterface choose;
	boolean includeCount;
	ArrayList<String> orderedlist;
	int rowCount;
	public ChooseFromListDialog(String title,boolean includeCount, ChooseFromListInterface choose) {
		initWidget(uiBinder.createAndBindUi(this));
		orderedlist = new ArrayList<String>();
		maplst = new HashMap<String,String>();
		rowCount = 0;
	}

	public ChooseFromListDialog(String title, ArrayList<String> orderedlist, Map<String,String> maplst, boolean includeCount, ChooseFromListInterface choose) {
		initWidget(uiBinder.createAndBindUi(this));
		this.header.setText(title);
		this.maplst = maplst;
		this.choose = choose;
		this.includeCount = includeCount;
		rowCount = 0;
		for(String label : orderedlist) {
			String rowtitle = maplst.get(label);
			addRowElement(label,rowtitle);
		}
	}

	public void setTitle(String newtitle) {
		header.setText(newtitle);
	}
	public void addRow(String label, String rowText) {
		orderedlist.add(label);
		maplst.put(label,rowText);
		addRowElement(label,rowText);
	}
	private void addRowElement(String label, String rowText) {
		ChooseFromListRow row = new ChooseFromListRow(rowCount,label,rowText,includeCount,choose);
		rowCount++;
		rowpanel.add(row);
	}
	
	@UiHandler("close")
	public void onClose(ClickEvent event) {
		modal.close();
	}
	
	public void open() {
		modal.open();
	}
	public void close() {
		modal.close();
	}
}
