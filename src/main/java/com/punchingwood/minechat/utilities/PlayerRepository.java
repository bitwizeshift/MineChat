package com.punchingwood.minechat.utilities;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerRepository
{
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

    public void put( Player player )
    {
        String name = player.getName().toLowerCase();
        this.data.put(name, player);
    }
    
    public void remove( Player player )
    {
        this.data.remove(player.getName().toLowerCase());
    }

    //-------------------------------------------------------------------------
    // Observers
    //-------------------------------------------------------------------------

    public Player get( String name )
    {
        return this.data.get(name.toLowerCase());
    }
}
