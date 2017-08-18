package info.esblurock.reaction.core.server.authentification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;


public class SetOfAuthorizationLevels {
	static private HashMap<String,AuthorizationLevel> authorization = null;
	static private HashMap<String,String> levels = null;
	
	
	
	public String getTooltip(String level) {
		return getLevels().get(level);
	}
	
	public boolean authorize(String userlevel, String task) throws IOException {
		AuthorizationLevel tasks = getAuthorizations().get(userlevel);
		return tasks.verifiedLevel(task);
	}
	
	public ArrayList<String> getPrivledges(String level) throws IOException {
		AuthorizationLevel tasks = getAuthorizations().get(level);
		return tasks.getPrivledges();
	}
	private HashMap<String,AuthorizationLevel> getAuthorizations() throws IOException {
		if(authorization == null) {
			initAuthorization();
		}
		return authorization;
	}
	public void initAuthorization() throws IOException {
		authorization = new HashMap<String,AuthorizationLevel>();
		String[] lvls1 = {MetaDataKeywords.accessLogin, 
				MetaDataKeywords.accessQuery
				};
		AuthorizationLevel alvl1 = new AuthorizationLevel(lvls1);
		authorization.put(MetaDataKeywords.accessTypeQuery, alvl1);
		
		String[] lvls2 = {
				MetaDataKeywords.accessLogin, 
				MetaDataKeywords.accessQuery, 
				MetaDataKeywords.accessProfile, 
				MetaDataKeywords.accessPassword, 
				};
		AuthorizationLevel alvl2 = new AuthorizationLevel(lvls2);
		authorization.put(MetaDataKeywords.accessTypeStandardUser, alvl2);
		
		String[] lvls3 = {
				MetaDataKeywords.accessLogin, 
				MetaDataKeywords.accessQuery, 
				MetaDataKeywords.accessProfile, 
				MetaDataKeywords.accessPassword, 
				MetaDataKeywords.accessUserDataInput, 
				MetaDataKeywords.accessUserDataDelete
				};
		AuthorizationLevel alvl3 = new AuthorizationLevel(lvls3);
		authorization.put(MetaDataKeywords.accessTypeDataUser, alvl3);
		
		String[] lvls4 = {
				MetaDataKeywords.accessLogin, 
				MetaDataKeywords.accessQuery, 
				MetaDataKeywords.accessProfile, 
				MetaDataKeywords.accessPassword, 
				MetaDataKeywords.accessUserDataInput, 
				MetaDataKeywords.accessUserDataDelete, 
				MetaDataKeywords.accessDataInput, 
				MetaDataKeywords.accessDataDelete
				};
		AuthorizationLevel alvl4 = new AuthorizationLevel(lvls4);
		authorization.put(MetaDataKeywords.accessTypeAdministrator, alvl4);
		
		String[] lvls5 = {
				MetaDataKeywords.accessLogin, 
				MetaDataKeywords.accessQuery, 
				MetaDataKeywords.accessProfile, 
				MetaDataKeywords.accessPassword, 
				MetaDataKeywords.accessUserDataInput, 
				MetaDataKeywords.accessUserDataDelete, 
				MetaDataKeywords.accessDataInput, 
				MetaDataKeywords.accessDataDelete};
		AuthorizationLevel alvl5 = new AuthorizationLevel(lvls5);
		authorization.put(MetaDataKeywords.accessTypeSuperUser, alvl5);
		/*
		URL url = getClass().getClassLoader().getResource(AuthResource);
		String content = Resources.toString(url,Charsets.UTF_8);
		initAuthorization(content);
		*/
	}
	/*	
	 * 	public void initAuthorization(String text) {
		
		
	
		StringTokenizer tok = new StringTokenizer(text, linedelimiter);
		while(tok.hasMoreTokens()) {
			String level = tok.nextToken();
			if(tok.hasMoreTokens()) {
				String levels = tok.nextToken();
				AuthorizationLevel lvls = new AuthorizationLevel(levels);
				authorization.put(level,lvls);
			}
		}
		
	}
	*/
	private HashMap<String,String> getLevels() {
		if(levels == null) {
			initLevels();
		}
		return levels;
	}
	private void initLevels() {
		levels = new HashMap<String,String>();
		levels.put(MetaDataKeywords.accessLogin, "Ability to login");
		levels.put(MetaDataKeywords.accessQuery, "Ability to execute queries");
		levels.put(MetaDataKeywords.accessProfile, "Able to change own profile");
		levels.put(MetaDataKeywords.accessPassword, "Able to change own password");
		levels.put(MetaDataKeywords.accessUserDataInput, "Able to input data under users name");
		levels.put(MetaDataKeywords.accessUserDataDelete, "Able to delete data under users name");
		levels.put(MetaDataKeywords.accessDataInput, "Able to input data under other users name");
		levels.put(MetaDataKeywords.accessDataDelete, "Able to delete data under other users name");
		levels.put(MetaDataKeywords.accessALL, "Able to perform all operations");		
/*		
		URL url = getClass().getClassLoader().getResource(levelResource);
		String content;
		try {
			content = Resources.toString(url, Charsets.UTF_8);
			levels = new HashMap<String,String>();
			StringTokenizer tok = new StringTokenizer(content, linedelimiter);
			while(tok.hasMoreTokens()) {
				String level = tok.nextToken();
				if(tok.hasMoreTokens()) {
					String tooltip = tok.nextToken();
					levels.put(level,tooltip);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}

}
