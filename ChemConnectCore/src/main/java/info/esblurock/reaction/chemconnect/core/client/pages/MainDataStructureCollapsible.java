package info.esblurock.reaction.chemconnect.core.client.pages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.chemconnect.core.client.administration.GetMainStructureSubElementsCallback;
import info.esblurock.reaction.chemconnect.core.client.administration.ListOfMainDataObjectCallback;
import info.esblurock.reaction.chemconnect.core.client.cards.CardModal;
import info.esblurock.reaction.chemconnect.core.client.cards.ClassificationInformationCard;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;

public class MainDataStructureCollapsible extends Composite {

	private static MainDataStructureCollapsibleUiBinder uiBinder = GWT
			.create(MainDataStructureCollapsibleUiBinder.class);

	interface MainDataStructureCollapsibleUiBinder extends UiBinder<Widget, MainDataStructureCollapsible> {
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
	
	public MainDataStructureCollapsible() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	public MainDataStructureCollapsible(ClassificationInformation info) {
		initWidget(uiBinder.createAndBindUi(this));
		clsinfo = info;
		title.setText(info.getDataType());
		init(clsinfo.getIdName());
	}
	public MainDataStructureCollapsible(String idName) {
		initWidget(uiBinder.createAndBindUi(this));
		clsinfo = null;
		title.setText(idName);
		init(idName);
	}
	private void init(String name) {
		card = new CardModal();
		body.add(card);
		expand.setIconType(IconType.ARROW_DOWNWARD);
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		GetMainStructureSubElementsCallback callback = new GetMainStructureSubElementsCallback(this);
		async.getChemConnectCompoundDataStructure(name,callback);
	}
	@UiHandler("info")
	public void onClick(ClickEvent event) {
		ClassificationInformationCard infocard = new ClassificationInformationCard(clsinfo);
		card.setContent(infocard, false);
		card.open();
	}
	@UiHandler("expand")
	public void onExpand(ClickEvent event) {
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		ListOfMainDataObjectCallback callback = new ListOfMainDataObjectCallback(this);
		async.getMainObjects(clsinfo, callback);
	}
	public void setStructureSubElements(ChemConnectCompoundDataStructure subelements) {
		this.subelements = subelements;
	}
	public ClassificationInformation getClsinfo() {
		return clsinfo;
	}
	public ChemConnectCompoundDataStructure getSubelements() {
		return subelements;
	}
	public void addObjectCollapsible(MainDataStructureInstanceCollapsible collapsible) {
		contentcollapsible.add(collapsible);
	}

}
