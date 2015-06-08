package com.shiniofthegami.regionaltextures.base;

import org.bukkit.World;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Overlay {

	private ProtectedRegion region;
	private String name;
	private Pack pack;
	private World world;
	private int weight;
	
	public Overlay(ProtectedRegion region, String name, Pack pack, World world){
		this.region = region;
		this.name = name;
		this.pack = pack;
		this.world = world;
	}
	
	public void setWeight(int weight){
		this.weight = weight;
	}
	
	public int getWeight(){
		return weight;
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
		return "(" + this.getName() + ", " + this.getRegion().getId() + ", " + this.getPack() + "," + this.getWorld().getName() + "," + this.getWeight() + ")";
	}
}
