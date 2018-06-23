package info.esblurock.reaction.chemconnect.core.data.gcs;

import java.io.Serializable;

public class GCSBlobContent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	GCSBlobFileInformation info;
	String url;
	String bytes;
	
	public GCSBlobContent() {
		
	}
	
	public GCSBlobContent(String url, GCSBlobFileInformation info) {
		super();
		this.info = info;
		this.url = url;
		this.bytes = null;
	}
	
	public GCSBlobFileInformation getInfo() {
		return info;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getBytes() {
		return bytes;
	}
	
	public void setBytes(String bytes) {
		this.bytes = bytes;
	}
	
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		if(info != null) {
			builder.append(this.info.toString());
		} else {
			builder.append("No Blob info");
		}
		if(bytes != null) {
			builder.append("Blob size: " + bytes.length() + "\n");
		} else {
			builder.append("No/empty Blob\n");
		}
		if(url != null) {
			builder.append("URL: " + url + "\n");
		} else {
			builder.append("No URL defined\n");
		}
		
		return builder.toString();
	}
	

}
