package info.esblurock.reaction.core.server.authentification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class AuthorizationLevel extends  ArrayList<String> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String all = "ALL";
	AuthorizationLevel(ArrayList<String> levels) {
		for(String level : levels) {
			this.add(level);
		}
	}
	AuthorizationLevel(String[] levels) {
		for(String level : levels) {
			this.add(level);
		}
	}
	AuthorizationLevel(String levels) {
		parse(levels," ");
	}
	AuthorizationLevel(String levels, String delimeter) {
		parse(levels,delimeter);
	}
	void parse(String levels, String delimeter) {
		StringTokenizer tok = new StringTokenizer(levels, delimeter);
		while(tok.hasMoreTokens()) {
			this.add(tok.nextToken());
		}
	}
	
	public boolean verifiedLevel(String level) {
		
			return this.contains(level) || this.contains(all);
	}
	public ArrayList<String> getPrivledges() {
		ArrayList<String> lst = new ArrayList<String>(this);
		return lst;
	}
}
