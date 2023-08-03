package me.bryang.chatlab.command;

import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.MessageSection;
import me.bryang.chatlab.configuration.section.RootSection;
import me.bryang.chatlab.message.MessageManager;
import me.bryang.chatlab.user.User;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import java.util.Map;

@InjectAll
public class ToggleMsgCommand implements CommandClass {

	private Map<String, User> userData;
	private MessageManager messageManager;
	private ConfigurationContainer<RootSection> configContainer;
	private ConfigurationContainer<MessageSection> messageContainer;

	@Command(
		names = "toggle-msg",
		desc = "Toggle private messages"
	)
	public void execute(@Sender Player sender) {

		RootSection configFile = configContainer.get();
		User user = userData.get(sender.getUniqueId().toString());

		if (!user.privateMessages()) {
			messageManager.sendMessage(sender, configFile.privateMessage.toggle.enable);
		} else {
			messageManager.sendMessage(sender, configFile.privateMessage.toggle.disable);
		}

		user.privateMessages(!user.privateMessages());
	}

}