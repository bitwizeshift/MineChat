package com.rodusek.minechat.permissions;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

/*****************************************************************************
 * An enumeration of all the possible permission nodes in this plugin
 * 
 * Permissions are recursively created from other permissions to dynamically
 * construct the corresponding permission nodes.
 *****************************************************************************/
public enum PluginPermission
{    
    //-------------------------------------------------------------------------
    // Base
    //-------------------------------------------------------------------------
    
    /** Base permission node */
    MINECHAT("minechat"),

    //-------------------------------------------------------------------------
    // Messaging
    //-------------------------------------------------------------------------

    /** Permission node for enabling messages */
    MESSAGE(MINECHAT,"message", PluginPermissionDefault.TRUE),

    //-------------------------------------------------------------------------
    // Formatting
    //-------------------------------------------------------------------------
    
    /** Permission node for enabling all formatting (parent node) */
    FORMATTING(MINECHAT,"formatting", PluginPermissionDefault.FALSE),
    
    /** Permission node for enabling reset */
    FORMATTING_RESET(FORMATTING,"reset"),
    
    /** Permission node for enabling the magic text */
    FORMATTING_MAGIC(FORMATTING,"magic"),
    
    /** Permission node for enabling italicized text formatting in chat */
    FORMATTING_ITALIC(FORMATTING,"italic"),
    
    /** Permission node for enabling bold text formatting in chat */
    FORMATTING_BOLD(FORMATTING,"bold"),
    
    /** Permission node for enabling strikethrough text formatting in chat */
    FORMATTING_STRIKETHROUGH(FORMATTING,"strikethrough"),
    
    /** Permission node for enabling underline text formatting in chat */
    FORMATTING_UNDERLINE(FORMATTING,"underline"),
    
    /** Permission node for enabling color formatting in chat */
    FORMATTING_COLOR(FORMATTING,"color"),
    
    //-------------------------------------------------------------------------
    // Commands
    //-------------------------------------------------------------------------

    /** Parent permission node for all (non-mod) commands */
    COMMAND(MINECHAT,"command",PluginPermissionDefault.TRUE),

    /** Permission node for allowing private message (pm) command */
    COMMAND_PRIVATE_MESSAGE(COMMAND,"pm"),

    /** Permission node for allowing the 'speeddial' command */
    COMMAND_SPEED_DIAL(COMMAND,"speeddial"),
    
    //-------------------------------------------------------------------------
    // Moderator Permissions
    //-------------------------------------------------------------------------

    /** Parent permission node for all mod permissions */
    MOD(MINECHAT,"mod", PluginPermissionDefault.OP),
    
    /** Parent permission node for all mod commands */
    MOD_COMMAND(MOD,"command"),
    
    /** Permission node for allowing the 'say' command */
    MOD_COMMAND_SAY(MOD_COMMAND,"say"),
    
    /** Permission node for allowing the 'minechat' command */
    MOD_COMMAND_MINECHAT(MOD_COMMAND,"minechat"),
    
    /** Permission node for allowing the 'reload' sub-command. Requires 'minechat' */
    MOD_COMMAND_MINECHAT_RELOAD(MOD_COMMAND_MINECHAT,"reload"),
    
    /** Permission node for enabling ranged-chat suport */
    MOD_RANGE(MOD,"range", PluginPermissionDefault.OP),
    
    /** Permission node for enabling global messages when in range chat mode */
    MOD_RANGE_GLOBAL(MOD_RANGE,"global"),
    
    /** Permission node for always overriding global chat */
    MOD_RANGE_OVERRIDE(MOD_RANGE,"override", PluginPermissionDefault.FALSE)
    ;

    //-------------------------------------------------------------------------
    // Private Members
    //-------------------------------------------------------------------------

    private final String name;
    private final PluginPermissionDefault defaultValue;
    private final List<PluginPermission> children;

    //-------------------------------------------------------------------------
    // Observers
    //-------------------------------------------------------------------------

    /**
     * Gets the name for this permission
     * 
     * @return The permission string
     */
    public String getName() { return this.name; }
    
    /**
     * Gets the default value for this permission
     * 
     * @return the default value for this permission
     */
    public PluginPermissionDefault getDefaultValue() { return this.defaultValue; }

    //-------------------------------------------------------------------------
    // Private Constructors
    //-------------------------------------------------------------------------

    /**
     * Constructs this permission with the given permissions string with a
     * default value of false
     * 
     * @param name the permission string
     */
    private PluginPermission( final String name )
    {
        this( name, PluginPermissionDefault.FALSE );
    }

    /**
     * Constructs this permission with the given permissions string and a 
     * default value
     * 
     * @param name the permission string
     * @param defaultValue the default value
     */
    private PluginPermission( final String name,
                              final PluginPermissionDefault defaultValue )
    {
        Validate.notNull( name, "Name cannot be null" );
        Validate.notEmpty( name, "Name cannot be empty" );

        this.name         = name;
        this.defaultValue = defaultValue;
        this.children     = new ArrayList<PluginPermission>();
    }

    /**
     * Constructs this permission with a parent permission, and the permission
     * string
     * 
     * The default value is set to whatever the parent permission's default 
     * value is.
     * 
     * @param parent the parent permission for this node to default from
     * @param name the permission string
     */
    private PluginPermission( final PluginPermission parent, 
                              final String name )
    {
        Validate.notNull( parent, "Parent cannot be null" );
        Validate.notNull( name, "Name cannot be null" );
        Validate.notEmpty( name, "Name cannot be empty" );
        
        this.name         = parent.name + "." + name;
        this.defaultValue = parent.defaultValue;
        this.children     = new ArrayList<PluginPermission>();

        parent.children.add(this);
    }

    /**
     * Constructs this permission with a parent permission, the given 
     * permission string, and the default permission value
     * 
     * @param parent the parent permission node
     * @param name the permission string
     * @param defaultValue the default value
     */
    private PluginPermission( final PluginPermission parent, 
                              final String name,
                              final PluginPermissionDefault defaultValue )
    {
        Validate.notNull( parent, "Parent cannot be null" );
        Validate.notNull( name, "Name cannot be null" );
        Validate.notEmpty( name, "Name cannot be empty" );

        this.name         = parent.name + "." + name;
        this.defaultValue = defaultValue;
        this.children     = new ArrayList<PluginPermission>();
        
        parent.children.add(this);
    }
}
