package me.bryang.chatlab.listener;

import me.bryang.chatlab.UpdateCheckHandler;
import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.RootSection;
import me.bryang.chatlab.message.MessageManager;
import me.bryang.chatlab.user.User;
import me.bryang.chatlab.user.UserDataHandler;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import team.unnamed.inject.InjectAll;

import java.util.Map;

@InjectAll
public class PlayerRegistryListener implements Listener {

	private Map<String, User> users;
	private ConfigurationContainer<RootSection> configurationContainer;

	private MessageManager messageManager;
	private UpdateCheckHandler updateChecker;
	private UserDataHandler userDataHandler;

	@EventHandler
	public void onRegistry(PlayerJoinEvent event) {

		Player sender = event.getPlayer();

		if (updateChecker.requestSuccess() &&
			sender.hasPermission("clab.check-update") &&
			configurationContainer.get().mainSettings.updateCheckChat &&
			!updateChecker.updated()) {

			messageManager.sendMessage(sender, """
				<blue>[ChatLab] <white>| <green>Hello! There is a update.
				<dark_green>>> <green><u><click:open_url:'https://github.com/devblook/chatlab/releases/tag/%s'>Click here</click></u> <white>to download the plugin."""
				.formatted(updateChecker.lastVersion()));
		}

		String senderUniqueId = sender.getUniqueId().toString();

		if (users.containsKey(senderUniqueId)) {
			return;
		}

		if (!userDataHandler.exists(senderUniqueId)){
			userDataHandler.deserializeAndPut(senderUniqueId);
		}else{
			users.put(senderUniqueId, new User());
		}
	}

	@EventHandler
	public void onUnRegistry(PlayerQuitEvent event) {

		String senderUniqueId = event.getPlayer().getUniqueId().toString();
		User user = users.get(senderUniqueId);

		if (user.hasRecentMessenger()) {

			Player sender = Bukkit.getPlayer(user.recentMessenger());
			User target = users.get(user.recentMessenger().toString());

			messageManager.sendMessage(sender, configurationContainer.get().reply.left,
				Placeholder.unparsed("target", event.getPlayer().getName()));

			user.recentMessenger(null);
			target.recentMessenger(null);
		}

		userDataHandler.serializeAndSave(senderUniqueId, user);
	}
}
