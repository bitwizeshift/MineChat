package com.punchingwood.minechat.formatting;

import org.bukkit.entity.Player;

public class PrivateMessage implements Message
{
    private final Player sender;
    private final Player receiver;
    private final String format;
    private final String message;
    private final Object hook = null;
    
    public PrivateMessage( final Player sender,
                           final Player receiver,
                           final String format,
                           final String message ) 
    {
        this.sender = sender;
        this.receiver = receiver;
        this.format = format;
        this.message = message;
    }

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
            prefix = "";
            suffix = "";
            group  = "";
        } else {
            prefix = "";
            suffix = "";
            group  = "";
        }
        
        result = result.replace( TemplateToken.SENDER_PREFIX.getToken(), prefix );
        result = result.replace( TemplateToken.SENDER_SUFFIX.getToken(), suffix );
        result = result.replace( TemplateToken.SENDER_WORLD.getToken(),  world );
        result = result.replace( TemplateToken.SENDER_GROUP.getToken(),  group );
        result = result.replace( TemplateToken.SENDER_NAME.getToken(), this.sender.getDisplayName() );
        result = result.replace( TemplateToken.RECEIVER_NAME.getToken(), this.receiver.getDisplayName() );
        result = result.replace( TemplateToken.MESSAGE_CONTENT.getToken(), this.message );

        final Message colored = new ColoredMessage( result );
        return colored.getMessage();
    }
}
