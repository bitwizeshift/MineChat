package com.punchingwood.minechat.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.punchingwood.minechat.completions.PlayerCompletion;
import com.punchingwood.minechat.configuration.PluginConfiguration;
import com.punchingwood.minechat.formatting.ColoredMessage;
import com.punchingwood.minechat.formatting.Message;
import com.punchingwood.minechat.formatting.PrivateMessage;
import com.punchingwood.minechat.permissions.PluginPermission;
import com.punchingwood.minechat.utilities.PlayerRepository;

public class PrivateMessageCommand extends PluginCommand
{
    //-------------------------------------------------------------------------
    // Static Members
    //-------------------------------------------------------------------------
    
    private static final String LAST_PLAYER_NAME = "-";
    private static final String NAME          = "pm";
    private static final String DESCRIPTION   = "Privately messages another player";
    private static final String USAGE         = "/" + NAME + " <player> <message...>";
    private static final List<String> ALIASES = Arrays.asList();

    //-------------------------------------------------------------------------
    // Members
    //-------------------------------------------------------------------------

    private final PlayerRepository players;
    private final PluginConfiguration config;
    private final Map<Player,Player> lastContact;
    
    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    
    public PrivateMessageCommand( final Plugin plugin,
                                  final PluginConfiguration config,
                                  final PlayerRepository players )
    {
        super(plugin,NAME,DESCRIPTION,USAGE,ALIASES);
        this.config      = config;
        this.players     = players;
        this.lastContact = new HashMap<Player,Player>();
    }

    //-------------------------------------------------------------------------
    // Command Management
    //-------------------------------------------------------------------------

    private enum ContactType
    {
        SPEED_DIAL,
        REDIAL,
        DIAL;
    }
    
    /**
     * 
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     */
    @Override
    public boolean execute( final CommandSender sender, 
                            final String label, 
                            final String[] args )
    {        
        if( !(sender instanceof Player) ) {
            sender.sendMessage(ChatColor.RED + "Only players can use /" + label );
            return true;
        }
                
        if( !sender.hasPermission( PluginPermission.COMMAND_PRIVATE_MESSAGE.toString() ) ) {
            sender.sendMessage(ChatColor.RED + "Insufficient permissions");
            return true;   
        }
        
        if( args.length <= 1 ) {
            sender.sendMessage(ChatColor.RED + "Usage: " + super.getUsage() );
            return true;
        }
        
        final String name = args[0];
        
        final ContactType type;
        final Player receiver;

        // Get recipient
        if( name.equals(LAST_PLAYER_NAME) ) {
            type = ContactType.REDIAL;
            receiver = this.lastContact.get( (Player) sender );
        } else if( name.length() == 1 && Character.isDigit(name.charAt(0)) ) {
            type = ContactType.SPEED_DIAL;
            receiver = null;
//            final SpeedDial favorites = this.playerSpeedDialMap.get( (Player) sender );
//            if( favorites == null ) {
//                receiver = null;
//            } else {
//                int number = Integer.parseInt(name);
//                receiver   = favorites.get(number);
//            }
        } else {
            type = ContactType.DIAL;
            receiver = this.players.get(name);
        }
        
        // No 'last' player to send to
        if( receiver == null ) {
            if( type == ContactType.REDIAL ) {
                sender.sendMessage(ChatColor.RED + "No player to send to. You haven't sent any PMs since logging in yet!");
                return false;
            }
            
            if( type == ContactType.SPEED_DIAL ) {
                sender.sendMessage(ChatColor.RED + "No player set to speed-dial on " + "!");
                return false;
            }
            
            if( type == ContactType.DIAL ) {
                sender.sendMessage(ChatColor.RED + "You can only message online players!");                
                return false;
            }
        }
        
        // Player not online
        if( receiver == null || !receiver.isOnline() ) {
            return false;
        }
        
        final String  message = compileMessage( args, 1, args.length );
        final Message colored = new ColoredMessage( sender, message );

        { // Determine sender message
            
            final String  format = this.config.getPrivateMessageSenderFormat();
            final Message output = new PrivateMessage( (Player) sender, 
                                                       receiver, 
                                                       format,
                                                       colored.getMessage() );
            sender.sendMessage( output.getMessage() );
        }
        { // Determine recipient message
            final String  format = this.config.getPrivateMessageReceiverFormat();
            final Message output = new PrivateMessage( (Player) sender, 
                                                       receiver, 
                                                       format,
                                                       colored.getMessage() );
            receiver.sendMessage( output.getMessage() );
        }

        // Record the communication
        this.lastContact.put( (Player) sender, (Player) receiver );
        return true;
    }

    @Override
    public List<String> tabComplete( final CommandSender sender,
                                     final String alias,
                                     final String[] args )
    {
        if( !(sender instanceof Player) ) return null;
        if( args.length > 1 ) return null;
        
        final List<String> completions = new ArrayList<String>();

        if( args.length == 0 || (args.length == 1 && args[0].equals("") ) ) {

            // If there is a last contact, add it to the completion
            if( this.lastContact.get( (Player) sender ) != null ) 
            {
                completions.add(LAST_PLAYER_NAME);
            }
            
            completions.addAll(PlayerCompletion.completeWithAllPlayers());
            
            return completions;
        }
        
        return PlayerCompletion.completeWithPlayer( args[0] );
    }

    //-------------------------------------------------------------------------
    //
    //-------------------------------------------------------------------------

    static private String compileMessage( String[] args, int start, int end ) 
    {
        if (start > args.length) {
            throw new IndexOutOfBoundsException();
        }
        final StringBuilder result = new StringBuilder(args[start]);
        
        for( int i = start+1; i < end; ++i ) {
            result.append(" ");
            result.append(args[i]);
        }
        return result.toString();
    }

}
