package info.esblurock.reaction.chemconnect.core.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.Cookies;

import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;

public class SetUpUserCookies {
	public static void setupDefaultGuestUserCookies() {
		String username = "Guest";
		String sessionid = "";
		String ip = "";
		String host = "";
		String accountPrivilege = MetaDataKeywords.accessTypeQuery;
		int maximumTransactions = 10000000;
		UserDTO guest = new UserDTO(username,sessionid,ip,host,accountPrivilege,maximumTransactions);
		ArrayList<String> privs = new ArrayList<String>();
		privs.add(MetaDataKeywords.accessLogin);
		privs.add(MetaDataKeywords.accessQuery);
		guest.setPrivledges(privs);
		setDefaultCookie("account_name", "Guest");
		setup(guest);
	}
	
	
	public static void setup(UserDTO result) {
		String sessionID = result.getSessionId();
		final long DURATION = 1000 * 60 * 60;
		Date expires = new Date(System.currentTimeMillis()
				+ DURATION);
		Cookies.setCookie("sid", sessionID, expires, null,
				"/", false);
		Cookies.setCookie("user", result.getName(),
				expires, null, "/", false);
		Cookies.setCookie("level", result.getUserLevel(),
				expires, null, "/", false);
		
		ArrayList<String> lst = result.getPrivledges();
		setCookie(MetaDataKeywords.accessQuery,lst);
		setCookie(MetaDataKeywords.accessUserDataInput,lst);
		setCookie(MetaDataKeywords.accessUserDataDelete,lst);
		setCookie(MetaDataKeywords.accessDataInput,lst);
		setCookie(MetaDataKeywords.accessDataDelete,lst);
		MaterialToast.fireToast("Welcome: " + result.getName() + "(" + result.getHostname() + ")");
	}

	public static void setDefaultCookie(String name, String value) {
		final long DURATION = 1000 * 60 * 60;
		Date expires = new Date(System.currentTimeMillis()
				+ DURATION);
		Cookies.setCookie(name, value,
				expires, null, "/", false);		
	}
	
	public static void setCookie(String access, ArrayList<String> accesslist) {
		final long DURATION = 1000 * 60 * 60;
		Date expires = new Date(System.currentTimeMillis()
				+ DURATION);
		String ansB = Boolean.FALSE.toString();
		if(accesslist.contains(access)) {
			ansB = Boolean.TRUE.toString();
		}
		Cookies.setCookie(access, ansB, expires, null, "/", false);
	}

	public static void removeCookie(String name) {
		Cookies.removeCookie(name);
	}
	
	public static void zeroAllCookies() {
		removeCookie("account_name");
		removeCookie("family_name");
		removeCookie("given_name");
		removeCookie("user");
		removeCookie("auth_id");
		removeCookie("authorizationType");
		removeCookie("hasAccount");
		removeCookie("sid");
		removeCookie("level");
		removeCookie(MetaDataKeywords.accessUserDataInput);
		removeCookie(MetaDataKeywords.accessUserDataDelete);
		removeCookie(MetaDataKeywords.accessDataInput);
		removeCookie(MetaDataKeywords.accessDataDelete);
		/*
		Collection<String> names = Cookies.getCookieNames();
		for(String name: names) {
			Cookies.removeCookie(name);
		}
		*/
	}
	
	
}
