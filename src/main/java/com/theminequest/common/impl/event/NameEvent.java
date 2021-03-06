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
import com.theminequest.api.quest.event.QuestEvent;
import com.theminequest.api.quest.event.UserQuestEvent;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

/**
 * Make an entry appear in Current Tasks. This allows
 * for quest makers to manually specify a task in Current Tasks for
 * the player to accomplish. <b>This does NOT block
 * automatic completion of events due to a manual
 * specification inside V2Task.</b>
 */
@V1Documentation(
		type = "Event",
		ident = "NameEvent",
		description = "Add an entry to Current Tasks.",
		arguments = { "Task" },
		typeArguments = { DocArgType.STRING }
		)
public class NameEvent extends QuestEvent implements UserQuestEvent {
	
	private String task;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theminequest.MineQuest.Events.QEvent#parseDetails(java.lang.String[])
	 * [0] task
	 */
	@Override
	public void setupArguments(String[] details) {
		task = details[0];
	}
	
	@Override
	public boolean conditions() {
		return false;
	}
	
	@Override
	public CompleteStatus action() {
		return CompleteStatus.IGNORE;
	}
	
	@Override
	public Integer switchTask() {
		return null;
	}
	
	@Override
	public String getDescription() {
		return task;
	}
	
}
