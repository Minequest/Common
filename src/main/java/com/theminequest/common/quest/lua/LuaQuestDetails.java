package com.theminequest.common.quest.lua;

import static com.theminequest.common.util.I18NMessage._;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

import org.luaj.vm2.LuaTable;

import com.theminequest.api.quest.Quest;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.requirement.QuestRequirement;

public class LuaQuestDetails implements QuestDetails {
	
	private LuaTable table;
	private String name;
	
	public LuaQuestDetails(File questFile) {
		setProperty(QuestDetails.QUEST_FILE, questFile);
		name = questFile.getName().substring(0, questFile.getName().indexOf(".lua"));
		// DEFAULTS start
		setProperty(QuestDetails.QUEST_DISPLAYNAME, questFile.getName());
		setProperty(QuestDetails.QUEST_DESCRIPTION, _("No description provided..."));
		setProperty(QuestDetails.QUEST_ACCEPT, _("Quest Accepted!"));
		setProperty(QuestDetails.QUEST_ABORT, _("Quest Aborted!"));
		setProperty(QuestDetails.QUEST_COMPLETE, _("Quest Completed!"));
		setProperty(QuestDetails.QUEST_FAIL, _("Quest Failed!"));
		setProperty(QuestDetails.QUEST_SPAWNRESET, true);
		
		double[] spawnPoint = new double[3];
		spawnPoint[0] = 0;
		spawnPoint[1] = 64;
		spawnPoint[2] = 0;
		setProperty(QuestDetails.QUEST_SPAWNPOINT, spawnPoint);
		
		setProperty(QuestDetails.QUEST_EDITMESSAGE, _("&7You can't edit this part of the world."));
		setProperty(QuestDetails.QUEST_WORLD, "world");
		setProperty(QuestDetails.QUEST_LOADWORLD, true);
		
		setProperty(QuestDetails.QUEST_TASKS, new HashMap<Integer, Integer[]>(0));
		setProperty(QuestDetails.QUEST_EVENTS, new HashMap<Integer, String>(0));
		
		setProperty(QuestDetails.QUEST_WORLDFLAGS, 0);
		setProperty(QuestDetails.QUEST_REQUIREMENTDETAILS, new HashMap<Integer, QuestRequirement>());
		setProperty(QuestDetails.QUEST_GETREQUIREMENTS, new LinkedList<Integer>());
		setProperty(QuestDetails.QUEST_STARTREQUIREMENTS, new LinkedList<Integer>());
		
	}
	
	
	
	@Override
	public int compareTo(QuestDetails arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public <E> E getProperty(String key) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public <E> E setProperty(String key, Serializable property) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean containsProperty(String key) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public <E> E removeProperty(String key) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Quest generateQuest(long questId, String questOwner) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
