package com.theminequest.common.impl.v1parser;

import java.util.List;
import java.util.Map;

import com.theminequest.api.quest.QuestDetails;
import com.theminequest.common.quest.v1.V1Parser;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Task",
		description = "Define a task.",
		arguments = { "Unique ID", "Tasks (comma-delimited)" },
		typeArguments = { DocArgType.INT, DocArgType.STRING }
		)
public class TaskHandler implements V1Parser {
	
	@Override
	public void handle(QuestDetails q, List<String> line) {
		int id = Integer.parseInt(line.get(0));
		String[] e = line.get(1).split(",");
		Map<Integer, String[]> tasks = q.getProperty(QuestDetails.QUEST_TASKS);
		tasks.put(id, e);
	}
	
}
