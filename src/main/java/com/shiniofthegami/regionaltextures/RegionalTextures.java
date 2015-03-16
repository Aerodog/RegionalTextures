package com.shiniofthegami.regionaltextures;

import org.bukkit.plugin.java.JavaPlugin;

import com.shiniofthegami.regionaltextures.commands.PacksCommand;
import com.shiniofthegami.regionaltextures.handlers.PackHandler;
import com.shiniofthegami.regionaltextures.handlers.RegionOverlayHandler;
import com.shiniofthegami.regionaltextures.util.Debugger;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class RegionalTextures extends JavaPlugin{
	
	private final WorldGuardPlugin worldguardPlugin = WGBukkit.getPlugin();
	public void onEnable(){
		Debugger.debug("LOADING REGIONALTEXTURES CONFIG");
		this.saveDefaultConfig();
		Debugger.debug("Initializing PackHandler!");
		PackHandler.init(this);
		Debugger.debug("Initializing RegionOverlayHandler!");
		RegionOverlayHandler.init(this);
		Debugger.debug("Registering packs command!");
		this.getCommand("packs").setExecutor(new PacksCommand(this));
	}
	
	public WorldGuardPlugin getWorldGuard(){
		return worldguardPlugin;
	}
}
