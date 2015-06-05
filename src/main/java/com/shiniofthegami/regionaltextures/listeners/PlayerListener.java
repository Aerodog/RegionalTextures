package com.shiniofthegami.regionaltextures.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.shiniofthegami.regionaltextures.base.Overlay;
import com.shiniofthegami.regionaltextures.handlers.OverlayHandler;
import com.shiniofthegami.regionaltextures.handlers.PackHandler;
import com.shiniofthegami.regionaltextures.util.Debugger;

public class PlayerListener implements Listener{
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e){
		handle(e.getPlayer(), e.getFrom(), e.getTo());
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		handle(e.getPlayer(), e.getFrom(), e.getTo());
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if(PackHandler.isExcluded(p)){
			p.sendMessage(ChatColor.AQUA + "You are excluded from automatic texture changing.");
			Debugger.debug("OnJoin : Player " + p.getDisplayName() + " excluded from texture changing.");
			return;
		}
		
		Location loc = p.getLocation();
		Overlay o = OverlayHandler.getOverlay(loc);
		
		if(o == null){
			PackHandler.applyDefaultPack(p);
			return;
		}
		o.getPack().apply(p);
	}
	
	public void handle(Player p, Location from, Location to){
		if(PackHandler.isExcluded(p)){
			return;
		}
		Overlay fromOverlay = OverlayHandler.getOverlay(from);
		Overlay toOverlay = OverlayHandler.getOverlay(to);
		
		if(toOverlay == fromOverlay){
			return;
		}
		
		if(toOverlay == null){
			PackHandler.applyDefaultPack(p);
			return;
		}
		
		if(toOverlay.getPack() == fromOverlay.getPack()){
			return;
		}
		
		toOverlay.getPack().apply(p);
	}
}
