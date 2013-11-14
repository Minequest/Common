package com.theminequest.common.impl.v1parser;

import java.util.List;
import java.util.Map;

import com.theminequest.api.Managers;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.requirement.QuestRequirement;
import com.theminequest.common.quest.v1.V1Parser;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@Deprecated
@V1Documentation(
		type = "Repeatable",
		description = "Determine whether this quest is repeatable.",
		arguments = { "Repeatable?" },
		typeArguments = { DocArgType.BOOL }
		)
public class RepeatableHandler implements V1Parser {
	
	@Override
	public void handle(QuestDetails q, List<String> line) {
		if (line.get(0).equalsIgnoreCase("false")) {
			Map<Integer, QuestRequirement> reqs = q.getProperty(QuestDetails.QUEST_REQUIREMENTDETAILS);
			QuestRequirement req = Managers.getRequirementManager().construct("NotRepeatableRequirement", -1, new String[0]);
			reqs.put(-1, req);
			List<Integer> getreqs = q.getProperty(QuestDetails.QUEST_GETREQUIREMENTS);
			getreqs.add(-1);
		}
	}
	
}
