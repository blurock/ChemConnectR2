package info.esblurock.reaction.core.server.db.spreadsheet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.metadata.StandardDatasetMetaData;
import info.esblurock.reaction.core.server.db.InterpretData;

public class ReadYamlFileInputConversion {
	@SuppressWarnings("unchecked")
	public ArrayList<ConvertInputDataBase> readYaml(InputStream in, String sourceID)
			throws IOException {
		Reader targetReader = new InputStreamReader(in);
		YamlReader reader = new YamlReader(targetReader);
		ArrayList<ConvertInputDataBase> top = null;
		try {
			Object object = reader.read();
			Map<String, Object> map = (Map<String, Object>) object;
			top = ExtractListOfObjects(map, sourceID);
		} catch (YamlException e) {
			throw new IOException("error reading yaml file\n" + e.toString());
		}
		return top;
	}

	private ArrayList<ConvertInputDataBase> ExtractListOfObjects(Map<String, Object> map, String sourceID) throws IOException {
		ArrayList<ConvertInputDataBase> lst = new ArrayList<ConvertInputDataBase>();
		for (Object nameO : map.keySet()) {
			String name = (String) nameO;
			if (name.compareTo("interpreter") != 0) {
				ConvertInputDataBase convert = extractConversion(name, map, sourceID);
				lst.add(convert);
			}
		}
		
		return lst;
	}

	@SuppressWarnings("unchecked")
	private ConvertInputDataBase extractConversion(String elementName, Map<String, Object> map, String sourceID) throws IOException {
		
		Map<String, Object> structuremap = null;
		if (map != null) {
			structuremap = (Map<String, Object>) map.get(elementName);
			String elementStructure = (String) structuremap.get(StandardDatasetMetaData.conversionStructure);
			
			ConvertInputDataBase convert = null;
			if (structuremap != null) {
				InterpretData interpret = InterpretData.valueOf(elementStructure);
				DatabaseObject top = interpret.fillFromYamlString(null, structuremap, sourceID);
				convert = (ConvertInputDataBase) top;
			}
			return convert;
		}
		
		
		
		return null;
	}
}
