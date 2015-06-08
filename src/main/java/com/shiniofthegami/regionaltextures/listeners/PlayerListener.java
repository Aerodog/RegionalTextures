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
import com.shiniofthegami.regionaltextures.base.Pack;
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
		this.applyPack(o, p);
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
		
		if(fromOverlay == null){
			this.applyPack(toOverlay, p);
			return;
		}
		
		if(toOverlay.getPack() == (fromOverlay.getPack())){
			Debugger.debug("Packs " + toOverlay.getPack() + " and " + fromOverlay.getPack() + " identical, not changing (Player: " + p.getDisplayName() + ")");
			return;
		}
		if(toOverlay.getPack() == null){
			PackHandler.setExcluded(p,true);
			p.sendMessage(ChatColor.GOLD + "NOTE:" + ChatColor.GRAY +  "You have entered a custmizable region and are excluded from automatic texture changing!");
			return;
		}
		if(fromOverlay.getPack() == null){
			p.sendMessage(ChatColor.AQUA + "Leaving customizable region!");
			
		if(PackHandler.isExcluded(p)){
			p.sendMessage(ChatColor.AQUA + "Type" + ChatColor.GOLD + "/usepack automatic" + ChatColor.AQUA + "to enable automatic texture changes.");
			return;
		}
		}
		this.applyPack(toOverlay, p);
	}
	private void applyPack(Overlay o, Player p){
		Pack pack = o.getPack();
		if(pack != null){
			pack.apply(p);
		}
	}
}
