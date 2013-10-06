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
import com.theminequest.api.group.Group;
import com.theminequest.api.group.GroupException;
import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.api.quest.event.DelayedQuestEvent;
import com.theminequest.api.statistic.LogStatus;
import com.theminequest.api.statistic.QuestStatisticUtils;
import com.theminequest.api.statistic.QuestStatisticUtils.QSException;

public class QuestStartEvent extends DelayedQuestEvent {
	
	private long delay;
	private String questName;
	private boolean block;
	private int nextTask;
	
	private CompleteStatus completeStatus;
	
	private boolean setup;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theminequest.MineQuest.Events.QEvent#parseDetails(java.lang.String[])
	 * Details:
	 * [0] DELAY in MS
	 * [1] questname to start
	 * [2] block on completion
	 * [3] next task
	 */
	@Override
	public void setupArguments(String[] details) {
		if (getQuest().isInstanced())
			throw new IllegalArgumentException("Cannot utilise this event on an instanced quest!");
		
		delay = Long.parseLong(details[0]);
		questName = details[1];
		block = Boolean.parseBoolean(details[2]);
		nextTask = Integer.parseInt(details[3]);
		
		completeStatus = CompleteStatus.SUCCESS;
		setup = false;
		
		// check if questName exists
		if (Managers.getQuestManager().getDetails(questName) == null)
			throw new IllegalArgumentException("No such quest " + questName);
	}
	
	@Override
	public long getDelay() {
		return delay;
	}
	
	@Override
	public boolean delayedConditions() {
		
		String questOwner = getQuest().getQuestOwner();
		
		if (!setup) {
			
			Group group = Managers.getGroupManager().get(getQuest());
			
			MQPlayer owner = Managers.getPlatform().getPlayer(questOwner);
			
			// Only give if owner is available
			if (owner != null && group.contains(owner)) {
				
				LogStatus status = QuestStatisticUtils.hasQuest(questOwner, questName);
				
				if (status != LogStatus.GIVEN && status != LogStatus.ACTIVE) {
					try {
						QuestStatisticUtils.giveQuest(questOwner, questName);
					} catch (QSException e) {
					}
				}
				
				if (status != LogStatus.ACTIVE) {
					try {
						group.startQuest(Managers.getQuestManager().getDetails(questName));
					} catch (GroupException e) {
						throw new RuntimeException(e);
					}
				}
			} else {
				block = false;
				completeStatus = CompleteStatus.FAIL;
			}
			
			setup = true;
		} else {
			LogStatus status = QuestStatisticUtils.hasQuest(questOwner, questName);
			
			switch (status) {
			case COMPLETED:
				completeStatus = CompleteStatus.SUCCESS;
				block = false;
				break;
			case FAILED:
				completeStatus = CompleteStatus.FAIL;
				block = false;
				break;
			case UNKNOWN:
				throw new RuntimeException("UNKNOWN status given instead of expected status ACTIVE, COMPLETED, FAILED, GIVEN");
			default:
				break;
			}

		}
		
		return !block;
	}
	
	@Override
	public CompleteStatus action() {
		return completeStatus;
	}
	
	@Override
	public Integer switchTask() {
		return nextTask;
	}
	
}
