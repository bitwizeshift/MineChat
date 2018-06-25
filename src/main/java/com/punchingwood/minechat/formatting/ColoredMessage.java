package com.punchingwood.minechat.formatting;

import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;

import com.punchingwood.minechat.PluginPermission;

public class ColoredMessage implements Message
{
    private final static Pattern CHAT_FORMAT_COLOR         = Pattern.compile("(?i)&([0-9A-F])");
    private final static Pattern CHAT_FORMAT_MAGIC         = Pattern.compile("(?i)&([K])");
    private final static Pattern CHAT_FORMAT_BOLD          = Pattern.compile("(?i)&([L])");
    private final static Pattern CHAT_FORMAT_STRIKETHROUGH = Pattern.compile("(?i)&([M])");
    private final static Pattern CHAT_FORMAT_UNDERLINE     = Pattern.compile("(?i)&([N])");
    private final static Pattern CHAT_FORMAT_ITALIC        = Pattern.compile("(?i)&([O])");
    private final static Pattern CHAT_FORMAT_RESET         = Pattern.compile("(?i)&([R])");

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
            hasColor = this.permissible.hasPermission( PluginPermission.FORMATTING_COLOR.toString() );
            hasMagic = this.permissible.hasPermission( PluginPermission.FORMATTING_MAGIC.toString() );
            hasBold  = this.permissible.hasPermission( PluginPermission.FORMATTING_BOLD.toString() );
            hasStringthrough = this.permissible.hasPermission( PluginPermission.FORMATTING_STRIKETHROUGH.toString() );
            hasUnderline = this.permissible.hasPermission( PluginPermission.FORMATTING_UNDERLINE.toString() );
            hasItalic = this.permissible.hasPermission( PluginPermission.FORMATTING_ITALIC.toString() );
            hasReset = this.permissible.hasPermission( PluginPermission.FORMATTING_RESET.toString() );
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
        if( hasAll ) {
            result.replaceAll("&((?i)[0-9a-fk-or])", "\u00A7$1");
        } else {
            if( hasColor ) {
                result = CHAT_FORMAT_COLOR.matcher(result).replaceAll("\u00A7$1");
            }
            if( hasMagic ) {
                result = CHAT_FORMAT_MAGIC.matcher(result).replaceAll("\u00A7$1");
            }
            if( hasBold ) {
                result = CHAT_FORMAT_BOLD.matcher(result).replaceAll("\u00A7$1");
            }
            if( hasStringthrough ) {
                result = CHAT_FORMAT_STRIKETHROUGH.matcher(result).replaceAll("\u00A7$1");
            }
            if( hasUnderline ) {
                result = CHAT_FORMAT_UNDERLINE.matcher(result).replaceAll("\u00A7$1");   
            }
            if( hasItalic ) {
                result = CHAT_FORMAT_ITALIC.matcher(result).replaceAll("\u00A7$1");  
            }
            if( hasReset ) {
                result = CHAT_FORMAT_RESET.matcher(result).replaceAll("\u00A7$1"); 
            } 
        }
  
        this.formattedMessage = result;
        
        return this.formattedMessage;
    }
}
