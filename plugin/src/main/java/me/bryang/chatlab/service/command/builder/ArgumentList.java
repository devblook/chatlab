package me.bryang.chatlab.service.command.builder;

public enum ArgumentList{
    MSG("/msg <player> <message>"),
    REPLY("/reply <message>"),
    IGNORE("/ignore <player>"),
    UNIGNORE("/unignore <player>"),

    CLAB("/clab help");

    private String usage;

    ArgumentList(String usage){
        this.usage = usage;
    }

    public String getUsage() {
        return usage;
    }
}
