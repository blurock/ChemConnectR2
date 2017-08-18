package info.esblurock.reaction.chemconnect.core.data.description;

import com.googlecode.objectify.ObjectifyService;

public class RegisterDescriptionData {
	public static void register() {
		ObjectifyService.register(DataSetReference.class);
		ObjectifyService.register(DescriptionDataData.class);
	}
}
