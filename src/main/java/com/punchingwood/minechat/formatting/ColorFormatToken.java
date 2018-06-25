package com.punchingwood.minechat.formatting;

public enum ColorFormatToken
{
    BLACK('0'),
    DARK_BLUE('1'),
    DARK_GREEN('2'),
    DARK_AQUA('3'),
    DARK_RED('4'),
    DARK_PURPLE('5'),
    GOLD('6'),
    GRAY('7'),
    DARK_GRAY('8'),
    BLUE('9'),
    GREEN('a'),
    AQUA('b'),
    RED('c'),
    LIGHT_PURPLE('d'),
    YELLOW('e'),
    WHITE('f'),
    MAGIC('k'),
    BOLD('l'),
    STRIKETHROUGH('m'),
    UNDERLINE('n'),
    ITALIC('o'),
    RESET('r')
    ;

    //-------------------------------------------------------------------------
    // Members
    //-------------------------------------------------------------------------
    
    private final char token;

    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------

    /**
     * Constructs this TextFormatToken from the given token string
     * 
     * @param token the token identifier
     */
    private ColorFormatToken( char token )
    {
        this.token = token;
    }
    
    //-------------------------------------------------------------------------
    // Object
    //-------------------------------------------------------------------------

    /**
     * Converts this formatting token into a string
     * 
     * @return the string for this token
     */
    @Override
    public String toString() { return "&" + this.token; }
}
