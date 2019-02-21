package info.esblurock.reaction.core.server.services.util;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import info.esblurock.reaction.chemconnect.core.data.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;


public class ContextAndSessionUtilities {
	public static String userattribute = "user";

	HttpSession session;
	ServletContext context;

	public ContextAndSessionUtilities(
			ServletContext context,
			HttpSession session) {
		this.context = context;
		this.session = session;
	}
	
	public String getId() {
		return session.getId();
	}
	public void setUserInfo(UserDTO dto) {
		setUserInfoInContext(dto);
		storeUserInSession(dto);
	}

	public UserDTO getUserInfo() {
		UserDTO dto = getUserInfoFromSession();
		return dto;
	}
	public String getUserName() {
		UserDTO dto = getUserInfo();
		String name = null;
		if(dto != null) {
			name = dto.getName();
		}
		return name;
	}
	public void removeUser() {
		deleteUserFromSession();
		removeUserFromContext();
		
	    if (session != null) {
		      session.invalidate();
		}
	}

	public void setUserInfoInContext(UserDTO dto) {
		Object obj = context.getAttribute(userattribute);
		if(obj == null) {
			context.setAttribute(userattribute, dto);
		}
	}
	public String getUserNameFromContext() {
		UserDTO user = getUserInfo();
		return user.getName();
	}

	public UserDTO getUserInfoFromContext() {
		UserDTO user = null;
		Object obj = context.getAttribute(userattribute);
		if (obj != null && obj instanceof UserDTO) {
			user = (UserDTO) obj;
		}
		return user;
	}
	public void removeUserFromContext() {
		context.removeAttribute(userattribute);
	}

	private UserDTO getUserInfoFromSession() {
		UserDTO user = null;
		Object userObj = session.getAttribute(userattribute);
		if (userObj != null && userObj instanceof UserDTO) {
			user = (UserDTO) userObj;
		} else {
		}
		return user;
	}
	
	private void storeUserInSession(UserDTO user) {
		Object userObj = session.getAttribute(userattribute);
		if(userObj == null) {
			session.setAttribute(userattribute, user);
		}
	}

	public void deleteUserFromSession() {
		session.removeAttribute(userattribute);
	}
}
