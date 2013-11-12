package com.theminequest.common.quest.js;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContinuationPending;
import org.mozilla.javascript.EcmaError;
import org.mozilla.javascript.Scriptable;

import com.theminequest.api.Managers;
import com.theminequest.api.platform.entity.MQPlayer;

public class JsQuestGlobalFunctions {
	
	public static final String JSLIBLOC = Managers.getPlatform().getResourceDirectory().getAbsolutePath() + File.separator + "jslib";
	
	private JsTask task;
	private Scriptable global;
	
	public JsQuestGlobalFunctions(JsTask task, Scriptable global) {
		this.task = task;
		this.global = global;
	}
	
	public void sleep(long ticks) {
		Context cx = Context.enter();
		try {
			ContinuationPending pending = cx.captureContinuation();
			pending.setApplicationState(1);
			Managers.getPlatform().scheduleAsynchronousTask(new Runnable() {
				
				@Override
				public void run() {
					task.unpause();
				}
				
			}, ticks);
			throw pending;
		} finally {
			Context.exit();
		}
	}
	
	public MQPlayer[] party() {
		List<MQPlayer> player = Managers.getGroupManager().get(task.getQuest()).getMembers();
		return player.toArray(new MQPlayer[player.size()]);
	}
	
	public void message(String... messages) {
		for (MQPlayer p : party()) {
			for (String s : messages)
				p.sendMessage(s);
		}
	}
	
	public String getTaskDescription() {
		return task.getTaskDescription();
	}
	
	public void setTaskDescription(String description) {
		task.setTaskDescription(description);
	}
	
	public void require(String fileReq) throws IOException {
		
		File file = new File(JSLIBLOC, fileReq + ".js");
		if (!file.exists())
			file = new File(JSLIBLOC, fileReq);
		if (!file.exists())
			throw new FileNotFoundException(fileReq + " does not exist!");
		
		Context cx = Context.enter();
		
		try {
			try (BufferedReader read = new BufferedReader(new FileReader(file))) {
				cx.evaluateReader(global, read, file.getName(), 1, null);
			} catch (EcmaError err) {
				Managers.logf(Level.SEVERE, "[ECMA] In evaluating %s/%s: %s", task.getQuest().getQuestOwner(), task.getQuest().getDetails().getName(), err.toString());
				for (MQPlayer player : Managers.getGroupManager().get(task.getQuest()).getMembers())
					player.sendMessage(Managers.getPlatform().chatColor().RED() + "Error with the quest: " + err.toString());
			}
		} finally {
			Context.exit();
		}
		
	}
	
}
