package me.bryang.chatlab.command;

import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.MessageSection;
import me.bryang.chatlab.configuration.section.RootSection;
import me.bryang.chatlab.message.MessageManager;
import me.bryang.chatlab.storage.repository.Repository;
import me.bryang.chatlab.storage.user.User;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;

@InjectAll
public class ReplyCommand implements CommandClass {

	@Named("users")
	private Repository<User> userRepository;

	private ConfigurationContainer<RootSection> configurationContainer;
	private ConfigurationContainer<MessageSection> messageContainer;

	private MessageManager messageManager;

	@Command(
		names = {"reply", "r"},
		desc = "Command to reply to a message.")
	public void execute(@Sender Player sender, @Text String senderMessage) {

		MessageSection messageSection = messageContainer.get();
		RootSection configFile = configurationContainer.get();

		User user = userRepository.findById(sender.getUniqueId().toString());

		if (!user.hasRecentMessenger()) {
			messageManager.sendMessage(sender, messageSection.error.noReply);
			return;
		}

		Player target = Bukkit.getPlayer(user.recentMessenger());

		if (target == null) {
			messageManager.sendMessage(sender, messageSection.error.noReply);
			return;
		}

		messageManager.sendMessage(sender, configFile.privateMessage.fromSender,
			Placeholder.unparsed("receptor", target.getName()),
			Placeholder.unparsed("message", senderMessage));

		messageManager.sendMessage(target, configFile.privateMessage.toReceptor,
			Placeholder.unparsed("sender", sender.getName()),
			Placeholder.unparsed("message", senderMessage));
	}
}
