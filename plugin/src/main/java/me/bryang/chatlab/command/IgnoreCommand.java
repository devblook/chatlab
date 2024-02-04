package me.bryang.chatlab.command;

import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.MessageSection;
import me.bryang.chatlab.configuration.section.RootSection;
import me.bryang.chatlab.message.MessageManager;
import me.bryang.chatlab.storage.repository.Repository;
import me.bryang.chatlab.storage.user.User;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import java.util.List;
import java.util.UUID;

@Command(
	names = "ignore",
	desc = "Command to ignore a player.")
@InjectAll
public class IgnoreCommand implements CommandClass {

	@Named("users")
	private Repository<User> userRepository;

	private ConfigurationContainer<RootSection> configurationContainer;
	private ConfigurationContainer<MessageSection> messageContainer;

	private MessageManager messageManager;

	@Command(names = "")
	public void execute(@Sender Player sender, OfflinePlayer targetFormatted) {

		RootSection rootSection = configurationContainer.get();
		MessageSection messageSection = messageContainer.get();

		if (targetFormatted.getName().equalsIgnoreCase("-list")) {

			RootSection.Ignore.SeeIgnoredPlayers ignoredPlayersSector = rootSection.ignore.seeIgnoredPlayers;
			List<String> ignoredPlayers = userRepository.findById(sender.getUniqueId().toString()).ignoredPlayers();

			String ignoredPlayersData;
			if (!ignoredPlayers.isEmpty()) {

				List<String> ignoredPlayersFormatted = ignoredPlayers
					.stream()
					.map(field -> Bukkit.getOfflinePlayer(UUID.fromString(field)).getName())
					.toList();

				ignoredPlayersData = String.join(", ", ignoredPlayersFormatted);
			} else {
				ignoredPlayersData = ignoredPlayersSector.error;
			}

			ignoredPlayersSector.format
				.forEach(message -> messageManager.sendMessage(sender, message,
					Placeholder.unparsed("ignored_players_size", String.valueOf(ignoredPlayers.size())),
					Placeholder.unparsed("ignored_players_data", ignoredPlayersData)));
			return;
		}

		if (!targetFormatted.isOnline()) {
			messageManager.sendMessage(sender, messageSection.error.playerOffline);
			return;
		}

		Player target = targetFormatted.getPlayer();
		if (sender.getUniqueId() == target.getUniqueId()) {
			messageManager.sendMessage(sender, messageSection.error.yourselfIgnore);
			return;
		}

		User user = userRepository.findById(sender.getUniqueId().toString());

		if (user.containsIgnoredPlayers(target.getUniqueId())) {
			messageManager.sendMessage(sender, messageSection.error.playerAlreadyIgnored,
				Placeholder.unparsed("player", target.getName()));
			return;
		}

		user.ignore(target.getUniqueId());
		messageManager.sendMessage(sender, rootSection.ignore.ignoredPlayer,
			Placeholder.unparsed("player", target.getName()));
	}
}
