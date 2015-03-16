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
		loadPacks();
	}
	
	public static void addPack(Pack o){
		packs.add(o);
		savePacks();
	}
	
	public static void clearPacks(){
		packs.clear();
	}
	
	public static void savePacks(){
		Debugger.debug("Saving packs to file!");
		pl.getConfig().set("packs",null);
		for(Pack p : packs){
			pl.getConfig().set("packs." + p.getName() + ".url", p.getURL());
		}
		pl.saveConfig();
		RegionOverlayHandler.loadRegionOverlays();
	}
	
	public static void loadPacks(){
		if(!pl.getConfig().contains("packs")){
			Debugger.debug("Packs section undefined!");
			return;
		}
		clearPacks();
		ConfigurationSection packs = pl.getConfig().getConfigurationSection("packs");
		for(String key : packs.getKeys(false)){
			String packURL = packs.getString(key + ".url");
			if(packURL == null){
				Debugger.debug("Pack URL not defined for pack" + key + "!");
				continue;
			}
			Pack o = new Pack(key, packURL);
			addPack(o);
			Debugger.debug("Loaded pack " + o.toString());
		}
	}
	
	public static Pack getPack(String name){
		for(Pack p : packs){
			if(p.getName().equalsIgnoreCase(name)){
				return p;
			}
		}
		return null;
	}
	
	public static List<Pack> getPacks(){
		return packs;
	}
	
	public static List<String> getPackNames(){
		List<String> names = new ArrayList<String>();
		for(Pack p : packs){
			names.add(p.getName());
		}
		return names;
	}
	
	public static void removePack(Pack p){
		packs.remove(p);
	}
	
}
