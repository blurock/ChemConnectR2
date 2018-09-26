package info.esblurock.reaction.chemconnect.core.client.pages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.chemconnect.core.client.administration.CompoundDataStructureCallback;
import info.esblurock.reaction.chemconnect.core.client.cards.CardModal;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;

public class MainDataStructureInstanceCollapsible extends Composite {

	private static MainDataStructureInstanceCollapsibleUiBinder uiBinder = GWT
			.create(MainDataStructureInstanceCollapsibleUiBinder.class);

	interface MainDataStructureInstanceCollapsibleUiBinder
			extends UiBinder<Widget, MainDataStructureInstanceCollapsible> {
	}


	@UiField
	MaterialLink title;
	@UiField
	MaterialCollapsible contentcollapsible;
	@UiField
	MaterialLink info;
	@UiField
	MaterialCollapsibleItem body;
	@UiField
	MaterialLink expand;

	CardModal card;
	ClassificationInformation clsinfo;
	ChemConnectCompoundDataStructure subelements;
	DatabaseObject object;
	
	
	
	public MainDataStructureInstanceCollapsible(DatabaseObject object, 
			ClassificationInformation clsinfo,
			ChemConnectCompoundDataStructure subelements) {
		initWidget(uiBinder.createAndBindUi(this));
		this.clsinfo = clsinfo;
		this.object = object;
		this.subelements = subelements;
		title.setText(object.getIdentifier());
		init();
	}
	private void init() {
		card = new CardModal();
		body.add(card);
		expand.setIconType(IconType.ARROW_DOWNWARD);
	}
	@UiHandler("expand")
	public void onExpand(ClickEvent event) {
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		CompoundDataStructureCallback callback = new CompoundDataStructureCallback(this);
		async.extractRecordElementsFromStructure(clsinfo,subelements, object,callback);
	}
	public MaterialCollapsible getCollapsible() {
		return contentcollapsible;
	}
}
