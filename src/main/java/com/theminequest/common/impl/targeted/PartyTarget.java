package com.theminequest.common.impl.targeted;

import java.util.Collection;

import com.theminequest.api.Managers;
import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.api.quest.Quest;
import com.theminequest.api.targeted.QuestTarget;

public class PartyTarget extends QuestTarget {
	
	private static final long serialVersionUID = -2580349246007354874L;

	@Override
	public void parseDetails(String[] details) {
		// nothing to do
	}
	
	@Override
	public Collection<MQPlayer> getPlayers(Quest quest) {
		return Managers.getGroupManager().get(quest).getMembers();
	}
	
}
