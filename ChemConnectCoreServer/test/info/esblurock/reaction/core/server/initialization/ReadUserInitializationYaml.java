package info.esblurock.reaction.core.server.initialization;

//import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import org.junit.Test;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import info.esblurock.reaction.core.server.initialization.yaml.ReadYamlDataset;

public class ReadUserInitializationYaml {

	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		String fileS = "resources/contact/UserInitialization.yaml";
		String sourceID = "1";
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileS);
		Reader targetReader = new InputStreamReader(in);
		YamlReader reader = new YamlReader(targetReader);
			Object object;
			try {
				object = reader.read();
				Map<String, Object> map = (Map<String, Object>) object;
				ReadYamlDataset.ExtractListOfObjects(map,sourceID);
			} catch (YamlException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	}

