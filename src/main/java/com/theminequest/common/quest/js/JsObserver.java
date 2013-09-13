package com.theminequest.common.quest.js;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.debug.DebugFrame;
import org.mozilla.javascript.debug.DebuggableScript;
import org.mozilla.javascript.debug.Debugger;

// thanks to http://stackoverflow.com/a/10331881
public class JsObserver implements Debugger {
	
	// signal to disconnect
	private boolean disconnect;
	
	// current frame
	private DebugFrame debugFrame;
	
	public JsObserver() {
		disconnect = false;
		debugFrame = null;
	}
	
	public boolean isDisconnected() {
		return disconnect;
	}
	
	public void setDisconnected(boolean isDisconnected) {
		this.disconnect = isDisconnected;
		if (debugFrame != null) {
			((ObservingDebugFrame) debugFrame).setDisconnected(isDisconnected);
		}
	}
	
	@Override
	public DebugFrame getFrame(Context cx, DebuggableScript fnOrScript) {
		if (debugFrame == null) {
			debugFrame = new ObservingDebugFrame(disconnect);
		}
		return debugFrame;
	}
	
	@Override
	public void handleCompilationDone(Context arg0, DebuggableScript arg1, String arg2) {
	}
	
	// internal ObservingDebugFrame class
	public class ObservingDebugFrame implements DebugFrame {
		boolean isDisconnected = false;
		
		public boolean isDisconnected() {
			return isDisconnected;
		}
		
		public void setDisconnected(boolean isDisconnected) {
			this.isDisconnected = isDisconnected;
		}
		
		private ObservingDebugFrame(boolean isDisconnected) {
			this.isDisconnected = isDisconnected;
		}
		
		public void onEnter(Context cx, Scriptable activation, Scriptable thisObj, Object[] args) {
		}
		
		public void onLineChange(Context cx, int lineNumber) {
			if (isDisconnected) {
				throw new RuntimeException("Script Execution terminaed");
			}
		}
		
		public void onExceptionThrown(Context cx, Throwable ex) {
		}
		
		public void onExit(Context cx, boolean byThrow, Object resultOrException) {
		}
		
		@Override
		public void onDebuggerStatement(Context arg0) {
		}
	}
}