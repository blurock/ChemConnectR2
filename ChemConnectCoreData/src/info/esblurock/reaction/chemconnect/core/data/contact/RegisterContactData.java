package info.esblurock.reaction.chemconnect.core.data.contact;

import com.googlecode.objectify.ObjectifyService;

public class RegisterContactData {
	public static void register() {
		ObjectifyService.register(ContactInfoData.class);
		ObjectifyService.register(ContactLocationInformation.class);
		ObjectifyService.register(PersonalDescription.class);
	}
}
