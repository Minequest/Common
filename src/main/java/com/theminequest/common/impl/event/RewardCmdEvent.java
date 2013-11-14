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

import java.util.Arrays;

import com.theminequest.api.CompleteStatus;
import com.theminequest.api.Managers;
import com.theminequest.api.group.Group;
import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.api.quest.event.QuestEvent;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Event",
		ident = "RewardCmdEvent",
		description = "Execute a set of commands for all players, replacing %p with each player.",
		arguments = { "Next Task", "Commands" },
		typeArguments = { DocArgType.INT, DocArgType.STRARRAY }
		)
public class RewardCmdEvent extends QuestEvent {
	
	private int taskid;
	private String[] cmds;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theminequest.MineQuest.Events.QEvent#parseDetails(java.lang.String[])
	 * [0] taskid
	 * [n] command
	 * * %p in commands are substituted with player name.
	 */
	@Override
	public void setupArguments(String[] details) {
		taskid = Integer.parseInt(details[0]);
		cmds = Arrays.copyOfRange(details, 1, details.length, String[].class);
	}
	
	@Override
	public boolean conditions() {
		return true;
	}
	
	@Override
	public CompleteStatus action() {
		Group g = Managers.getGroupManager().get(getQuest());
		for (MQPlayer p : g.getMembers())
			for (String s : cmds) {
				String mod = s.replace("%p", p.getName());
				Managers.getPlatform().callCommand(mod);
			}
		return CompleteStatus.SUCCESS;
	}
	
	@Override
	public Integer switchTask() {
		return taskid;
	}
	
}
