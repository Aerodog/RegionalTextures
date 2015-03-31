package com.shiniofthegami.regionaltextures.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.shiniofthegami.regionaltextures.RegionalTextures;
import com.shiniofthegami.regionaltextures.base.CommandHandler;
import com.shiniofthegami.regionaltextures.handlers.PackHandler;

public class ToggleTexturesCommand extends CommandHandler{

	public ToggleTexturesCommand(RegionalTextures pl) {
		super(pl);

	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("You have to be ingame to use this command.");
			return true;
		}
		Player p = (Player) sender;
		PackHandler.togglePlayer(p);
		return true;
	}

}
