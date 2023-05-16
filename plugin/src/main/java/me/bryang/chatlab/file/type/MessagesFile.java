package me.bryang.chatlab.file.type;

import me.bryang.chatlab.file.PluginFiles;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class MessagesFile extends PluginFiles {

    private Error error = new Error();

    @ConfigSerializable
    public static class Error{

        @Comment("You can't use this command in console")
        private String console = "You can't do this in console";

        @Comment("Specific argument is missing")
        private String noArgument = "<red>[Error] <dark_gray>| <white>Unknown argument. \n<gray>-<white>Usage: <green><usage><white>.";

        @Comment("Player isn't online.")
        private String playerOffline = "<red>[Error] <dark_gray>| <white>The player is offline.";

        @Comment("No have a recent messenger for use reply command")
        private String noReply = "<red>[Error] <dark_gray>| <white>You doesn't reply with anyone.";

        @Comment("General message for player without permissions")
        private String noPermission = "<red>[Error] <dark_gray>| <white>You don't have permission to do this.";

        @Comment("Talking to yourself")
        private String yourselfTalk = "<red>[Error] <dark_gray>| <white>You can't send private message to yourself.";
    }


    public String consoleMessage() {
        return error.console;
    }

    public String noArgumentMessage() {
        return error.noArgument;
    }

    public String playerOfflineMessage() {
        return error.playerOffline;
    }

    public String replyMessage() {
        return error.noReply;
    }

    public String noPermissionMessage() {
        return error.noPermission;
    }

    public String yourselfTalk() {
        return error.yourselfTalk;
    }
}
