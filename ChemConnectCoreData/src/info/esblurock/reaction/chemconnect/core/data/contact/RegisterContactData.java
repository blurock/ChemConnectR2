package info.esblurock.reaction.chemconnect.core.data.contact;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.base.ResetDatabaseObjects;

public class RegisterContactData {
	public static void register() {
		ObjectifyService.register(Organization.class);
		ObjectifyService.register(OrganizationDescription.class);
		ObjectifyService.register(ContactInfoData.class);
		ObjectifyService.register(ContactLocationInformation.class);
		ObjectifyService.register(PersonalDescription.class);
		ObjectifyService.register(GPSLocation.class);
		ObjectifyService.register(IndividualInformation.class);
		ObjectifyService.register(NameOfPerson.class);
	}
	
	public static void reset() {
		ResetDatabaseObjects.resetClass(Organization.class);
		ResetDatabaseObjects.resetClass(OrganizationDescription.class);
		ResetDatabaseObjects.resetClass(ContactInfoData.class);
		ResetDatabaseObjects.resetClass(ContactLocationInformation.class);
		ResetDatabaseObjects.resetClass(PersonalDescription.class);
		ResetDatabaseObjects.resetClass(GPSLocation.class);
		ResetDatabaseObjects.resetClass(IndividualInformation.class);
		ResetDatabaseObjects.resetClass(NameOfPerson.class);
	}
}
