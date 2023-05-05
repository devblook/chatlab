package me.bryang.chatlab.file.types;

import me.bryang.chatlab.file.PluginFiles;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class MessagesFile extends PluginFiles {

    private Error error = new Error();


    public static class Error{

        @Comment("# You can't use this command in console")
        private String console = "You can't do this in console";

        @Comment("# Specific argument is missing")
        private String noArgument = "&a[Server] &8| &fUnknown argument. Usage: &a%usage%&f.";

        @Comment("# Player isn't online.")
        private String playerOffline = "&a[Server] &8| &fUnknown argument. Usage: &a%usage%&f.";

        @Comment("# No have a recent messenger for use reply command")
        private String noReply = "&a[Server] &8| &FYou doesn't reply with anyone.";

        @Comment("# General message for player without permissions")
        private String noPermission = "&a[Server] &8| &fYou don't have permission to do this.";


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

}
