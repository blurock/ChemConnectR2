package info.esblurock.reaction.core.server.gcs;

//import static org.junit.Assert.*;

import org.junit.Test;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class GCSTest {

	@Test
	public void test() {
		Storage storage = StorageOptions.getDefaultInstance().getService();
		
	}

}
