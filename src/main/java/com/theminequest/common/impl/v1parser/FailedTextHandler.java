package com.theminequest.common.impl.v1parser;

import java.util.List;

import com.theminequest.api.quest.QuestDetails;
import com.theminequest.common.quest.v1.V1Parser;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "FailedText",
		description = "Text that is shown on a player failing the quest.",
		arguments = { "Failure Text" },
		typeArguments = { DocArgType.STRING }
		)
public class FailedTextHandler implements V1Parser {
	
	@Override
	public void handle(QuestDetails q, List<String> line) {
		q.setProperty(QuestDetails.QUEST_FAIL, line.get(0));
	}
	
}
