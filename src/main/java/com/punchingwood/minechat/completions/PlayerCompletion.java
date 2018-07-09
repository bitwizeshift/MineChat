package com.punchingwood.minechat.completions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class PlayerCompletion
{
    public static List<String> completeWithAllPlayers()
    {
        final List<String> matched = new ArrayList<String>();
        for( Player player : Bukkit.getServer().getOnlinePlayers() ) {
            matched.add( player.getName() );
        }

        Collections.sort( matched, String.CASE_INSENSITIVE_ORDER );
        return matched;
    }
    
    public static List<String> completeWithPlayer( final String substr )
    {
        Validate.notNull(substr, "sender cannot be null");

        final List<Player> matched = Bukkit.matchPlayer(substr);
        final List<String> names = new ArrayList<String>();
        for( final Player player : matched ) {
            names.add( player.getName() );
        }

        Collections.sort( names, String.CASE_INSENSITIVE_ORDER );
        return names;
    }

    public static List<String> completeWithVisiblePlayers( final Player sender,
                                                           final String substr )
    {
        Validate.notNull(sender, "sender cannot be null");
        Validate.notNull(substr, "substr cannot be null");
        
        final List<Player> matched = Bukkit.matchPlayer(substr);
        final List<String> names = new ArrayList<String>();
        for( final Player player : matched ) {
            if( player == sender ) continue;
            if( !sender.canSee(player) ) continue;
            
            names.add( player.getName() );
        }

        Collections.sort( names, String.CASE_INSENSITIVE_ORDER );
        return names;
    }
}
