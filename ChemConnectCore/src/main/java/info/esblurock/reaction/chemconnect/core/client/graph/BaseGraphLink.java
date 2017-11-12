package info.esblurock.reaction.chemconnect.core.client.graph;

import java.io.Serializable;

public class BaseGraphLink implements Serializable {
	private static final long serialVersionUID = 1L;

	private String source;
	
	private String target;
	
	private String type;

	public BaseGraphLink() {
		
	}
	public BaseGraphLink(String source, String target, String type) {
		this.source = source;
		this.target = target;
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public String getTarget() {
		return target;
	}

	public String getType() {
		return type;
	};
}
