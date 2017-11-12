package info.esblurock.reaction.chemconnect.core.data.transfer.structure;

import java.io.Serializable;

public class ChemConnectDataStructureElement implements Serializable {
	
	private static final long serialVersionUID = 1L;
	String linktype;
	String elementType;
	int cardinality;
	boolean isSome;
	
	public ChemConnectDataStructureElement(String linktype, String elementType, int cardinality, boolean isSome) {
		super();
		this.linktype = linktype;
		this.elementType = elementType;
		this.cardinality = cardinality;
		this.isSome = isSome;
	}
	public String getLinktype() {
		return linktype;
	}
	public void setLinktype(String linktype) {
		this.linktype = linktype;
	}
	public String getElementType() {
		return elementType;
	}
	public void setElementType(String elementType) {
		this.elementType = elementType;
	}
	public int getCardinality() {
		return cardinality;
	}
	public void setCardinality(int cardinality) {
		this.cardinality = cardinality;
	}
	public boolean isSome() {
		return isSome;
	}
	public void setSome(boolean isSome) {
		this.isSome = isSome;
	}	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix + elementType);
		build.append(": ");
		build.append(linktype);
		if(cardinality == 1) {
			build.append("\t (singlet)");
		} else if(cardinality > 0) {
			build.append("\t (");
			build.append(elementType);
			build.append(")");
		}  else if(isSome) {
			build.append("\t (some)");
		}
		return build.toString();
	}
}
