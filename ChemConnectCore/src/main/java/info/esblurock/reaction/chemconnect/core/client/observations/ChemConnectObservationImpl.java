package info.esblurock.reaction.chemconnect.core.client.observations;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.client.catalog.choose.ChooseFullNameFromCatagoryRow;
import info.esblurock.reaction.chemconnect.core.client.catalog.choose.ObjectVisualizationInterface;
import info.esblurock.reaction.chemconnect.core.client.pages.DataStructurePages;
import info.esblurock.reaction.chemconnect.core.client.resources.BaseText;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ChemConnectObservationView;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class ChemConnectObservationImpl extends Composite implements ChemConnectObservationView, ObjectVisualizationInterface {

	private static ChemConnectObservationImplUiBinder uiBinder = GWT.create(ChemConnectObservationImplUiBinder.class);

	interface ChemConnectObservationImplUiBinder extends UiBinder<Widget, ChemConnectObservationImpl> {
	}

	Presenter listener;
	String name;
	
	@UiField
	MaterialNavBrand navtitle;
	@UiField
	MaterialPanel toppanel;
	@UiField
	MaterialLink initialize;
	@UiField
	MaterialLink observationsdeclaration;
	@UiField
	MaterialPanel catagorysetup;
	@UiField
	MaterialPanel content;
	@UiField
	MaterialLink pagetitle;

	
	BaseText basetext = GWT.create(BaseText.class);
	ChooseFullNameFromCatagoryRow choose;
	DatabaseObject obj;
	DataCatalogID catid;

	public ChemConnectObservationImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	
	void init() {
		navtitle.setText("Observation Setup and Management");
		observationsdeclaration.setText(basetext.observations());
		refresh();

	}

	@Override
	public void setName(String helloName) {
		name = helloName;
	}
	
	@UiHandler("initialize")
	void onClickInitialize(ClickEvent event) {
		MaterialToast.fireToast("Initialize Generic Objects");
	}
	
	@UiHandler("observationsdeclaration")
	public void onObservations(ClickEvent event) {
		MaterialToast.fireToast("Observations");
		handleHistoryToken("Observations");
		}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

	private void handleHistoryToken(String token) {
		DataStructurePages page = DataStructurePages.DataStructures;
		pagetitle.setText(page.getTitle());
		if (!"".equals(token)) {
			page = DataStructurePages.valueOf(token);
			pagetitle.setText(page.getTitle());
		}
		changeNav(page);
	}

	private void changeNav(DataStructurePages page) {
		Window.scrollTo(0, 0);
		content.clear();
		Widget widget = page.getContent();
		content.add(widget);
	}

	@Override
	public void createCatalogObject(DatabaseObject obj, DataCatalogID catid) {
		MaterialToast.fireToast(catid.getFullName());
		this.obj = obj;
		this.catid = catid;
	}

	@Override
	public void insertCatalogObject(DatabaseObjectHierarchy subs) {
		
		
	}

	@Override
	public void refresh() {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(MetaDataKeywords.chemConnectObservable);
		String user = Cookies.getCookie("user");
		String object = MetaDataKeywords.observationCorrespondenceSpecification;
		choose = new ChooseFullNameFromCatagoryRow(this,user,object,choices,toppanel);
		catagorysetup.add(choose);
	}

	
}
