package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.base.ResetDatabaseObjects;

public class RegistrerDataset {
		public static void register() {
			ObjectifyService.register(Consortium.class);
		}
		
		public static void reset() {
			ResetDatabaseObjects.resetClass(Consortium.class);
		}	
}
