package info.esblurock.reaction.core.server.services;

import java.io.IOException;

import info.esblurock.reaction.chemconnect.core.common.client.async.InitializationService;
import info.esblurock.reaction.core.server.initialization.InitializeDatabaseObjectImpl;

@SuppressWarnings("serial")
public class InitalizationServiceImpl  extends ServerBase implements InitializationService {

	@Override
	public void initializeDatabaseObjects() throws IOException {
		InitializeDatabaseObjectImpl.initializeDatabaseObjects();
	}

}
