package com.theminequest.common.quest.js;

import java.io.Serializable;

import com.theminequest.api.quest.Quest;
import com.theminequest.common.quest.CommonQuestDetails;

/**
 * JsQuestDetails works differently than most details, in that
 * when it generates a Quest, it switches the detail attributes
 * to those in a Js Object.
 */
public class JsQuestDetails extends CommonQuestDetails {
	
	private static final long serialVersionUID = 7749931627674838925L;
	
	public static final String JS_SOURCE = "js.source";
	
	public static final String JS_LINESTART = "js.linestart";

	public JsQuestDetails(String name) {
		super(name);
		setProperty(QUEST_LOADWORLD, true);
	}
	
	@Override
	public <E> E setProperty(String key, Serializable property) {
		if (key.equals(QUEST_LOADWORLD))
			throw new IllegalArgumentException("Cannot set LOADWORLD");
		
		return super.setProperty(key, property);
	}

	@Override
	public <E> E removeProperty(String key) {
		if (key.equals(QUEST_LOADWORLD))
			throw new IllegalArgumentException("Cannot remove LOADWORLD");

		return super.removeProperty(key);
	}

	@Override
	public Quest generateQuest(long questId, String questOwner) {
		return new JsQuest(questId, questOwner, this);
	}
	
}
