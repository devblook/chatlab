package me.bryang.chatlab.command;

import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.MessageSection;
import me.bryang.chatlab.configuration.section.RootSection;
import me.bryang.chatlab.message.MessageManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import java.util.List;
import java.util.UUID;

@InjectAll
@Command(
	names = {"socialspy", "spychat", "spymsg"},
	desc = "Check private messages",
	permission = "clab.socialspy")
public class SocialSpyCommand implements CommandClass {

	@Named("users-in-spy")
	private List<String> usersInSpy;

	private ConfigurationContainer<RootSection> configContainer;
	private ConfigurationContainer<MessageSection> messageContainer;

	private MessageManager messageManager;

	@Command(names = {"on", "enable"})
	public void enableSubCommand(@Sender Player sender){

		RootSection.MsgSpy msgSpySection = configContainer.get().msgSpy;
		MessageSection.Error errorSection = messageContainer.get().error;

		String senderUniqueId = sender.getUniqueId().toString();

		if (usersInSpy.contains(senderUniqueId)) {
			messageManager.sendMessage(sender, errorSection.socialSpyAlreadyEnabled);
			return;
		}

		usersInSpy.add(senderUniqueId);
		messageManager.sendMessage(sender, msgSpySection.enabled);
	}

	@Command(names = {"off", "disable"})
	public void disableSubCommand(@Sender Player sender){

		RootSection.MsgSpy msgSpySection = configContainer.get().msgSpy;
		MessageSection.Error errorSection = messageContainer.get().error;

		String senderUniqueId = sender.getUniqueId().toString();

		if (!usersInSpy.contains(senderUniqueId)) {
			messageManager.sendMessage(sender, errorSection.socialSpyAlreadyDisabled);
			return;
		}

		usersInSpy.remove(senderUniqueId);
		messageManager.sendMessage(sender, msgSpySection.disabled);
	}

	@Command(names = "list")
	public void listSubCommand(@Sender Player sender){

		String playerInSpySize = String.valueOf(usersInSpy.size());
		String playerInSpyData = String.join(", ", usersInSpy
			.stream()
			.map(id -> Bukkit.getPlayer(UUID.fromString(id)).getName())
			.toList());

		messageManager.sendMessage(sender,
			configContainer.get().msgSpy.socialSpyListFormat,
			Placeholder.unparsed("player-in-spy-size", playerInSpySize),
			Placeholder.unparsed("player-in-spy-data", playerInSpyData));
	}
}
