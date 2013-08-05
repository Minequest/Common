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

import com.theminequest.api.platform.MQPlayer;
import com.theminequest.api.requirement.QuestRequirement;
import com.theminequest.api.statistic.LogStatus;
import com.theminequest.api.statistic.QuestStatisticUtils;

public class QuestFailedRequirement extends QuestRequirement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -947407723002529492L;
	private String questToCheck;
	
	@Override
	public void parseDetails(String[] details) {
		questToCheck = details[0];
	}
	
	@Override
	public boolean isSatisfied(MQPlayer player) {
		LogStatus ls = QuestStatisticUtils.hasQuest(player.getName(), questToCheck);
		return ls == LogStatus.FAILED;
	}
	
}
