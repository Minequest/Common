package com.theminequest.common.quest.requirement;

import java.util.HashMap;
import java.util.logging.Level;

import com.theminequest.api.Managers;
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
	
	private HashMap<String, Class<? extends QuestRequirement>> classes;
	
	public CommonRequirementManager() {
		Managers.log("[Requirements] Starting Manager...");
		classes = new HashMap<String, Class<? extends QuestRequirement>>();
		
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
		register(requirement.getSimpleName(), requirement);
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
	public QuestRequirement construct(String requirementName, int ID, String[] properties) {
		if (!classes.containsKey(requirementName))
			return null;
		Class<? extends QuestRequirement> cl = classes.get(requirementName);
		try {
			QuestRequirement e = cl.getConstructor().newInstance();
			e.setupProperties(ID, properties);
			return e;
		} catch (Exception e) {
			Managers.log(Level.SEVERE, "[Requirements] In creating " + requirementName + ":");
			e.printStackTrace();
			return null;
		}
	}
	
}
