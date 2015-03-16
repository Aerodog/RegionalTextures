package com.shiniofthegami.regionaltextures.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.shiniofthegami.regionaltextures.RegionalTextures;
import com.shiniofthegami.regionaltextures.base.CommandHandler;

public class PacksCommand extends CommandHandler implements TabCompleter{
	List<String> arguments = new ArrayList<String>();
	public PacksCommand(RegionalTextures pl) {
		super(pl);
		arguments.add("list");
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<String> onTabComplete(CommandSender sender, Command command,
			String alias, String[] args) {
		if(args.length == 0){
			return arguments;
		}
		return null;
	}

}
