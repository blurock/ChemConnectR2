package info.esblurock.reaction.chemconnect.core.data.base;

import com.googlecode.objectify.annotation.Entity;

@SuppressWarnings("serial")
@Entity
public class ChemConnectCompoundMultipleInfo extends ChemConnectCompoundMultiple {

	public ChemConnectCompoundMultipleInfo() {
		super();
	}

	public ChemConnectCompoundMultipleInfo(ChemConnectCompoundMultiple multiple) {
		super(multiple,multiple.getType(), multiple.getIds());
	}
}
