package info.esblurock.reaction.core.server.initialization.yaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.InterpretData;

public class ReadYamlDatabaseObjectHierarchy {

	public static DatabaseObjectHierarchy readYaml(InputStream in) throws IOException {
		System.out.println("readYaml: ");
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy();
		Reader targetReader = new InputStreamReader(in);
		YamlReader reader = new YamlReader(targetReader);
		try {
			Object object = reader.read();
			Map<String, Object> map = (Map<String, Object>) object;
			
			String structurename = (String) map.get("structure");
			System.out.println("readYaml: " + structurename);
			InterpretData interpret = InterpretData.valueOf(structurename);
			String sourceID = null;
			DatabaseObject top = null;
			DatabaseObject obj = interpret.fillFromYamlString(top, map, sourceID);
			System.out.println(obj.toString());
			
		} catch (YamlException e) {
			throw new IOException("error reading yaml file\n" + e.toString());
		}

		
		
		return hierarchy;
	}
}
