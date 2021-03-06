package com.theminequest.common.impl.v1parser;

import java.util.List;

import com.theminequest.api.quest.QuestDetails;
import com.theminequest.common.quest.v1.V1Parser;
import com.theminequest.common.quest.v1.V1QuestDetails;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "V1",
		description = "Run this quest according to old V1 specifications.",
		arguments = { },
		typeArguments = { }
		)
public class OldV1Handler implements V1Parser {
	
	@Override
	public void handle(QuestDetails q, List<String> line) {
		q.setProperty(V1QuestDetails.V1_OLDSTYLETASK, Boolean.parseBoolean(line.get(0)));
	}
	
}
