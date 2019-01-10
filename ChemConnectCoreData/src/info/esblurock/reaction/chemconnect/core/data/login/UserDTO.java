package info.esblurock.reaction.chemconnect.core.data.login;

import java.io.Serializable;
import java.util.ArrayList;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	String sessionID;
	String userName;
	String IP;
	String hostname;
	String userLevel;
	ArrayList<String> privledges;
	ArrayList<String> access;
	int maximumTransactions;
	
	
	public UserDTO() {
	}
	
	public UserDTO(String username,String sessionid,String ip, String host, String userlevel, int maximumTransactions) {
		userName = username;
		sessionID = sessionid;
		IP = ip;
		hostname = host;
		userLevel = userlevel;
		this.maximumTransactions = maximumTransactions;
		access = new ArrayList<String>();
	}
	
	public boolean getLoggedIn() {
		return true;
	}

	public String getName() {
		return userName;
	}

	public String getSessionId() {
		return sessionID;
	}
	public void setSessionId(String sessionid) {
		sessionID = sessionid;
	}

	public String getIP() {
		return IP;
	}

	public String getHostname() {
		return hostname;
	}

	public String getUserLevel() {
		return userLevel;
	}
	public boolean checkLevel(String task) {
		int index = privledges.indexOf(task);
		boolean ans = false;
		if(index >= 0) ans = true;
		return ans;
	}
	public void setPrivledges(ArrayList<String> privledges) {
		this.privledges = privledges;
	}
	
	public ArrayList<String> getPrivledges() {
		return privledges;
	}

	public boolean underMaximumTransactions(int count) {
		return count < maximumTransactions;
	}
	public void addAccess(String accesskey) {
		access.add(accesskey);
	}
	
	public ArrayList<String> getAccess() {
		return access;
	}

	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append(userName);
		build.append(": (");
		build.append(sessionID);
		build.append(", ");
		build.append(hostname);
		build.append(", ");
		build.append(IP);
		build.append(")  ");
		build.append(userLevel);
		build.append("\n"); 
		build.append(privledges);
		build.append("\n"); 
		build.append(access);
		build.append("\n"); 
		
		return build.toString();
	}
}
