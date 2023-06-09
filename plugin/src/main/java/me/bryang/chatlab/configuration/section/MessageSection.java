package me.bryang.chatlab.configuration.section;

import me.bryang.chatlab.configuration.ConfigurationSection;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class MessageSection extends ConfigurationSection {

	public Error error = new Error();

	@ConfigSerializable
	public static class Error {

		@Comment("Message that action cannot be used at the console.")
		public String console = "You can't do this in console";

		@Comment("Specific argument is missing")
		public String noArgument = "<red>[Error] <dark_gray>| <white>Unknown argument. \n<gray>-<white>Usage: <green><usage><white>.";

		@Comment("Message to notify the player hes trying to message isn't online.")
		public String playerOffline = "<red>[Error] <dark_gray>| <white>The specified player is offline!";

		@Comment("Message to player that have no-one to reply to.")
		public String noReply = "<red>[Error] <dark_gray>| <white>You don't have no-one to reply to.";

		@Comment("General message for player with no permissions")
		public String noPermission = "<red>[Error] <dark_gray>| <white>You don't have permission to do this.";

		@Comment("Message to player that's try to messaging to itself.")
		public String yourselfTalk = "<red>[Error] <dark_gray>| <white>You cannot send private message to yourself.";

		@Comment("Message to player that's try to ignoring to itself.")
		public String yourselfIgnore = "<red>[Error] <dark_gray>| <white>You cannot ignore to yourself.";

		@Comment("Message when you input a player to ignore that already is ignored")
		public String playerAlreadyIgnored = "<red>[Error] <dark_gray>| <white>The player <green><player> <white>is already ignored.";

		@Comment("Message when you input a player to unignore that already is unignored")
		public String playerAlreadyUnIgnored = "<red>[Error] <dark_gray>| <white>The player <green><player> <white>is already unignored.";

	}
}
