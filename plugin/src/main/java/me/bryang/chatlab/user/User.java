package me.bryang.chatlab.user;

import java.util.UUID;

public class User {

    private UUID repliedTarget;


    public void setRepliedTarget(UUID senderUniqueId){
        this.repliedTarget = senderUniqueId;
    }

    public UUID getRepliedTarget(){
        return repliedTarget;
    }
}
