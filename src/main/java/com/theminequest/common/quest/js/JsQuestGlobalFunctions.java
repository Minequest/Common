package com.theminequest.common.quest.js;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContinuationPending;

public class JsQuestGlobalFunctions {
	
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
