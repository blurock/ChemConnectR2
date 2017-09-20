package info.esblurock.reaction.chemconnect.core.data.transaction;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class DataSourceIdentification implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id  String user;
	@Index  Integer count;
	
    public DataSourceIdentification() {
    	
    }
    public DataSourceIdentification(String user) {
    	this.user = user;
    	this.count = new Integer(0);
    }
    
    public String getCountAsString() {
    		return count.toString();
    }
    public int increment() {
    	return ++count;
    }
}
