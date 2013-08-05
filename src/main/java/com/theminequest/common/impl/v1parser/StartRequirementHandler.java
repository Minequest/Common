package com.theminequest.common.impl.v1parser;

import java.util.List;

import com.theminequest.api.quest.QuestDetails;
import com.theminequest.common.quest.v1.V1Parser;

public class StartRequirementHandler implements V1Parser {
	
	@Override
	public void handle(QuestDetails q, List<String> line) {
		List<Integer> startreqs = q.getProperty(QuestDetails.QUEST_STARTREQUIREMENTS);
		for (String l : line)
			for (String s : l.split(","))
				startreqs.add(Integer.parseInt(s));
	}
	
}
