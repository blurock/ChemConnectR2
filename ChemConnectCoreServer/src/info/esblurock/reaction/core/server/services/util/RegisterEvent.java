package info.esblurock.reaction.core.server.services.util;

import java.io.IOException;
import java.util.logging.Logger;

import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;
import info.esblurock.reaction.chemconnect.core.data.transaction.SessionEvent;
import info.esblurock.reaction.core.server.db.DatabaseWriteBase;
import info.esblurock.reaction.ontology.QueryBase;

public class RegisterEvent {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(RegisterEvent.class.getName());

	static public int checkLevel0 = 0;
	static public int checkLevel1 = 1;
	static public int checkLevel2 = 2;
	static public int checkLevel3 = 3;

	private static int maxCount = 1000;
	

	static public void register(UserDTO user, String event, String eventinfo, int checklevel) throws IOException {
		if (user != null) {
			SessionEvent sessionevent = new SessionEvent(user.getName(), user.getIP(), event, eventinfo);
			if (checklevel > checkLevel0) {
				int c = QueryBase.getNextEventCount(user.getName());
				if (c > maxCount) {
					throw new IOException("Transaction count exceeds maximum: contact administrator");
				}
			}
			DatabaseWriteBase.writeDatabaseObject(sessionevent);
		} else {
			throw new IOException("NO LOGIN: User not logged in");
		}
	}
}
