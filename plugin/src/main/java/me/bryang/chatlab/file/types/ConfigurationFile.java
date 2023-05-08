package me.bryang.chatlab.file.types;

import me.bryang.chatlab.file.PluginFiles;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
@SuppressWarnings("FieldMayBeFinal")
public class ConfigurationFile extends PluginFiles {


    private Settings settings = new Settings();
    private PrivateMessage privateMessage = new PrivateMessage();
    private Reply reply = new Reply();



    public static class Settings {

        @Comment("Enable bstats")
        private boolean enableStats = true;

        public boolean enableStats(){
            return enableStats;
        }

    }

    public static class PrivateMessage{

        private String fromSender = """
                
                <white>You &8» <light_green>%target% <dark_grey>» <white>%message%""";
        private String toReceptor = "<light_green>%sender% &8» <white>You <dark_grey>» <white>%message%";

    }

   public static class Reply{
        private String left = "<light_green>[Chat] <light_grey>| <white>The player <green>%target%<white>that you talk, left the server.";

   }

   public boolean statsEnabled(){
        return settings.enableStats;
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
