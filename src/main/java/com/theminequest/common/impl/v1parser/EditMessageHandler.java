package com.theminequest.common.impl.v1parser;

import java.util.List;

import com.theminequest.api.platform.ChatColor;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.common.quest.v1.V1Parser;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "EditMessage",
		description = "Message to alert players that they cannot edit the dungeon.",
		arguments = { "Edit Rejection Message" },
		typeArguments = { DocArgType.STRING }
		)
public class EditMessageHandler implements V1Parser {
	
	@Override
	public void handle(QuestDetails q, List<String> line) {
		q.setProperty(QuestDetails.QUEST_EDITMESSAGE, ChatColor.GRAY() + line.get(0));
	}
	
}
