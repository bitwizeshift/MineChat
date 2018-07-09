package com.punchingwood.minechat.listeners;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.punchingwood.minechat.formatting.ColoredMessage;
import com.punchingwood.minechat.formatting.Message;
import com.punchingwood.minechat.permissions.PluginPermission;
import com.punchingwood.minechat.configuration.PluginConfiguration;
import com.punchingwood.minechat.formatting.ChatMessage;

public class MessageListener implements Listener
{
    private final PluginConfiguration config;
    
    public MessageListener( final PluginConfiguration config )
    {
        this.config = config;
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat( final AsyncPlayerChatEvent event ) 
    {
        if( event.isCancelled() ) return;
        
        final String format = this.config.getChatMessageFormat();
        final Player player = event.getPlayer();
        final Message colored   = new ColoredMessage( player, event.getMessage() );
        final Message formatted = new ChatMessage( player, format );
        
        event.setMessage( colored.getMessage() );
        event.setFormat( formatted.getMessage() );

        // Only continue if ranged-chat is enabled
        if( !this.config.isRangeChatEnabled() ) return;
        
        final double range = this.config.getRangedChatRadius();
        final Set<Player> recipients = event.getRecipients();
        
        // Reset recipients, and only send to local recipients
        recipients.clear();
        recipients.addAll(getLocalRecipients( player, range ));
    }

    // ---------------------------------------------------------------------------------    

    /**
     * Gets a list of local Players within range of the sender. 
     * 
     * @param sender
     * @param range
     * @return
     */
    private static Collection<? extends Player> getLocalRecipients( final Player sender, 
                                                                    final double range ) 
    {
        final Location location = sender.getLocation();
        final List<Player> recipients = new LinkedList<Player>();
        final double distance = Math.pow(range, 2);
                
        // Range override retrieves all online playerss
        if( sender.hasPermission(PluginPermission.MOD_RANGE_OVERRIDE.toString()) ) {
            return Bukkit.getServer().getOnlinePlayers();
        }
        
        for( Player recipient : Bukkit.getServer().getOnlinePlayers() ) {
            
            // Recipient are not from same world
            if( !recipient.getWorld().equals(sender.getWorld()) ) {
                continue;
            }
            
            // Sender can override 
            if( location.distanceSquared(recipient.getLocation()) > distance ) {
                continue;
            }

            recipients.add(recipient);
        }
        return recipients;
    }
}
