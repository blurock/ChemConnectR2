package info.esblurock.reaction.chemconnect.core.client.catalog.protocol;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.catalog.SetUpDatabaseObjectHierarchyCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.catalog.choose.ChooseFullNameFromCatagoryRow;
import info.esblurock.reaction.chemconnect.core.client.catalog.choose.ObjectVisualizationInterface;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ProtocolDefinitionView;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.transfer.ProtocolSetupTransfer;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class ProtocolDefinition extends Composite implements ObjectVisualizationInterface, ProtocolDefinitionView {

	private static ProtocolDefinitionUiBinder uiBinder = GWT.create(ProtocolDefinitionUiBinder.class);

	interface ProtocolDefinitionUiBinder extends UiBinder<Widget, ProtocolDefinition> {
	}

	Presenter listener;
	
	@UiField
	MaterialCollapsible contentcollapsible;
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialPanel topPanel;
	
	ChooseFullNameFromCatagoryRow choose;
	ProtocolSetupTransfer transfer;

	public ProtocolDefinition() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public ProtocolDefinition(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	
	private void init() {
		refresh();
	}

	@Override
	public void insertCatalogObject(DatabaseObjectHierarchy subs) {
		StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(null, subs,modalpanel);
		contentcollapsible.add(item);
	}

	@Override
	public void createCatalogObject(DatabaseObject obj, DataCatalogID catid) {
		String protocolS = choose.getObjectType();
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		SetUpDatabaseObjectHierarchyCallback callback = new SetUpDatabaseObjectHierarchyCallback(contentcollapsible,modalpanel);
		String title = "Protocol: " + protocolS;
		async.getInitialProtocol(obj,title,catid,callback);
		
	}
	@Override
	public void setName(String titleName) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

	@Override
	public void refresh() {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(MetaDataKeywords.dataTypeProtocol);
		String user = Cookies.getCookie("user");
		String object = MetaDataKeywords.protocol;
		choose = new ChooseFullNameFromCatagoryRow(this,user,object,choices,modalpanel);
		topPanel.clear();
		topPanel.add(choose);
	}


}
