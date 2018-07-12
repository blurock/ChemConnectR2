package info.esblurock.reaction.chemconnect.core.client.device;

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
import info.esblurock.reaction.chemconnect.core.client.catalog.choose.ChooseFullNameFromCatagoryRow;
import info.esblurock.reaction.chemconnect.core.client.catalog.choose.ObjectVisualizationInterface;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class DeviceWithSubystemsDefinition extends Composite 
	implements  ObjectVisualizationInterface
	{

	private static DeviceWithSubystemsDefinitionUiBinder uiBinder = GWT
			.create(DeviceWithSubystemsDefinitionUiBinder.class);

	interface DeviceWithSubystemsDefinitionUiBinder extends UiBinder<Widget, DeviceWithSubystemsDefinition> {
	}

	String enterkeyS;
	String keynameS;

	@UiField
	MaterialCollapsible contentcollapsible;
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialPanel topPanel;

	ChooseFullNameFromCatagoryRow choose;
	
	public DeviceWithSubystemsDefinition() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	private void init() {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("dataset:DataTypeDevice");
		choices.add("dataset:DataTypeSubSystem");
		choices.add("dataset:DataTypeComponent");
		String user = Cookies.getCookie("user");
		String object = "dataset:SubSystemDescription";
		choose = new ChooseFullNameFromCatagoryRow(this,user,object,choices,modalpanel);
		topPanel.add(choose);
	}
	public void createCatalogObject(DatabaseObject obj,DataCatalogID datid) {
		SetUpDatabaseObjectHierarchyCallback callback = new SetUpDatabaseObjectHierarchyCallback(contentcollapsible,modalpanel);
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		String deviceType = choose.getObjectType();
		async.getDevice(obj,deviceType,datid,callback);
	}
	@Override
	public void insertCatalogObject(DatabaseObjectHierarchy subs) {
		StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(subs,modalpanel);
		contentcollapsible.add(item);
		
		
	}


}
