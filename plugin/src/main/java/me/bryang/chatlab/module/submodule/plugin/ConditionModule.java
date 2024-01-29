package me.bryang.chatlab.module.submodule.plugin;

import me.bryang.chatlab.chat.condition.Condition;
import me.bryang.chatlab.chat.condition.ConditionType;
import me.bryang.chatlab.chat.condition.type.DefaultCondition;
import me.bryang.chatlab.chat.condition.type.GroupCondition;
import me.bryang.chatlab.chat.condition.type.PermissionCondition;
import team.unnamed.inject.AbstractModule;

public class ConditionModule extends AbstractModule {

	@Override
	protected void configure() {
		multibind(Condition.class)
			.asMap(ConditionType.class)
			.bind(ConditionType.DEFAULT).to(DefaultCondition.class)
			.bind(ConditionType.GROUP).to(GroupCondition.class)
			.bind(ConditionType.PERMISSION).to(PermissionCondition.class);
	}
}
