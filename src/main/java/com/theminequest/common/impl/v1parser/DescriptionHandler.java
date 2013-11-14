package com.theminequest.common.impl.v1parser;

import java.util.List;

import com.theminequest.api.quest.QuestDetails;
import com.theminequest.common.quest.v1.V1Parser;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Description",
		description = "Quest Description.",
		arguments = { "Description" },
		typeArguments = { DocArgType.STRING }
		)
public class DescriptionHandler implements V1Parser {
	
	@Override
	public void handle(QuestDetails q, List<String> line) {
		q.setProperty(QuestDetails.QUEST_DESCRIPTION, line.get(0));
	}
	
}
