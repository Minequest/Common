package com.theminequest.common.impl.v1parser;

import java.util.List;
import java.util.Map;

import com.theminequest.api.Managers;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.requirement.QuestRequirement;
import com.theminequest.common.quest.v1.V1Parser;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Requirement",
		description = "Requirement Handler.",
		arguments = { },
		typeArguments = { },
		hide = true
		)
public class RequirementHandler implements V1Parser {
	
	/*
	 * Requirement:ID:Name:Details
	 */
	@Override
	public void handle(QuestDetails q, List<String> line) {
		int number = Integer.parseInt(line.remove(0).trim());
		String name = line.remove(0).trim();
		String details = "";
		for (String s : line)
			details += s + ":";
		if (details.length() != 0)
			details = details.substring(0, details.length() - 1);
		Map<Integer, QuestRequirement> reqs = q.getProperty(QuestDetails.QUEST_REQUIREMENTDETAILS);
		QuestRequirement req = Managers.getRequirementManager().construct(name.trim(), number, details.split(":"));
		if (req != null)
			reqs.put(number, req);
		else
			Managers.logf("[Quest] Retrieving requirement %s for detail %s failed!", number, q.getName());
	}
	
}
