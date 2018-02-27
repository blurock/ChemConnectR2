package info.esblurock.reaction.chemconnect.core.data.image;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.base.ResetDatabaseObjects;

public class RegisterImageInformation {
	public static void register() {
		ObjectifyService.register(UploadedImage.class);
		ObjectifyService.register(ImageUploadTransaction.class);
	}
	public static void reset() {
		ResetDatabaseObjects.resetClass(UploadedImage.class);
		ResetDatabaseObjects.resetClass(ImageUploadTransaction.class);
	}

}
