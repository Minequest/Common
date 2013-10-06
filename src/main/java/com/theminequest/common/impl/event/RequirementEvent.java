/*
 * This file is part of MineQuest, The ultimate MMORPG plugin!.
 * MineQuest is licensed under GNU General Public License v3.
 * Copyright (C) 2012 The MineQuest Team
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.theminequest.common.impl.event;

import com.theminequest.api.CompleteStatus;
import com.theminequest.api.Managers;
import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.api.quest.QuestDetailsUtils;
import com.theminequest.api.quest.event.DelayedQuestEvent;

public class RequirementEvent extends DelayedQuestEvent {
	
	private long delay;
	private int taskifmet;
	private int taskifnotmet;
	private int tasktosend;
	private int requirementid;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theminequest.MineQuest.API.Events.QuestEvent#parseDetails(java.lang
	 * .String[])
	 * [0] delay
	 * [1] Requirement ID
	 * [2] task id if met
	 * [3] task id if not met
	 */
	@Override
	public void setupArguments(String[] details) {
		delay = Long.parseLong(details[0]);
		requirementid = Integer.parseInt(details[1]);
		taskifmet = Integer.parseInt(details[2]);
		taskifnotmet = Integer.parseInt(details[3]);
		tasktosend = -1;
	}
	
	@Override
	public boolean delayedConditions() {
		return true;
	}
	
	@Override
	public CompleteStatus action() {
		MQPlayer leader = Managers.getGroupManager().get(getQuest()).getLeader();
		if (QuestDetailsUtils.requirementMet(getQuest().getDetails(), requirementid, leader))
			tasktosend = taskifmet;
		else
			tasktosend = taskifnotmet;
		return CompleteStatus.SUCCESS;
	}
	
	@Override
	public Integer switchTask() {
		return tasktosend;
	}

	@Override
	public long getDelay() {
		return delay;
	}
	
}
