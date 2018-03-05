package info.esblurock.reaction.chemconnect.core.data.gcs;

import java.io.Serializable;

public class GCSBlobContent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	GCSBlobFileInformation info;
	String url;
	
	public GCSBlobContent() {
		
	}
	public GCSBlobContent(String url, GCSBlobFileInformation info) {
		super();
		this.info = info;
		this.url = url;
	}
	public GCSBlobFileInformation getInfo() {
		return info;
	}
	public String getUrl() {
		return url;
	}
	
	

}
