package info.esblurock.reaction.core.server.initialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import info.esblurock.reaction.chemconnect.core.data.initialization.InitializationFile;
import info.esblurock.reaction.core.server.db.DatabaseWriteBase;
import info.esblurock.reaction.io.db.QueryBase;

public class DatabaseInitializeBase {
	
	static String fileInterpreterClassS = "interpreter";

	protected Object object;
	
	public boolean alreadyRead(String fileS) {
		boolean answer = true;
		try {
			InitializationFile init = (InitializationFile) QueryBase.getFirstDatabaseObjectsFromSingleProperty(InitializationFile.class.getName(), 
					"fileName", fileS);
			if(init == null)
				answer = false;
		} catch (IOException e) {
			answer = false;
		}
		return answer;
	}
	
	
	
	public void readInitializationFile(String fileS, String filetypeS) throws IOException {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileS);
		
		InitializationFile filetowrite = new InitializationFile(fileS);
		DatabaseWriteBase.writeDatabaseObject(filetowrite);
		System.out.println("readInitializationFile: '" + filetypeS + "' " + filetypeS.compareToIgnoreCase("yaml"));
		if(filetypeS.compareToIgnoreCase("yaml") == 0) {
			readInitializationYamlFile(in);
		} else {
			throw new IOException("Initialization file not found: " + filetypeS);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void readInitializationYamlFile(InputStream in) throws IOException {
		Reader targetReader = new InputStreamReader(in);
		YamlReader reader = new YamlReader(targetReader);
		Map map;
		try {
			System.out.println("readInitializationYamlFile: ");
			Object object = reader.read();
			map = (Map) object;
			String interpreterS = (String) map.get(fileInterpreterClassS);
			System.out.println("Interpreter: " + interpreterS);
			Class interpreterClass = Class.forName(interpreterS);
			YamlFileInterpreterBase base = (YamlFileInterpreterBase) interpreterClass.newInstance();
			System.out.println("Interpreter Class:" + base.getClass().getCanonicalName());
			String username = "Administration";
			String sourceID = QueryBase.getDataSourceIdentification(username);
			base.interpret(map, sourceID);
		} catch (YamlException e) {
			throw new IOException(e.toString());
		} catch (ClassNotFoundException e) {
			throw new IOException("Interpreter not found");
		} catch (InstantiationException e) {
			throw new IOException("Interpreter not found (no new instance)");
		} catch (IllegalAccessException e) {
			throw new IOException("Interpreter not found (no new instance)");
		}
	}
}
