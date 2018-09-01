package info.esblurock.reaction.core.server.authentification;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

import com.google.cloud.PageImpl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobListOption;
import com.google.cloud.storage.Storage.BucketListOption;
import com.google.cloud.storage.StorageOptions;

import info.esblurock.reaction.chemconnect.core.common.client.async.LoginService;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.IndividualInformation;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.dataset.DatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.data.login.UnverifiedUserAccount;
import info.esblurock.reaction.chemconnect.core.data.login.UserAccountInformation;
import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.rdf.KeywordRDF;
import info.esblurock.reaction.chemconnect.core.data.transaction.EventCount;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.db.GoogleCloudStorageBase;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.db.image.UserImageServiceImpl;
import info.esblurock.reaction.core.server.initialization.CreateDefaultObjectsFactory;
import info.esblurock.reaction.core.server.mail.SendMail;
import info.esblurock.reaction.core.server.services.ServerBase;
import info.esblurock.reaction.core.server.services.util.ContextAndSessionUtilities;
import info.esblurock.reaction.io.db.QueryBase;

public class LoginServiceImpl extends ServerBase implements LoginService {
	private static final long serialVersionUID = 4456105400553118785L;

	public static int standardMaxTransitions = 1000;

	String from = "edward.blurock@gmail.com";

	public static String login = "Login";

	String admin = "Administration";
	String adminpass = "laguna";
	String level = "dataset:SuperUser";

	@Override
	public UserDTO loginServer(String name, String password) throws IOException {
		String msg1 = "loginServer: " + name + " Password: " + password;
		System.out.println(msg1);
		ContextAndSessionUtilities util = getUtilities();
		String passwd = null;
		String lvl = null;
		if (admin.equals(name)) {
			System.out.println("Login: System Administrator");
			passwd = adminpass;
			lvl = level;
			QueryBase.getNextEventCount(name);

			try {
				IndividualInformation person = (IndividualInformation) QueryBase
						.getFirstDatabaseObjectsFromSingleProperty(IndividualInformation.class.getCanonicalName(),
								"owner", "Administration");
				System.out.println("User: " + person.toString());
			} catch (IOException ex) {
				String sourceID = QueryBase.getDataSourceIdentification("Administration");
				String username = "Administration";
				String access = "Administration";
				String owner = "Administration";
				String orgname = "BlurockConsultingAB";
				String title = "Blurock Consulting AB";
				String userrole = MetaDataKeywords.accessTypeAdministrator;
				CreateDefaultObjectsFactory.createAndWriteDefaultUserOrgAndCatagories(username, userrole, access, owner, orgname,
						title, sourceID);
			}

		} else {
			System.out.println("Login: Normal user: " + name);

			UserAccountInformation account = getAccount(name);
			if (account != null) {
				passwd = account.getPassword();
				lvl = account.getUserrole();
			} else {
				System.out.println("User not found");
				throw new IOException("User not found");
			}
		}

		UserDTO user = null;
		if (passwd != null) {
			if (password.equals(passwd)) {
				System.out.println("Password matches");
				String sessionid = util.getId();
				System.out.println("sessionid= " + sessionid);
				String ip = getThreadLocalRequest().getRemoteAddr();
				System.out.println("IP=" + ip);
				String host = getThreadLocalRequest().getRemoteHost();
				System.out.println("Host=" + host);
				user = new UserDTO(name, sessionid, ip, host, lvl, standardMaxTransitions);
				addAccessKeys(user);
				user.setPrivledges(getPrivledges(lvl));
				util.setUserInfo(user);
				System.out.println("Verifying user: " + login);
				verify(login, login);
				//String directory = "upload/" + user.getName() + "/";
				String directory = "upload/";
				System.out.println("----------------------------------------------------------");
				System.out.println("Blob list: '" + directory + "'");
				System.out.println("----------------------------------------------------------");
				DatabaseObject obj = new DatabaseObject("chemconnect",user.getName(),user.getName(),"1");
				//HierarchyNode topnode = GoogleCloudStorageBase.getBlobHierarchy(obj,"chemconnect",directory);
				//System.out.println(topnode.toString("getBlobHierarchy blobs: "));
			} else {
				throw new IOException("name mismatch; " + name);
			}
		} else {
			throw new IOException("name mismatch; " + name);
		}
		return user;
	}

	private void addAccessKeys(UserDTO user) {
		String username = user.getName();
		List<KeywordRDF> lst = QueryBase.findRDF(null, "dataset:userReadAccess", username);
		for (KeywordRDF rdf : lst) {
			user.addAccess(rdf.getIdentifier());
		}
	}

	private UnverifiedUserAccount getUnverifiedAccount(String username) throws IOException {
		UnverifiedUserAccount unverified = null;
		List<DatabaseObject> lst = QueryBase.getDatabaseObjectsFromSingleProperty(UnverifiedUserAccount.class.getName(),
				"username", username);
		if (lst.size() > 0) {
			unverified = (UnverifiedUserAccount) lst.get(0);
		} else {
			throw new IOException("Account not available to be activated for " + username);
		}
		System.out.println("loginVerification: " + unverified);
		return unverified;
	}

