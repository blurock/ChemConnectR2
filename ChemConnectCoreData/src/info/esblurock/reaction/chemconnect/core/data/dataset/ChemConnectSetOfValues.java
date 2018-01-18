package info.esblurock.reaction.chemconnect.core.data.dataset;

import java.util.HashSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;

@SuppressWarnings("serial")
@Entity
public class ChemConnectSetOfValues  extends ChemConnectCompoundDataStructure {

	@Unindex
	HashSet<String> values;

	public ChemConnectSetOfValues() {
		this.values = null;
	}
	public ChemConnectSetOfValues(ChemConnectCompoundDataStructure compound, HashSet<String> values) {
		super(compound);
		this.values = values;
	}	
	public void fill(ChemConnectDataStructure compound, HashSet<String> values) {
		super.fill(compound);
		this.values = values;
	}
	public HashSet<String> getValues() {
		return values;
	}
}
