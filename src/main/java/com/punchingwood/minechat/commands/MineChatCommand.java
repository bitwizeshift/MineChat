package com.punchingwood.minechat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.punchingwood.minechat.CommandName;
import com.punchingwood.minechat.PluginConfiguration;

public class MineChatCommand implements CommandExecutor, PluginCommand
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
    public MineChatCommand( final CommandName command,
                            final PluginConfiguration config )
    {
        this.name   = command;
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
        if( args.length!=1 ) return false;
        
        switch(args[0]) {
        case "reload":
            this.reloadPlugin();
            sender.sendMessage(ChatColor.YELLOW + "MineChat reloaded");
            return true;
        default:
            break;
        }
        sender.sendMessage(ChatColor.RED + "Unknown command " + args[0]);
        return false;
    }

    //-------------------------------------------------------------------------
    // PluginCommand
    //-------------------------------------------------------------------------

    @Override
    public String getCommandName() { return this.name.getName(); }

    //-------------------------------------------------------------------------
    // Private : Modifiers
    //-------------------------------------------------------------------------

    private void reloadPlugin()
    {
        this.config.reload();
    }
}
