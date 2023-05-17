package me.bryang.chatlab.configuration.section;

import me.bryang.chatlab.configuration.ConfigurationSection;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class MessageSection extends ConfigurationSection {

    public Error error = new Error();

    @ConfigSerializable
    public static class Error {

        @Comment("You can't use this command in console")
        public String console = "You can't do this in console";

        @Comment("Specific argument is missing")
        public String noArgument = "<red>[Error] <dark_gray>| <white>Unknown argument. \n<gray>-<white>Usage: <green><usage><white>.";

        @Comment("Player isn't online.")
        public String playerOffline = "<red>[Error] <dark_gray>| <white>The player is offline.";

        @Comment("No have a recent messenger for use reply command")
        public String noReply = "<red>[Error] <dark_gray>| <white>You doesn't reply with anyone.";

        @Comment("General message for player without permissions")
        public String noPermission = "<red>[Error] <dark_gray>| <white>You don't have permission to do this.";

        @Comment("Talking to yourself")
        public String yourselfTalk = "<red>[Error] <dark_gray>| <white>You can't send private message to yourself.";
    }
}
