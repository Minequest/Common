package com.theminequest.common.impl.event;

import com.theminequest.api.CompleteStatus;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.quest.event.QuestEvent;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(type = "Event",
				ident = "SetSpawnEvent",
				description = "Set the spawn point of the quest.",
				arguments = { "X", "Y", "Z" },
				typeArguments = { DocArgType.FLOAT, DocArgType.FLOAT, DocArgType.FLOAT }
)
public class SetSpawnEvent extends QuestEvent {
	
	private double[] loc;
	
	@Override
	public void setupArguments(String[] arguments) {
		loc = new double[3];
		loc[0] = Double.parseDouble(arguments[0]);
		loc[1] = Double.parseDouble(arguments[1]);
		loc[2] = Double.parseDouble(arguments[2]);
	}
	
	@Override
	public boolean conditions() {
		return true;
	}
	
	@Override
	public CompleteStatus action() {
		getQuest().getDetails().setProperty(QuestDetails.QUEST_SPAWNPOINT, loc);
		return CompleteStatus.SUCCESS;
	}
	
	@Override
	public Integer switchTask() {
		return null;
	}
	
}
