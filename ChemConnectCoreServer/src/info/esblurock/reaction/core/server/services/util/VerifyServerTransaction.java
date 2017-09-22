package info.esblurock.reaction.core.server.services.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;
import info.esblurock.reaction.core.server.authentification.SetOfAuthorizationLevels;


public class VerifyServerTransaction {

	private static final Logger log = Logger.getLogger(VerifyServerTransaction.class.getName());
	private static SetOfAuthorizationLevels authorization = new SetOfAuthorizationLevels();

	public static ArrayList<String> getPrivledges(String level)
			throws IOException {
		return authorization.getPrivledges(level);
	}
	public static void verify(UserDTO user, String event, String ip,
			String sessionid, String tasktype) throws IOException {
		if (authorization.authorize(user.getUserLevel(), tasktype)) {
			log.info("Login verify: \t" + tasktype);
			log.info("event=" + event);
			log.info("ip=" + ip + "   \tUserIP= " + user.getIP());
			log.info("sessionid=" + sessionid + " \tUser session=" + user.getSessionId());
			log.info("tasktype=" + tasktype);
			/*
			if (ip.equals(user.getIP())) {
				System.out.println("Login verify 3:" + ip);
				if (user.getSessionId().equals(sessionid)) {
					System.out.println("Login verify 4:" + user.getSessionId());
					SessionEvent sessionevent = new SessionEvent(
							user.getName(), user.getIP(), event);
					System.out.println("Login verify 5:" + event);
					pm.makePersistent(sessionevent);
					System.out.println("Login verify 6: session stored");
				} else {
					String message = "Session id mismatch "
							+ user.getSessionId() + ", " + sessionid;
					System.out.println(message);
				}
			} else {
				String message = "Session IP (" + ip + ") and User IP ("
						+ user.getIP() + ") do not match";
				System.out.println(message);
				throw new Exception(message);
			}
			*/
		} else {
			String message = "Task (" + tasktype
					+ ") Authorization Failure for " + user.getName() + "("
					+ user.getUserLevel() + ")";
			log.log(Level.WARNING,message);
			throw new IOException(message);
		}
	}
}
