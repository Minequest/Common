package com.theminequest.common.impl.v1parser;

import java.util.List;

import com.theminequest.api.quest.QuestDetails;
import com.theminequest.common.quest.v1.V1Parser;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Reset",
		description = "Reset the player's spawn point during the quest.",
		arguments = { "Reset?" },
		typeArguments = { DocArgType.BOOL }
		)
public class ResetHandler implements V1Parser {
	
	@Override
	public void handle(QuestDetails q, List<String> line) {
		q.setProperty(QuestDetails.QUEST_SPAWNRESET, line.get(0).equalsIgnoreCase("true"));
	}
	
}
