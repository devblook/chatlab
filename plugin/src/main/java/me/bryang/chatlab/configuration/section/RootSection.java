package me.bryang.chatlab.configuration.section;

import me.bryang.chatlab.chat.condition.ConditionType;
import me.bryang.chatlab.chat.serializer.FormatConfig;
import me.bryang.chatlab.configuration.ConfigurationSection;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@ConfigSerializable
public class RootSection extends ConfigurationSection {

	@Comment("Plugin settings")
	public MainSettings mainSettings = new MainSettings();

	@Comment("Private messages section")
	public PrivateMessage privateMessage = new PrivateMessage();

	@Comment("Reply messages section")
	public Reply reply = new Reply();

	@Comment("Chat format section")
	public ChatFormat chatFormat = new ChatFormat();

	@Comment("Ignore messages section")
	public Ignore ignore = new Ignore();

	@Comment("Private message spy section")
	public MsgSpy msgSpy = new MsgSpy();

	@ConfigSerializable
	public static class MainSettings {

		@Comment("""
			Enable message if the plugin isn't updated when you enter to the server with a permission.
			Permission: clab.check-update
			""")
		public boolean updateCheckChat = true;
	}


	@ConfigSerializable
	public static class PrivateMessage {

		public String fromSender = "<red>[MSG] <dark_gray>| <white>You <dark_gray>»<green> <receptor> <dark_grey>: <white><message>";
		public String toReceptor = "<red>[MSG] <dark_gray>| <green><sender> <dark_gray>» <white>You <dark_grey>: <white><message> <color:#ff8000><click:suggest_command:'/r '><hover:show_text:'Click to reply!'>[R]</hover></click> <color:#D1AE08><click:run_command:'/ignore <sender> '><hover:show_text:'Click to ignore the player!'>[I]</hover></click>";

		public Toggle toggle = new Toggle();

		@ConfigSerializable
		public static class Toggle {

			public String enable = "<red>[MSG] <dark_gray>| <white>Enabled private messages.";
			public String disable = "<red>[MSG] <dark_gray>| <white>Disabled private messages.";
		}
	}

	@ConfigSerializable
	public static class Reply {

		@Comment("Message to notify when the player he's talking to left")
		public String left = "<green>[Chat] <gray>| <white>The player <green><target> <white>you were talking to, has left the server.";
	}

	@ConfigSerializable
	public static class Ignore {

		@Comment("Message when the player input a target to ignore")
		public String ignoredPlayer = "<color:#ff8000>[Ignore] <dark_gray>| <white>You ignored a player. <green>[<player>]<green>";

		@Comment("Message when the player input a target to unignore")
		public String unignoredPlayer = "<color:#ff8000>[Ignore] <dark_gray>| <white>You un-ignored a player. <green>[<player>]<green>";

		@Comment("Message when you see the list of ignored players")
		public SeeIgnoredPlayers seeIgnoredPlayers = new SeeIgnoredPlayers();

		@ConfigSerializable
		public static class SeeIgnoredPlayers {

			@Comment("""
				Message when the player see the list of ignored players
				List of variables:
				- <ignored_players_size>: The size of ignored players.
				- <ignored_players_data>: The list of ignored players.""")
			public List<String> format = Arrays.asList("<red>[Ignore] <white>List of ignored players <green>[<ignored_players_size>]<white>:",
				"<dark_green>>> <green><ignored_players_data>");

			@Comment("Message that replace de list of ignored players data if the player is ignoring anyone")
			public String error = "You aren't ignoring anyone.";
		}
	}

	@ConfigSerializable
	public static class MsgSpy{

		@Comment("Format when a player send a private message.")
		public String privateMessageFormat = "<gold>[Spy] <dark_gray>| <green><sender> <dark_green>> <green><receptor> <dark_gray>: <white><message>";

		@Comment("Message when a player enable the social-spy mode")
		public String enabled = "<gold>[Spy] <dark_gray>| <white>Enabled spy-private message mode.";

		@Comment("Message when a player disable the social-spy mode")
		public String disabled = "<gold>[Spy] <dark_gray>| <white>Disabled spy-private message mode.";

		@Comment("Social-spy list format")
		public List<String> socialSpyListFormat = List.of("<gold>[Spy] <dark_gray>| <white>List of players using social-spy <green>[<player-in-spy-size>] <dark_gray>: <gold><player-in-spy-data>");
	}

	@ConfigSerializable
	public static class ChatFormat{

		@Comment("Enable the chat-format option.")
		public boolean enabled = true;

		@Comment("""
   			Note: If you doesn't have Vault and a plugin permission you only can use the default option.
			Type of conditions:
			- GROUP: Divide formats in groups.
			- PERMISSIONS: Divide formats in permissions.
			- DEFAULT: Use only default group.
			""")
		public ConditionType conditionType = ConditionType.DEFAULT;

		public Default defaultFormat = new Default();

		@Comment("Enable op format [When a player has OP or '*' permission")
		public OPFormat opFormat = new OPFormat();
		public Map<String, FormatConfig> groupFormats = Map.of(
			"vip", new FormatConfig("vip" , "<green>[VIP] >> <white><player> <dark_gray>: <white><message>"),
			"admin", new FormatConfig("admin" , "<red>[ADMIN] >> <white><player> <dark_gray>: <white><message>"));
	}
	@ConfigSerializable
	public static class OPFormat{

		public boolean enabled = false;
		public String format = "<red>[OP] <white><player> <dark_gray>: <white><message>";
	}
	@ConfigSerializable
	public static class Default{

		public String format = "<green>>> <white><player> <dark_gray>: <white><message>";
	}


}
