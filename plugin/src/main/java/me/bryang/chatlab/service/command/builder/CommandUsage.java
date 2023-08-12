package me.bryang.chatlab.service.command.builder;

public enum CommandUsage {
	MSG("/msg <player> <message>"),
	TOGGLE_MSG("/toggle_msg"),
	REPLY("/reply <message>"),
	IGNORE("/ignore <player>"),
	UNIGNORE("/unignore <player>"),
	CLAB("/clab help"),
	SOCIALSPY("/socialspy");

	private final String usage;

	CommandUsage(String usage) {
		this.usage = usage;
	}

	public String usage() {
		return usage;
	}
}
