/*
 * This file is part of MineQuest, The ultimate MMORPG plugin!.
 * MineQuest is licensed under GNU General Public License v3.
 * Copyright (C) 2012 The MineQuest Team
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.theminequest.common.impl.requirement;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.requirement.QuestRequirement;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Requirement",
		ident = "RealDateRequirement",
		description = "Allow only between certain dates. We interpret the dates specified in SHORT style - see the Java API for more information.",
		arguments = { "Before Date (SHORT style)", "After Date (SHORT style)" },
		typeArguments = { DocArgType.STRING, DocArgType.STRING }
		)
public class RealDateRequirement extends QuestRequirement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4107985366769467335L;
	private Date before;
	private Date after;
	
	@Override
	public void parseDetails(String[] details) {
		try {
			before = DateFormat.getInstance().parse(details[0]);
			after = DateFormat.getInstance().parse(details[1]);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public boolean isSatisfied(QuestDetails details, MQPlayer player) {
		Date current = Calendar.getInstance().getTime();
		return current.after(before) && current.before(after);
	}
	
}
