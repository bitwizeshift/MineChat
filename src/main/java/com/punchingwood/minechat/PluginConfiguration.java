package com.punchingwood.minechat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginConfiguration
{
    //-------------------------------------------------------------------------
    // Members
    //-------------------------------------------------------------------------

    private final JavaPlugin plugin;
    private final File file;
    private final String resourcePath;
    private FileConfiguration config;
    
    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------

    public PluginConfiguration( final JavaPlugin plugin,
                                final String resourcePath )
    {
        this.plugin = plugin;
        this.resourcePath = resourcePath;

        File folder = plugin.getDataFolder();
        assert folder != null;        
        this.file = new File(folder, resourcePath);
        
        reload();
    }
    
    //-------------------------------------------------------------------------
    // Modifiers
    //-------------------------------------------------------------------------
    
    /**
     * Reloads the configuration from this PluginConfiguration
     */
    public synchronized void reload()
    {
        this.config = null;
        if( this.file.exists() ) {
            loadConfigurationFromDisk();
        } else {
            loadConfigurationFromPlugin();
        }   
    }

    //-------------------------------------------------------------------------

    /**
     * Saves the configuration from this PluginConfiguration to disk.
     * 
     * @throws IOException if there is an issue saving the file to disk
     */
    public synchronized void save()
    {
        // It should be impossible for the configuration to be 'null' here
        assert this.config != null;
        
        // Copy the plugin-provided file over first, and then immediately 
        // write the configuration.
        //
        // Although this second write will generally be redundant, this guards 
        // against the case where the configuration file is deleted while the
        // server is running. Rather than dumping the configuration and 
        // stipping all YAML comments, this will instead preserve the comments 
        // and just overwrite the nodes
        if (!this.file.exists()) {
            this.plugin.saveResource(this.resourcePath, false);
        }
        
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            Logger logger = this.plugin.getLogger();
            logger.log(Level.SEVERE, "Could not save " + this.file.getName() + " to " + this.file, e);
        }
    }

    private static final String RANGED_CHAT_RADIUS  = "ranged-chat.radius";
    private static final String RANGED_CHAT_ENABLED = "ranged-chat.enabled";
    private static final String MESSAGE_FORMAT_CHAT = "message-format.chat";
    private static final String MESSAGE_FORMAT_SAY  = "message-format.say";
    private static final String MESSAGE_FORMAT_PM_SENDER = "message-format.pm.sender";
    private static final String MESSAGE_FORMAT_PM_RECEIVER = "message-format.pm.receiver";
    
    public double getRangedChatRadius()
    {
        if( this.config.contains(RANGED_CHAT_RADIUS) ) {
            return this.config.getDouble(RANGED_CHAT_RADIUS);
        }
        return 0.0;
    }
    
    public boolean isRangeChatEnabled()
    {
        if( this.config.contains(RANGED_CHAT_ENABLED) ) {
            return this.config.getBoolean(RANGED_CHAT_ENABLED);
        }
        return false;
    }
    
    public String getChatMessageFormat()
    {
        if( this.config.contains(MESSAGE_FORMAT_CHAT) ) {
            return this.config.getString(MESSAGE_FORMAT_CHAT);
        }
        return "${prefix}${sender}${suffix}&f: ${message}";
    }
    
    public String getSayMessageFormat()
    {
        if( this.config.contains(MESSAGE_FORMAT_SAY) ) {
            return this.config.getString(MESSAGE_FORMAT_SAY);
        }
        return "&eServer&f: ${message}";
    }
    
    public String getPrivateMessageSenderFormat()
    {
        if( this.config.contains(MESSAGE_FORMAT_PM_SENDER ) ) {
            return this.config.getString(MESSAGE_FORMAT_PM_SENDER);
        }
        return "&7[to: ${receiver}]&f: ${message}";
    }
    
    public String getPrivateMessageReceiverFormat()
    {
        if( this.config.contains(MESSAGE_FORMAT_PM_RECEIVER) ) {
            return this.config.getString(MESSAGE_FORMAT_PM_RECEIVER);
        }
        return "&7[from: ${sender}]&f: ${message}";
    }
    
    //-------------------------------------------------------------------------
    // Private : Loading
    //-------------------------------------------------------------------------

    private void loadConfigurationFromPlugin()
    {
        this.config = new YamlConfiguration();
        InputStream stream = this.plugin.getResource(this.resourcePath);
        assert stream != null;

        InputStreamReader reader = new InputStreamReader(stream);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(reader);
        this.config.setDefaults(config);
    }
    
    private void loadConfigurationFromDisk()
    {
        this.config = new YamlConfiguration();
        try {
            this.config.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            Logger logger = this.plugin.getLogger();
            logger.log(Level.SEVERE, "Could not load " + this.file.getName() + " from " + this.file, e);
        }
    }
}
