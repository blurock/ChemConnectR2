package info.esblurock.reaction.chemconnect.core.client.administration;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialDialogFooter;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.client.GeneralVoidReturnCallback;
import info.esblurock.reaction.chemconnect.core.client.pages.DataStructurePages;
import info.esblurock.reaction.chemconnect.core.client.place.DeviceWithSubystemsDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.client.resources.BaseText;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ChemConnectAdministrationView;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.common.client.async.InitializationService;
import info.esblurock.reaction.chemconnect.core.common.client.async.InitializationServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
public class ChemConnectAdministrationImpl extends Composite implements ChemConnectAdministrationView {

	private static ChemConnectAdministrationImplUiBinder uiBinder = GWT
			.create(ChemConnectAdministrationImplUiBinder.class);

	interface ChemConnectAdministrationImplUiBinder extends UiBinder<Widget, ChemConnectAdministrationImpl> {
	}

	BaseText basetext = GWT.create(BaseText.class);
	
	Presenter listener;
	String name;
	@UiField
	MaterialNavBrand navtitle;
	@UiField
	MaterialLink interconnect;
	@UiField
	MaterialLink devicedeclaration;
	@UiField
	MaterialLink observationsdeclaration;
	@UiField
	MaterialLink methodologydeclaration;
	@UiField
	MaterialLink managecatalog;
	@UiField
	MaterialLink blobstorage;
	@UiField
	MaterialLink block;
	@UiField
	MaterialLink cleardb;
	@UiField
	MaterialLink readinit;
	@UiField
	MaterialLink users;
	@UiField
	MaterialLink organizations;
	@UiField
	MaterialLink people;
	@UiField
	MaterialLink pagetitle;
	@UiField
	MaterialTitle title;
	String modaltask;
	@UiField
	MaterialLink ok;
	@UiField
	MaterialLink close;
	@UiField
	MaterialDialog okmodal;
	@UiField
	MaterialDialogFooter footer;
	@UiField
	MaterialPanel content;
	
	public ChemConnectAdministrationImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void  init() {
		
		navtitle.setText(basetext.administrationpage());
		interconnect.setIconType(IconType.DEVICE_HUB);
		interconnect.setText("Concept Hierarchy");
		cleardb.setText(basetext.cleardatabase());
		cleardb.setIconType(IconType.AUTORENEW);
		readinit.setText(basetext.readinitailize());
		readinit.setIconType(IconType.FILE_DOWNLOAD);
		users.setText(basetext.useraccounts());
		users.setIconType(IconType.PEOPLE);
		organizations.setText(basetext.Organizations());
		organizations.setIconType(IconType.PLACE);
		devicedeclaration.setText(basetext.devicedefinition());
		devicedeclaration.setIconType(IconType.BUILD);
		observationsdeclaration.setText(basetext.observations());
		observationsdeclaration.setIconType(IconType.BUILD);
		methodologydeclaration.setText(basetext.methodology());
		methodologydeclaration.setIconType(IconType.BUILD);
		managecatalog.setText("Manage Catalog");
		managecatalog.setIconType(IconType.BUILD);
		blobstorage.setText("File Staging");
		blobstorage.setIconType(IconType.BUILD);
		block.setText("Matrix Block");
		block.setIconType(IconType.BUILD);
		okmodal.setBackgroundColor(Color.BLUE_GREY_DARKEN_1);
		footer.setBackgroundColor(Color.BLUE_GREY);
		footer.setTextColor(Color.BLACK);
		ok.setText("OK");
		close.setIconType(IconType.CLOSE);
		people.setText("People");
		people.setIconType(IconType.BUILD);
		
		/*
		SampleForcedGraph sample = new SampleForcedGraph(this);
		GraphNodesWithForces graphDS = new GraphNodesWithForces(sample);
		content.add(graphDS);
		graphDS.start();
		*/
		/*
		RadialReingoldTilfordTree reingold = new RadialReingoldTilfordTree();
		content.add(reingold);
		reingold.start();
		*/
		/*
		HorizontalHierarchy cluster = new HorizontalHierarchy();
		content.add(cluster);
		cluster.start();
		*/
		/*
		FocusAndContext context = new FocusAndContext();
		content.add(context);
		context.start();
		*/
		/*
		BarChart bar = new BarChart();
		Window.alert("Bar started 2");
		Button but = new Button("Press me");
		content.add(but);
		content.add(bar);
		Window.alert("Bar started 3");
		bar.start();
		Window.alert("Bar started 4");
	*/
		
	}
	
