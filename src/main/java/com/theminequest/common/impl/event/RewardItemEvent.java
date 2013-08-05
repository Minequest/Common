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

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.theminequest.api.CompleteStatus;
import com.theminequest.api.Managers;
import com.theminequest.api.group.Group;
import com.theminequest.api.platform.MQItemStack;
import com.theminequest.api.platform.MQMaterial;
import com.theminequest.api.platform.MQPlayer;
import com.theminequest.api.quest.event.QuestEvent;

public class RewardItemEvent extends QuestEvent {
	
	private int taskid;
	private LinkedHashMap<MQMaterial, Integer> items = new LinkedHashMap<MQMaterial, Integer>();
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theminequest.MineQuest.Events.QEvent#parseDetails(java.lang.String[])
	 * [n] itemid,qty
	 */
	@Override
	public void setupArguments(String[] details) {
		taskid = Integer.parseInt(details[0]);
		for (int i = 1; i < details.length; i++) {
			String[] d = details[i].split(",");
			items.put(Managers.getPlatform().findMaterial(d[0].trim()), Integer.parseInt(d[1].trim()));
		}
	}
	
	@Override
	public boolean conditions() {
		return true;
	}
	
	@Override
	public CompleteStatus action() {
		Group g = Managers.getGroupManager().get(getQuest());
		for (MQPlayer p : g.getMembers())
			for (Entry<MQMaterial, Integer> item : items.entrySet())
				p.getInventory().add(new MQItemStack(item.getKey(), item.getValue()));
		return CompleteStatus.SUCCESS;
	}
	
	@Override
	public Integer switchTask() {
		return taskid;
	}
	
}
