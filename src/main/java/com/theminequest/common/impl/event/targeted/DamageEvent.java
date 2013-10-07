package com.theminequest.common.impl.event.targeted;

import java.util.Collection;

import com.theminequest.api.CompleteStatus;
import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.api.quest.event.TargetedQuestEvent;

public class DamageEvent extends TargetedQuestEvent {
	
	private double damage;
	
	@Override
	public CompleteStatus targetAction(Collection<MQPlayer> entities) {
		for (MQPlayer player : entities)
			player.setHealth(player.getHealth() - damage);
		return CompleteStatus.SUCCESS;
	}
	
	@Override
	public void setupArguments(String[] arguments) {
		this.damage = Double.parseDouble(arguments[0]);
	}
	
	@Override
	public Integer switchTask() {
		return null;
	}
	
}
