package com.punchingwood.minechat.formatting;

public class BroadcastMessage implements Message
{
    //-------------------------------------------------------------------------
    // Members
    //-------------------------------------------------------------------------

    private final String format;
    private final String message;

    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------

    public BroadcastMessage( final String format, 
                             final String message )
    {
        this.format = format;
        this.message = message;
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

        final Message colored = new ColoredMessage( result );
        result = colored.getMessage();
        result = result.replace( TemplateToken.MESSAGE_CONTENT.getToken(), this.message );
        
        return result;
    }
}
