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
import com.theminequest.api.quest.event.DelayedQuestEvent;
import com.theminequest.api.statistic.QuestStatisticUtils;
import com.theminequest.api.statistic.QuestStatisticUtils.QSException;

public class QuestGiveEvent extends DelayedQuestEvent {
	
	private long delay;
	private String questGive;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theminequest.MineQuest.Events.QEvent#parseDetails(java.lang.String[])
	 * Details:
	 * [0] DELAY in MS
	 * [1] questname available
	 */
	@Override
	public void setupArguments(String[] details) {
		delay = Long.parseLong(details[0]);
		questGive = details[1];
	}
	
	@Override
	public long getDelay() {
		return delay;
	}
	
	@Override
	public boolean delayedConditions() {
		String questOwner = getQuest().getQuestOwner();
		
		MQPlayer owner = Managers.getPlatform().getPlayer(questOwner);
		
		// Only give if owner is available
		if (owner != null) {
			
			if (Managers.getGroupManager().get(getQuest()).contains(owner)) {
				try {
					QuestStatisticUtils.giveQuest(questOwner, questGive);
				} catch (QSException e) {}
			}
		}
		return true;
	}
	
	@Override
	public CompleteStatus action() {
		return CompleteStatus.SUCCESS;
	}
	
	@Override
	public Integer switchTask() {
		return null;
	}
	
}
