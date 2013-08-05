package com.theminequest.common.impl.v1parser;

import java.util.List;

import com.theminequest.api.quest.QuestDetails;
import com.theminequest.common.quest.v1.V1Parser;

public class GetRequirementHandler implements V1Parser {
	
	@Override
	public void handle(QuestDetails q, List<String> line) {
		List<Integer> getreqs = q.getProperty(QuestDetails.QUEST_GETREQUIREMENTS);
		for (String l : line)
			for (String s : l.split(","))
				getreqs.add(Integer.parseInt(s));
	}
	
}
