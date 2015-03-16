package com.shiniofthegami.regionaltextures.base;

import org.bukkit.command.CommandExecutor;

import com.shiniofthegami.regionaltextures.RegionalTextures;

public abstract class CommandHandler implements CommandExecutor{
	protected final RegionalTextures pl;
	public CommandHandler(RegionalTextures pl){
		this.pl = pl;
	}
}
