package sky_bai.sponge.customtitle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class BaiCommand implements CommandCallable{

	@Override
	public CommandResult process(CommandSource source, String arguments) throws CommandException {
		if (arguments.startsWith("reload")) {
			CustomTitle.setConfig();
			source.sendMessage(Text.of("§l[CustomTitle]§r插件重载完成"));
			return CommandResult.success();
		} else if (arguments.startsWith("send ")) {
			String mesNameString = arguments.replace("send ", "");
			if (!BaiConfig.CTCSet.contains(mesNameString) && !(source instanceof Player)) {
				return CommandResult.empty();
			}
			Player player = (Player) source;
			Map<String, Object> a1 = BaiConfig.CTConfig.get(mesNameString);
			player.sendTitle(Title.builder().fadeIn((Integer) a1.get("FadeIn")).fadeOut((Integer) a1.get("FadeOut")).stay((Integer) a1.get("Stay")).title(Text.of(a1.get("Title"))).subtitle(Text.of(a1.get("SubTitle"))).build());
			return CommandResult.success();
		} else if (arguments.startsWith("sendto ")) {
			String playername =  arguments.replace("sendto ", "").replaceFirst(" .*","");
			Player player = null;
			if (Sponge.getServer().getPlayer(playername).isPresent()) {
				player = Sponge.getServer().getPlayer(playername).get();
			}
			if (player == null) {
				return CommandResult.empty();
			}
			String mesNameString = arguments.replace("sendto ", "").replace(playername+ " ","");
			Map<String, Object> a1 = BaiConfig.CTConfig.get(mesNameString);
			player.sendTitle(Title.builder().fadeIn((Integer) a1.get("FadeIn")).fadeOut((Integer) a1.get("FadeOut")).stay((Integer) a1.get("Stay")).title(Text.of(a1.get("Title"))).subtitle(Text.of(a1.get("SubTitle"))).build());
			return CommandResult.success();
		}
		return CommandResult.empty();
	}

	@Override
	public List<String> getSuggestions(CommandSource source, String arguments, Location<World> targetPosition) throws CommandException {
		List<String> argumentsList = new ArrayList<String>();
		if (arguments.length() == 0) {
			argumentsList.add("reload");
			argumentsList.add("send");
			argumentsList.add("sendto");
		} else if (arguments.contains("sendto ")) {
			for (Player player : Sponge.getServer().getOnlinePlayers()) {
				argumentsList.add(player.getName());
				if (arguments.contains("sendto "+player.getName())) {
					argumentsList.clear();
					argumentsList.addAll(BaiConfig.CTCSet);
				}
			}
		} else if (arguments.contains("send ")) {
			argumentsList.addAll(BaiConfig.CTCSet);
		}
		return argumentsList;
	}

	@Override
	public boolean testPermission(CommandSource source) {
		return source.hasPermission("CustomTitle.plugin");
	}

	@Override
	public Optional<Text> getShortDescription(CommandSource source) {
		return Optional.of(Text.of());
	}

	@Override
	public Optional<Text> getHelp(CommandSource source) {
		return Optional.of(Text.of());
	}

	@Override
	public Text getUsage(CommandSource source) {
		return Text.of();
	}

}
