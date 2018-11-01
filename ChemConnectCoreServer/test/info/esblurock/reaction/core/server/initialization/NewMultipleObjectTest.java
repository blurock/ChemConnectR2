package info.esblurock.reaction.core.server.initialization;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.InterpretData;

public class NewMultipleObjectTest {

	@Test
	public void test() {
		
		DatabaseObject obj = new DatabaseObject();
		obj.setIdentifier("NewMultiple");
		DatabaseObjectHierarchy hierarchy = InterpretData.ChemConnectCompoundMultiple.createEmptyObject(obj);
		ChemConnectCompoundMultiple multiple = (ChemConnectCompoundMultiple) hierarchy.getObject();
		multiple.setType("dataset:DataObjectLink");
		DatabaseObjectHierarchy multhier = CreateDefaultObjectsFactory.createEmptyMultipleObject(multiple);
		if(obj.getKey() == null) {
			System.out.println(multhier.getObject().getKey() + " is null");
		}
		System.out.println(multhier.getObject().getKey());
		System.out.println(multhier.toString());
		
	}

}
