package com.shiniofthegami.regionaltextures;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.shiniofthegami.regionaltextures.commands.PacksCommand;
import com.shiniofthegami.regionaltextures.commands.OverlaysCommand;
import com.shiniofthegami.regionaltextures.handlers.PackHandler;
import com.shiniofthegami.regionaltextures.handlers.OverlayHandler;
import com.shiniofthegami.regionaltextures.listeners.PlayerListener;
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
		Debugger.debug("Initializing OverlayHandler!");
		OverlayHandler.init(this);
		Debugger.debug("Registering packs command!");
		PacksCommand packsHandler = new PacksCommand(this);
		this.getCommand("packs").setExecutor(packsHandler);
		this.getCommand("packs").setTabCompleter(packsHandler);
		Debugger.debug("Registering overlays command!");
		OverlaysCommand regionsHandler = new OverlaysCommand(this);
		this.getCommand("overlays").setExecutor(regionsHandler);
		this.getCommand("overlays").setTabCompleter(regionsHandler);
		Debugger.debug("Registering listeners!");
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
	}
	
	public WorldGuardPlugin getWorldGuard(){
		return worldguardPlugin;
	}
}
