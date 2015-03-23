package com.shiniofthegami.regionaltextures.base;

import org.bukkit.entity.Player;


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
