package com.shiniofthegami.regionaltextures.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Utils {
	
	public final static int MAX_WORLD_HEIGHT = Bukkit.getServer().getWorlds().get(0).getMaxHeight();
	
	public static Location clampY(Location l){
		if(l.getBlockY() > MAX_WORLD_HEIGHT){
			l.setY(MAX_WORLD_HEIGHT);
		}else if(l.getBlockY() < 0){
			l.setY(0);
		}
		return l;
	}
}
