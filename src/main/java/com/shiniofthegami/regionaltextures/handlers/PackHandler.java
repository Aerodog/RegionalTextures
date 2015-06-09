package com.shiniofthegami.regionaltextures.handlers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.shiniofthegami.regionaltextures.RegionalTextures;
import com.shiniofthegami.regionaltextures.base.Pack;
import com.shiniofthegami.regionaltextures.util.Debugger;

public class PackHandler {
	private static List<String> blacklistedNames = new ArrayList<String>();
	
	private static RegionalTextures pl;
	private static List<Pack> packs = new ArrayList<Pack>();
	private static Set<UUID> excludedPlayers = new HashSet<UUID>();
	public static void init(RegionalTextures pl){
		PackHandler.pl = pl;
		PackHandler.blacklistName("default");
		PackHandler.blacklistName("auto");
		PackHandler.blacklistName("automatic");
		PackHandler.blacklistName("custom");
		PackHandler.blacklistName("none");
		loadPacks();
	}
	
	public static void blacklistName(String name){
		if(!blacklistedNames.contains(name.toLowerCase())){
			blacklistedNames.add(name.toLowerCase());
		}
	}
	
	public static List<String> getBlacklistedNames(){
		return blacklistedNames;
	}
	
	public static boolean isExcluded(Player p){
		return excludedPlayers.contains(p.getUniqueId());
	}
	
	public static void setExcluded(Player p, boolean b){
		if(b != isExcluded(p)){
			togglePlayer(p);
		}
	}
	
	public static void togglePlayer(Player p){
		if(excludedPlayers.contains(p.getUniqueId())){
			excludedPlayers.remove(p.getUniqueId());
			p.sendMessage(ChatColor.AQUA + "Server Textures now change per region.");
			Debugger.debug("Including player " + p.getName() + " in automatic changing!");
		}else{
			excludedPlayers.add(p.getUniqueId());
			p.sendMessage(ChatColor.AQUA + "Excluding you from regional Resource Pack changes. Reconnect to use local (downloaded) resource packs.");
			Debugger.debug("Excluding player " + p.getName() + " from automatic changing!");
		}
		
	}
	
	public static void applyDefaultPack(Player p){
		String defaultPack = pl.getConfig().getString("default");
		if(defaultPack == null || defaultPack.equals("none")){
			return;
		}
		p.setResourcePack(defaultPack);
		p.sendMessage(ChatColor.GRAY + "Applying" + ChatColor.AQUA + " default pack " + ChatColor.GRAY + "to you!");
	}
	
	public static void addPack(Pack o){
		packs.add(o);
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
	}
	
	public static void loadPacks(){
		if(!pl.getConfig().contains("packs")){
			Debugger.debug("Packs section undefined!");
			return;
		}
		clearPacks();
		ConfigurationSection packs = pl.getConfig().getConfigurationSection("packs");
		for(String key : packs.getKeys(false)){
			if(PackHandler.getBlacklistedNames().contains(key)){
				Debugger.debug("Name " + key + " is blacklisted, skipping.");
				continue;
			}
			String packURL = packs.getString(key + ".url");
			if(packURL == null){
				Debugger.debug("Pack URL not defined for pack" + key + "!");
				continue;
			}
			Pack o = new Pack(key, packURL);
			addPack(o);
			Debugger.debug("Loaded pack " + o.toString());
		}
		savePacks();
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
		savePacks();
	}
	
}
