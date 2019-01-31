package info.esblurock.reaction.chemconnect.core.client.graph.rdf;

import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.client.graph.GraphNodesWithForces;
import info.esblurock.reaction.chemconnect.core.data.rdf.SetOfKeywordRDF;

public class GraphSetOfKeywordRDFs implements AsyncCallback<SetOfKeywordRDF> {
	ForceGraphPanel content;
	public GraphSetOfKeywordRDFs(ForceGraphPanel content) {
		this.content = content;
		MaterialLoader.loading(true);
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		System.out.println("ERROR: in Graph set: " + arg0);
	}

	@Override
	public void onSuccess(SetOfKeywordRDF rdfs) {
		MaterialLoader.loading(false);
		ForceGraphFromKeywordRDF sample = new ForceGraphFromKeywordRDF(rdfs,content);
		GraphNodesWithForces graphDS = new GraphNodesWithForces(sample);
		content.setPanel(graphDS);
		graphDS.start();
	}

}
