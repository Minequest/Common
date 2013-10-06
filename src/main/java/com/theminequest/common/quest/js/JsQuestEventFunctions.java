package com.theminequest.common.quest.js;

public class JsQuestEventFunctions {

	/*
	 * Implementation works in a way that allows the Js to be
	 * multithreaded. For example:
	 * 
	 * eventOneID = event.create("QuestEvent", 1, 3, varargs, etc)
	 * ^ this is how we initialise a new event
	 * 
	 * You can initialise more, then do:
	 * result = event.startAndWait(eventOneID, eventTwoID, eventThreeID, etc)
	 * ^ (if any event fails the result is fail)
	 * or:
	 * event.start(eventOneID) // to have eventOne start
	 * result = event.result(eventOneID) // waits until event finishes, then passes result
	 */
	
}
