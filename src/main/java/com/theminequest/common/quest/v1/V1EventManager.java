/*
 * This file is part of Common Implementation + API for MineQuest, This provides the common classes for the MineQuest official implementation of the API..
 * Common Implementation + API for MineQuest is licensed under GNU General Public License v3.
 * Copyright (C) The MineQuest Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.theminequest.common.quest.v1;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;

import com.theminequest.api.Managers;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.quest.QuestTask;
import com.theminequest.api.quest.event.QuestEvent;
import com.theminequest.common.impl.event.CompleteQuestEvent;
import com.theminequest.common.impl.event.FailQuestEvent;
import com.theminequest.common.impl.event.HealthEvent;
import com.theminequest.common.impl.event.MessageEvent;
import com.theminequest.common.impl.event.NameEvent;
import com.theminequest.common.impl.event.QuestGiveEvent;
import com.theminequest.common.impl.event.QuestStartEvent;
import com.theminequest.common.impl.event.RequirementEvent;
import com.theminequest.common.impl.event.RewardCmdEvent;
import com.theminequest.common.impl.event.RewardItemEvent;
import com.theminequest.common.impl.event.TaskEvent;
import com.theminequest.common.quest.event.EventManager;

public class V1EventManager implements EventManager {
	
	private LinkedHashMap<String, Class<? extends QuestEvent>> classes;
	private List<QuestEvent> activeevents;
	private Runnable activechecker;
	private volatile boolean stop;
	
	public V1EventManager() {
		Managers.log("[Common|V1EventManager] Starting...");
		classes = new LinkedHashMap<String, Class<? extends QuestEvent>>(0);
		activeevents = new CopyOnWriteArrayList<QuestEvent>();
		stop = false;
		activechecker = new Runnable() {
			
			@Override
			public void run() {
				while (!stop) {
					checkAllEvents();
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			}
			
		};
		Thread t = new Thread(activechecker);
		t.setDaemon(true);
		t.setName("MineQuest-V1EventManager");
		t.start();
		
		addEvent(CompleteQuestEvent.class);
		addEvent(FailQuestEvent.class);
		addEvent(HealthEvent.class);
		addEvent(MessageEvent.class);
		addEvent(NameEvent.class);
		addEvent(com.theminequest.common.impl.event.QuestEvent.class);
		addEvent(QuestGiveEvent.class);
		addEvent(QuestStartEvent.class);
		addEvent(RequirementEvent.class);
		addEvent(RewardCmdEvent.class);
		addEvent(RewardItemEvent.class);
		addEvent(TaskEvent.class);
	}

	public void dismantleRunnable() {
		stop = true;
	}
	
	private void addEvent(Class<? extends QuestEvent> event) {
		addEvent(event.getSimpleName(), event);
	}

	@Override
	public void addEvent(String eventname, Class<? extends QuestEvent> event) {
		if (classes.containsKey(eventname.toLowerCase()))
			throw new IllegalArgumentException("Another class is using this name!");
		try {
			event.getConstructor();
		} catch (Exception e) {
			throw new IllegalArgumentException("Constructor tampered with!");
		}
		classes.put(eventname.toLowerCase(), event);
	}
	
	@Override
	public QuestEvent constructEvent(String eventname, QuestTask task, int eventID, String[] arguments) {
		if (!classes.containsKey(eventname.toLowerCase()))
			return null;
		Class<? extends QuestEvent> cl = classes.get(eventname.toLowerCase());
		try {
			QuestEvent e = cl.getConstructor().newInstance();
			e.setupProperties(task, eventID, arguments);
			return e;
		} catch (Exception e) {
			Managers.logf(Level.SEVERE, "[Common|V1EventManager] In retrieving event %s from %s/%s:", eventname, task.getQuest().getQuestOwner(), task.getQuest().getDetails().getName());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void registerEventListeners(Collection<QuestEvent> events) {
		activeevents.addAll(events);
	}
	
	@Override
	public void deregisterEventListener(QuestEvent e) {
		activeevents.remove(e);
	}

	@Override
	public void checkAllEvents() {
		for (QuestEvent e : activeevents) {
			try {
				e.check();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}
	
}
