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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.theminequest.api.quest.QuestDetails;

public abstract class CommonQuestDetails implements QuestDetails {
	
	private static final long serialVersionUID = -7652738404226858155L;
	
	private Map<String, Serializable> details;
	
	public CommonQuestDetails() {
		details = Collections.synchronizedMap(new HashMap<String, Serializable>());
	}
	
	@Override
	public int compareTo(QuestDetails arg0) {
		return ((String)details.get(QUEST_NAME)).compareTo(((String)arg0.getProperty(QUEST_NAME)));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <E> E getProperty(String key) {
		return (E) details.get(key);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <E> E setProperty(String key, Serializable property) {
		return (E) details.put(key, property);
	}
	
	@Override
	public boolean containsProperty(String key) {
		return details.containsKey(key);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <E> E removeProperty(String key) {
		return (E) details.remove(key);
	}
	
}
