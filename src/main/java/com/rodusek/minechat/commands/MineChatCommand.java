package com.rodusek.minechat.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.StringUtil;

import com.rodusek.minechat.configuration.PluginConfiguration;

public class MineChatCommand extends PluginCommand
{
    //-------------------------------------------------------------------------
    // Static Members
    //-------------------------------------------------------------------------

    private static final String NAME          = "minechat";
    private static final String DESCRIPTION   = "Administrative commands for minechat";
    private static final String USAGE         = "/" + NAME + " reload";
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
    public MineChatCommand( final Plugin plugin,
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
    
    @Override
    public List<String> tabComplete( final CommandSender sender,
                                     final String alias,
                                     final String[] args )
    {
        if( args.length != 1 ) return null;
        
        final String substr = args[0];
        if( StringUtil.startsWithIgnoreCase("reload", substr) ) {
            return Arrays.asList("reload");
        }
        return null;
    }

    //-------------------------------------------------------------------------
    // Private : Modifiers
    //-------------------------------------------------------------------------

    private void reloadPlugin()
    {
        this.config.reload();
    }
}
