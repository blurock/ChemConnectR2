package info.esblurock.reaction.core.server.gcs;

import static org.junit.Assert.*;

import org.junit.Test;

public class BlobIsolateFromURL {

	@Test
	public void test() {
		String url = "https://storage.googleapis.com/combustion/Blurock/ExperimentalDataSets/ExampleData/DataFileImageStructure/pressuretraces.jpg";

		String prefix = "https://storage.googleapis.com/";
		int bucketend = url.indexOf('/', prefix.length());
		String bucket = url.substring(prefix.length(), bucketend);
		String path = url.substring(bucketend + 1);
		
		System.out.println("Bucket: '" +  bucket + "'");
		System.out.println("Path  : '" +  path + "'");
		
	}

}
