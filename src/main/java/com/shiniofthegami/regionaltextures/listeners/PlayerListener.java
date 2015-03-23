package com.shiniofthegami.regionaltextures.listeners;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.shiniofthegami.regionaltextures.RegionalTextures;
import com.shiniofthegami.regionaltextures.base.Overlay;
import com.shiniofthegami.regionaltextures.handlers.OverlayHandler;
import com.shiniofthegami.regionaltextures.handlers.PackHandler;
import com.shiniofthegami.regionaltextures.util.Debugger;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class PlayerListener implements Listener{

	private final RegionalTextures pl;
	public PlayerListener(RegionalTextures pl){
		this.pl = pl;
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e){
		Player p = e.getPlayer();
		Location from = e.getFrom();
		Location to = e.getTo();
		List<Overlay> overlays = OverlayHandler.getOverlays();
		for(Overlay o : overlays){
			if(!inRegion(from, o.getRegion())&&inRegion(to,o.getRegion())){
				o.getPack().apply(p);
				p.sendMessage(ChatColor.GRAY + "Applying pack " + ChatColor.AQUA + o.getPack().getName() + ChatColor.GRAY + " to you!");
				Debugger.debug("Applying pack " + o.getPack() + " to Player " + p.getName());
				return;
			}else if(inRegion(from, o.getRegion())&&!inRegion(to,o.getRegion())){
				if(!handle(p)){
					PackHandler.applyDefaultPack(p);
					return;
				}			
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		Player p = e.getPlayer();
		Location from = e.getFrom();
		Location to = e.getTo();
		List<Overlay> overlays = OverlayHandler.getOverlays();
		for(Overlay o : overlays){
			if(!inRegion(from, o.getRegion())&&inRegion(to,o.getRegion())){
				o.getPack().apply(p);
				p.sendMessage(ChatColor.GRAY + "Applying pack " + ChatColor.AQUA + o.getPack().getName() + ChatColor.GRAY + " to you!");
				Debugger.debug("Applying pack " + o.getPack() + " to Player " + p.getName());
				return;
			}else if(inRegion(from, o.getRegion())&&!inRegion(to,o.getRegion())){
				Overlay o2 = handle(to);
				if(o2 != null){
					o2.getPack().apply(p);
					p.sendMessage(ChatColor.GRAY + "Applying pack " + ChatColor.AQUA + o2.getPack().getName() + ChatColor.GRAY + " to you!");
					Debugger.debug("Applying pack " + o2.getPack() + " to Player " + p.getName());
					return;
				}else{
					PackHandler.applyDefaultPack(p);
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e){
		Player p = e.getPlayer();
		if(!handle(p)){
			PackHandler.applyDefaultPack(p);
		}
	}
	
	public Overlay handle(Location l){
		List<Overlay> overlays = OverlayHandler.getOverlays();
		for(Overlay o : overlays){
			if(inRegion(l,o.getRegion())){
				return o;
			}
		}
		return null;
	}
	
	public boolean handle(Player p){
		List<Overlay> overlays = OverlayHandler.getOverlays();
		for(Overlay o : overlays){
			if(inRegion(p.getLocation(),o.getRegion())){
				o.getPack().apply(p);
				p.sendMessage(ChatColor.GRAY + "Applying pack " + ChatColor.AQUA + o.getPack().getName() + ChatColor.GRAY + " to you!");
				Debugger.debug("Applying pack " + o.getPack() + " to Player " + p.getName());
				return true;
			}
		}
		return false;
	}
	
	public boolean inRegion(Location l, ProtectedRegion r){
		return r.contains(l.getBlockX(), l.getBlockY(), l.getBlockZ());
	}
}
