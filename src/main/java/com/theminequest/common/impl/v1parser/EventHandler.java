package com.theminequest.common.impl.v1parser;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.theminequest.api.quest.QuestDetails;
import com.theminequest.common.quest.v1.V1Parser;

public class EventHandler implements V1Parser {
	
	@Override
	public void handle(QuestDetails q, List<String> line) {
		Iterator<String> iter = line.iterator();
		int number = Integer.parseInt(iter.next());
		StringBuilder details = new StringBuilder();
		
		String eventname = iter.next();
		// T = targeted event
		if (eventname.equals("T")) {
			eventname = iter.next();
			details.append(eventname);
			details.append(":T");
		} else
			details.append(eventname);
		
		while (iter.hasNext()) {
			details.append(':');
			details.append(iter.next());
		}
		Map<Integer, String> events = q.getProperty(QuestDetails.QUEST_EVENTS);
		events.put(number, details.toString());
	}
	
}
