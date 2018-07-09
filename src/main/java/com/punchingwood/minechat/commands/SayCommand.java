package com.punchingwood.minechat.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.punchingwood.minechat.configuration.PluginConfiguration;
import com.punchingwood.minechat.formatting.BroadcastMessage;
import com.punchingwood.minechat.formatting.ColoredMessage;
import com.punchingwood.minechat.formatting.Message;
import com.punchingwood.minechat.permissions.PluginPermission;

/**
 * Handles the /say command
 */
public class SayCommand extends PluginCommand
{
    //-------------------------------------------------------------------------
    // Static Members
    //-------------------------------------------------------------------------
    
    private static final String NAME          = "say";
    private static final String DESCRIPTION   = "Broadcasts a message to the server";
    private static final String USAGE         = "/" + NAME + " <message...>";
    private static final List<String> ALIASES = Arrays.asList();

    //-------------------------------------------------------------------------
    // Members
    //-------------------------------------------------------------------------

    private final PluginConfiguration config;
    
    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    
    /** 
     * 
     * 
     * @param command
     */
    public SayCommand( final Plugin plugin,
                       final PluginConfiguration config )
    {
        super(plugin,NAME,DESCRIPTION,USAGE,ALIASES);
        this.config = config;
    }
    
    //-------------------------------------------------------------------------
    // CommandExecutor
    //-------------------------------------------------------------------------

    @Override
    public boolean execute( final CommandSender sender, 
                            final String label, 
                            final String[] args )
    {
        if((sender instanceof Player) && 
           !sender.hasPermission(PluginPermission.MOD_COMMAND_SAY.toString())) 
        {
            sender.sendMessage(ChatColor.RED + "Insufficient Permissions");
            return false;
        }
        
        if (args.length == 0)  {
            sender.sendMessage(ChatColor.RED + "Usage: " + super.getUsage() );
            return false;
        }

        final String  format    = this.config.getSayMessageFormat();
        final String  message   = compileMessage( args, 0, args.length );
        final Message colored   = new ColoredMessage( sender, message );
        final Message broadcast = new BroadcastMessage( format, colored.getMessage() );

        Bukkit.broadcastMessage( broadcast.getMessage() );
        return true;
    }
    
    //-------------------------------------------------------------------------
    // Private : Observers
    //-------------------------------------------------------------------------
    
    static private String compileMessage( String[] args, int start, int end ) 
    {
        if (start > args.length) {
            throw new IndexOutOfBoundsException();
        }
        final StringBuilder result = new StringBuilder(args[start]);
        
        for( int i = start+1; i < end; ++i ) {
            result.append(" ");
            result.append(args[i]);
        }
        return result.toString();
    }
}
