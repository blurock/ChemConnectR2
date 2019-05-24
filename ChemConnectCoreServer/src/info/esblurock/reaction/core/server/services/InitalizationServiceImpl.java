package info.esblurock.reaction.core.server.services;

import java.io.IOException;

import info.esblurock.reaction.chemconnect.core.common.client.async.InitializationService;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.register.ResetDatabaseObjects;
import info.esblurock.reaction.core.server.db.image.BlobKeyCorrespondence;
import info.esblurock.reaction.core.server.initialization.InitializeFromGCSURL;
import info.esblurock.reaction.core.server.services.util.RegisterEvent;

@SuppressWarnings("serial")
public class InitalizationServiceImpl  extends ServerBase implements InitializationService {

	@Override
	public void initializeDatabaseObjects() throws IOException {
		verify("Read in initialization files", MetaDataKeywords.accessDataInput);
		InitializeFromGCSURL.initialize();
		register("Initialize", "Read in initialization files", RegisterEvent.checkLevel3);
	}
	public void clearDatabaseObjects() throws IOException {
		verify("Clear database objects", MetaDataKeywords.accessDataDelete);
		ResetDatabaseObjects.clearDatabase();
		ResetDatabaseObjects.resetClass(BlobKeyCorrespondence.class);
		register("Initialize", "Clear database objects", RegisterEvent.checkLevel3);
	}
}
