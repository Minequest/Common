package com.theminequest.common.impl.event;

import com.theminequest.api.CompleteStatus;
import com.theminequest.api.quest.event.DelayedQuestEvent;

public class FailQuestEvent extends DelayedQuestEvent {
	
	private long delay;
	
	/*
	 * (non-Javadoc)
	 * @see com.theminequest.api.quest.event.QuestEvent#setupArguments(java.lang.String[])
	 * 
	 * [0]: delay in MS
	 */
	@Override
	public void setupArguments(String[] arguments) {
		delay = Long.parseLong(arguments[0]);
	}

	@Override
	public boolean delayedConditions() {
		return true;
	}
	
	@Override
	public CompleteStatus action() {
		return CompleteStatus.FAIL;
	}
	
	@Override
	public Integer switchTask() {
		return null;
	}

	@Override
	public long getDelay() {
		return delay;
	}
	
}
