package info.esblurock.reaction.chemconnect.core.data.transaction;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;


@SuppressWarnings("serial")
@Entity
public class SessionEvent extends DatabaseObject {

    @Index
	String sessionIP;
    @Index
	String event;
    @Index
	String eventInfo;
	
	public SessionEvent() {	
	}

	
	/**
	 * @param id The id (username) of the session
	 * @param sessionIP the IP of the session
	 * @param event Event tag
	 * @param eventInfo further information about the event
	 * 
	 * The access and owner are the same as the id.
	 * The sourceID is set to ""
	 */
	public SessionEvent(String id,
			String sessionIP, String event, String eventInfo) {
		super(id,id,id,"");
		this.sessionIP = sessionIP;
		this.event = event;
		this.eventInfo = eventInfo;
	}

	public String getSessionIP() {
		return sessionIP;
	}

	public String getEvent() {
		return event;
	}


	public String getEventInfo() {
		return eventInfo;
	}	
	
}
