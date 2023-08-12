package me.bryang.chatlab.command;

import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.RootSection;
import me.bryang.chatlab.message.MessageManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import java.util.List;

@InjectAll
public class SocialSpyCommand implements CommandClass {

	@Named("users-in-spy")
	private List<String> usersInSpy;

	private ConfigurationContainer<RootSection> configContainer;

	private MessageManager messageManager;

	@Command(
		names = {"socialspy, spychat, spymsg"},
		desc = "Check private messages.")
	public void execute(@Sender Player sender){

		RootSection.MsgSpy msgSpySection = configContainer.get().msgSpy;

		String senderUniqueId = sender.getUniqueId().toString();

		if (!usersInSpy.contains(senderUniqueId)){
			usersInSpy.add(senderUniqueId);
			messageManager.sendMessage(sender, msgSpySection.enabled);
		}else{
			usersInSpy.remove(senderUniqueId);
			messageManager.sendMessage(sender, msgSpySection.disabled);
		}

	}
}
