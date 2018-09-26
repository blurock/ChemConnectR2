package info.esblurock.reaction.core.server.initialization;

//import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cache.AsyncCacheFilter;
import com.googlecode.objectify.util.Closeable;

import info.esblurock.reaction.chemconnect.core.data.transaction.RegisterTransactionData;
import info.esblurock.reaction.io.db.QueryBase;

public class SourceIDTest {
	protected Closeable session;
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@BeforeClass
	public static void setUpBeforeClass() {
		// Reset the Factory so that all translators work properly.
		ObjectifyService.setFactory(new ObjectifyFactory());
	}

	@Before
	public void setUp() {
		this.session = ObjectifyService.begin();
		RegisterTransactionData.register();
		System.out.println("Classes Registered");
		this.helper.setUp();
	}

	@After
	public void tearDown() {
		AsyncCacheFilter.complete();
		this.session.close();
		this.helper.tearDown();
	}

	@Test
	public void test() {
		String username = "Administration";
		String id = QueryBase.getDataSourceIdentification(username);
		System.out.println(id);
		String id1 = QueryBase.getDataSourceIdentification(username);
		System.out.println(id1);
		String id2 = QueryBase.getDataSourceIdentification(username);
		System.out.println(id2);
		String id3 = QueryBase.getDataSourceIdentification(username);
		System.out.println(id3);
	}

}
