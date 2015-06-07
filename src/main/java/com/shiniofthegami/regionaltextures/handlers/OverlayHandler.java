package com.shiniofthegami.regionaltextures.handlers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import com.shiniofthegami.regionaltextures.RegionalTextures;
import com.shiniofthegami.regionaltextures.base.Pack;
import com.shiniofthegami.regionaltextures.base.Overlay;
import com.shiniofthegami.regionaltextures.util.Debugger;
import com.shiniofthegami.regionaltextures.util.Utils;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class OverlayHandler {
	private static RegionalTextures pl;
	private static List<Overlay> overlays = new ArrayList<Overlay>();
	private static List<World> worlds = new ArrayList<World>();
	public static void init(RegionalTextures pl){
		OverlayHandler.pl = pl;
		loadOverlays();
	}
	
	public static void addOverlay(Overlay r){
		overlays.add(r);
		if(!worlds.contains(r.getWorld())){
			worlds.add(r.getWorld());
		}
	}
	
	public static void clearOverlays(){
		overlays.clear();
	}
	
	public static void saveOverlays(){
		Debugger.debug("Saving overlays to file!");
		pl.getConfig().set("overlays",null);
		for(Overlay o : overlays){
			pl.getConfig().set("overlays." + o.getName() + ".region", o.getRegion().getId());
			pl.getConfig().set("overlays." + o.getName() + ".world", o.getWorld().getName());
			pl.getConfig().set("overlays." + o.getName() + ".pack", o.getPack().getName());
		}
		pl.saveConfig();
	}
	
	public static List<Overlay> getOverlays(){
		return overlays;
	}
	
	public static List<String> getOverlayNames(){
		List<String> names = new ArrayList<String>();
		for(Overlay o : overlays){
			names.add(o.getName());
		}
		return names;
	}
	
	public static void removeOverlay(Overlay o){
		overlays.remove(o);
		saveOverlays();
	}
	
	public static Overlay getOverlay(String name){
		for(Overlay o : overlays){
			if(o.getName().equalsIgnoreCase(name)){
				return o;
			}
		}
		return null;
	}
	
	public static Overlay getOverlay(Location l){
		for(Overlay o : overlays){
			if(o.getRegion().contains(l.getBlockX(), Utils.getClampedY(l), l.getBlockZ())){
				return o;
			}
		}
		return null;
	}
	
	public static List<ProtectedRegion> getRegions(){
		List<ProtectedRegion> regions = new ArrayList<ProtectedRegion>();
		for(Overlay o : overlays){
			regions.add(o.getRegion());
		}
		return regions;
	}
	
	public static void loadOverlays(){
		if(!pl.getConfig().contains("overlays")){
			Debugger.debug("Overlays section undefined!");
			return;
		}
		clearOverlays();
		ConfigurationSection overlays = pl.getConfig().getConfigurationSection("overlays");
		for(String key : overlays.getKeys(false)){
			String regionname = overlays.getString(key + ".region");
			String worldname = overlays.getString(key + ".world");
			String packname = overlays.getString(key + ".pack");
			if(regionname == null){
				Debugger.debug("WorldGuard Region name not defined for overlay" + key + "!");
				continue;
			}
			if(worldname == null){
				Debugger.debug("World name not defined for overlay" + key + "!");
				continue;
			}
			if(packname == null){
				Debugger.debug("Pack name not defined for overlay" + key + "!");
				continue;
			}
			World world = Bukkit.getWorld(worldname);
			if(world == null){
				Debugger.debug(key +": world '" + worldname + "' does not exist!");
				continue;
			}
			RegionManager regionManager = pl.getWorldGuard().getRegionManager(world);
			if(regionManager == null){
				Debugger.debug(key + ": region manager for world '" + worldname + "' not found!");
				continue;
			}
			ProtectedRegion region = regionManager.getRegion(regionname);
			if(region == null){
				Debugger.debug(key + ": WorldGuard region '" + regionname + "' not found!");
				continue;
			}
			Pack pack = PackHandler.getPack(packname);
			if(pack == null){
				Debugger.debug(key + ": Pack '" + packname + "' not found!");
				continue;
			}
			Overlay o = new Overlay(region, key, pack, world);
			addOverlay(o);
			Debugger.debug("Loaded region " + o.toString());
		}
		saveOverlays();
	}
}
