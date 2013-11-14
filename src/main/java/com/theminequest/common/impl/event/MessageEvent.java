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

import static com.theminequest.common.util.I18NMessage._;

import com.theminequest.api.CompleteStatus;
import com.theminequest.api.Managers;
import com.theminequest.api.group.Group;
import com.theminequest.api.platform.ChatColor;
import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.api.quest.event.DelayedQuestEvent;
import com.theminequest.api.util.ChatUtils;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Event",
		ident = "MessageEvent",
		description = "Message the entire party on the quest.",
		arguments = { "Delay", "Message to Send" },
		typeArguments = { DocArgType.INT, DocArgType.STRING }
		)
public class MessageEvent extends DelayedQuestEvent {
	
	private String message;
	private int delay;
	
	@Override
	public void setupArguments(String[] details) {
		delay = Integer.parseInt(details[0]);
		StringBuilder message = new StringBuilder();
		for (int i = 1; i < details.length; i++) {
			message.append(details[i]);
			if (i != (details.length - 1))
				message.append(':');
		}
		this.message = ChatUtils.colorize(message.toString());
	}
	
	@Override
	public boolean delayedConditions() {
		return true;
	}
	
	@Override
	public CompleteStatus action() {
		Group g = Managers.getGroupManager().get(getQuest());
		for (MQPlayer p : g.getMembers())
			p.sendMessage(ChatColor.YELLOW() + "[" + _("Quest") + "] " + ChatColor.WHITE() + message);
		return CompleteStatus.SUCCESS;
	}
	
	@Override
	public Integer switchTask() {
		return null;
	}
	
	@Override
	public long getDelay() {
		return delay;
	}
	
}
