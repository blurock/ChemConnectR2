package info.esblurock.reaction.chemconnect.core.common.client.async;

import java.io.IOException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import info.esblurock.reaction.chemconnect.core.data.login.UserAccountInformation;
import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;


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

   public UserDTO loginServer(String name) throws IOException;

   public UserDTO loginFromSessionServer();
    
   public Boolean changePassword(String name, String newPassword);

   public void logout();
   
   public String storeUserAccount(UserAccountInformation account);

   public String removeUser(String key);
   
   public UserAccountInformation getAccount(String key);

String loginVerification(String username, String key) throws IOException;

String firstLoginToServer(String name) throws IOException;
}
