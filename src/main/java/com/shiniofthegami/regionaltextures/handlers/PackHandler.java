package com.shiniofthegami.regionaltextures.handlers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import com.shiniofthegami.regionaltextures.RegionalTextures;
import com.shiniofthegami.regionaltextures.base.Pack;
import com.shiniofthegami.regionaltextures.util.Debugger;

public class PackHandler {
	private static RegionalTextures pl;
	private static List<Pack> packs = new ArrayList<Pack>();
	public static void init(RegionalTextures pl){
		PackHandler.pl = pl;
	}
	
	public static void addOverlay(Pack o){
		packs.add(o);
	}
	
	public static void loadOverlays(){
		if(!pl.getConfig().contains("packs")){
			Debugger.debug("Packs section undefined!");
			return;
		}
		ConfigurationSection overlays = pl.getConfig().getConfigurationSection("packs");
		for(String key : overlays.getKeys(false)){
			String packURL = overlays.getString(key + ".url");
			if(packURL == null){
				Debugger.debug("Pack URL not defined for pack" + key + "!");
				continue;
			}
			Pack o = new Pack(key, packURL);
			addOverlay(o);
			Debugger.debug("Loaded pack " + o.toString());
		}
	}
	
	public static Pack getPack(String name){
		for(Pack o : packs){
			if(o.getName().equalsIgnoreCase(name)){
				return o;
			}
		}
		return null;
	}
}
