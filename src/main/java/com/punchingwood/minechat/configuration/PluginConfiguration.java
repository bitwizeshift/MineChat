package com.punchingwood.minechat.configuration;

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

/*****************************************************************************
 * 
 * 
 * 
 *****************************************************************************/
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
        this.plugin       = plugin;
        this.resourcePath = resourcePath;

        final File folder = plugin.getDataFolder();
        assert folder != null;        
        this.file = new File(folder, resourcePath);
        
        copyDefaultConfigurationToDisk();
        loadConfigurationFromDisk();
        loadDefaultConfiguration();
    }
    
    //-------------------------------------------------------------------------
    // Modifiers
    //-------------------------------------------------------------------------
        
    /**
     * Reloads the configuration from this PluginConfiguration
     * 
     * If the configuration file no longer exists on disk, the default 
     * configurations will be reloaded from the plugin instead.
     *       
     * Unlike when this configuration is constructed, reloading the plugin will
     * not copy the configuration back to disk; for this behavior, 
     * see {@link PluginConfiguration#save()} 
     */
    public synchronized void reload()
    {
        this.config = null;
        if( !this.file.exists() ) {
            loadConfigurationFromDisk();
            loadDefaultConfiguration();
        } else {
            loadConfigurationFromPlugin();
            loadDefaultConfiguration();
        }
    }

    //-------------------------------------------------------------------------

    /**
     * Saves the configuration from this PluginConfiguration to disk.
     * 
     * If the file does not yet exist on disk, the file is copied from the 
     * plugin.
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
        copyDefaultConfigurationToDisk();
        
        try {
            this.config.save(this.file);
        } catch (final IOException e) {
            final Logger logger = this.plugin.getLogger();
            logger.log(Level.SEVERE, "Could not save '" + this.file.getName() + "' to " + this.file, e);
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
        return this.config.getDouble(RANGED_CHAT_RADIUS);
    }
    
    public boolean isRangeChatEnabled()
    {
        return this.config.getBoolean(RANGED_CHAT_ENABLED);
    }
    
    public String getChatMessageFormat()
    {
        return this.config.getString(MESSAGE_FORMAT_CHAT);
    }
    
    public String getSayMessageFormat()
    {
        return this.config.getString(MESSAGE_FORMAT_SAY);
    }
    
    public String getPrivateMessageSenderFormat()
    {
        return this.config.getString(MESSAGE_FORMAT_PM_SENDER);
    }
    
    public String getPrivateMessageReceiverFormat()
    {
        return this.config.getString(MESSAGE_FORMAT_PM_RECEIVER);
    }
    
    //-------------------------------------------------------------------------
    // Private : Loading
    //-------------------------------------------------------------------------

    /**
     * Copies the default configuration file to disk.
     * 
     * This will only copy if the file does not already exist on disk.
     */
    private void copyDefaultConfigurationToDisk()
    {
        if (!this.file.exists()) {
            this.plugin.saveResource(this.resourcePath, false);
        }
    }
    
    /**
     * Loads the configuration file from the plugin.
     */
    private void loadConfigurationFromPlugin()
    {
        this.config = new YamlConfiguration();
        final InputStream stream = this.plugin.getResource(this.resourcePath);
        assert stream != null;

        final InputStreamReader reader = new InputStreamReader(stream);
        this.config = YamlConfiguration.loadConfiguration(reader);
    }
    
    /**
     * Loads the configuration file from disk.
     */
    private void loadConfigurationFromDisk()
    {
        this.config = new YamlConfiguration();
        try {
            this.config.load( this.file );
        } catch (IOException | InvalidConfigurationException e) {
            final Logger logger = this.plugin.getLogger();
            logger.log(Level.SEVERE, "Could not load '" + this.file.getName() + "' from " + this.file, e);
        }
    }
    
    /**
     * Loads the default configuration from the plugin's resources
     */
    private void loadDefaultConfiguration()
    {
        final InputStream stream = this.plugin.getResource(this.resourcePath);
        assert stream != null;
        assert this.config != null;

        final InputStreamReader reader = new InputStreamReader(stream);
        final YamlConfiguration defaults = YamlConfiguration.loadConfiguration(reader);
        
        this.config.addDefaults(defaults);
    }

}
