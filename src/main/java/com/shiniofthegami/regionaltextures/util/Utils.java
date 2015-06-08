package com.shiniofthegami.regionaltextures.util;

import java.math.BigInteger;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Utils {
	
	public final static int MAX_WORLD_HEIGHT = Bukkit.getServer().getWorlds().get(0).getMaxHeight();
	public final static BigInteger MAX = new BigInteger(String.valueOf(Integer.MAX_VALUE));
	public final static BigInteger MIN = new BigInteger(String.valueOf(Integer.MIN_VALUE));
	
	static{
		Debugger.debug("Max world height : " + MAX_WORLD_HEIGHT);
	}
	
	public static int getClampedY(Location l){
		if(l.getBlockY() >= MAX_WORLD_HEIGHT){
			return MAX_WORLD_HEIGHT-1;
		}else if(l.getBlockY() < 0){
			return 0;
		}
		return l.getBlockY();
	}
	
	public static boolean isAnyInt(String value) {
		value = value.charAt(0) == '-' ? value.replaceFirst("-", "") : value;
		return value.replaceFirst("[^0-9]", "").equals(value);
	}
	
	public static int getClampedInt(String value) {
		BigInteger number = new BigInteger(value);
		if (number.compareTo(MAX) >= 0) {
			return Integer.MAX_VALUE;
		}
		if (number.compareTo(MIN) <= 0) {
			return Integer.MIN_VALUE;
		}
		return number.intValue();
	}
}
