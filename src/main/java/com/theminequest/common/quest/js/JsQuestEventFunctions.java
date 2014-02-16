package com.theminequest.common.quest.js;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.mozilla.javascript.Scriptable;

import com.theminequest.api.quest.event.QuestEvent;
import com.theminequest.api.quest.event.TargetedQuestEvent;
import com.theminequest.common.Common;

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
	 * result = event.result(eventOneID) // waits until event finishes, then
	 * passes result
	 */
	
	private JsTask task;
	
	private int nextID;
	private Object lock;
	
	private HashMap<Integer, QuestEvent> events;
	
	private HashSet<Integer> waiting;
	private volatile boolean waitAny;
	
	public JsQuestEventFunctions(JsTask task) {
		this.task = task;
		
		this.nextID = 0;
		this.lock = new Object();
		
		this.events = new HashMap<Integer, QuestEvent>();
		this.waiting = new HashSet<Integer>();
		this.waitAny = false;
	}
	
	/**
	 * Create an Event and retrieve the ID of the newly created event.<br/>
	 * This will NOT succeed on targeted events!
	 * 
	 * @param eventName Event Name
	 * @param arguments Arguments (varargs)
	 * @return ID, or -1 if the creation failed.
	 */
	public int create(String eventName, String... arguments) {
		int toReturn = -1;
		synchronized (lock) {
			QuestEvent eventObject = Common.getCommon().getV1EventManager().constructEvent(eventName, task, nextID, arguments);
			
			if (eventObject == null)
				return -1;
			
			if (eventObject instanceof TargetedQuestEvent)
				throw new IllegalArgumentException(eventName + " is a targeted event and cannot be instantated with create()");
			
			events.put(nextID, eventObject);
			toReturn = nextID;
			nextID++;
		}
		return toReturn;
	}
	
	public Set<Integer> waitingOn() {
		return waiting;
	}
	
	public boolean waitAny() {
		return waitAny;
	}
	
	public void setWaitAny(boolean waitAny) {
		this.waitAny = waitAny;
	}
	
	public int startAndWait(int... eventIDs) {
		for (int i : eventIDs) {
			waiting.add(i);
			events.get(i).fireEvent();
		}
	}
	
	public void start(int... ids) {
		
	}
	
	public int result(int id) {
		
	}
	
}
