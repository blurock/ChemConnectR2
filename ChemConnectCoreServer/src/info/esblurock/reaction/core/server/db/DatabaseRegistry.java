package info.esblurock.reaction.core.server.db;

import javax.servlet.ServletContextListener;

import info.esblurock.reaction.chemconnect.core.data.contact.RegisterContactData;
import info.esblurock.reaction.chemconnect.core.data.description.RegisterDescriptionData;
import info.esblurock.reaction.chemconnect.core.data.initialization.RegisterInitializationData;

import javax.servlet.ServletContextEvent;

public class DatabaseRegistry  implements ServletContextListener  {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		RegisterContactData.register();
		RegisterDescriptionData.register();
		RegisterInitializationData.register();
	}

}
