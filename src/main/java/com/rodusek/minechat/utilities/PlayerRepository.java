package com.rodusek.minechat.utilities;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/*****************************************************************************
 * A repository which stores an association of player's names with a
 * reference to the player's instance.
 *****************************************************************************/
public final class PlayerRepository
{
    // TODO(bitwizeshift): Consider making 'Player' a WeakReference, since 
    //                     this class has no reason to keep it alive.
    private final Map<String,Player> data;
    
    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    
    /**
     * Initialize the <code>DataStore</code> object with a 
     * max capacity equal to the maximum number of players on
     * the server.
     */
    public PlayerRepository()
    {
        this.data = new HashMap<String, Player>();
        // Initialize the data with current online players
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            this.data.put(player.getName().toLowerCase(), player);
        }
    }
    
    //-------------------------------------------------------------------------
    // Modifiers
    //-------------------------------------------------------------------------

    /**
     * Inserts a player into this PlayerRepository
     * 
     * @param player the player to add into the repository
     */
    public void put( final Player player )
    {
        final String name = player.getName().toLowerCase();
        this.data.put(name, player);
    }
    
    /**
     * Removes a player from this repository
     * 
     * @param player the player to remove
     */
    public void remove( final Player player )
    {
        this.data.remove(player.getName().toLowerCase());
    }

    //-------------------------------------------------------------------------
    // Observers
    //-------------------------------------------------------------------------

    /**
     * Gets an instance of a player by the player's name (case insensitive)
     * 
     * @param name the name of the player to retrieve
     * @return the player instance, if found -- otherwise null
     */
    public Player get( final String name )
    {
        return this.data.get(name.toLowerCase());
    }
}
