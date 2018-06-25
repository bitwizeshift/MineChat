package com.punchingwood.minechat;

import com.punchingwood.minechat.commands.PluginCommand;
import com.punchingwood.minechat.commands.MineChatCommand;
import com.punchingwood.minechat.commands.PrivateMessageCommand;
import com.punchingwood.minechat.commands.SayCommand;
import com.punchingwood.minechat.listeners.MessageListener;
import com.punchingwood.minechat.listeners.PlayerActivityListener;
import com.punchingwood.minechat.utilities.PlayerRepository;

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
            = new SayCommand(CommandName.SAY,this.config);
        this.pmCommand       
            = new PrivateMessageCommand(CommandName.PRIVATE_MESSAGE,this.config,this.players);
        this.mineChatCommand 
            = new MineChatCommand(CommandName.MINECHAT, this.config);
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
    
    private <E extends PluginCommand> void registerCommand( E command )
    {
        assert command != null;
        getCommand(command.getCommandName()).setExecutor(command);
    }
}
