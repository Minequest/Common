package com.theminequest.common.impl.v1parser;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.theminequest.api.quest.QuestDetails;
import com.theminequest.common.quest.v1.V1Parser;

public class TargetEventHandler implements V1Parser {
	
	public static final String TARGETED_EVENT_STR = "hKI5QgeHmXMOUD4lPRPz";
	
	// TargetEvent:event#:eventname:delayMS:target#:...
	@Override
	public void handle(QuestDetails q, List<String> line) {
		Iterator<String> iter = line.iterator();
		int number = Integer.parseInt(iter.next());
		StringBuilder details = new StringBuilder();
		
		String eventname = iter.next();
		details.append(eventname);
		details.append(":" + TARGETED_EVENT_STR);
		details.append(":" + iter.next() + ":" + iter.next());
		// eventname:T:delayMS:target#:...
		
		while (iter.hasNext()) {
			details.append(':');
			details.append(iter.next());
		}
		
		Map<Integer, String> events = q.getProperty(QuestDetails.QUEST_EVENTS);
		events.put(number, details.toString());
	}
	
}
