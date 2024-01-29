package me.bryang.chatlab.hook;

public interface Hook{

	HookStatus install();

	String errorMessage();

	String pluginName();

}
