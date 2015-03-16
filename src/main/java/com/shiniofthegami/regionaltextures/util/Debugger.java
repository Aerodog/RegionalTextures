package com.shiniofthegami.regionaltextures.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.Bukkit;

public class Debugger {
	private static boolean debugging = false;
	
	private static void log(String file, String toLog, Level logLevel){
		Log.createFile(file);
		ArrayList<String> log = Log.parseFile(file);
		log.add("[" + logLevel.getName() + "] " + toLog);
		try {
			Log.writeTextToFile(file, log);
		} catch (IOException e) {
			Bukkit.getLogger().log(Level.SEVERE, "Error saving to file "+file+".log!", e.getCause());
		}
		if(debugging){
			Bukkit.getLogger().log(logLevel, toLog);
		}
	}
	public static void debug(String toLog){
		log("debug",toLog,Level.WARNING);
	}
}