	public void nodeClicked(String nodename) {
		navtitle.setText("Clicked" + nodename);
	}
	public void nodeMouseOver(String nodename) {
		navtitle.setText("Mouseover: " + nodename);
	}
	
	@UiHandler("interconnect")
	public void onInterconnect(ClickEvent event) {
		handleHistoryToken("GraphicalStructures");
	}
	
	@UiHandler("devicedeclaration")
	public void onDevicedDclaration(ClickEvent event) {
	listener.goTo(new DeviceWithSubystemsDefinitionPlace("Device"));
	}
	
	@UiHandler("methodologydeclaration")
	public void onProtocol(ClickEvent event) {
		MaterialToast.fireToast("Protocol");
		handleHistoryToken("Protocol");
		}
	
	@UiHandler("observationsdeclaration")
	public void onObservations(ClickEvent event) {
		MaterialToast.fireToast("Observations");
		handleHistoryToken("Observations");
		}
	
	@UiHandler("managecatalog")
	public void onManageCatalog(ClickEvent event) {
		MaterialToast.fireToast("ManageCatalog");
		handleHistoryToken("ManageCatalog");
		}

	@UiHandler("blobstorage")
	public void onBlobStorage(ClickEvent event) {
		handleHistoryToken("BlobStorage");
		}

	
	@UiHandler("cleardb")
		public void onClearDB(ClickEvent event) {
		title.setTitle(basetext.cleardatabase());
		title.setDescription(basetext.suretozero());
		modaltask = "cleardb";
		okmodal.open();
	}
	@UiHandler("readinit")
	public void onReadInit(ClickEvent event) {
		title.setTitle(basetext.readinitailize());
		title.setDescription(basetext.suretozero());
		modaltask = "readinit";
		okmodal.open();
	}
	@UiHandler("ok")
	public void onOK(ClickEvent event) {
		if(modaltask.compareTo("cleardb") == 0) {
			MaterialToast.fireToast("Clear");
			InitializationServiceAsync async = GWT.create(InitializationService.class);
			GeneralVoidReturnCallback callback = new GeneralVoidReturnCallback("Clearing of database Successful");
			async.clearDatabaseObjects(callback);
		} else if(modaltask.compareTo("readinit") == 0) {
			MaterialToast.fireToast("Read init");
			InitializationServiceAsync async = InitializationService.Util.getInstance();
			GeneralVoidReturnCallback callback = new GeneralVoidReturnCallback("Initialization Successful");
			async.initializeDatabaseObjects(callback);		
		}
		okmodal.close();
	}
	@UiHandler("close")
	public void onClose(ClickEvent event) {
		okmodal.close();
	}
	@UiHandler("users")
	public void onUsers(ClickEvent event) {
		title.setTitle(basetext.cleardatabase());
		title.setDescription(basetext.suretozero());
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		ListOfUsersCallback callback = new ListOfUsersCallback();
		async.getListOfUsers(callback);
	}
	@UiHandler("organizations")
	public void onOrganizations(ClickEvent event) {
		MaterialToast.fireToast("Organizations");
		handleHistoryToken("Organization");
	}
	@UiHandler("people")
	public void onPeople(ClickEvent event) {
		MaterialToast.fireToast("People");
		handleHistoryToken("DatabasePerson");
	}
	@UiHandler("block")
	public void onBlock(ClickEvent event) {
		MaterialToast.fireToast("Isolate Matrix Block");
		handleHistoryToken("IsolateMatrixBlock");
	}
	
	@Override
	public void setName(String titleName) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

	public void addMainStructures(ArrayList<ClassificationInformation> clslst) {
		for(ClassificationInformation info: clslst) {
			MaterialToast.fireToast(info.toString());
		}
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

	public MaterialPanel getContent() {
		return content;
	}
	
	
	
}
