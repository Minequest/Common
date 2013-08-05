package com.theminequest.common.impl.v1parser;

import java.util.List;
import java.util.Map;

import com.theminequest.api.Managers;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.requirement.QuestRequirement;
import com.theminequest.common.quest.v1.V1Parser;

@Deprecated
public class RepeatableHandler implements V1Parser {
	
	@Override
	public void handle(QuestDetails q, List<String> line) {
		if (line.get(0).equalsIgnoreCase("false")) {
			Map<Integer, QuestRequirement> reqs = q.getProperty(QuestDetails.QUEST_REQUIREMENTDETAILS);
			QuestRequirement req = Managers.getRequirementManager().construct("NotRepeatableRequirement", -1, q, "");
			reqs.put(-1, req);
			List<Integer> getreqs = q.getProperty(QuestDetails.QUEST_GETREQUIREMENTS);
			getreqs.add(-1);
		}
	}
	
}
