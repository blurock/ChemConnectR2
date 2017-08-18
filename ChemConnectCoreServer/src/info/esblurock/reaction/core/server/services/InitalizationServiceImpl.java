package info.esblurock.reaction.core.server.services;

import java.io.IOException;

import info.esblurock.reaction.core.common.client.async.InitializationService;
import info.esblurock.reaction.core.server.initialization.DatabaseInitializeBase;

@SuppressWarnings("serial")
public class InitalizationServiceImpl  extends ServerBase implements InitializationService {

	@Override
	public void initializeDatabaseObjects() throws IOException {
		String isAInitialization = "resources/experiment/isAInitialization.yaml";
		String apparatusProperties = "resources/experiment/ApparatusPropertiesInitialization.yaml";

		DatabaseInitializeBase base = new DatabaseInitializeBase();
		if (!base.alreadyRead(isAInitialization)) {
			base.readInitializationFile(isAInitialization, "yaml");
		}
		if (!base.alreadyRead(apparatusProperties)) {
			base.readInitializationFile(apparatusProperties, "yaml");
		}
	}

}
