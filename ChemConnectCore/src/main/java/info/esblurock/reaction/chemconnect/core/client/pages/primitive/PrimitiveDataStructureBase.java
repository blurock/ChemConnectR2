package info.esblurock.reaction.chemconnect.core.client.pages.primitive;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class PrimitiveDataStructureBase extends Composite {

	private static PrimitiveDataStructureBaseUiBinder uiBinder = GWT.create(PrimitiveDataStructureBaseUiBinder.class);

	interface PrimitiveDataStructureBaseUiBinder extends UiBinder<Widget, PrimitiveDataStructureBase> {
	}

	
	@UiField
	MaterialPanel row;
	boolean editable;
	
	PrimitiveDataStructureInformation primitiveinfo;
	DatabaseObject obj;
	
	public PrimitiveDataStructureBase() {
		initWidget(uiBinder.createAndBindUi(this));
		obj = new DatabaseObject();
		init();
	}

	public PrimitiveDataStructureBase(PrimitiveDataStructureInformation primitiveinfo) {
		initWidget(uiBinder.createAndBindUi(this));
		this.primitiveinfo = primitiveinfo;
		init();
		this.primitiveinfo = primitiveinfo;
		obj = new DatabaseObject(primitiveinfo);
	}
	public void init() {
		editable = false;
	}

	public void add(Widget widget) {
		row.add(widget);
	}
	
	public void fill(PrimitiveDataStructureInformation primitiveinfo) {
		obj = new DatabaseObject(primitiveinfo);
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
	public String getIdentifier() {
		return obj.getIdentifier();
	}

	public DatabaseObject getDatabaseObject() {
		return obj;
	}
	
	public PrimitiveDataStructureInformation getPrimitiveDataStructureInformation() {
		return primitiveinfo;
	}
	
	public void setIdentifier(DatabaseObject obj) {
		this.obj = new DatabaseObject(obj);
		if(primitiveinfo != null) {
			primitiveinfo.fill(obj.getIdentifier(), obj.getAccess(), obj.getOwner(), obj.getSourceID());
			primitiveinfo.setIdentifier(obj.getIdentifier());
		}
	}

}
