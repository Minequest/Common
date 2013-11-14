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
import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.api.quest.event.DelayedQuestEvent;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Event",
		ident = "HealthEvent",
		description = "Set the health of the whole party to a certain amount.",
		arguments = { "Delay", "Percentile of Health (0-100)" },
		typeArguments = { DocArgType.INT, DocArgType.FLOAT }
		)
public class HealthEvent extends DelayedQuestEvent {
	
	private long delay;
	private double percentile;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theminequest.MineQuest.Events.QEvent#parseDetails(java.lang.String[])
	 * Details:
	 * [0] DELAY in MS
	 * [1] Percentile of max health
	 */
	@Override
	public void setupArguments(String[] details) {
		delay = Long.parseLong(details[0]);
		percentile = Double.parseDouble(details[1]);
		if (percentile > 100)
			percentile = 100;
		else if (percentile < 0)
			percentile = 0;
	}
	
	@Override
	public long getDelay() {
		return delay;
	}
	
	@Override
	public boolean delayedConditions() {
		return true;
	}
	
	@Override
	public CompleteStatus action() {
		Group g = Managers.getGroupManager().get(getQuest());
		for (MQPlayer p : g.getMembers())
			p.setHealth(p.getMaxHealth() * (percentile/100));
		return CompleteStatus.SUCCESS;
	}
	
	@Override
	public Integer switchTask() {
		return null;
	}
	
}
