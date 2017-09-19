package info.esblurock.reaction.chemconnect.core.common.client.async;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;

public interface LoginServiceAsync {
	public void loginServer(String name, String password,
			AsyncCallback<UserDTO> callback);

	public void loginFromSessionServer(AsyncCallback<UserDTO> callback);
	public void changePassword(String name, String newPassword,
			AsyncCallback<Boolean> callback);

	public void logout(AsyncCallback<Void> callback);

	void storeUserAccount(UserAccount account, AsyncCallback<String> callback);

	void removeUser(String key, AsyncCallback<String> callback);

	void getAccount(String key, AsyncCallback<UserAccount> callback);

	void loginVerification(String username, String key, AsyncCallback<String> callback);

	void firstLoginToServer(String name, AsyncCallback<String> callback);
}

