package info.esblurock.reaction.chemconnect.core.data.transaction;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Unindex;

@Entity
public class EventCount {

	@Id String username;
	@Unindex Integer count;
	
	public EventCount() {
		this.username = null;
		count = 1;
	}
	public EventCount(String username) {
		this.username = username;
		count = 1;
	}
	public int increment() {
		return ++count;
	}
	public int getCount() {
		return count;
	}
	
}
