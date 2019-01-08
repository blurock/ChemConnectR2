package info.esblurock.reaction.chemconnect.core.common.client.async;

import java.io.IOException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.data.login.UserAccountInformation;
import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;


@RemoteServiceRelativePath("loginservice")
public interface LoginService extends RemoteService {

    /* Utility class for simplifying access to the instance of async service.
    */
   public static class Util
   {
       private static LoginServiceAsync instance;

       public static LoginServiceAsync getInstance()
       {
           if (instance == null)
           {
               instance = GWT.create(LoginService.class);
           }
           return instance;
       }
   }

   public UserDTO loginGuestServer() throws IOException;

   public void logout();
   
   public String removeUser(String key);
   
   public UserAccount getAccount(String key);

String googleAuthorization();
DatabaseObjectHierarchy createNewUser(UserAccount uaccount,
		NameOfPerson person) throws IOException;
}
