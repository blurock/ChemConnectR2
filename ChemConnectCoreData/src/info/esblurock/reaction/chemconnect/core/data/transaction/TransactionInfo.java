package info.esblurock.reaction.chemconnect.core.data.transaction;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;


// TODO: Auto-generated Javadoc
/**
 * The Class TransactionInfo. This is the set of {@link KeywordRDF}.
 * If the number of {@link KeywordRDF} exceeds maxSize, 
 * 
 * The array of {@link KeywordRDF} elements are labels as persistent dependent
 * so that they all are stored with the {@link TransactionInfo} is made persistent 
 * (in the finish function of the {@link StoreObject} finish function
 */
@SuppressWarnings("serial")
@Entity
public class TransactionInfo extends DatabaseObject {

	/** The stored object key. */
	@Index
	Long storedObjectKey;
	    
    /** The transaction object class name (as a string)**/
    @Index
    String transactionObjectType;
    
   /** empty constructor
     * Instantiates a new transaction info.
     */
    public TransactionInfo() {
    }

	/**
	 * Instantiates a new transaction info.
	 *
	 * @param user the user who is creating the transaction
	 * @param keyword the keyword string name of the object being represented in the transaction
	 * @param transactionObjectType the transaction object classname as String
	 */
	public TransactionInfo(String keyword, String access, String owner, String sourceID,
			String transactionObjectType) {
		super(keyword,access,owner,sourceID);
		this.transactionObjectType = transactionObjectType;
	}
	
	/**
	 * Adds the {@link KeywordRDF}.
	 * If the number of {@link KeywordRDF} elements exceeds maxSize,
	 * then they are transfered to a {@link SetOfTransactionRDFs}
	 * and that structure is stored here.
	 * This was done so as to not have too much info in this class
	 *
	 * @param rdf the rdf
	 */
	

	/**
	 * Gets the transaction object classname.
	 *
	 * @return the transaction object type
	 */
	public String getTransactionObjectType() {
		return transactionObjectType;
	}
	
	/**
	 * Gets the stored object key.
	 *
	 * @return the stored object key
	 */
	public Long getStoredObjectKey() {
		return storedObjectKey;
	}
	
	/**
	 * Sets the stored object key.
	 *
	 * @param storedObjectKey the new stored object key
	 */
	public void setStoredObjectKey(Long storedObjectKey) {
		this.storedObjectKey = storedObjectKey;
	}
}
