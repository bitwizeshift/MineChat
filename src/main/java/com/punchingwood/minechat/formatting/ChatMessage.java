package com.punchingwood.minechat.formatting;

import org.bukkit.entity.Player;

public class ChatMessage implements Message
{
    static final String SENDER_PLACEHOLDER  = "%1$s";
    static final String MESSAGE_PLACEHOLDER = "%2$s";
    
    //-------------------------------------------------------------------------
    // Members
    //-------------------------------------------------------------------------

    private final Player sender;
    private final String format;
    private final Object hook = null;

    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------

    public ChatMessage( final Player sender,
                          final String format )
    {
        this.sender = sender;
        this.format = format;
    }

    //-------------------------------------------------------------------------
    // 
    //-------------------------------------------------------------------------

    /**
     * 
     * @return 
     */
    @Override
    public String getMessage()
    {
        final Message timestamped = new TimestampedMessage( this.format );
        String result = timestamped.getMessage();

        final String prefix;
        final String suffix;
        final String group;
        final String world  = this.sender.getWorld().getName();
        
        if( this.hook == null ) {
//          prefix = hook.getPrefix(players[0]);
//          suffix = hook.getSuffix(players[0]);
//          group  = hook.getGroupNames(players[0])[0];
            prefix = "";
            suffix = "";
            group  = "";
        } else {
            prefix = "";
            suffix = "";
            group  = "";
        }
        
        result = result.replace( TemplateToken.PLAYER_PREFIX.getToken(), prefix );
        result = result.replace( TemplateToken.PLAYER_SUFFIX.getToken(), suffix );
        result = result.replace( TemplateToken.PLAYER_WORLD.getToken(),  world );
        result = result.replace( TemplateToken.PLAYER_GROUP.getToken(),  group );
        result = result.replace( TemplateToken.MESSAGE_SENDER.getToken(), SENDER_PLACEHOLDER );
        result = result.replace( TemplateToken.MESSAGE_CONTENT.getToken(), MESSAGE_PLACEHOLDER );

        final Message colored = new ColoredMessage( result );
        return colored.getMessage();
    }
}
