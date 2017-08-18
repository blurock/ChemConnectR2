package info.esblurock.reaction.core.server.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.google.appengine.api.datastore.DatastoreTimeoutException;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.base.DateAsString;
import info.esblurock.reaction.chemconnect.core.data.rdf.KeywordRDF;
import info.esblurock.reaction.chemconnect.core.data.transaction.TransactionInfo;


/**
 * The Class StoreObject. this is the base object for the storage of objects in
 * the database There are basically two parts:
 * <ul>
 * <li>Storage of the object itself
 * <li>Storage of the RDF information
 * <ul>
 * 
 * This provides the basic operations for storage which includes the updating of
 * the {@link TransactionInfo} data.
 * 
 */
public class StoreObject {
	private static Logger log = Logger.getLogger(StoreObject.class.getName());
	final static String isAS = "isA";

	/** The persistence manager from JDO. */
	// PersistenceManager pm = PMF.get().getPersistenceManager();

	/**
	 * The type delimiter. Used to separate the 'type' code (Object or String)
	 * and the keyword/info
	 */
	public static String typeDelimiter = "#";

	/**
	 * The type delimiter. Used to separate the 'dataset' name from 'further'
	 * information needed to identify the set.
	 */
	public static String setDelimitor = "!";
	
	/** RDF predicate: The creation date. */
	public static String creationDate = "InputDate";

	/** RDF predicate: The date type. */
	public static String dateType = "Date";

	/** RDF predicate: The entered by. */
	public static String enteredBy = "EnteredBy";

	/** The object predicate. An class object key is stored in the object */
	public static String objectPredicate = "Object";

	/** The string type. String information is stored in the object */
	public static String stringType = "String";

	/** The string type. String information is stored in the object */
	public static String setType = "Set";

	public static String makeStringPredicate(String predicate) {
		return predicate + typeDelimiter + stringType;
	}
	
	/** The keyword. */
	protected String keyword;

	/** The object. */
	protected DatabaseObject object;

	/** The transaction. */
	protected TransactionInfo transaction;
	
	protected String user;
	protected String sourceCode;
	protected Date date;

	/** The store object. */
	protected boolean storeObject;

	final static public int maxStored = 3000;
	ArrayList<DatabaseObject> toBeStored;
	int rdfCount;

	public StoreObject() {
		toBeStored = new ArrayList<DatabaseObject>();
		storeObject = true;
	}
	public StoreObject(String user, String keyword, String sourceCode) {
		toBeStored = new ArrayList<DatabaseObject>();
		this.user = user;
		this.sourceCode = sourceCode;
		this.keyword = keyword;
		storeObject = true;
		date = new Date();
	}

	/**
	 * Instantiates a new store object.
	 *
	 * @param keyword
	 *            the keyword base for the RDF information
	 * @param object
	 *            the data class object
	 * @param transaction
	 *            the accumulated transaction information
	 * @param storeObject
	 *            the store object true if the object should be stored
	 */
	public StoreObject(String keyword, DatabaseObject object, TransactionInfo transaction, boolean storeObject) {
		toBeStored = new ArrayList<DatabaseObject>();
		user = transaction.getUser();
		sourceCode = transaction.getSourceCode();
		date = transaction.getCreationDate();
		start(keyword, object, transaction, storeObject);
	}

	/**
	 * Instantiates a new store object.
	 *
	 * @param keyword
	 *            the keyword base for the RDF information
	 * @param object
	 *            the data class object
	 * @param transaction
	 *            the accumulated transaction information
	 */
	public StoreObject(String keyword, DatabaseObject object, TransactionInfo transaction) {
		toBeStored = new ArrayList<DatabaseObject>();
		start(keyword, object, transaction, true);
	}

	/**
	 * Common routine for the constructors.
	 *
	 * @param keyword
	 *            the keyword base for the RDF information
	 * @param object
	 *            the data class object
	 * @param transaction
	 *            the accumulated transaction information
	 * @param storeObject
	 *            the store object true if the object should be stored
	 */
	protected void start(String keyword, DatabaseObject object, TransactionInfo transaction, boolean storeObject) {
		rdfCount = 0;
		this.keyword = keyword;
		this.object = object;
		this.transaction = transaction;
		this.storeObject = storeObject;
		storeObject();
		storeRDF();
	}

