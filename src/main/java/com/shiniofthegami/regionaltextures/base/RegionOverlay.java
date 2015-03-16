package com.shiniofthegami.regionaltextures.base;

import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class RegionOverlay {

	private ProtectedRegion region;
	private String name;
	private Pack pack;
	private World world;
	
	public RegionOverlay(ProtectedRegion region, String name, Pack pack, World world){
		this.region = region;
		this.name = name;
		this.pack = pack;
		this.world = world;
	}
	
	public void apply(Player p){
		p.setResourcePack(pack.getURL());
	}
	
	public String getName(){
		return name;
	}
	
	public ProtectedRegion getRegion(){
		return region;
	}
	
	public Pack getPack(){
		return pack;
	}
	
	public World getWorld(){
		return world;
	}
	
	public String toString(){
		return "(" + this.getName() + ", " + this.getRegion().getId() + ", " + this.getPack().toString() + "," + this.getWorld().toString() + ")";
	}
}
