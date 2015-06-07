package com.shiniofthegami.regionaltextures.commands;

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
			setExclusion(p, true);
			PackHandler.applyDefaultPack(p);
			return true;
		}
		if (args[0].equalsIgnoreCase("auto") || args[0].equalsIgnoreCase("automatic")) {
			Overlay overlay = OverlayHandler.getOverlay(p.getLocation());
			if (overlay != null && overlay.getPack() != null) {
				setExclusion(p, false);
				overlay.getPack().apply(p);
			}
			else if (overlay == null) {
				setExclusion(p, false);
				PackHandler.applyDefaultPack(p);
			}
			return true;
		}
		for (Pack pack : PackHandler.getPacks()) {
			if(pack.getName().equalsIgnoreCase(args[0])) {
				setExclusion(p, true);
				pack.apply(p);
				return true;
			}
		}
		return false;
	}
	
	private static void setExclusion(Player p, boolean on) {
		if ((PackHandler.isExcluded(p) && !on) || (!PackHandler.isExcluded(p) && on)) {
			PackHandler.togglePlayer(p);
		}
	}
}
