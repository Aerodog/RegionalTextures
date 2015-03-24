package com.shiniofthegami.regionaltextures.base;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.shiniofthegami.regionaltextures.util.Debugger;


public class Pack {
	private String packURL;
	private String packName;
	
	public Pack(String packName, String packURL){
		if(!packURL.startsWith("http")){
			packURL = "http://" + packURL;
		}
		this.packURL = packURL;
		this.packName = packName;
	}
	
	
	public void apply(Player p){
		p.setResourcePack(this.getURL());
		p.sendMessage(ChatColor.GRAY + "Applying pack " + ChatColor.AQUA + this.getName() + ChatColor.GRAY + " to you!");
		Debugger.debug("Applying pack " + this + " to Player " + p.getName());
	}
	
	public String getURL(){
		return packURL;
	}
	
	public String getName(){
		return packName;
	}
	
	public String toString(){
		return "[" + this.getName() + ", " + this.getURL() + "]";
	}
	
}
