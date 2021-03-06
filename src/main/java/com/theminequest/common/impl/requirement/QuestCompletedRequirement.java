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

import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.requirement.QuestRequirement;
import com.theminequest.api.statistic.LogStatus;
import com.theminequest.api.statistic.QuestStatisticUtils;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Requirement",
		ident = "QuestCompletedRequirement",
		description = "Pass only players that have completed a quest.",
		arguments = { "Quest" },
		typeArguments = { DocArgType.STRING }
		)
public class QuestCompletedRequirement extends QuestRequirement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8495764495250951034L;
	private String questToCheck;
	
	@Override
	public void parseDetails(String[] details) {
		questToCheck = details[0];
	}
	
	@Override
	public boolean isSatisfied(QuestDetails details, MQPlayer player) {
		LogStatus ls = QuestStatisticUtils.hasQuest(player.getName(), questToCheck);
		return ls == LogStatus.COMPLETED;
	}
	
}
