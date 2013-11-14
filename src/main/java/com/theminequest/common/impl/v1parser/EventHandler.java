package com.theminequest.common.impl.v1parser;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.theminequest.api.quest.QuestDetails;
import com.theminequest.common.quest.v1.V1Parser;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Event",
		description = "Event Handler.",
		arguments = { },
		typeArguments = { },
		hide = true
		)
public class EventHandler implements V1Parser {
	
	@Override
	public void handle(QuestDetails q, List<String> line) {
		Iterator<String> iter = line.iterator();
		int number = Integer.parseInt(iter.next());
		StringBuilder details = new StringBuilder();
		
		String eventname = iter.next();
		details.append(eventname);
		
		while (iter.hasNext()) {
			details.append(':');
			details.append(iter.next());
		}
		Map<Integer, String> events = q.getProperty(QuestDetails.QUEST_EVENTS);
		events.put(number, details.toString());
	}
	
}
