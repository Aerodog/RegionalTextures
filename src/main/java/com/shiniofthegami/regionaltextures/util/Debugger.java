package com.shiniofthegami.regionaltextures.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;

import org.bukkit.Bukkit;

public class Debugger {
	private static boolean debugging = false;
	
	private static void log(String file, String toLog, Level logLevel){
		Log.createFile(file);
		ArrayList<String> log = Log.parseFile(file);
		Date d = Calendar.getInstance().getTime();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		log.add("[" + df.format(d) + "]" + "[" + logLevel.getName() + "] " + toLog);
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
		log("debug",toLog,Level.INFO);
	}
}
