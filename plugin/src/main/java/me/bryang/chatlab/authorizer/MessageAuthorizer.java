package me.bryang.chatlab.authorizer;

import javax.inject.Inject;
import java.util.Set;

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