	/**
	 * Finish: to be called if all the transactions are done.
	 * 
	 * From the classes that override this method, the procedure should do
	 * operations that occur after the object has been stored (this is meant
	 * mainly when the main object has dependent objects within it).
	 * 
	 * <ul>
	 * <li>RDFs: enteredby and creationdata
	 * <li>Finalize the transaction by entering the object key and storing the
	 * {@link TransactionInfo}
	 */
	public void finish() {
		storeStringRDF(enteredBy, user);
		storeStringRDF(creationDate, DateAsString.dateAsString(date));
		flushStore();
	}

	public void isA(String objectS) {
		storeStringRDF(isAS, objectS);
	}

	/**
	 * Store if the object has not been stored yet (checking if key is null).
	 *
	 * @param object
	 *            the object
	 */
	public void store(DatabaseObject object) {
		Long key = object.getKey();
		if (key == null) {
			if (object instanceof KeywordRDF) {
				rdfCount++;
			}
			toBeStored.add(object);
			if (toBeStored.size() > maxStored) {
				flushStore();
			}
		}
	}

	public void flushStore() {
		log.info("StoreObject: flushStore(): " + toBeStored.size());
		int timeout_ms = 100;
		if (toBeStored.size() > 0) {
			while (true) {
				try {
					DatabaseWriteBase.writeListOfDatabaseObjects(toBeStored);
					break;
				} catch (DatastoreTimeoutException e) {
					try {
						Thread.currentThread();
						Thread.sleep(timeout_ms);
						timeout_ms *= 2;
						log.log(Level.WARNING,"flushStore(): DatastoreTimeoutException increase wait to: " + timeout_ms);
						if(timeout_ms > 10000) {
							log.log(Level.SEVERE,"flushStore(): DatastoreTimeoutException waiting does not seem to help ");
						}
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
			toBeStored = new ArrayList<DatabaseObject>();
		}
	}

	/**
	 * Store as RDF
	 * 
	 * The String refers to that the object is a string value
	 * 
	 * The 'global' keyword of the class is the subject The predicate and the
	 * object (description) is supplied.
	 *
	 * @param predicate
	 *            the predicate relation between subject and object
	 * @param description
	 *            the object description of the subject
	 */
	public void storeStringRDF(String predicate, String description) {
		String typepredicate = predicate + typeDelimiter + stringType;
		KeywordRDF objectrdf = new KeywordRDF(keyword, typepredicate, description, user,sourceCode);
		store(objectrdf);
	}
	/**
	 * Store as RDF
	 * 
	 * The String refers to that the object is a set of values
	 * 
	 * The 'global' keyword of the class is the subject The predicate and the
	 * object (description) is supplied.
	 *
	 * @param predicate
	 *            the predicate relation between subject and object
	 * @param description
	 *            the object description of the subject
	 */
	public void storeSetRDF(String predicate, String dataset, String further) {
		String description = dataset;
		if(further != null) {
			description += setDelimitor + further;
		}
		String typepredicate = predicate + typeDelimiter + setType;
		KeywordRDF objectrdf = new KeywordRDF(keyword, typepredicate, description, user,sourceCode);
		store(objectrdf);
	}

	/**
	 * Store object rdf.
	 *
	 * The subject is the keyword of the object The predicate is the object
	 * predicate objectPredicate (a constant of the class). The object is the
	 * key to the object
	 * 
	 * @param object
	 *            the object itself (which has already been stored in the
	 *            database and has a key
	 */
	public void storeObjectRDF(DatabaseObject object) {
		storeObjectRDF(keyword, object);
	}

	/**
	 * The subject is the keyword given The predicate is the object predicate
	 * objectPredicate (a constant of the class). The object is the key to the
	 * object
	 *
	 * @param objectkey
	 *            the Object keyword to use
	 * @param object
	 *            the object
	 */
	public void storeObjectRDF(String objectkey, DatabaseObject object) {
		Long key = object.getKey();
		String keyS = key.toString();
		String classname = object.getClass().getName();
		String typepredicate = classname + typeDelimiter + objectPredicate;
		KeywordRDF objectrdf = new KeywordRDF(objectkey, typepredicate, keyS, user,sourceCode);
		store(objectrdf);
	}

	/**
	 * Store object if storeObject is true.
	 */
	protected void storeObject() {
		if (storeObject) {
			store(object);
		}
	}

	/**
	 * Store base RDF storage. The creation date is stored
	 */
	protected void storeRDF() {
		Date date = new Date();
		storeStringRDF(creationDate, DateAsString.dateAsString(date));
	}


	public ArrayList<DatabaseObject> getToBeStored() {
		return toBeStored;
	}
	public int getRdfCount() {
		return rdfCount;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
