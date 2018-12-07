package info.esblurock.reaction.core.server.yaml;

import java.io.IOException;

import org.junit.Test;


import info.esblurock.reaction.core.server.read.ReadWriteYamlDatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class ReadYamlDatabaseObjectHierarchyTest {

	@Test
	public void test() {
		String sourceID = "0";
		String newuser = "Administration";
		String addr = "https://storage.googleapis.com/combustion/Guest/CHEMCONNECT/ChemConnectIsolateBlockUntilBlankLine/Guest-CHEMCONNECT-ChemConnectIsolateBlockUntilBlankLine-IsolateIgnitionDelayTime.yaml";
		try {
			DatabaseObjectHierarchy hierarchy = ReadWriteYamlDatabaseObjectHierarchy.initializeFromYamlDatabaseObjectHierarchy(addr, newuser, sourceID);
			System.out.println(hierarchy.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
