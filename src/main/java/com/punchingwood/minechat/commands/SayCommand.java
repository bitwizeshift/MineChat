package com.punchingwood.minechat.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.punchingwood.minechat.CommandName;
import com.punchingwood.minechat.PluginConfiguration;
import com.punchingwood.minechat.PluginPermission;
import com.punchingwood.minechat.formatting.BroadcastMessage;
import com.punchingwood.minechat.formatting.ColoredMessage;
import com.punchingwood.minechat.formatting.Message;

/**
 * Handles the /say command
 */
public class SayCommand implements CommandExecutor, PluginCommand
{
    //-------------------------------------------------------------------------
    // Members
    //-------------------------------------------------------------------------

    private final CommandName name;
    private final PluginConfiguration config;
    
    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    
    /**
     * 
     * 
     * @param command
     */
    public SayCommand( final CommandName command,
                       final PluginConfiguration config )
    {
        this.name = command;
        this.config = config;
    }
    
    //-------------------------------------------------------------------------
    // CommandExecutor
    //-------------------------------------------------------------------------

    @Override
    public boolean onCommand( final CommandSender sender, 
                              final Command command, 
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
            sender.sendMessage(ChatColor.RED + "Usage: " + getUsageString() );
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
    // PluginCommand
    //-------------------------------------------------------------------------

    /**
     * Gets the bound command name for this command
     * 
     * @return the command
     */
    @Override
    public String getCommandName() { return name.getName(); }

    //-------------------------------------------------------------------------
    // Private : Observers
    //-------------------------------------------------------------------------

    /**
     * Gets the usage string for this command
     * 
     * @return the usage string
     * @return
     */
    private String getUsageString()
    {
        return "/" + getCommandName() + " <message ...>";
    }
    
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
