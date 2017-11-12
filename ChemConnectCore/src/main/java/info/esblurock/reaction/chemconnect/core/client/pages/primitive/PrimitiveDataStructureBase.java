package info.esblurock.reaction.chemconnect.core.client.pages.primitive;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class PrimitiveDataStructureBase extends Composite {

	private static PrimitiveDataStructureBaseUiBinder uiBinder = GWT.create(PrimitiveDataStructureBaseUiBinder.class);

	interface PrimitiveDataStructureBaseUiBinder extends UiBinder<Widget, PrimitiveDataStructureBase> {
	}

	@UiField
	MaterialIcon info;
	@UiField
	MaterialLabel elementName;
	@UiField
	MaterialPanel elementpanel;
	
	boolean editable;
	
	
	PrimitiveDataStructureInformation primitiveinfo;
	
	public PrimitiveDataStructureBase() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public PrimitiveDataStructureBase(PrimitiveDataStructureInformation primitiveinfo) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		this.primitiveinfo = primitiveinfo;
		elementName.setText(primitiveinfo.getIdentifier());		
	}
	public void init() {
		editable = false;
		info.setIconColor(Color.BLACK);
		elementName.setTextColor(Color.BLACK);
	}

	@UiHandler("info")
	public void onInfo(ClickEvent event) {
		
	}
	
	public void addAsStringLabel() {
		MaterialLink label = new MaterialLink();
		label.setTextColor(Color.BLACK);
		label.setText(primitiveinfo.getValue());
		elementpanel.add(label);
	}
	
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public MaterialPanel getPanel() {
		return elementpanel;
	}
}
