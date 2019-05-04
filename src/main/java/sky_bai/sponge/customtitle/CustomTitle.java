package sky_bai.sponge.customtitle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;

@Plugin(id = "customtitle", name = "CustomTitle")
public class CustomTitle {

	public final static Logger logger = LoggerFactory.getLogger("CustomTitle");;

	@Listener
	public void onServerStart(GamePreInitializationEvent event) {
		logger.info("插件加载中....");
		this.setConfigPath();
		this.setConfigFile();
		CustomTitle.setConfig();
		Sponge.getCommandManager().register(this, new BaiCommand(), "CustomTitle", "CT");
		logger.info("插件加载完成.");
	}

	final private void setConfigFile() {
		try {
			Sponge.getAssetManager().getAsset(this, "config.yml").get().copyToDirectory(BaiConfig.CustomTitleConfigPath, false, true);
		} catch (Exception e1) {
		}
	}

	final private void setConfigPath() {
		BaiConfig.CustomTitleConfigPath = Sponge.getConfigManager().getPluginConfig(this).getDirectory().resolveSibling("CustomTitle");
		BaiConfig.configPath = BaiConfig.CustomTitleConfigPath.resolve("config.yml");
	}

	static public void setConfig() {
		BaiConfig.setCTConfig();
	}
}
