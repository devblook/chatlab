package me.bryang.chatlab.message.authorizer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

@Singleton
public class MessageAuthorizer {

    @Inject
    private Set<Authorizer> authorizers;

    public boolean isAuthorized(String senderUniqueId, String receptorUniqueId){

        for (Authorizer authorizer : authorizers){

            if (!authorizer.denied(senderUniqueId, receptorUniqueId)){
                return false;
            }

        }

        return true;
    }
}
