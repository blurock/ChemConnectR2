package info.esblurock.reaction.core.server.services.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;


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
		//UserDTO sessiondto = getUserAlreadyFromSession();
		UserDTO dto = getUserInfoFromContext();
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
	}

	public void setUserInfoInContext(UserDTO dto) {
		context.setAttribute(userattribute, dto);
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
/*
	private UserDTO getUserAlreadyFromSession() {

		UserDTO user = null;
		Object userObj = session.getAttribute("user");
		if (userObj != null && userObj instanceof UserDTO) {
			user = (UserDTO) userObj;
		}
		return user;
	}
*/
	private void storeUserInSession(UserDTO user) {
		session.setAttribute(userattribute, user);
	}

	private void deleteUserFromSession() {
		session.removeAttribute(userattribute);
	}
}
