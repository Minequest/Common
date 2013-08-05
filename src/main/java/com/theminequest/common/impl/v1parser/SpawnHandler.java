package com.theminequest.common.impl.v1parser;

import java.util.List;

import com.theminequest.api.quest.QuestDetails;
import com.theminequest.common.quest.v1.V1Parser;

public class SpawnHandler implements V1Parser {
	
	@Override
	public void handle(QuestDetails q, List<String> line) {
		double[] spawnPoint = q.getProperty(QuestDetails.QUEST_SPAWNPOINT);
		if (!line.get(0).equals(""))
			spawnPoint[0] = Double.parseDouble(line.get(0));
		if (!line.get(1).equals(""))
			spawnPoint[1] = Double.parseDouble(line.get(1));
		if (!line.get(2).equals(""))
			spawnPoint[2] = Double.parseDouble(line.get(2));
	}
	
}
