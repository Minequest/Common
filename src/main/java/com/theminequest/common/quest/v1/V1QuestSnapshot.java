/*
 * This file is part of Common Implementation + API for MineQuest, This provides the common classes for the MineQuest official implementation of the API..
 * Common Implementation + API for MineQuest is licensed under GNU General Public License v3.
 * Copyright (C) The MineQuest Team
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
package com.theminequest.common.quest.v1;

import com.theminequest.api.quest.Quest;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.quest.QuestSnapshot;

public class V1QuestSnapshot implements QuestSnapshot {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1004431884983310464L;
	private QuestDetails details;
	private int lasttask;
	private String owner;
	
	public V1QuestSnapshot(Quest quest) {
		details = quest.getDetails();
		if (quest.getActiveTask() != null)
			lasttask = quest.getActiveTask().getTaskID();
		else
			lasttask = -1;
		owner = quest.getQuestOwner();
	}
	
	@Override
	public int compareTo(QuestSnapshot o) {
		return details.compareTo(o.getDetails());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof V1QuestSnapshot))
			return false;
		V1QuestSnapshot s = (V1QuestSnapshot) obj;
		return (details.equals(s.getDetails()) && owner.equals(s.getQuestOwner()));
	}
	
	@Override
	public int hashCode() {
		int baseHash = 933;
		baseHash = 31 * baseHash + details.hashCode();
		baseHash = 31 * baseHash + owner.hashCode();
		return baseHash;
	}

	@Override
	public QuestDetails getDetails() {
		return details;
	}
	
	@Override
	public int getLastTaskID() {
		return lasttask;
	}
	
	@Override
	public String getQuestOwner() {
		return owner;
	}
	
	@Override
	public Quest recreateQuest() {
		return new V1Quest(-1, getDetails(), getQuestOwner());
	}
	
}
