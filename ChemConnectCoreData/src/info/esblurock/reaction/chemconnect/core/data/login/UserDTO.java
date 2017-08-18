package info.esblurock.reaction.chemconnect.core.data.login;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	String sessionID;
	String userName;
	String IP;
	String hostname;
	String userLevel;
	ArrayList<String> privledges;
	
	
	public UserDTO() {
	}
	
	public UserDTO(String username,String sessionid,String ip, String host, String userlevel) {
		userName = username;
		sessionID = sessionid;
		IP = ip;
		hostname = host;
		userLevel = userlevel;
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
}
