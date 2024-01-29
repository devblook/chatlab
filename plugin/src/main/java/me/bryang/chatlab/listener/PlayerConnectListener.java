package me.bryang.chatlab.listener;

import me.bryang.chatlab.UpdateCheckHandler;
import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.RootSection;
import me.bryang.chatlab.message.MessageManager;
import me.bryang.chatlab.storage.repository.Repository;
import me.bryang.chatlab.storage.user.User;
import me.bryang.chatlab.storage.user.gson.GsonStorageManager;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.slf4j.Logger;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@InjectAll
public class PlayerConnectListener implements Listener {

	@Named("users")
	private Repository<User> userRepository;
	private ConfigurationContainer<RootSection> configurationContainer;

	private Logger logger;
	private MessageManager messageManager;
	private UpdateCheckHandler updateChecker;
	private GsonStorageManager gsonStorageManager;

	@Named("async-thread")
	private ExecutorService executorService;

	@EventHandler
	public void loginEvent(AsyncPlayerPreLoginEvent event){
		String senderUniqueId = event.getUniqueId().toString();

		if (userRepository.exists(senderUniqueId)) {
			return;
		}

		User user = gsonStorageManager.exists(senderUniqueId)
			? gsonStorageManager.deserialize(senderUniqueId)
			: new User(senderUniqueId);

		userRepository.create(user);

	}
	@EventHandler
	public void joinEvent(PlayerJoinEvent event) {

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

	}

	@EventHandler
	public void quitEvent(PlayerQuitEvent event) {

		String senderUniqueId = event.getPlayer().getUniqueId().toString();
		User user = userRepository.findById(senderUniqueId);

		if (user.hasRecentMessenger()) {

			Player sender = Bukkit.getPlayer(user.recentMessenger());
			User target = userRepository.findById(user.recentMessenger().toString());

			messageManager.sendMessage(sender, configurationContainer.get().reply.left,
				Placeholder.unparsed("target", event.getPlayer().getName()));

			user.recentMessenger(null);
			target.recentMessenger(null);
		}

		CompletableFuture
			.runAsync(() -> {
				gsonStorageManager.save(user);
				userRepository.deleteById(user.id());
			}, executorService)
			.exceptionally(throwable -> {

					logger.info("There was a error to save " + event.getPlayer().getName() + " data.");
					logger.info("Message: " + throwable.getMessage());
					return null;
			});
	}
}
