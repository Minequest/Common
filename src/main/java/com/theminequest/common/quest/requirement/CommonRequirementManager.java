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
package com.theminequest.common.quest.requirement;

import java.util.LinkedHashMap;
import java.util.logging.Level;

import com.theminequest.api.Managers;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.requirement.QuestRequirement;
import com.theminequest.api.requirement.RequirementManager;
import com.theminequest.common.impl.requirement.HasItemRequirement;
import com.theminequest.common.impl.requirement.LargeGroupSizeRequirement;
import com.theminequest.common.impl.requirement.NotRepeatableRequirement;
import com.theminequest.common.impl.requirement.PlayerRequirement;
import com.theminequest.common.impl.requirement.QuestCompletedRequirement;
import com.theminequest.common.impl.requirement.QuestFailedRequirement;
import com.theminequest.common.impl.requirement.RealDateRequirement;
import com.theminequest.common.impl.requirement.SmallGroupSizeRequirement;
import com.theminequest.common.impl.requirement.WorldRequirement;

public class CommonRequirementManager implements RequirementManager {
	
	private LinkedHashMap<String, Class<? extends QuestRequirement>> classes;
	
	public CommonRequirementManager() {
		Managers.log("[Requirements] Starting Manager...");
		classes = new LinkedHashMap<String, Class<? extends QuestRequirement>>();
		
		register(HasItemRequirement.class);
		register(LargeGroupSizeRequirement.class);
		register(NotRepeatableRequirement.class);
		register(PlayerRequirement.class);
		register(QuestCompletedRequirement.class);
		register(QuestFailedRequirement.class);
		register(RealDateRequirement.class);
		register(SmallGroupSizeRequirement.class);
		register(WorldRequirement.class);
		
	}
	
	private void register(Class<? extends QuestRequirement> requirement) {
		register(requirement.getName(), requirement);
	}
	
	@Override
	public void register(String reqname, Class<? extends QuestRequirement> requirement) {
		if (classes.containsKey(reqname) || classes.containsValue(requirement))
			throw new IllegalArgumentException("We already have this class!");
		try {
			requirement.getConstructor();
		} catch (Exception e) {
			throw new IllegalArgumentException("Constructor tampered with!");
		}
		classes.put(reqname, requirement);
	}
	
	@Override
	public QuestRequirement construct(String requirementName, int ID, QuestDetails details, String properties) {
		if (!classes.containsKey(requirementName))
			return null;
		Class<? extends QuestRequirement> cl = classes.get(requirementName);
		try {
			QuestRequirement e = cl.getConstructor().newInstance();
			e.setupProperties(ID, details, properties);
			return e;
		} catch (Exception e) {
			Managers.log(Level.SEVERE, "[Requirements] In creating " + requirementName + " for Quest " + details.getProperty(QuestDetails.QUEST_NAME) + ":");
			e.printStackTrace();
			return null;
		}
	}
	
}
