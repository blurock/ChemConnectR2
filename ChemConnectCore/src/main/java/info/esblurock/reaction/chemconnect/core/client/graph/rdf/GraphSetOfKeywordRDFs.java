package info.esblurock.reaction.chemconnect.core.client.graph.rdf;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.client.graph.GraphNodesWithForces;
import info.esblurock.reaction.chemconnect.core.data.rdf.SetOfKeywordRDF;

public class GraphSetOfKeywordRDFs implements AsyncCallback<SetOfKeywordRDF> {
	ForceGraphPanel content;
	public GraphSetOfKeywordRDFs(ForceGraphPanel content) {
		this.content = content;
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		System.out.println("Error in Graph: " + arg0);
	}

	@Override
	public void onSuccess(SetOfKeywordRDF rdfs) {
		ForceGraphFromKeywordRDF sample = new ForceGraphFromKeywordRDF(rdfs,content);
		GraphNodesWithForces graphDS = new GraphNodesWithForces(sample);
		content.setPanel(graphDS);
		graphDS.start();
	}

}
