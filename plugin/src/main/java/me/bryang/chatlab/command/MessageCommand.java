package me.bryang.chatlab.command;

import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.MessageSection;
import me.bryang.chatlab.configuration.section.RootSection;
import me.bryang.chatlab.event.LocalSpyEvent;
import me.bryang.chatlab.message.MessageManager;
import me.bryang.chatlab.storage.repository.Repository;
import me.bryang.chatlab.storage.user.User;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;

@InjectAll
public class MessageCommand implements CommandClass {

	@Named("users")
	private Repository<User> userRepository;

	private ConfigurationContainer<RootSection> configurationContainer;
	private ConfigurationContainer<MessageSection> messageContainer;

	private MessageManager messageManager;
	private LocalSpyEvent localSpyEvent;

	@Command(
		names = {"msg", "pm", "m", "message", "tell", "w", "whisper"},
		desc = "Command to send a private message.")
	public void execute(@Sender Player sender, Player target, @Text String senderMessage){

		RootSection configFile = configurationContainer.get();
		MessageSection messageSection = messageContainer.get();

		if (sender.getUniqueId() == target.getUniqueId()) {
			messageManager.sendMessage(sender, messageSection.error.yourselfTalk);
			return;
		}

		User targetUser = userRepository.findById(target.getUniqueId().toString());

		if (!targetUser.privateMessages() && !sender.hasPermission("clab.msg-toggle-bypass")) {
			messageManager.sendMessage(sender, messageSection.error.msgDisabled,
				Placeholder.unparsed("player", target.getName()));
			return;
		}

		messageManager.sendMessage(sender, configFile.privateMessage.fromSender,
			Placeholder.parsed("receptor", target.getName()),
			Placeholder.unparsed("message", senderMessage));

		if (targetUser.containsIgnoredPlayers(sender.getUniqueId())
			&& !sender.hasPermission("clab.ignore-bypass")) {
			return;
		}

		messageManager.sendMessage(target, configFile.privateMessage.toReceptor,
			Placeholder.parsed("sender", sender.getName()),
			Placeholder.unparsed("message", senderMessage));

		User senderUser = userRepository.findById(target.getUniqueId().toString());

		senderUser.recentMessenger(target.getUniqueId());
		targetUser.recentMessenger(sender.getUniqueId());

		localSpyEvent.call(sender, target, senderMessage);
	}
}
