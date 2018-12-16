package com.rodusek.minechat;

import com.rodusek.minechat.commands.MineChatCommand;
import com.rodusek.minechat.commands.PluginCommand;
import com.rodusek.minechat.commands.PrivateMessageCommand;
import com.rodusek.minechat.commands.SayCommand;
import com.rodusek.minechat.configuration.PluginConfiguration;
import com.rodusek.minechat.listeners.MessageListener;
import com.rodusek.minechat.listeners.PlayerActivityListener;
import com.rodusek.minechat.utilities.PlayerRepository;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MineChat extends JavaPlugin 
{
    // Commands
    private PluginCommand pmCommand;
    private PluginCommand sayCommand;
    private PluginCommand mineChatCommand;
    
    // Listeners
    private Listener playerActivityListener;
    private Listener messageListener;
    
    // Configuration
    private PluginConfiguration config;
    
    private PlayerRepository players = new PlayerRepository();

    /**
     * Called when this plugin is enabled
     */
    @Override
    public void onEnable() 
    {
        initializeConfiguration();
        
        initializeCommands();
        initializeListeners();
        
        registerCommands();
        registerListeners();
    }


    private void initializeCommands()
    {
        this.sayCommand      
            = new SayCommand(this,this.config);
        this.pmCommand       
            = new PrivateMessageCommand(this,this.config,this.players);
        this.mineChatCommand 
            = new MineChatCommand(this,this.config);
    }
    
    private void initializeListeners()
    {
        this.playerActivityListener = new PlayerActivityListener( this.players );
        this.messageListener = new MessageListener( this.config );
    }

    private void initializeConfiguration()
    {
        this.config = new PluginConfiguration( this, "config.yml" );
    }

    /**
     * Called when this plugin is enabled
     */
    @Override
    public void onDisable() 
    {
        this.config.save();
    }

    // -----------------------------------------------------------------------
    
    /**
     * Sets up the listeners for this plugin
     */
    private void registerListeners()
    {
        PluginManager manager = getServer().getPluginManager();
        
        manager.registerEvents( this.messageListener, this);
        manager.registerEvents( this.playerActivityListener, this);
    }
    
    /**
     * Sets up the commands that this plugin may use
     */
    private void registerCommands()
    {
        registerCommand( this.sayCommand );
        registerCommand( this.pmCommand );
        registerCommand( this.mineChatCommand );
    }
    
    private void registerCommand( BukkitCommand command )
    {
        CommandMap map = null;
        Field field;

        try {
            field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            map = (CommandMap)field.get(Bukkit.getServer());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        
        assert command != null;

        if (map.getCommand(command.getName()) == null) {
            map.register(command.getName(), command);
        }
    }
}
