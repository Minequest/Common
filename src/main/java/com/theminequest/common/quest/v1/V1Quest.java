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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import com.theminequest.api.CompleteStatus;
import com.theminequest.api.Managers;
import com.theminequest.api.group.Group;
import com.theminequest.api.group.GroupManager;
import com.theminequest.api.platform.event.QuestCompleteEvent;
import com.theminequest.api.platform.event.QuestStartedEvent;
import com.theminequest.api.platform.event.TaskCompleteEvent;
import com.theminequest.api.quest.Quest;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.quest.QuestSnapshot;
import com.theminequest.api.quest.QuestTask;
import com.theminequest.api.quest.QuestUtils;
import com.theminequest.api.statistic.QuestStatisticUtils;
import com.theminequest.api.util.SetUtils;
import com.theminequest.common.quest.MutableQuestDetails;

public class V1Quest implements Quest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7904219637011746046L;
	
	private QuestDetails details;
	
	private long questid;
	
	private CompleteStatus finished;
	private QuestTask activeTask;
	
	private String questOwner;
		
	protected V1Quest(long questid, QuestDetails id, String questOwner) {
		details = id;
		this.questid = questid;
		activeTask = null;
		this.questOwner = questOwner;
		
		// load the world if necessary/move team to team leader
		String world = details.getProperty(QuestDetails.QUEST_WORLD);
		
		if (!Managers.getPlatform().hasWorld(world))
			Managers.getPlatform().loadWorld(world, (int) details.getProperty(QuestDetails.QUEST_WORLDFLAGS));
		
		if (details.getProperty(QuestDetails.QUEST_LOADWORLD)) {
			world = Managers.getPlatform().copyWorld(world);
			if (!Managers.getPlatform().hasWorld(world))
				Managers.getPlatform().loadWorld(world, (int) details.getProperty(QuestDetails.QUEST_WORLDFLAGS));
			details.setProperty(QuestDetails.QUEST_WORLD, world);
		} else
			details = new MutableQuestDetails(this);
				
		// plugins should use QuestStartedEvent to setup their properties
		// inside the quest.
		QuestStartedEvent event = new QuestStartedEvent(this);
		Managers.getPlatform().callEvent(event);
	}
	
	@Override
	public synchronized void startQuest() {
		Map<Integer, String[]> tasks = details.getProperty(QuestDetails.QUEST_TASKS);
		startTask(SetUtils.getFirstKey(tasks.keySet()));
	}
	
	/**
	 * Start a task of the quest.
	 * 
	 * @param taskid
	 *            task to start
	 * @return true if task was started successfully
	 */
	@Override
	public synchronized void startTask(int taskid) {
		Map<Integer, String[]> tasks = details.getProperty(QuestDetails.QUEST_TASKS);
		if (taskid == -1) {
			finishQuest(CompleteStatus.SUCCESS);
			return;
		}
		
		boolean detailsToggle = false;
		if (details.getProperty(V1QuestDetails.V1_OLDSTYLETASK) != null)
			detailsToggle = details.getProperty(V1QuestDetails.V1_OLDSTYLETASK);
		
		if (!tasks.containsKey(taskid)) {
			Managers.logf(Level.SEVERE, "[Common|V1Quest] Starting task %s failed for %s/%s!", taskid, getQuestOwner(), getDetails().getName());
			finishQuest(CompleteStatus.ERROR);
			return;
		}
				
		if (activeTask != null && !detailsToggle) {
			if (activeTask.isComplete() == null)
				activeTask.cancelTask();
						
			if (activeTask.getTaskID() == taskid) {
				Managers.logf(Level.SEVERE, "[Common|V1Quest] Tried to start already running task %s for %s/%s!", taskid, getQuestOwner(), getDetails().getName());
				finishQuest(CompleteStatus.ERROR);
				return;
			}
		}
				
		String[] eventnums = tasks.get(taskid);
		Set<Integer> eventnum = new HashSet<Integer>();
		for (String e : eventnums)
			eventnum.add(Integer.parseInt(e));
		
		if (detailsToggle)
			activeTask = new V1Task(this, taskid, eventnum, (V1Task) activeTask);
		else
			activeTask = new V2Task(this, taskid, eventnum);
		
		activeTask.start();
		
		// main world quest
		if (questid == -1)
			QuestStatisticUtils.checkpointQuest(this);
	}
	
	@Override
	public boolean isInstanced() {
		return details.getProperty(QuestDetails.QUEST_LOADWORLD);
	}
	
	@Override
	public synchronized QuestTask getActiveTask() {
		return activeTask;
	}
	
	@Override
	public synchronized void finishQuest(CompleteStatus c) {
		finished = c;
		if ((activeTask != null) && (activeTask.isComplete() == null))
			activeTask.cancelTask();
		activeTask = null;
		
		GroupManager qGM = Managers.getGroupManager();
		Group g = qGM.get(this);
		
		Managers.getQuestManager().completeQuest(this);
		
		QuestCompleteEvent event = new QuestCompleteEvent(this, c, g);
		Managers.getPlatform().callEvent(event);
	}
	
	@Override
	public void cleanupQuest() {
		if (details.getProperty(QuestDetails.QUEST_LOADWORLD))
			Managers.getPlatform().destroyWorld((String) details.getProperty(QuestDetails.QUEST_WORLD), true);
		activeTask = null;
		questOwner = null;
		questid = -1;
	}
	
	@Override
	public synchronized CompleteStatus isFinished() {
		return finished;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		if (arg0 == null)
			return false;
		if (!(arg0 instanceof Quest))
			return false;
		Quest q = (Quest) arg0;
		return (q.getQuestID() == questid) && (q.getQuestOwner().equals(getQuestOwner()) && q.getDetails().equals(getDetails()));
	}
	
	@Override
	public int hashCode() {
		int baseHash = 564;
		baseHash = (int) (31 * baseHash + (questid % Integer.MAX_VALUE));
		baseHash = 31 * baseHash + getQuestOwner().hashCode();
		baseHash = 31 * baseHash + getDetails().hashCode();
		return baseHash;
	}

	@Override
	public synchronized String toString() {
		return details.toString() + ":" + getQuestOwner() + ":" + getQuestID();
	}
	
	@Override
	public int compareTo(Quest arg0) {
		return ((Long) getQuestID()).compareTo(arg0.getQuestID());
	}
	
	@Override
	public long getQuestID() {
		return questid;
	}
	
	@Override
	public QuestDetails getDetails() {
		return details;
	}

	@Override
	public String getQuestOwner() {
		return questOwner;
	}
	
	@Override
	public QuestSnapshot createSnapshot() {
		return new V1QuestSnapshot(this);
	}
	
	@Override
	public void completeTask(QuestTask task, CompleteStatus status, int nextTask) {
		TaskCompleteEvent event = new TaskCompleteEvent(task);
		Managers.getPlatform().callEvent(event); // should this be deferred? FIXME
		
		switch (status) {
		case CANCELED:
		case IGNORE:
			return;
		case FAIL:
		case ERROR:
			finishQuest(status);
		default:
			if (nextTask == -1)
				finishQuest(status);
			else if (nextTask != -2)
				startTask(nextTask);
			else
				startTask(QuestUtils.getNextTask(this));
		}
	}
	
}
