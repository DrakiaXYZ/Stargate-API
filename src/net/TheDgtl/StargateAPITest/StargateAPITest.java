package net.TheDgtl.StargateAPITest;

import java.util.logging.Logger;

import net.TheDgtl.Stargate.Portal;
import net.TheDgtl.Stargate.event.StargateAccessEvent;
import net.TheDgtl.Stargate.event.StargateActivateEvent;
import net.TheDgtl.Stargate.event.StargateCloseEvent;
import net.TheDgtl.Stargate.event.StargateDeactivateEvent;
import net.TheDgtl.Stargate.event.StargateListener;
import net.TheDgtl.Stargate.event.StargateOpenEvent;
import net.TheDgtl.Stargate.event.StargatePortalEvent;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.java.JavaPlugin;

public class StargateAPITest extends JavaPlugin {
	private Logger log;
	private final sgListener stargateListener = new sgListener();
	@Override
	public void onEnable() {
		log = getServer().getLogger();
		log.info(getDescription().getName() + " v" + getDescription().getVersion() + " Enabled");
		
		getServer().getPluginManager().registerEvent(Event.Type.CUSTOM_EVENT, stargateListener, Priority.Normal, this);
	}

	@Override
	public void onDisable() {
		
	}
	
	private class sgListener extends StargateListener {
		@Override
		public void onStargateOpen(StargateOpenEvent event) {
			Portal portal = event.getPortal();
			Player player = event.getPlayer();
			log.info("Stargate Opened: " + portal.getName() + " on network " + portal.getNetwork());
			if (player != null)
				log.info("Portal opened by " + player.getName());
		}
		
		@Override
		public void onStargateClose(StargateCloseEvent event) {
			Portal portal = event.getPortal();
			log.info("Stargate Closed: " + portal.getName() + " on network " + portal.getNetwork());
		}
		
		@Override
		public void onStargatePortal(StargatePortalEvent event) {
			Portal origin = event.getPortal();
			Portal dest = event.getDestination();
			Location exit = event.getExit();
			
			log.info("Origin: " + origin.getName() + " dest: " + dest.getName());
			log.info("Exit Location: " + exit);
			/*
			 * This is an example of changing the exit location for a gate

			exit.setX(exit.getX() + 20);
			exit.setZ(exit.getZ() + 10);
			
			log.info("Setting exit to new location: " + exit);
			
			*/
		}
		
		@Override
		public void onStargateActivate(StargateActivateEvent event) {
			Portal portal = event.getPortal();
			Player player = event.getPlayer();
			
			log.info("Stargate " + portal.getName() + " on network " + portal.getNetwork() + " activated");
			if (player != null)
				log.info("Activated by " + player.getName());
		}
		
		@Override
		public void onStargateDeactivate(StargateDeactivateEvent event) {
			Portal portal = event.getPortal();
			
			log.info("Stargate " + portal.getName() + " on network " + portal.getNetwork() + " deactivated");
		}
		
		@Override
		public void onStargateAccess(StargateAccessEvent event) {
			Portal portal = event.getPortal();
			Player player = event.getPlayer();
			if (portal.getNetwork().equalsIgnoreCase("Faction1")) {
				if (player.getName().equalsIgnoreCase("Drakia")) {
					// Bypass perm checks for any Faction1 gate
					event.setBypassPerms(true);
				}
			}
			
			if (portal.getNetwork().equalsIgnoreCase("Faction2")) {
				if (player.getName().equalsIgnoreCase("Drakia")) {
					// Deny access to any Faction 2 gate
					event.setDeny(true);
				}
			}
		}
	}
	
}
