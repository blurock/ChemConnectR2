package info.esblurock.reaction.chemconnect.core.client.pages.catalog.link;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataObjectLink;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class PrimitiveDataObjectLinkRow extends Composite {

	private static PrimitiveDataObjectLinkRowUiBinder uiBinder = GWT.create(PrimitiveDataObjectLinkRowUiBinder.class);

	interface PrimitiveDataObjectLinkRowUiBinder extends UiBinder<Widget, PrimitiveDataObjectLinkRow> {
	}

	@UiField
	MaterialPanel toppanel;
	@UiField
	MaterialTooltip tip;
	@UiField
	MaterialIcon info;
	@UiField
	MaterialTooltip concepttip;
	@UiField
	MaterialLink concept;
	@UiField
	MaterialTooltip linktip;
	@UiField
	MaterialLink link;

	String identifier;
	String typeWithNamespace;
	boolean typeSet;
	DatabaseObject obj;
	
	public PrimitiveDataObjectLinkRow() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		
	}

	public PrimitiveDataObjectLinkRow(DatabaseObject cobject) {
		initWidget(uiBinder.createAndBindUi(this));
		fill(cobject);
	}

	void init() {
		this.obj = new DatabaseObject();
		identifier = "ID";
		tip.setText(identifier);
		this.obj.setIdentifier(identifier);
		
		concepttip.setText("Link concept");
		concept.setText("no concept");
		linktip.setText("Link to another structure");
		link.setText("no link");
	}

	public void fill(DatabaseObject cobject) {
		DataObjectLink objlink = (DataObjectLink) cobject;
		concept.setText(TextUtilities.removeNamespace(objlink.getLinkConcept()));
		link.setText(objlink.getDataStructure());
		String parentlink = "Parent: " + objlink.getParentLink();
		linktip.setText(parentlink);
	}
	@UiHandler("info")
	void onClickInfo(ClickEvent e) {
		MaterialToast.fireToast(identifier);
	}
	public void setIdentifier(DatabaseObject obj) {
		this.obj = new DatabaseObject(obj);
		tip.setText(this.obj.getIdentifier());
	}


}
