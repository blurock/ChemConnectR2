package info.esblurock.reaction.chemconnect.core.data.description;

import java.util.HashSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
@Entity
public class ChemConnectParameterDescription extends ChemConnectCompoundDataStructure {

	@Unindex
	HashSet<String> parameterDescription;

	public ChemConnectParameterDescription() {
	}
	
	public ChemConnectParameterDescription(ChemConnectCompoundDataStructure compound,
			HashSet<String> parameterDescription) {
		super(compound);
		this.parameterDescription = parameterDescription;
	}
	public void fill(ChemConnectCompoundDataStructure compound,
			HashSet<String> parameterDescription) {
		super.fill(compound);
		this.parameterDescription = parameterDescription;
	}
	
	
	

}
