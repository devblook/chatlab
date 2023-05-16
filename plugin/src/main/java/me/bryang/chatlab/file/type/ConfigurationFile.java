package me.bryang.chatlab.file.type;

import me.bryang.chatlab.file.PluginFiles;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
@SuppressWarnings("FieldMayBeFinal")
public class ConfigurationFile extends PluginFiles {

    @Comment("Private message format")
    private PrivateMessage privateMessage = new PrivateMessage();
    private Reply reply = new Reply();


    @ConfigSerializable
    public static class PrivateMessage{

        private String fromSender = "<red>[MSG] <dark_gray>| <white>You <dark_gray>»<green> <target> <dark_grey>: <white><message>";
        private String toReceptor = "<red>[MSG] <dark_gray>| <green><sender> <dark_gray>» <white>You <dark_grey>: <white><message>";

    }

    @ConfigSerializable
    public static class Reply{
        @Comment("When the player left")
    private String left = "<green>[Chat] <gray>| <white>The player <green><target> <white>that you talk, left the server.";

   }

   public String fromSenderMessage(){
        return privateMessage.fromSender;
   }

   public String toReceptorMessage(){
        return privateMessage.toReceptor;
   }

   public String leftMessage(){
        return reply.left;
   }

}
