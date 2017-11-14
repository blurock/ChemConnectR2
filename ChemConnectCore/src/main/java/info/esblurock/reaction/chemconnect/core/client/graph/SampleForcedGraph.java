package info.esblurock.reaction.chemconnect.core.client.graph;


import info.esblurock.reaction.chemconnect.core.client.administration.ChemConnectAdministrationImpl;

public class SampleForcedGraph extends ForceGraphBehaviorAndInformation {

	static BaseGraphLink[] lnkarray = new BaseGraphLink[] {
			  new BaseGraphLink("Microsoft", "Amazon", "licensing"),
			  new BaseGraphLink("Microsoft", "HTC", "licensing"),
			  new BaseGraphLink("Samsung", "Apple", "suit"),
			  new BaseGraphLink("Motorola", "Apple", "suit"),
			  new BaseGraphLink("Nokia", "Apple", "resolved"),
			  new BaseGraphLink("HTC", "Apple", "suit"),
			  new BaseGraphLink("Kodak", "Apple", "suit"),
			  new BaseGraphLink("Microsoft", "Barnes & Noble", "suit"),
			  new BaseGraphLink("Microsoft", "Foxconn", "suit"),
			  new BaseGraphLink("Oracle", "Google", "suit"),
			  new BaseGraphLink("Apple", "HTC", "suit"),
			  new BaseGraphLink("Microsoft", "Inventec", "suit"),
			  new BaseGraphLink("Samsung", "Kodak", "resolved"),
			  new BaseGraphLink("LG", "Kodak", "resolved"),
			  new BaseGraphLink("RIM", "Kodak", "suit"),
			  new BaseGraphLink("Sony", "LG", "suit"),
			  new BaseGraphLink("Kodak", "LG", "resolved"),
			  new BaseGraphLink("Apple", "Nokia", "resolved"),
			  new BaseGraphLink("Qualcomm", "Nokia", "resolved"),
			  new BaseGraphLink("Apple", "Motorola", "suit"),
			  new BaseGraphLink("Microsoft", "Motorola", "suit"),
			  new BaseGraphLink("Motorola", "Microsoft", "suit"),
			  new BaseGraphLink("Huawei", "ZTE", "suit"),
			  new BaseGraphLink("Ericsson", "ZTE", "suit"),
			  new BaseGraphLink("Kodak", "Samsung", "resolved"),
			  new BaseGraphLink("Apple", "Samsung", "suit"),
			  new BaseGraphLink("Kodak", "RIM", "suit"),
			  new BaseGraphLink("Nokia", "Qualcomm", "suit")		
	};

	ChemConnectAdministrationImpl top;
	
	public SampleForcedGraph(ChemConnectAdministrationImpl top) {
		super(lnkarray);
		this.top = top;
	}

	@Override
	public void mouseOver(String nodeName) {
		top.nodeMouseOver(nodeName);
	}

	@Override
	public void mouseClick(String nodeName) {
		top.nodeClicked(nodeName);
	}

}
