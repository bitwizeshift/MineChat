package com.punchingwood.minechat.commands;

import org.bukkit.command.CommandExecutor;

public interface PluginCommand extends CommandExecutor
{
    String getCommandName();
}
