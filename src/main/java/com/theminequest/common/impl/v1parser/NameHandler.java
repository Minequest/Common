package com.theminequest.common.impl.v1parser;

import java.util.List;

import com.theminequest.api.quest.QuestDetails;
import com.theminequest.common.quest.v1.V1Parser;

public class NameHandler implements V1Parser {
	
	@Override
	public void handle(QuestDetails q, List<String> line) {
		q.setProperty(QuestDetails.QUEST_DISPLAYNAME, line.get(0));
	}
	
}
