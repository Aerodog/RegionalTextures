package com.shiniofthegami.regionaltextures.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.shiniofthegami.regionaltextures.RegionalTextures;
import com.shiniofthegami.regionaltextures.base.CommandHandler;
import com.shiniofthegami.regionaltextures.base.Overlay;
import com.shiniofthegami.regionaltextures.base.Pack;
import com.shiniofthegami.regionaltextures.handlers.OverlayHandler;
import com.shiniofthegami.regionaltextures.handlers.PackHandler;

public class UsePackCommand extends CommandHandler {

	public UsePackCommand(RegionalTextures pl) {
		super(pl);
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("You have to be ingame to use this command.");
			return true;
		}
		if (args.length != 1) {
			return false;
		}
		Player p = (Player) sender;
		if (args[0].equalsIgnoreCase("default")) {
			PackHandler.setExclusion(p, true);
			PackHandler.applyDefaultPack(p);
			return true;
		}
		if (args[0].equalsIgnoreCase("auto") || args[0].equalsIgnoreCase("automatic")) {
			Overlay overlay = OverlayHandler.getOverlay(p.getLocation());
			if (overlay != null && overlay.getPack() != null) {
				PackHandler.setExclusion(p, false);
				overlay.getPack().apply(p);
			}
			else if (overlay == null) {
				PackHandler.setExclusion(p, false);
				PackHandler.applyDefaultPack(p);
			}
			return true;
		}
		Pack pack = PackHandler.getPack(args[0]);
		if (pack != null) {
			PackHandler.setExclusion(p, true);
			pack.apply(p);
			return true;
		}
		sender.sendMessage(ChatColor.RED + "Resource pack could not be found!");
		return true;
	}
}
