package me.bryang.chatlab.command;

import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.RootSection;
import me.bryang.chatlab.message.MessageManager;
import me.bryang.chatlab.storage.repository.Repository;
import me.bryang.chatlab.storage.user.User;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;

@InjectAll
public class ToggleMsgCommand implements CommandClass {

	@Named("users")
	private Repository<User> userRepository;

	private ConfigurationContainer<RootSection> configContainer;

	private MessageManager messageManager;

	@Command(
		names = "toggle-msg",
		desc = "Toggle private messages"
	)
	public void execute(@Sender Player sender) {

		RootSection configFile = configContainer.get();
		User user = userRepository.findById(sender.getUniqueId().toString());

		if (!user.privateMessages()) {
			messageManager.sendMessage(sender, configFile.privateMessage.toggle.enable);
		} else {
			messageManager.sendMessage(sender, configFile.privateMessage.toggle.disable);
		}

		user.privateMessages(!user.privateMessages());
	}
}