package info.esblurock.reaction.core.server.yaml;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.cache.AsyncCacheFilter;
import com.googlecode.objectify.util.Closeable;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

//import static com.piyasatakip.backend.OfyService.ofy;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by devrimtuncer on 27/03/16.
 */
public class ObjectifyServiceTest {


    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    protected Closeable session;

    @BeforeClass
    public static void setUpBeforeClass() {
        // Reset the Factory so that all translators work properly.
        ObjectifyService.setFactory(new ObjectifyFactory());
        ObjectifyService.register(Food.class);
    }

    @Before
    public void setUp() {
        this.session = ObjectifyService.begin();
        this.helper.setUp();
    }

    @After
    public void tearDown() {
        AsyncCacheFilter.complete();
        this.session.close();
        this.helper.tearDown();
    }

    @SuppressWarnings("deprecation")
	@Test
    public void doTest() {
    	try {
    		System.out.println("doTest()");
        Food food = new Food("yam");

   		System.out.println("doTest()");
   	         // 1) save food to data store
        ObjectifyService.ofy().save().entity(food).now();

   		System.out.println("doTest()");
   	        // 2) retrieve food from data store
        Food foodRetrieved = ObjectifyService.ofy().load().type(Food.class).filter("foodtype", "yam").first().now();

   		System.out.println("doTest()");
   	         assertNotNull(foodRetrieved);
    	} catch(Exception ex) {
    		ex.printStackTrace(System.out);
    	}
    }

    @Entity
    private class Food {
        @Id
        Long id;

        @Index
        String foodtype;

        public Food(String food) {
            foodtype = food;
        }
    }
}