	@Override
	public String loginVerification(String username, String key) throws IOException {
		System.out.println("loginVerification: " + username);
		System.out.println("loginVerification: " + key);
		// UnverifiedUserAccount unverified = (UnverifiedUserAccount)
		// QueryBase.getObjectById(UnverifiedUserAccount.class, key);
		// List<DatabaseObject> lst =
		// QueryBase.getDatabaseObjectsFromSingleProperty(UnverifiedUserAccount.class.getName(),"username",username);
		UnverifiedUserAccount unverified = getUnverifiedAccount(username);
		System.out.println("loginVerification: " + unverified);
		String email = unverified.getEmail();
		if (unverified.getUsername().equals(username)) {
			String password = unverified.getPassword();
			// Date creation = unverified.getCreationDate();
			String userrole = MetaDataKeywords.accessTypeStandardUser;
			DatabaseWriteBase.initializeIndividualInformation(username, password, email, userrole);
			String subject = "Welcome to MolConnect";
			String msg = "Your account has been verified<br>" + "This account is under a limited usage agreement <br>"
					+ "Have fun.<br>" + "<br>" + "Updates and instructions will be posted on the website<br>" + "<br>"
					+ "If you have any questions, don't hesitate to email me<br>" + "Regards<br>"
					+ "Edward Blurock<br>";
			SendMail send = new SendMail();
			send.sendMail(email, from, subject, msg);

		} else {
			throw new IOException("'" + username + "' does not match authorization key");
		}
		return email;
	}

	@Override
	public String firstLoginToServer(String username) throws IOException {
		UnverifiedUserAccount unverified = getUnverifiedAccount(username);
		loginServer(unverified.getUsername(), unverified.getPassword());
		QueryBase.deleteUsingPropertyValue(UnverifiedUserAccount.class, "username", username);
		return username;
	}

	@Override
	public UserDTO loginFromSessionServer() {
		ContextAndSessionUtilities util = getUtilities();
		UserDTO user = util.getUserInfoFromContext();
		return user;
	}

	@Override
	public void logout() {
		ContextAndSessionUtilities util = getUtilities();
		util.removeUser();
	}

	@Override
	public Boolean changePassword(String name, String newPassword) {
		return false;
		// change password logic
	}

	public String storeUserAccount(UserAccountInformation account) {
		UserAccountInformation userexists = getAccount(account.getIdentifier());
		String useremail = null;
		if (userexists == null) {
			userexists = getAccountFromEmail(account.getEmail());
			if (userexists == null) {

				UnverifiedUserAccount unverified = new UnverifiedUserAccount(account.getIdentifier(),
						account.getPassword(), account.getEmail());
				DatabaseWriteBase.writeDatabaseObject(unverified);

				// String host = "http://127.0.0.1:8080/";
				String host = "http://blurock-reaction.appspot.com/";
				String webappS = "ChemConnectCloud.html";
				String page = "ReactionLoginValidationPlace:validate";
				String charset = StandardCharsets.UTF_8.name();

				String id;
				try {
					String keyS = Long.toString(unverified.getKey());
					id = "id=" + URLEncoder.encode(keyS, charset);
					String name = "name=" + URLEncoder.encode(account.getIdentifier(), charset);
					String vlink = host + webappS + "?" + id + "&" + name + "#" + page;

					String message = "Thank you for registering for an account in MolConnect. "
							+ "<br>To activate your account, just follow the following link:" + "<br><a href=\"" + vlink
							+ "\">MolConnect email Validation</a>" + "<br><br>Regards" + "<br><b>MolConnect<b> at"
							+ "<br><it>Blurock Consulting AB<it>";

					System.out.println("Send Message: (" + account.getEmail() + ")\n" + message);
					SendMail mail = new SendMail();
					String subject = "MolConnect: account email validation";
					mail.sendMail(account.getEmail(), from, subject, message);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				// pm.makePersistent(account);
				EventCount count = new EventCount(account.getIdentifier());
				DatabaseWriteBase.writeEntity(count);
				useremail = account.getEmail();
			} else {
				System.out.println("ERROR: email already exists");
			}
		} else {
			System.out.println("ERROR: user name already exists");
		}
		return useremail;
	}

	public UserAccountInformation getAccount(String username) {
		UserAccountInformation account = null;

		try {
			account = (UserAccountInformation) QueryBase.getFirstDatabaseObjectsFromSingleProperty(
					UserAccountInformation.class.getCanonicalName(), "username", username);
		} catch (IOException e) {
		}
		return account;
	}

	/**
	 * @param email
	 * @return the user account
	 */
	public UserAccountInformation getAccountFromEmail(String email) {
		UserAccountInformation account = null;

		try {
			account = (UserAccountInformation) QueryBase.getFirstDatabaseObjectsFromSingleProperty(
					UserAccountInformation.class.getCanonicalName(), "email", email);
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
