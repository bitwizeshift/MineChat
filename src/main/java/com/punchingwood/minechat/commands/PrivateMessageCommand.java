package com.punchingwood.minechat.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.punchingwood.minechat.CommandName;
import com.punchingwood.minechat.PluginConfiguration;
import com.punchingwood.minechat.PluginPermission;
import com.punchingwood.minechat.formatting.ColoredMessage;
import com.punchingwood.minechat.formatting.Message;
import com.punchingwood.minechat.formatting.PrivateMessage;
import com.punchingwood.minechat.utilities.PlayerRepository;

public class PrivateMessageCommand implements CommandExecutor, PluginCommand
{
    //-------------------------------------------------------------------------
    // Static Members
    //-------------------------------------------------------------------------
    
    static private final String LAST_PLAYER_NAME = "-";

    //-------------------------------------------------------------------------
    // Members
    //-------------------------------------------------------------------------

    private final CommandName name;
    private final PlayerRepository players;
    private final PluginConfiguration config;
    private final Map<Player,Player> lastContact;
    
    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    
    public PrivateMessageCommand( final CommandName name, 
                                  final PluginConfiguration config,
                                  final PlayerRepository players )
    {
        this.name        = name;
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
    public boolean onCommand( final CommandSender sender, 
                              final Command command, 
                              final String label, 
                              final String[] args )
    {        
        if( !(sender instanceof Player) ) {
            sender.sendMessage(ChatColor.RED + "Only players can use /" + this.getCommandName());
            return false;
        }
                
        if( !sender.hasPermission( PluginPermission.COMMAND_PRIVATE_MESSAGE.toString() ) ) {
            sender.sendMessage(ChatColor.RED + "Insufficient permissions");
            return false;   
        }
        
        if( args.length <= 1 ) {
            sender.sendMessage(ChatColor.RED + "Usage: " + getUsage( label ) );
            return false;
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
        
        final String  message = compileMessage( args, 1, args.length-1 );
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

    //-------------------------------------------------------------------------
    //
    //-------------------------------------------------------------------------

    @Override
    public String getCommandName() { return this.name.getName(); }

    //-------------------------------------------------------------------------

    /** 
     * Gets the usage string for this command 
     * 
     * @return the usage string
     */
    private String getUsage( final String label )
    {
        return "/" + label + " <user> <message...>";
    }

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
