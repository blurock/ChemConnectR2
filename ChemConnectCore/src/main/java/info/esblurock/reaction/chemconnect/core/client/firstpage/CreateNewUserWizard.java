package info.esblurock.reaction.chemconnect.core.client.firstpage;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTitle;
import info.esblurock.reaction.chemconnect.core.client.TopChemConnectPanel;
import info.esblurock.reaction.chemconnect.core.client.modal.OKAnswerInterface;
import info.esblurock.reaction.chemconnect.core.client.modal.OKModal;
import info.esblurock.reaction.chemconnect.core.common.client.async.LoginService;
import info.esblurock.reaction.chemconnect.core.common.client.async.LoginServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;

public class CreateNewUserWizard extends Composite implements OKAnswerInterface {

	private static CreateNewUserWizardUiBinder uiBinder = GWT.create(CreateNewUserWizardUiBinder.class);

	interface CreateNewUserWizardUiBinder extends UiBinder<Widget, CreateNewUserWizard> {
	}

	@UiField
	MaterialTextBox accountname;
	@UiField
	MaterialButton submit;
	@UiField
	MaterialTextBox persontitle;
	@UiField
	MaterialTextBox personfirstname;
	@UiField
	MaterialTextBox personlastname;
	@UiField
	MaterialTitle title;
	@UiField
	MaterialLabel titletext;
	
	String authID;
	String authSource;
	DataCatalogID datid;
	NameOfPerson person;
	UserAccount useraccount;
	
	MaterialCollapsible collapsiblePanel;
	MaterialPanel modalpanel;
	MaterialPanel mainPanel;
	TopChemConnectPanel toppanel;
	
	public CreateNewUserWizard(TopChemConnectPanel toppanel, MaterialPanel mainPanel, MaterialCollapsible collapsiblePanel,MaterialPanel modalpanel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.toppanel = toppanel;
		this.collapsiblePanel = collapsiblePanel;
		this.modalpanel = modalpanel;
		this.mainPanel = mainPanel;
		init();
		fromCookies();
	}

	private void init() {
		title.setTitle("New User Wizard");
		accountname.setLabel("Account Name");
		persontitle.setLabel("Title");
		personfirstname.setLabel("First Name");
		personlastname.setLabel("Last Name");
		submit.setText("Create new user");
		
		String titletextS = "Fill out this form for the minimal information for a new user";
		titletext.setText(titletextS);
		
		accountname.setPlaceholder("");
		persontitle.setPlaceholder("");
		personfirstname.setPlaceholder("");
		personlastname.setPlaceholder("");
}
	private void fromCookies() {
		String given_nameS = Cookies.getCookie("given_name");
		personfirstname.setText(given_nameS);
		String family_nameS = Cookies.getCookie("family_name");
		personlastname.setText(family_nameS);
		authID = Cookies.getCookie("auth_id");
		String account = Cookies.getCookie("account_name");
		Cookies.setCookie("user", account);
		accountname.setText(account);
		authSource = Cookies.getCookie("authorizationType");
	}
	
	@UiHandler("submit")
	public void submitClick(ClickEvent event) {
		String titleS = "";
		String givenNameS = personfirstname.getValue();
		String familyNameS = personlastname.getValue();
		String account = accountname.getValue();
		ChemConnectCompoundDataStructure datastructure = new ChemConnectCompoundDataStructure(account,null);
		person = new NameOfPerson(datastructure,titleS,givenNameS,familyNameS);
		String accountPrivilege = MetaDataKeywords.accessTypeDataUser;
		ChemConnectDataStructure structure = new ChemConnectDataStructure();
		useraccount = new UserAccount(structure, accountname.getText(), authID, authSource, accountPrivilege);
		String text = "Create user '" + accountname.getText() + "' for " + givenNameS + " " + familyNameS;
		String oktext = "Create New User";
		OKModal okpanel = new OKModal("Create", text, oktext, this);
		modalpanel.clear();
		modalpanel.add(okpanel);
		okpanel.openModal();
	}

	@Override
	public void answeredOK(String answer) {
		Cookies.setCookie("user", useraccount.getAccountUserName());
		toppanel.setInUser();
		mainPanel.clear();
		LoginServiceAsync async = LoginService.Util.getInstance();
		collapsiblePanel.clear();
		SetUpAfterUserCreation callback = new SetUpAfterUserCreation(toppanel,collapsiblePanel,modalpanel);
		async.createNewUser(useraccount, person,callback);
	}

}
