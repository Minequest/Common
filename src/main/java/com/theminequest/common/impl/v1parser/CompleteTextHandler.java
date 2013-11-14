package com.theminequest.common.impl.v1parser;

import java.util.List;

import com.theminequest.api.quest.QuestDetails;
import com.theminequest.common.quest.v1.V1Parser;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "CompleteText",
		description = "Text that is shown on a player completing the quest.",
		arguments = { "Complete Text" },
		typeArguments = { DocArgType.STRING }
		)
public class CompleteTextHandler implements V1Parser {
	
	@Override
	public void handle(QuestDetails q, List<String> line) {
		q.setProperty(QuestDetails.QUEST_COMPLETE, line.get(0));
	}
	
}
