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

import java.util.Arrays;

import com.theminequest.api.platform.MQPlayer;
import com.theminequest.api.requirement.QuestRequirement;

public class PlayerRequirement extends QuestRequirement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8085862073374143696L;
	private String[] players;
	
	@Override
	public void parseDetails(String[] details) {
		players = details;
		Arrays.sort(players);
		for (int i = 0; i < players.length; i++)
			players[i] = players[i].toLowerCase();
	}
	
	@Override
	public boolean isSatisfied(MQPlayer player) {
		return Arrays.binarySearch(players, player.getName().toLowerCase()) >= 0;
	}
	
}
