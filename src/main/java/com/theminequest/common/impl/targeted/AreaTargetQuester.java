package com.theminequest.common.impl.targeted;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.theminequest.api.Managers;
import com.theminequest.api.group.Group;
import com.theminequest.api.platform.MQLocation;
import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.api.quest.Quest;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.targeted.QuestTarget;

public class AreaTargetQuester extends QuestTarget {
	
	private static final long serialVersionUID = 7775158671780728971L;
	
	private int otherTarget;
	private double radius;
	
	@Override
	public void parseDetails(String[] details) {
		otherTarget = Integer.parseInt(details[0]);
		radius = Double.parseDouble(details[1]);
	}
	
	@Override
	public Collection<MQPlayer> getPlayers(Quest quest) {
		Map<Integer, QuestTarget> targets = quest.getDetails().getProperty(QuestDetails.QUEST_TARGET);
		if (!targets.containsKey(otherTarget))
			throw new RuntimeException("no such target " + otherTarget);
		Collection<MQPlayer> players = targets.get(otherTarget).getPlayers(quest);
		
		if (players.size() == 0)
			return players; // empty
		
		MQPlayer randomPlayer = players.iterator().next();
		MQLocation loc = randomPlayer.getLocation();
		
		Group group = Managers.getGroupManager().get(quest);
		
		List<MQPlayer> inArea = new LinkedList<MQPlayer>();
		
		List<MQPlayer> members = group.getMembers();
		for (MQPlayer p : members) {
			if (!p.getLocation().getWorld().equals(loc.getWorld()))
				continue;
			if (p.getLocation().distance(loc) <= radius)
				inArea.add(p);
		}
		
		return inArea;
	}
	
}
