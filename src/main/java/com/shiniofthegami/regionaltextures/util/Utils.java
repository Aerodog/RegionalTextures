package com.shiniofthegami.regionaltextures.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Utils {
	
	public final static int MAX_WORLD_HEIGHT = Bukkit.getServer().getWorlds().get(0).getMaxHeight();
	
	static{
		Debugger.debug("Max world height : " + MAX_WORLD_HEIGHT);
	}
	
	public static int getClampedY(Location l){
		if(l.getBlockY() > MAX_WORLD_HEIGHT){
			return MAX_WORLD_HEIGHT;
		}else if(l.getBlockY() < 0){
			return 0;
		}
		return l.getBlockY();
	}
}
