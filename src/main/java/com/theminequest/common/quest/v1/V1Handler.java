/*
 * This file is part of Common Implementation + API for MineQuest, This provides the common classes for the MineQuest official implementation of the API..
 * Common Implementation + API for MineQuest is licensed under GNU General Public License v3.
 * Copyright (C) The MineQuest Team
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
package com.theminequest.common.quest.v1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

import org.mozilla.universalchardet.UniversalDetector;

import com.theminequest.api.Managers;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.quest.handler.QuestHandler;
import com.theminequest.common.impl.v1parser.AcceptTextHandler;
import com.theminequest.common.impl.v1parser.CancelTextHandler;
import com.theminequest.common.impl.v1parser.CompleteTextHandler;
import com.theminequest.common.impl.v1parser.DescriptionHandler;
import com.theminequest.common.impl.v1parser.EditMessageHandler;
import com.theminequest.common.impl.v1parser.EventHandler;
import com.theminequest.common.impl.v1parser.FailedTextHandler;
import com.theminequest.common.impl.v1parser.GetRequirementHandler;
import com.theminequest.common.impl.v1parser.NameHandler;
import com.theminequest.common.impl.v1parser.OldV1Handler;
import com.theminequest.common.impl.v1parser.RepeatableHandler;
import com.theminequest.common.impl.v1parser.RequirementHandler;
import com.theminequest.common.impl.v1parser.ResetHandler;
import com.theminequest.common.impl.v1parser.SpawnHandler;
import com.theminequest.common.impl.v1parser.StartRequirementHandler;
import com.theminequest.common.impl.v1parser.TargetEventHandler;
import com.theminequest.common.impl.v1parser.TargetHandler;
import com.theminequest.common.impl.v1parser.TaskHandler;

public class V1Handler implements QuestHandler<V1Parser> {
	
	private HashMap<String, V1Parser> parsers;
	private UniversalDetector encodingDetector;
	
	public V1Handler() {
		parsers = new HashMap<String, V1Parser>();
		encodingDetector = new UniversalDetector(null);
		
		setupDefaults();
	}
	
	private void setupDefaults() {
		addParser("accepttext", new AcceptTextHandler());
		addParser("canceltext", new CancelTextHandler());
		addParser("description", new DescriptionHandler());
		addParser("editmessage", new EditMessageHandler());
		addParser("event", new EventHandler());
		addParser("failedtext", new FailedTextHandler());
		addParser("finishtext", new CompleteTextHandler());
		addParser("getreq", new GetRequirementHandler());
		addParser("name", new NameHandler());
		addParser("repeatable", new RepeatableHandler());
		addParser("requirement", new RequirementHandler());
		addParser("reset", new ResetHandler());
		addParser("spawn", new SpawnHandler());
		addParser("startreq", new StartRequirementHandler());
		addParser("target", new TargetHandler());
		addParser("targetevent", new TargetEventHandler());
		addParser("task", new TaskHandler());
		addParser("v1", new OldV1Handler());
	}

	@Override
	public void addParser(String name, V1Parser parser) {
		parsers.put(name.toLowerCase(), parser);
	}

	@Override
	public boolean hasParser(String name) {
		return parsers.containsKey(name.toLowerCase());
	}

	@Override
	public V1Parser getParser(String name) {
		return parsers.get(name.toLowerCase());
	}

	@Override
	public V1Parser removeParser(String name) {
		return parsers.remove(name.toLowerCase());
	}
	
	@Override
	public QuestDetails parseQuest(File questFile) throws IOException {
		
		QuestDetails details = new V1QuestDetails(questFile);
		
		// detect encoding
		FileInputStream encodingStreamDetect = null;
		String encoding = null;
		try {
			encodingStreamDetect = new FileInputStream(questFile);
			encodingDetector.reset();
			byte[] detectorBuffer = new byte[4096];
			int nread;
			while ((nread = encodingStreamDetect.read(detectorBuffer)) > 0 && !encodingDetector.isDone()) {
				encodingDetector.handleData(detectorBuffer, 0, nread);
			}
			encodingDetector.dataEnd();
			encoding = encodingDetector.getDetectedCharset();
		} finally {
			if (encodingStreamDetect != null)
				encodingStreamDetect.close();
		}
		
		Scanner fileReader = null;
		try {
			if (encoding == null)
				fileReader = new Scanner(questFile);
			else
				fileReader = new Scanner(questFile, encoding);
			
			int line = 0;
			
			while (fileReader.hasNextLine()) {
				line++;
				String nextline = new String(fileReader.nextLine().getBytes("UTF-8"), "UTF-8").trim();
				if (nextline.startsWith("#") || nextline.isEmpty()) // ignore and continue
					continue;
				
				List<String> ar = new ArrayList<String>();
				for (String s : nextline.split(":"))
					ar.add(s);
				String type = ar.remove(0).trim();
				
				if (!hasParser(type)) {
					Managers.log(Level.WARNING, "[Common|V1Handler] Unable to handle " + type + " at " + questFile.getName() + ":" + line + ".");
					continue;
				}
				
				V1Parser handler = getParser(type);
				handler.handle(details, ar);
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} finally {
			if (fileReader != null)
				fileReader.close();
		}
		
		return details;
	}
	
}
