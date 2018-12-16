package com.rodusek.minechat.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.rodusek.minechat.utilities.PlayerRepository;

/*****************************************************************************
 * A listener that updates on player activity changes
 *****************************************************************************/
public class PlayerActivityListener implements Listener
{
    //-------------------------------------------------------------------------
    // Members
    //-------------------------------------------------------------------------

    private final PlayerRepository repository;
    
    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    
    /**
     * Constructs an activity listener from a given player repository
     * 
     * @param repository
     */
    public PlayerActivityListener( final PlayerRepository repository )
    {
        this.repository = repository;
    }
    
    //-------------------------------------------------------------------------
    // Event Handling
    //-------------------------------------------------------------------------

    /**
     * Event set for when player joins the server.
     * Records player UUID and name on login.
     * 
     * @param event
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin( final PlayerJoinEvent event ) 
    {
        this.repository.put(event.getPlayer());
    }

    /**
     * Event set for when player leaves the server.
     * Removes player UUID and name on logout.
     * 
     * @param event
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit( final PlayerQuitEvent event )
    {
        this.repository.remove(event.getPlayer());
    }

}
