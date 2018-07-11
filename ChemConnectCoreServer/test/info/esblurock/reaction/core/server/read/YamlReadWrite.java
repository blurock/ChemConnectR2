package info.esblurock.reaction.core.server.read;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

import org.junit.Test;

import com.esotericsoftware.yamlbeans.YamlReader;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.initialization.CreateDefaultObjectsFactory;

public class YamlReadWrite {

	@Test
	public void test() {
		
		DatabaseObject obj = new DatabaseObject("AdministrationCatalog-HeatFluxBurner",
				"Public","Administration","1" );
	
		String devicename = "dataset:HeatFluxBurner";
		String purpose = "dataset:FlameVelocityMeasurements";
		String concept = "dataset:FlameStudies";
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj,"");
		DataCatalogID name = new DataCatalogID(structure,"Catalog-Base","Catalog","Simple");
		
		DatabaseObjectHierarchy devicehier = CreateDefaultObjectsFactory.fillSubSystemDescription(obj,
				devicename,purpose,concept,name);
		System.out.println("fillSubSystemDescription\n" + devicehier.toString());
		
		try {
			System.out.println("------------------------------------------------------------------------");
			Map<String,Object> mapping = ReadWriteYamlDatabaseObjectHierarchy.yamlDatabaseObjectHierarchy(devicehier);
			System.out.println("Mapping: \n");
			String yamlstring = PrettyPrintYaml.print(mapping);
			System.out.println(yamlstring);
			System.out.println("------------------------------------------------------------------------");
			Reader stringreader = new StringReader(yamlstring);
			YamlReader reader = new YamlReader(stringreader);
			Map<String,Object> map;
			System.out.println("readInitializationYamlFile: ");
			Object object = reader.read();
			map = (Map<String,Object>) object;
			System.out.println(PrettyPrintYaml.print(map));
			String sourceID = "0";
			DatabaseObject top = new DatabaseObject();
			DatabaseObjectHierarchy hierarchy = ReadWriteYamlDatabaseObjectHierarchy.readYamlDatabaseObjectHierarchy(top, map, sourceID);
			System.out.println("hierarchy\n" + hierarchy);
		} catch (IOException e) {
			System.out.println("Error: \n");
			System.out.println(e.toString());
		}
		
	}

}
