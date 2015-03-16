package com.shiniofthegami.regionaltextures.handlers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import com.shiniofthegami.regionaltextures.RegionalTextures;
import com.shiniofthegami.regionaltextures.base.Pack;
import com.shiniofthegami.regionaltextures.base.RegionOverlay;
import com.shiniofthegami.regionaltextures.util.Debugger;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class RegionOverlayHandler {
	private static RegionalTextures pl;
	private static List<RegionOverlay> regions = new ArrayList<RegionOverlay>();
	private static List<World> worlds = new ArrayList<World>();
	public static void init(RegionalTextures pl){
		RegionOverlayHandler.pl = pl;
	}
	
	public static void addRegion(RegionOverlay r){
		regions.add(r);
		if(!worlds.contains(r.getWorld())){
			worlds.add(r.getWorld());
		}
	}
	
	public static void loadRegionOverlays(){
		if(!pl.getConfig().contains("regions")){
			Debugger.debug("Regions section undefined!");
			return;
		}
		ConfigurationSection regionoverlays = pl.getConfig().getConfigurationSection("regions");
		for(String key : regionoverlays.getKeys(false)){
			String regionname = regionoverlays.getString(key + ".region");
			String worldname = regionoverlays.getString(key + ".world");
			String packname = regionoverlays.getString(key + ".pack");
			if(regionname == null){
				Debugger.debug("WorldGuard Region name not defined for region" + key + "!");
				continue;
			}
			if(worldname == null){
				Debugger.debug("World name not defined for region" + key + "!");
				continue;
			}
			if(packname == null){
				Debugger.debug("Pack name not defined for region" + key + "!");
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
			RegionOverlay r = new RegionOverlay(region, key, pack, world);
			addRegion(r);
			Debugger.debug("Loaded region " + r.toString());
		}
	}
}
