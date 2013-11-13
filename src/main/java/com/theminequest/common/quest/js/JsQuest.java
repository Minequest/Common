package com.theminequest.common.quest.js;

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

public class JsQuest implements Quest {
	
	private static final long serialVersionUID = -8728278231181944187L;
	
	private long questId;
	private String questOwner;
	private JsQuestDetails details;
	
	private JsTask task;
	private CompleteStatus status;
	
	private volatile boolean called;
	
	protected JsQuest(long questId, String questOwner, JsQuestDetails details) {
		this.questId = questId;
		this.questOwner = questOwner;
		this.details = details;
		
		this.task = null;
		this.status = null;
		
		this.called = false;
		
		// load the world if necessary/move team to team leader
		String world = details.getProperty(QuestDetails.QUEST_WORLD);
		
		if (!Managers.getPlatform().hasWorld(world))
			Managers.getPlatform().loadWorld(world, (int) details.getProperty(QuestDetails.QUEST_WORLDFLAGS));
		
		world = Managers.getPlatform().copyWorld(world);
		if (!Managers.getPlatform().hasWorld(world))
			Managers.getPlatform().loadWorld(world, (int) details.getProperty(QuestDetails.QUEST_WORLDFLAGS));
		details.setProperty(QuestDetails.QUEST_WORLD, world);
		
		// plugins should use QuestStartedEvent to setup their properties
		// inside the quest.
		QuestStartedEvent event = new QuestStartedEvent(this);
		Managers.getPlatform().callEvent(event);
		
	}
	
	@Override
	public int compareTo(Quest o) {
		return ((Long) getQuestID()).compareTo(o.getQuestID());
	}
	
	@Override
	public QuestDetails getDetails() {
		return details;
	}
	
	@Override
	public QuestTask getActiveTask() {
		return task;
	}
	
	@Override
	public long getQuestID() {
		return questId;
	}
	
	@Override
	public boolean isInstanced() {
		return true;
	}
	
	@Override
	public synchronized CompleteStatus isFinished() {
		return status;
	}
	
	@Override
	public synchronized void startQuest() {
		task = new JsTask(this);
		task.start();
	}
	
	@Override
	public synchronized void finishQuest(CompleteStatus finishState) {
		status = finishState;
		if ((task != null) && (task.isComplete() == null))
			task.cancelTask();
		task = null;
		
		GroupManager qGM = Managers.getGroupManager();
		Group g = qGM.get(this);
		
		Managers.getQuestManager().completeQuest(this);
		
		QuestCompleteEvent event = new QuestCompleteEvent(this, finishState, g);
		Managers.getPlatform().callEvent(event);
	}
	
	@Override
	public void cleanupQuest() {
		Managers.getPlatform().destroyWorld((String) details.getProperty(QuestDetails.QUEST_WORLD), true);
		task = null;
		questOwner = null;
		questId = -1;
	}
	
	@Override
	public void startTask(int taskID) {
		// ignored
	}
	
	@Override
	public void completeTask(QuestTask task, CompleteStatus status, int nextTask) {
		if (called)
			return;
		
		called = true;
		
		TaskCompleteEvent event = new TaskCompleteEvent(task);
		Managers.getPlatform().callEvent(event); // should this be deferred? FIXME
		
		finishQuest(status);
	}
	
	@Override
	public String getQuestOwner() {
		return questOwner;
	}
	
	@Override
	public QuestSnapshot createSnapshot() {
		return null;
	}
	
}
