package info.esblurock.reaction.chemconnect.core.common.client.async;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public interface LoginServiceAsync {
	public void logout(AsyncCallback<Void> callback);

	void removeUser(String key, AsyncCallback<String> callback);

	void getAccount(String key, AsyncCallback<UserAccount> callback);

	void createNewUser(UserAccount uaccount, NameOfPerson person, AsyncCallback<DatabaseObjectHierarchy> callback);

	void loginGuestServer(AsyncCallback<UserDTO> callback);

	void getUserInfo(AsyncCallback<UserDTO> callback);
}

