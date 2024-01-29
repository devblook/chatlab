package me.bryang.chatlab.event;

import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.RootSection;
import me.bryang.chatlab.message.MessageManager;
import me.bryang.chatlab.storage.repository.Repository;
import me.bryang.chatlab.storage.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;

@InjectAll
@Singleton
public class LocalSpyEvent {

	@Named("users-in-spy")
	private List<String> usersInSpy;
	@Named("users")
	private Repository<User> userRepository;

	private MessageManager messageManager;
	private ConfigurationContainer<RootSection> configContainer;

	public void call(Player sender, Player receptor, String message) {

		Component socialSpyMessage = messageManager.parse(configContainer.
				get()
				.msgSpy
				.privateMessageFormat,

			Placeholder.unparsed("sender", sender.getName()),
			Placeholder.unparsed("receptor", receptor.getName()),
			Placeholder.unparsed("message", message));

		usersInSpy
			.stream()
			.map(target -> Bukkit.getPlayer(UUID.fromString(target)))
			.forEach(target -> target.sendMessage(socialSpyMessage));
	}
}
