package com.punchingwood.minechat;

public enum PluginPermission
{
    //-------------------------------------------------------------------------
    // Base
    //-------------------------------------------------------------------------
    
    /** Base permission node */
    MINECHAT("minechat", false),

    //-------------------------------------------------------------------------
    // Messaging
    //-------------------------------------------------------------------------

    /** Node for enabling messages */
    MESSAGE(MINECHAT,"message", true),

    //-------------------------------------------------------------------------
    // Formatting
    //-------------------------------------------------------------------------
    
    /** Node for enabling formatting */
    FORMATTING(MINECHAT,"formatting", false),
    
    FORMATTING_RESET(FORMATTING,"reset"),
    FORMATTING_MAGIC(FORMATTING,"magic"),
    FORMATTING_ITALIC(FORMATTING,"italic"),
    FORMATTING_BOLD(FORMATTING,"bold"),
    FORMATTING_STRIKETHROUGH(FORMATTING,"strikethrough"),
    FORMATTING_UNDERLINE(FORMATTING,"underline"),
    FORMATTING_COLOR(FORMATTING,"color"),
    
    //-------------------------------------------------------------------------
    // Range 
    //-------------------------------------------------------------------------
    
    RANGE(MINECHAT,"range", true),
    RANGE_GLOBAL(RANGE,"global",false),
    RANGE_OVERRIDE(RANGE,"override",false),
    
    //-------------------------------------------------------------------------
    // Commands
    //-------------------------------------------------------------------------

    COMMAND(MINECHAT,"command",false),

    COMMAND_PRIVATE_MESSAGE(COMMAND,"pm",true),

    //-------------------------------------------------------------------------
    // Mod Commands
    //-------------------------------------------------------------------------

    MOD(MINECHAT,"mod", false),
    MOD_COMMAND(MOD,"command"),
    MOD_COMMAND_SAY(MOD_COMMAND,"say"),
    MOD_COMMAND_MINECHAT(MOD_COMMAND,"minechat"),
    MOD_COMMAND_MINECHAT_RELOAD(MOD_COMMAND_MINECHAT,"reload"),

    //-------------------------------------------------------------------------
    //
    //-------------------------------------------------------------------------

    SET(MINECHAT,"set", false),
    SET_PREFIX(SET,"prefix"),
    SET_SUFFIX(SET,"suffix"),
    SET_CHAT_FORMAT(SET,"chat-format"),
    SET_MSG_FORMAT(SET,"msg-format"),
    SET_SAY_FORMAT(SET,"say-format")
    ;

    //-------------------------------------------------------------------------
    // Private Members
    //-------------------------------------------------------------------------

    private final String permission;
    private final PluginPermission parent;
    private final boolean defaultValue;

    //-------------------------------------------------------------------------
    // Observers
    //-------------------------------------------------------------------------

    /**
     * Gets the permission string for this permission
     * 
     * @return The permission string
     */
    @Override
    public String toString()
    {
        if( this.parent == null ) return this.permission;
        return this.parent.toString() + "." + this.permission;
    }

    /**
     * Gets the default value for this permission
     * 
     * @return the default value for this permission
     */
    public boolean getDefaultValue() { return this.defaultValue; }
    
    //-------------------------------------------------------------------------
    // Private Constructors
    //-------------------------------------------------------------------------

    /**
     * Constructs this permission with the given permissions string and a 
     * default value
     * 
     * @param permission the permission string
     * @param defaultValue the default value
     */
    private PluginPermission( final String permission,
                              final boolean defaultValue )
    {
        assert permission != null;
        assert permission != "";

        this.parent       = null;
        this.permission   = permission;
        this.defaultValue = defaultValue;
    }

    /**
     * Constructs this permission with a parent permission, and the permission
     * string
     * 
     * The default value is set to whatever the parent permission's default 
     * value is.
     * 
     * @param parent the parent permission for this node to default from
     * @param permission the permission string
     */
    private PluginPermission( final PluginPermission parent, 
                              final String permission )
    {
        assert parent != null;
        assert permission != null;
        assert permission != "";
        
        this.parent       = parent;
        this.permission   = permission;
        this.defaultValue = this.parent.defaultValue;
    }

    /**
     * Constructs this permission with a parent permission, the given 
     * permission string, and the default permission value
     * 
     * @param parent the parent permission node
     * @param permission the permission string
     * @param defaultValue the default value
     */
    private PluginPermission( final PluginPermission parent, 
                              final String permission,
                              final boolean defaultValue )
    {
        assert parent != null;
        assert permission != null;
        assert permission != "";

        this.parent       = parent;
        this.permission   = permission;
        this.defaultValue = defaultValue;
    }
}
