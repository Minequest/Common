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
package com.theminequest.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.theminequest.api.Managers;

public class ExceptionHandler {
	
	public static String reportURL = null;
	public static String reportLoc = Managers.getPlatform().getResourceDirectory().getAbsolutePath() + File.separator + ".reports";
	private static boolean init = false;
	
	public static void init() {
		if (init)
			return;
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				StringWriter stackTrace = new StringWriter();
				e.printStackTrace(new PrintWriter(stackTrace));
				
				String message = e.getMessage();
				
				StringWriter suppressedTraces = new StringWriter();
				Throwable[] suppressed = e.getSuppressed();
				for (Throwable suppress : suppressed) {
					suppress.printStackTrace(new PrintWriter(suppressedTraces));
					suppressedTraces.write(System.lineSeparator());
					suppressedTraces.write("---------------------------------------");
					suppressedTraces.write(System.lineSeparator());
				}
				
				String thisThread = t.getName();
				long thisThreadId = t.getId();
				
				ThreadMXBean tmx = ManagementFactory.getThreadMXBean();
				ThreadInfo[] threads = tmx.dumpAllThreads(true, true);
				
				StringBuilder threadsInformation = new StringBuilder();
				
				for (ThreadInfo ti : threads) {
					threadsInformation.append(ti);
					threadsInformation.append(System.lineSeparator());
				}
				
				HashMap<String, String> information = new HashMap<String, String>();
				information.put("stacktrace", stackTrace.toString());
				information.put("message", message);
				information.put("suppressed", suppressedTraces.toString());
				information.put("threadname", thisThread);
				information.put("threadid", Long.toString(thisThreadId));
				information.put("threaddump", threadsInformation.toString());
				
				new Thread(report(information)).start();
			}
			
		});
		
		init = true;
	}
	
	public static Runnable report(final Map<String, String> reports) {
		return new Runnable() {

			@Override
			public void run() {

				// Save locally first.				
				String location = reportLoc + File.separator + System.currentTimeMillis() + ".log";
				File reportFile = new File(location);
				Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
				
				try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(reportFile)))) {
					writer.println(gson.toJson(reports));
					Managers.logf(Level.SEVERE, "Unhandled Exception occurred. Log at %s.", location);
				} catch (IOException e) {
					Managers.logf(Level.SEVERE, "Unhandled Exception occurred. Logging failed: %s.", e);
					Managers.logf(Level.SEVERE, "Unhandled Exception:\n%s\n", gson.toJson(reports));
				}
				
				if (reportURL == null)
					return;
			}
			
		};
	}
	
}
