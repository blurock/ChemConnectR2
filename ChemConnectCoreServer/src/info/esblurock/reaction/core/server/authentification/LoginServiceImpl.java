package info.esblurock.reaction.core.server.authentification;

import java.io.IOException;
import java.util.List;

import info.esblurock.reaction.chemconnect.core.common.client.async.LoginService;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.rdf.KeywordRDF;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.initialization.CreateDefaultObjectsFactory;
import info.esblurock.reaction.core.server.services.ServerBase;
import info.esblurock.reaction.core.server.services.util.ContextAndSessionUtilities;
import info.esblurock.reaction.ontology.QueryBase;

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
		System.out.println("loginGuestServer()");
		ContextAndSessionUtilities util = getUtilities();
		String name = "Guest";
		System.out.println("loginGuestServer() call getAccount");
		UserAccount account = WriteReadDatabaseObjects.getAccount(name);
		System.out.println("loginGuestServer() getAccount\n" + account);
		if(account == null) {
			System.out.println("loginGuestServer(): Create Guest");
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
			System.out.println("loginGuestServer(): Account");
			System.out.println(account.toString("Account:"));
			setUpSessionUser(account);
			verify(login, login);
		}
		UserDTO user = util.getUserInfoFromContext();
		return user;
	}
	
	public UserDTO getUserInfo() {
		System.out.println("getUserInfo() start");
		ContextAndSessionUtilities util = getUtilities();
		System.out.println("getUserInfo(): ContextAndSessionUtilities: " + util);
		UserDTO user = util.getUserInfoFromContext();
		System.out.println("getUserInfo()\n" + user);
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
		System.out.println("setUpSessionUser User:\n" + user.toString());
	}
	
	public DatabaseObjectHierarchy createNewUser(UserAccount uaccount,
			NameOfPerson person) throws IOException {
		DatabaseObjectHierarchy hierarchy = CreateDefaultObjectsFactory.createNewUser(uaccount,person);
		setUpSessionUser(uaccount);
		verify(login, login);
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
		UserAccount account = WriteReadDatabaseObjects.getAccount("Guest");
		try {
			setUpSessionUser(account);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	/**
	 * @param username
	 * @return
	 */
	public String removeUser(String username) {
		QueryBase.deleteUsingPropertyValue(UserAccount.class, "accountUserName", username);
		String ans = "SUCCESS";
		return ans;
	}

	@Override
	public UserAccount getAccount(String key) {
		UserAccount account = WriteReadDatabaseObjects.getAccount(key);
		return account;
	}
}
