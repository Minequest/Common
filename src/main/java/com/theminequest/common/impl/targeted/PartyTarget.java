package com.theminequest.common.impl.targeted;

import java.util.Collection;

import com.theminequest.api.Managers;
import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.api.quest.Quest;
import com.theminequest.api.targeted.QuestTarget;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Target",
		ident = "PartyTarget",
		description = "Represents the party on the quest.",
		arguments = { },
		typeArguments = { }
		)
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
