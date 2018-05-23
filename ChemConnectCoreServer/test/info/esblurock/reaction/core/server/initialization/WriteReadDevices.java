package info.esblurock.reaction.core.server.initialization;

import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class WriteReadDevices {

	@Test
	public void test() {
		DatabaseObject obj = new DatabaseObject("AdministrationCatalog-HeatFluxBurner",
				"Public","Administration","1" );
		String devicename = "dataset:HeatFluxBurner";
		String purpose = "dataset:FlameVelocityMeasurements";
		String concept = "dataset:FlameStudies";
		DatabaseObjectHierarchy devicehier = CreateDefaultObjectsFactory.fillSubSystemDescription(obj,
				devicename,purpose,concept);
		System.out.println(devicehier.toString());
	}

}
