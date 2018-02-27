package info.esblurock.reaction.core.server.db.image;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;



@SuppressWarnings("serial")
@Entity
public class StoredImage extends DatabaseObject {
    @Index
    private String name;

    @Unindex
    Blob image;

    public StoredImage() { }
    public StoredImage(String name, Blob image) {
        this.name = name; 
        this.image = image;
    }

    // JPA getters and setters and empty contructor
    // ...
    public Blob getImage() { 
    	return image; 
    }
    public void setImage(Blob image) { 
    	this.image = image; 
    }

}
