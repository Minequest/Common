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
package com.theminequest.common.quest;

import java.io.Serializable;

import com.theminequest.api.quest.Quest;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.statistic.QuestStatisticUtils;

/**
 * Represents a Mutable QuestDetail that needs to be serialized
 * upon modification to a datastore, such as a database.
 * 
 * Modifying this detail will call createSnapshot() on every call.
 * 
 */
public class MutableQuestDetails implements QuestDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3532065120496464254L;
	
	private QuestDetails details;
	private transient Quest quest;
	
	/**
	 * Initialize MutableQuestDetails as a wrapper to a Quest.
	 * 
	 * @param details
	 */
	public MutableQuestDetails(Quest quest) {
		details = quest.getDetails();
		this.quest = quest;
	}
	
	@Override
	public boolean equals(Object arg0) {
		return quest.equals(arg0);
	}
	
	@Override
	public int compareTo(QuestDetails arg0) {
		return details.compareTo(arg0);
	}
	
	@Override
	public <E> E getProperty(String key) {
		return details.getProperty(key);
	}
	
	@Override
	public <E> E setProperty(String key, Serializable property) {
		E formerProperty = details.setProperty(key, property);
		if (quest != null)
			QuestStatisticUtils.checkpointQuest(quest);
		return formerProperty;
	}
	
	@Override
	public boolean containsProperty(String key) {
		return details.containsProperty(key);
	}
	
	@Override
	public <E> E removeProperty(String key) {
		E removedProperty = details.removeProperty(key);
		if (quest != null)
			QuestStatisticUtils.checkpointQuest(quest);
		return removedProperty;
	}

	@Override
	public Quest generateQuest(long questId, String questOwner) {
		return details.generateQuest(questId, questOwner);
	}
	
}
