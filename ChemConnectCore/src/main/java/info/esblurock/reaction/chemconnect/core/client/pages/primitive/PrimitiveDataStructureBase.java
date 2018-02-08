package info.esblurock.reaction.chemconnect.core.client.pages.primitive;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class PrimitiveDataStructureBase extends Composite {

	private static PrimitiveDataStructureBaseUiBinder uiBinder = GWT.create(PrimitiveDataStructureBaseUiBinder.class);

	interface PrimitiveDataStructureBaseUiBinder extends UiBinder<Widget, PrimitiveDataStructureBase> {
	}

	String id;
	
	@UiField
	MaterialPanel row;
	boolean editable;
	
	PrimitiveDataStructureInformation primitiveinfo;
	
	public PrimitiveDataStructureBase() {
		initWidget(uiBinder.createAndBindUi(this));
		id = "";
		init();
	}

	public PrimitiveDataStructureBase(PrimitiveDataStructureInformation primitiveinfo) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		this.primitiveinfo = primitiveinfo;
		id = primitiveinfo.getIdentifier();		
	}
	public void init() {
		editable = false;
	}

	public void add(Widget widget) {
		row.add(widget);
	}
	
	public void fill(PrimitiveDataStructureInformation primitiveinfo) {
	}
		
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public void setRowColorMultiple(Color color) {
		row.setBackgroundColor(color);
	}
}
