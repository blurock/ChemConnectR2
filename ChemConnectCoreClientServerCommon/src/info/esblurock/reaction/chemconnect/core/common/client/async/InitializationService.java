package info.esblurock.reaction.chemconnect.core.common.client.async;

import java.io.IOException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("initialization")
public interface InitializationService extends RemoteService {
	   public static class Util
	   {
	       private static InitializationServiceAsync instance;

	       public static InitializationServiceAsync getInstance()
	       {
	           if (instance == null)
	           {
	               instance = GWT.create(InitializationService.class);
	           }
	           return instance;
	       }
	   }
	   public void initializeDatabaseObjects() throws IOException;
}
