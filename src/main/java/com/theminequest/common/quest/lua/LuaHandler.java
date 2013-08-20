package com.theminequest.common.quest.lua;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.quest.handler.QuestHandler;

public class LuaHandler implements QuestHandler<LuaValue> {
	
	protected Map<String, LuaValue> table;
	
	public LuaHandler() {
		table = new HashMap<String, LuaValue>();
	}

	@Override
	public QuestDetails parseQuest(File questFile) throws IOException {
		// FIXME convert HashMap to LuaTable, insert into environment
		// FIXME parse questFile to string for later evaluation
		// FIXME luaj threads are uninterruptible; forcing me to use .stop()
		return null;
	}

	@Override
	public void addParser(String name, LuaValue parser) {
		table.put(name, parser);
	}

	@Override
	public boolean hasParser(String name) {
		return table.containsKey(name);
	}

	@Override
	public LuaValue getParser(String name) {
		return table.get(name);
	}

	@Override
	public LuaValue removeParser(String name) {
		return table.remove(name);
	}
	
}
