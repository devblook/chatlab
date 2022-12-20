package me.bryang.chatlab.api;

public interface Service {

    void start();
    default void stop(){
        // Do nothing
    }
}
