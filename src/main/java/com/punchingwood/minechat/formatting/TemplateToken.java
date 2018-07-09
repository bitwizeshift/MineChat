package com.punchingwood.minechat.formatting;

/*****************************************************************************
 * The tokens used for message formatting.
 *
 *****************************************************************************/
public enum TemplateToken
{
    //-------------------------------------------------------------------------
    // Sender Tokens
    //-------------------------------------------------------------------------
    
    /** Token for the prefix of the message sender */
    SENDER_PREFIX("sender:prefix"),
    /** Token for the suffix of the message sender */
    SENDER_SUFFIX("sender:suffix"),
    /** Token for the message sender's world */
    SENDER_WORLD("sender:world"),
    /** Token for the message sender's group */
    SENDER_GROUP("sender:group"),
    /** Token for the message sender's name */
    SENDER_NAME("sender:name"),
    /** Token for the message sender's display name */
    SENDER_DISPLAY_NAME("sender:display-name"),

    //-------------------------------------------------------------------------
    // Receiver Tokens
    //-------------------------------------------------------------------------

    /** Token for the prefix of the message receiver */
    RECEIVER_PREFIX("receiver:prefix"),
    /** Token for the suffix of the message receiver */
    RECEIVER_SUFFIX("receiver:suffix"),
    /** Token for the message receiver's world */
    RECEIVER_WORLD("receiver:world"),
    /** Token for the message receiver's group */
    RECEIVER_GROUP("receiver:group"),
    /** Token for the message receiver's name */
    RECEIVER_NAME("receiver:name"),
    /** Token for the message receiver's display name */
    RECEIVER_DISPLAY_NAME("receiver:display-name"),
    
    //-------------------------------------------------------------------------
    // Message Tokens
    //-------------------------------------------------------------------------

    /** Token for the message contents */
    MESSAGE_CONTENT("message"),
    
    //-------------------------------------------------------------------------
    // Time-related Tokens
    //-------------------------------------------------------------------------

    /** Token for the server-time's second (no 0 padding) */
    TIME_SECOND("s"),
    /** Token for the server-time's second (with 0 padding) */
    TIME_SECOND_PADDED("ss"),
    /** Token for the server-time's minute (no 0 padding) */
    TIME_MINUTE("m"),
    /** Token for the server-time's minute (with 0 padding) */
    TIME_MINUTE_PADDED("mm"),
    /** Token for the server-time's hour in 12-hour format (no 0 padding) */
    TIME_12HOUR("h"),
    /** Token for the server-time's hour in 12-hour format (with 0 padding) */
    TIME_12HOUR_PADDED("hh"),
    /** Token for the server-time's hour in 24-hour format (no 0 padding) */
    TIME_24HOUR("H"),
    /** Token for the server-time's hour in 24-hour format (with 0 padding) */
    TIME_24HOUR_PADDED("HH"),
    /** Token for am/pm (lowercase) */
    TIME_AM_PM_LOWER("a"),
    /** Token for AM/PM (uppercase) */
    TIME_AM_PM_UPPER("A"),
    ;
    
    //-------------------------------------------------------------------------
    // Members
    //-------------------------------------------------------------------------

    /** The string representation of the token */
    private final String token;

    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------

    /**
     * Constructs the MessageFormatToken with the given string token
     * 
     * @param token the token that represents the message format
     */
    private TemplateToken( final String token )
    {
        this.token = token;
    }
        
    //-------------------------------------------------------------------------
    // Object
    //-------------------------------------------------------------------------

    public String getToken() { return "${" + this.token + "}"; }
}
