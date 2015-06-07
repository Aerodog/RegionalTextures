package com.shiniofthegami.regionaltextures.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.shiniofthegami.regionaltextures.RegionalTextures;
import com.shiniofthegami.regionaltextures.base.CommandHandler;
import com.shiniofthegami.regionaltextures.base.Pack;
import com.shiniofthegami.regionaltextures.handlers.PackHandler;
import com.shiniofthegami.regionaltextures.util.Debugger;

public class PacksCommand extends CommandHandler implements TabCompleter{
	static final List<String> blacklist = new ArrayList<String>(Arrays.asList("default", "auto", "automatic"));
	List<String> arguments = new ArrayList<String>();
	public PacksCommand(RegionalTextures pl) {
		super(pl);
		arguments.add("list");
		arguments.add("add");
		arguments.add("remove");
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
			if(args.length < 3){
				return false;
			}
			String name = args[1];
			String url = args[2];
			if (blacklist.contains(name.toLowerCase())) {
				sender.sendMessage(ChatColor.AQUA + name + ChatColor.RED + " is already used by RegionalTextures!");
				return true;
			}
			if(PackHandler.getPack(name)!=null){
				sender.sendMessage(ChatColor.RED + "A Pack with the name " + ChatColor.AQUA + name + ChatColor.RED + " already exists!");
				return true;
			}
			Pack p = new Pack(name, url);
			PackHandler.addPack(p);
			PackHandler.savePacks();
			sender.sendMessage(ChatColor.GRAY + "Added pack " + ChatColor.AQUA + p);
			Debugger.debug("Pack " + p + " added via command");
			return true;
		}
		if(args[0].equalsIgnoreCase("remove")){
			if(args.length < 2){
				return false;
			}
			String name = args[1];
			Pack p = PackHandler.getPack(name);
			if(p == null){
				sender.sendMessage(ChatColor.RED + "Pack " + ChatColor.AQUA + name + ChatColor.RED + " not found!");
				Debugger.debug("Pack remove command: Pack " + name + " not found!");
				return true;
			}
			PackHandler.removePack(p);
			sender.sendMessage(ChatColor.GRAY + "Pack " + ChatColor.AQUA + name + ChatColor.GRAY + " removed!");
			Debugger.debug("Pack remove command: Pack " + name + " removed!");
			return true;
		}
		return false;
	}

	public void printList(CommandSender sender){
		sender.sendMessage(ChatColor.AQUA + "Default pack: " + ChatColor.GREEN + pl.getConfig().getString("default"));
		sender.sendMessage(ChatColor.GRAY + "Packs listed as: " + ChatColor.AQUA + "NAME" + ChatColor.GRAY + " - " + ChatColor.AQUA + "URL");
		for(Pack p : PackHandler.getPacks()){
			sender.sendMessage(ChatColor.AQUA + p.getName() + ChatColor.GRAY + " - " + ChatColor.AQUA + p.getURL());
		}
	}
	
	public List<String> onTabComplete(CommandSender sender, Command command,
			String alias, String[] args) {
		if(args.length == 1 && args[0] == ""){
			return arguments;
		}
		if(args.length == 2 && args[1] == ""){
			if(args[0].equalsIgnoreCase("remove")){
				return PackHandler.getPackNames();
			}
		}
		return null;
	}

}
