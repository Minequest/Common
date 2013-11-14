package com.theminequest.common.impl.event;

import com.theminequest.api.CompleteStatus;
import com.theminequest.api.quest.event.DelayedQuestEvent;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Event",
		ident = "QuestEvent",
		description = "Activate another task after a specified delay.",
		arguments = { "Delay", "Task to Activate" },
		typeArguments = { DocArgType.INT, DocArgType.INT }
		)
public class QuestEvent extends DelayedQuestEvent {
	
	private long milliseconds;
	private int tasktotrigger;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theminequest.MineQuest.Events.QEvent#parseDetails(java.lang.String[])
	 * Basic Quest Event:
	 * [0]: delay in milliseconds
	 * [1]: task to trigger
	 */
	@Override
	public void setupArguments(String[] details) {
		milliseconds = Long.parseLong(details[0]);
		tasktotrigger = Integer.parseInt(details[1]);
	}
	
	@Override
	public long getDelay() {
		return milliseconds;
	}
	
	@Override
	public boolean delayedConditions() {
		return true;
	}
	
	@Override
	public CompleteStatus action() {
		return CompleteStatus.SUCCESS;
	}
	
	@Override
	public Integer switchTask() {
		return tasktotrigger;
	}
	
}
