package info.esblurock.reaction.core.server;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.io.dataset.InterpretData;
public class TestCanonicalClassName {

	@Test
	public void test() {
		InterpretData[] interpret = InterpretData.values();
		InterpretData interp = InterpretData.UserAccount;
		System.out.println("UserAccount: " + interp.canonicalClassName());
		System.out.println(DatabaseObject.class.getCanonicalName());
		
		for(int i=0; i<interpret.length;i++) {
			System.out.println(interpret[i].canonicalClassName());
		}
	
	}

}
