package info.esblurock.reaction.core.server.read;

import java.util.Map;
import java.util.Set;

public class PrettyPrintYaml {
	public static String print(Map<String, Object> mapping) {
		return print(mapping, "");
	}

	public static String print(Map<String, Object> mapping, String prefix) {
		StringBuilder build = new StringBuilder();
		Set<String> keys = mapping.keySet();
		String newprefix = prefix + "     ";
		for (String key : keys) {
			Object obj = mapping.get(key);
			if (String.class.isInstance(obj)) {
				build.append(prefix + key + ":   " + obj.toString() + "\n");
			}
		}
		for (String key : keys) {
			Object obj = mapping.get(key);
			if (obj != null) {
				if (!String.class.isInstance(obj)) {
					@SuppressWarnings("unchecked")
					Map<String, Object> submap = (Map<String, Object>) obj;
					build.append(prefix + key + ":\n");
					build.append(print(submap, newprefix));
				}
			} else {
				build.append(prefix + key + ":\n");
			}
		}

		return build.toString();
	}
}
