package com.punchingwood.minechat.commands;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.Plugin;

/*****************************************************************************
 * 
 * 
 *****************************************************************************/
public abstract class PluginCommand 
    extends BukkitCommand 
    implements PluginIdentifiableCommand
{
    //-------------------------------------------------------------------------
    // Private Members
    //-------------------------------------------------------------------------

    private final WeakReference<Plugin> plugin;
    
    //-------------------------------------------------------------------------
    // Protected Constructors
    //-------------------------------------------------------------------------
    
    /**
     * Constructs the plugin command with the plugin and string name
     * 
     * @param plugin the associated plugin for the command
     * @param name the name of the command
     */
    protected PluginCommand( final Plugin plugin,
                             final String name ) 
    {
        super(name);
        this.plugin = new WeakReference<Plugin>(plugin);
    }

    /**
     * Constructs the plugin command with the associated plugin, the name of
     * the command, and its description
     * 
     * @param plugin the associated plugin for the command
     * @param name the name of the command
     * @param description the description of this command
     */
    protected PluginCommand( final Plugin plugin,
                             final String name,
                             final String description ) 
    {
        super(name, description, "/" + name, new ArrayList<String>());
        this.plugin = new WeakReference<Plugin>(plugin);
    }

    /**
     * Constructs the plugin command with the plugin, string name
     * 
     * @param plugin the associated plugin for the command
     * @param name the name of the command
     * @param description the description of this command
     * @param usage the usage of this command
     */
    protected PluginCommand( final Plugin plugin,
                             final String name,
                             final String description, 
                             final String usage ) 
    {
        super(name, description, usage, new ArrayList<String>());
        this.plugin = new WeakReference<Plugin>(plugin);
    }

    /**
     * Constructs the plugin command with the associated plugin information
     * 
     * @param plugin the associated plugin for the command
     * @param name the name of the command
     * @param description the description of this command
     * @param usage the usage of this command
     * @param aliases the list of aliases for this command
     */
    protected PluginCommand( final Plugin plugin,
                             final String name,
                             final String description, 
                             final String usage, 
                             final List<String> aliases ) 
    {
        super(name, description, usage, aliases);
        this.plugin = new WeakReference<Plugin>(plugin);
    }

    //-------------------------------------------------------------------------
    // Observers
    //-------------------------------------------------------------------------

    /**
     * Gets the owner of this PluginIdentifiableCommand.
     *
     * @return Plugin that owns this PluginIdentifiableCommand.
     */
    @Override
    public Plugin getPlugin(){ return this.plugin.get(); }
    
    //-------------------------------------------------------------------------
    // Tab Completion
    //-------------------------------------------------------------------------

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender Source of the command.  For players tab-completing a
     *     command inside of a command block, this will be the player, not
     *     the command block. 
     * @param command Command which was executed
     * @param alias The alias used
     * @param args The arguments passed to the command, including final
     *     partial argument to be completed and command label
     * @return A List of possible completions for the final argument, or null
     *     to default to the command executor
     */
    @Override
    public List<String> tabComplete( final CommandSender sender,
                                     final String alias,
                                     final String[] args )
    {
        return null;
    }
}
