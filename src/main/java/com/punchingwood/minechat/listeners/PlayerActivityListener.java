package com.punchingwood.minechat.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.punchingwood.minechat.utilities.PlayerRepository;

public class PlayerActivityListener implements Listener
{
    private final PlayerRepository players;
    
    public PlayerActivityListener( final PlayerRepository players )
    {
        this.players = players;
    }
    
    /**
     * Event set for when player joins the server.
     * Records player UUID and name on login.
     * 
     * @param event
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) 
    {
        this.players.put(event.getPlayer());
    }

    /**
     * Event set for when player leaves the server.
     * Removes player UUID and name on logout.
     * 
     * @param event
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        this.players.remove(event.getPlayer());
    }

}
