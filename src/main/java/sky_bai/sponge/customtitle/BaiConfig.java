package sky_bai.sponge.customtitle;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

public class BaiConfig {
	static Path CustomTitleConfigPath;
	static Path configPath;
	static Set<String> CTCSet = new HashSet<String>();
	static Map<String, Map<String, Object>> CTConfig =  new HashMap<>();
	
	protected static ConfigurationNode getConfig(Path path) {
		try {
			return YAMLConfigurationLoader.builder().setPath(path).build().load();
		} catch (IOException e) {
			return null;
		}
	}

	@SuppressWarnings({ "unchecked" })
	public static void setCTConfig() {
		CTCSet.clear();
		CTConfig.clear();
		CTCSet.addAll(((HashMap<String,Object>)getConfig(configPath).getValue()).keySet());
		for (String CTC : CTCSet) {
			Map<String, Object> a1 = new HashMap<>();
			ConfigurationNode a2 = getConfig(configPath).getNode(CTC);
			a1.put("Title", a2.getNode("Title").getString(""));
			a1.put("SubTitle", a2.getNode("SubTitle").getString(""));
			a1.put("FadeIn", a2.getNode("FadeIn").getInt(0));
			a1.put("FadeOut", a2.getNode("FadeOut").getInt(0));
			a1.put("Stay", a2.getNode("Stay").getInt(0));
			CTConfig.put(CTC, a1);
		}
	}
}
