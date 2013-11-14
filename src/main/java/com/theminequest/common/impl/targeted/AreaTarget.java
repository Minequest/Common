package com.theminequest.common.impl.targeted;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.theminequest.api.Managers;
import com.theminequest.api.group.Group;
import com.theminequest.api.platform.MQLocation;
import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.api.quest.Quest;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.targeted.QuestTarget;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Target",
		ident = "AreaTarget",
		description = "Represents players in a certain area.",
		arguments = { "X", "Y", "Z", "Radius" },
		typeArguments = { DocArgType.FLOAT, DocArgType.FLOAT, DocArgType.FLOAT, DocArgType.FLOAT }
		)
public class AreaTarget extends QuestTarget {
	
	private static final long serialVersionUID = 4355616627742684553L;
	
	private double x;
	private double y;
	private double z;
	private double radius;
	
	@Override
	public void parseDetails(String[] details) {
		x = Double.parseDouble(details[0]);
		y = Double.parseDouble(details[1]);
		z = Double.parseDouble(details[2]);
		radius = Double.parseDouble(details[3]);
	}
	
	@Override
	public Collection<MQPlayer> getPlayers(Quest quest) {
		MQLocation loc = new MQLocation((String) quest.getDetails().getProperty(QuestDetails.QUEST_WORLD), x, y, z);
		Group group = Managers.getGroupManager().get(quest);
		
		List<MQPlayer> players = new LinkedList<MQPlayer>();
		
		List<MQPlayer> members = group.getMembers();
		for (MQPlayer p : members) {
			if (!p.getLocation().getWorld().equals(loc.getWorld()))
				continue;
			if (p.getLocation().distance(loc) <= radius)
				players.add(p);
		}
		
		return players;
	}
	
}
