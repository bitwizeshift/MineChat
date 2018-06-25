package com.punchingwood.minechat.formatting;

/**
 * The tokens used for message formatting.
 *
 */
public enum TemplateToken
{
    // Player details
    PLAYER_PREFIX("prefix"),
    PLAYER_SUFFIX("suffix"),
    PLAYER_WORLD("world"),
    PLAYER_GROUP("group"),
    
    // Message pieces
    MESSAGE_SENDER("sender"),
    MESSAGE_RECEIVER("receiver"),
    MESSAGE_CONTENT("message"),
   
    // Server-related
    SERVER_NAME("server"),
    
    // Time-related
    SERVER_TIME_SECOND("s"),
    SERVER_TIME_SECOND_PADDED("ss"),
    SERVER_TIME_MINUTE("m"),
    SERVER_TIME_MINUTE_PADDED("mm"),
    SERVER_TIME_12HOUR("h"),
    SERVER_TIME_12HOUR_PADDED("hh"),
    SERVER_TIME_24HOUR("H"),
    SERVER_TIME_24HOUR_PADDED("HH"),
    SERVER_TIME_AM_PM_LOWER("a"),
    SERVER_TIME_AM_PM_UPPER("A"),
    ;
    
    //-------------------------------------------------------------------------
    // Members
    //-------------------------------------------------------------------------

    private final String token;

    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------

    /**
     * Constructs the MessageFormatToken with the given string token
     * 
     * @param token the token that represents the message format
     */
    private TemplateToken( String token )
    {
        this.token = token;
    }
        
    //-------------------------------------------------------------------------
    // Object
    //-------------------------------------------------------------------------

    public String getToken() { return "${" + this.token + "}"; }
}
