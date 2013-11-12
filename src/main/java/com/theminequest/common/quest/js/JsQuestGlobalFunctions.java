package com.theminequest.common.quest.js;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContinuationPending;

import com.theminequest.api.Managers;

public class JsQuestGlobalFunctions {
	
	private JsTask task;
	
	public JsQuestGlobalFunctions(JsTask task) {
		this.task = task;
	}
	
	public void debugPause(long ticks) {
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
	
	public void pause() {
		Context cx = Context.enter();
		try {
			ContinuationPending pending = cx.captureContinuation();
			pending.setApplicationState(1);
			throw pending;
		} finally {
			Context.exit();
		}
	}
}
