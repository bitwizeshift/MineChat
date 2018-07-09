package com.punchingwood.minechat.formatting;

import java.util.regex.Pattern;

import org.bukkit.permissions.Permissible;

import com.punchingwood.minechat.permissions.PluginPermission;

public class ColoredMessage implements Message
{
    private static final char COLOR_CHAR = '\u00A7';
    private static final Pattern CHAT_FORMAT_ALL           = Pattern.compile("&\\{((?i)[0-9a-fA-Fk-oK-OrR])\\}");
    private static final Pattern CHAT_FORMAT_COLOR         = Pattern.compile("&\\{((?i)[0-9a-fA-F])\\}");
    private static final Pattern CHAT_FORMAT_MAGIC         = Pattern.compile("&\\{((?i)[kK])\\}");
    private static final Pattern CHAT_FORMAT_BOLD          = Pattern.compile("&\\{((?i)[lL])\\}");
    private static final Pattern CHAT_FORMAT_STRIKETHROUGH = Pattern.compile("&\\{((?i)[mM])\\}");
    private static final Pattern CHAT_FORMAT_UNDERLINE     = Pattern.compile("&\\{((?i)[nN])\\}");
    private static final Pattern CHAT_FORMAT_ITALIC        = Pattern.compile("&\\{((?i)[oO])\\}");
    private static final Pattern CHAT_FORMAT_RESET         = Pattern.compile("&\\{((?i)[rR])\\}");

    //-------------------------------------------------------------------------
    // Members
    //-------------------------------------------------------------------------
    
    final Permissible permissible;
    final String      format;
    
    String formattedMessage;

    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------

    public ColoredMessage( final Permissible permissible, 
                           final String format )
    {
        assert permissible != null;
        assert format != null;
        
        this.format = format;
        this.permissible = permissible;
        this.formattedMessage = null;
    }
    
    public ColoredMessage( final String format )
    {
        assert format != null;

        this.format = format;
        this.permissible = null;
        this.formattedMessage = null;
    }
    
    //-------------------------------------------------------------------------
    // 
    //-------------------------------------------------------------------------

    @Override
    public String getMessage()
    {
        if( formattedMessage != null ) { 
            return this.formattedMessage;
        }

        // Lazily evaluate the formatted message

        String result = this.format;
        
        boolean hasColor;
        boolean hasMagic;
        boolean hasBold;
        boolean hasStringthrough;
        boolean hasUnderline;
        boolean hasItalic;
        boolean hasReset;

        if( this.permissible != null ) {
            hasColor = this.permissible.hasPermission( PluginPermission.FORMATTING_COLOR.getName() );
            hasMagic = this.permissible.hasPermission( PluginPermission.FORMATTING_MAGIC.getName() );
            hasBold  = this.permissible.hasPermission( PluginPermission.FORMATTING_BOLD.getName() );
            hasStringthrough = this.permissible.hasPermission( PluginPermission.FORMATTING_STRIKETHROUGH.getName() );
            hasUnderline = this.permissible.hasPermission( PluginPermission.FORMATTING_UNDERLINE.getName() );
            hasItalic = this.permissible.hasPermission( PluginPermission.FORMATTING_ITALIC.getName() );
            hasReset = this.permissible.hasPermission( PluginPermission.FORMATTING_RESET.getName() );
        } else {
            hasColor = true;
            hasMagic = true;
            hasBold  = true;
            hasStringthrough = true;
            hasUnderline = true;
            hasItalic = true;
            hasReset = true;    
        }
        
        boolean hasAll = hasColor && hasMagic && hasBold && 
                         hasStringthrough && hasUnderline && hasReset;
        // Optimized color replacement
        final String replacement = COLOR_CHAR + "$1";
        if( hasAll ) {
            result = CHAT_FORMAT_ALL.matcher(result).replaceAll(replacement);
        } else {
            if( hasColor ) {
                result = CHAT_FORMAT_COLOR.matcher(result).replaceAll(replacement);
            }
            if( hasMagic ) {
                result = CHAT_FORMAT_MAGIC.matcher(result).replaceAll(replacement);
            }
            if( hasBold ) {
                result = CHAT_FORMAT_BOLD.matcher(result).replaceAll(replacement);
            }
            if( hasStringthrough ) {
                result = CHAT_FORMAT_STRIKETHROUGH.matcher(result).replaceAll(replacement);
            }
            if( hasUnderline ) {
                result = CHAT_FORMAT_UNDERLINE.matcher(result).replaceAll(replacement);   
            }
            if( hasItalic ) {
                result = CHAT_FORMAT_ITALIC.matcher(result).replaceAll(replacement);  
            }
            if( hasReset ) {
                result = CHAT_FORMAT_RESET.matcher(result).replaceAll(replacement); 
            } 
        }
  
        this.formattedMessage = result;
        
        return this.formattedMessage;
    }
}
