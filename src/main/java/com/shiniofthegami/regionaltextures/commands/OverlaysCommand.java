package com.shiniofthegami.regionaltextures.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.shiniofthegami.regionaltextures.RegionalTextures;
import com.shiniofthegami.regionaltextures.base.CommandHandler;
import com.shiniofthegami.regionaltextures.base.Overlay;
import com.shiniofthegami.regionaltextures.base.Pack;
import com.shiniofthegami.regionaltextures.handlers.OverlayHandler;
import com.shiniofthegami.regionaltextures.handlers.PackHandler;
import com.shiniofthegami.regionaltextures.util.Debugger;
import com.shiniofthegami.regionaltextures.util.Utils;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class OverlaysCommand extends CommandHandler implements TabCompleter{
	List<String> arguments = new ArrayList<String>();
	public OverlaysCommand(RegionalTextures pl) {
		super(pl);
		arguments.add("list");
		arguments.add("add");
		arguments.add("remove");
		arguments.add("update");
		arguments.add("weight");
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(args.length < 1){
			return false;
		}
		if(!arguments.contains(args[0])){
			return false;
		}
		if(args[0].equalsIgnoreCase("list")){
			printList(sender);
			return true;
		}
		if(args[0].equalsIgnoreCase("add")){
			if(!(sender instanceof Player)){
				sender.sendMessage("You need to be ingame to add overlays!");
				return true;
			}
			Player p = (Player) sender;
			if(args.length < 4){
				return false;
			}
			String name = args[1];
			String id = args[2];
			String packName = args[3];
			
			if(OverlayHandler.getOverlay(name)!=null){
				sender.sendMessage(ChatColor.RED + "An Overlay with the name " + ChatColor.AQUA + name + ChatColor.RED + " already exists!");
				return true;
			}
			RegionManager regionManager = pl.getWorldGuard().getRegionManager(p.getWorld());
			if(regionManager == null){
				Debugger.debug("Overlay add command: region manager for world '" + p.getWorld().getName() + "' not found!");
				sender.sendMessage(ChatColor.RED + "Error finding region manager for world!");
				return true;
			}
			ProtectedRegion region = regionManager.getRegion(id);
			if(region == null){
				Debugger.debug("Overlay add command: WorldGuard region '" + id + "' not found!");
				sender.sendMessage(ChatColor.RED + "WorldGuard region '" + id + "' not found!");
				return true;
			}
			Pack pack = null;
			if(!packName.equalsIgnoreCase("custom")){
			 pack = PackHandler.getPack(packName);
			if(pack == null){
				Debugger.debug("Overlay add command: Pack '" + packName + "' not found!");
				sender.sendMessage(ChatColor.RED + "Pack '" + packName + "' not found!");
				return true;
			}
			}	
			Overlay o = new Overlay(region, name, pack, p.getWorld());
			OverlayHandler.addOverlay(o);
			OverlayHandler.saveOverlays();
			sender.sendMessage("Added overlay " + o);
			Debugger.debug("Overlay " + o + " added via command");
			return true;
		}
		if(args[0].equalsIgnoreCase("remove")){
			if(args.length < 2){
				return false;
			}
			String name = args[1];
			Overlay o = OverlayHandler.getOverlay(name);
			if(o == null){
				sender.sendMessage(ChatColor.RED + "Overlay " + ChatColor.AQUA + name + ChatColor.RED + " not found!");
				Debugger.debug("Overlay remove command: Overlay " + name + " not found!");
				return true;
			}
			OverlayHandler.removeOverlay(o);
			sender.sendMessage(ChatColor.GRAY + "Overlay " + ChatColor.AQUA + name + ChatColor.GRAY + " removed!");
			Debugger.debug("Overlay remove command: Overlay " + name + " removed!");
			return true;
		}
		if(args[0].equalsIgnoreCase("update")){
			if(args.length > 1){
				return false;
			}
			OverlayHandler.loadOverlays();
			sender.sendMessage(ChatColor.GOLD + "Overlays updated!");
			Debugger.debug("Overlay update command: Overlays updated!");
			return true;
		}
		if(args[0].equalsIgnoreCase("weight")){
			if(args.length < 3 || !Utils.isAnyInt(args[2])){
				return false;
			}
			Debugger.debug("Overlay weight command: Number: " + args[2] + " Number validation: " + !Utils.isAnyInt(args[2]));
			String name = args[1];
			int weight = Utils.getClampedInt(args[2]);
			Overlay o = OverlayHandler.getOverlay(name);
			if(o == null){
				sender.sendMessage(ChatColor.RED + "Overlay " + ChatColor.AQUA + name + ChatColor.RED + " not found!");
				return true;
			}
			o.setWeight(weight);
			sender.sendMessage(ChatColor.GRAY + "Weight " + ChatColor.AQUA + weight + ChatColor.GRAY + " set to " + ChatColor.AQUA + name);
			Debugger.debug("Overlay weight command: " + weight + " set to " + name);
			return true;
		}
		return false;
	}
	
	public void printList(CommandSender sender){
		sender.sendMessage(ChatColor.GRAY + "Regions listed as: " + ChatColor.AQUA + "NAME" + ChatColor.GRAY + " - " + ChatColor.AQUA + "REGION ID" + ChatColor.GRAY + " - " + ChatColor.AQUA + "PACK" + ChatColor.GRAY + " - " + ChatColor.AQUA + "WEIGHT");
		for(Overlay o : OverlayHandler.getOverlays()){
			sender.sendMessage(ChatColor.AQUA + o.getName() + ChatColor.GRAY + " - " + ChatColor.AQUA + o.getRegion().getId() + ChatColor.GRAY + " - " + ChatColor.AQUA + o.getPack().getName() + ChatColor.GRAY + " - " + ChatColor.AQUA + o.getWeight());
		}
	}
	
	public List<String> onTabComplete(CommandSender sender, Command command,
			String alias, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}
}
