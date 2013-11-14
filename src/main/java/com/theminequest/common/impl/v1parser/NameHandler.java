package com.theminequest.common.impl.v1parser;

import java.util.List;

import com.theminequest.api.quest.QuestDetails;
import com.theminequest.common.quest.v1.V1Parser;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Name",
		description = "Display Name - what users see.",
		arguments = { "Display Name" },
		typeArguments = { DocArgType.STRING }
		)
public class NameHandler implements V1Parser {
	
	@Override
	public void handle(QuestDetails q, List<String> line) {
		q.setProperty(QuestDetails.QUEST_DISPLAYNAME, line.get(0));
	}
	
}
