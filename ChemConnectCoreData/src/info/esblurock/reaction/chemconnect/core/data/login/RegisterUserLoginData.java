package info.esblurock.reaction.chemconnect.core.data.login;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.base.ResetDatabaseObjects;

public class RegisterUserLoginData {
	public static void register() {
		ObjectifyService.register(UserAccount.class);
		ObjectifyService.register(UserAccountInformation.class);
		ObjectifyService.register(UnverifiedUserAccount.class);
	}
	
	public static void reset() {
		ResetDatabaseObjects.resetClass(UserAccount.class);
		ResetDatabaseObjects.resetClass(UserAccountInformation.class);
		ResetDatabaseObjects.resetClass(UnverifiedUserAccount.class);
	}
}
