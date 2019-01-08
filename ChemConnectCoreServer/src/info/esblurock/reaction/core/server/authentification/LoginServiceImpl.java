package info.esblurock.reaction.core.server.authentification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

import info.esblurock.reaction.chemconnect.core.common.client.async.LoginService;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.IndividualInformation;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.contact.PersonalDescription;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.login.UnverifiedUserAccount;
import info.esblurock.reaction.chemconnect.core.data.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.data.login.UserAccountInformation;
import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.rdf.KeywordRDF;
import info.esblurock.reaction.chemconnect.core.data.transaction.EventCount;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.db.InterpretData;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.initialization.CreateDefaultObjectsFactory;
import info.esblurock.reaction.core.server.mail.SendMail;
import info.esblurock.reaction.core.server.services.ServerBase;
import info.esblurock.reaction.core.server.services.util.ContextAndSessionUtilities;
import info.esblurock.reaction.io.db.QueryBase;

public class LoginServiceImpl extends ServerBase implements LoginService {
	private static final long serialVersionUID = 4456105400553118785L;
    //private static final String NETWORK_NAME = "G+";
    //private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/plus/v1/people/me";

	public static int standardMaxTransitions = 1000;

	String from = "edward.blurock@gmail.com";

	public static String login = "Login";

	String guest = "Guest";
	String guestpass = "laguna";
	String admin = "Administration";
	String adminpass = "laguna";
	String guestlevel = MetaDataKeywords.accessTypeQuery;
	String adminlevel = MetaDataKeywords.accessTypeSuperUser;

	@Override
	public UserDTO loginGuestServer() throws IOException {
		ContextAndSessionUtilities util = getUtilities();
		String name = "Guest";
		UserAccount account = getAccount(name);
		String lvl = guestlevel;
		if(account == null) {
			String accountUserName = name;
			String authorizationName = name;
			String authorizationType = "Default";
			String accountPrivilege = MetaDataKeywords.accessTypeQuery;
			ChemConnectDataStructure datastructure = new ChemConnectDataStructure();
			account = new UserAccount(datastructure, accountUserName, authorizationName, authorizationType, accountPrivilege);
			ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure();
			NameOfPerson person = new NameOfPerson(structure, "", "", name);
			createNewUser(account, person);
		} else {
			setUpSessionUser(account);
		}
		UserDTO user = util.getUserInfoFromContext();
		return user;
	}
	
	private void setUpSessionUser(UserAccount uaccount) throws IOException {
		String name = uaccount.getAccountUserName();
		String lvl = uaccount.getAccountPrivilege();
		ContextAndSessionUtilities util = getUtilities();
		UserDTO user = null;
		String sessionid = util.getId();
		String ip = getThreadLocalRequest().getRemoteAddr();
		String host = getThreadLocalRequest().getRemoteHost();
		user = new UserDTO(name, sessionid, ip, host, lvl, standardMaxTransitions);
		addAccessKeys(user);
		user.setPrivledges(getPrivledges(lvl));
		util.setUserInfo(user);
		verify(login, login);
	}
	
	public DatabaseObjectHierarchy createNewUser(UserAccount uaccount,
			NameOfPerson person) throws IOException {
		DatabaseObjectHierarchy hierarchy = CreateDefaultObjectsFactory.createNewUser(uaccount,person);
		setUpSessionUser(uaccount);
		return hierarchy;
	}
	
	private void addAccessKeys(UserDTO user) {
		String username = user.getName();
		List<KeywordRDF> lst = QueryBase.findRDF(null, MetaDataKeywords.userReadAccess, username);
		for (KeywordRDF rdf : lst) {
			user.addAccess(rdf.getIdentifier());
		}
	}

	@Override
	public void logout() {
		ContextAndSessionUtilities util = getUtilities();
		util.removeUser();
	}

	public UserAccount getAccount(String username) {
		UserAccount account = null;
		try {
			account = (UserAccount) QueryBase.getFirstDatabaseObjectsFromSingleProperty(
					UserAccount.class.getCanonicalName(), "username", username);
		} catch (IOException e) {
		}
		return account;
	}


	/**
	 * @param username
	 * @return
	 */
	public String removeUser(String username) {
		QueryBase.deleteUsingPropertyValue(UserAccountInformation.class, "username", username);
		String ans = "SUCCESS";
		return ans;
	}


	
}
