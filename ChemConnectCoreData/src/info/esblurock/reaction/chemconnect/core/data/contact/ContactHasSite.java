package info.esblurock.reaction.chemconnect.core.data.contact;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class ContactHasSite extends ChemConnectCompoundDataStructure {
	
	@Index
	String httpAddressType;
	@Index
	String httpAddress;
	
	public ContactHasSite() {
		httpAddress = "http://";
	}
	public ContactHasSite(ChemConnectCompoundDataStructure structure, String httpAddressType, String httpAddress) {
		super(structure);
		this.httpAddress = httpAddress;
		this.httpAddressType = httpAddressType;
	}
	public String getHttpAddress() {
		return httpAddress;
	}
	public void setHttpAddress(String httpAddress) {
		this.httpAddress = httpAddress;
	}
	
	public String getHttpAddressType() {
		return httpAddressType;
	}
	public void setHttpAddressType(String addressType) {
		this.httpAddressType = addressType;
	}
	public void fill(DatabaseObject object) {
		super.fill(object);
		ContactHasSite compound = (ContactHasSite) object;
		this.httpAddress = compound.getHttpAddress();
	}

	
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "HTTP type:    " + httpAddressType + "\n");
		build.append(prefix + "HTTP address: " + httpAddress + "\n");
		return build.toString();
	}

}
