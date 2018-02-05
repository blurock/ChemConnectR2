package info.esblurock.reaction.chemconnect.core.client.pages.primitive;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class PrimitiveDataStructureBase extends Composite {

	private static PrimitiveDataStructureBaseUiBinder uiBinder = GWT.create(PrimitiveDataStructureBaseUiBinder.class);

	interface PrimitiveDataStructureBaseUiBinder extends UiBinder<Widget, PrimitiveDataStructureBase> {
	}

	String id;
	
	@UiField
	MaterialRow row;
	boolean editable;
	MaterialPanel panel;
	
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
		panel = null;
	}

	public void fill(PrimitiveDataStructureInformation primitiveinfo) {
	}
		
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public MaterialPanel getPanel() {
		if(panel == null) {
			MaterialColumn column = new MaterialColumn();
			row.add(column);
			column.setGrid("s12");
			panel = new MaterialPanel();
		}
		return panel;
	}
}
