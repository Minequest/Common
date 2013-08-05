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
package com.theminequest.common.impl.requirement;

import com.theminequest.api.Managers;
import com.theminequest.api.group.Group;
import com.theminequest.api.platform.MQPlayer;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.requirement.QuestRequirement;

public class SmallGroupSizeRequirement extends QuestRequirement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6251975731620195546L;
	private int size;
	
	@Override
	public void parseDetails(String[] details) {
		size = Integer.parseInt(details[0]);
	}
	
	@Override
	public boolean isSatisfied(MQPlayer player) {
		Boolean isInstanced = getDetails().getProperty(QuestDetails.QUEST_LOADWORLD);
		if (!isInstanced)
			return true;
		
		Group gsg = Managers.getGroupManager().get(player);
		return (gsg.getMembers().size() <= size);
	}
	
}
