package dev.acronical.pvptoggle;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PvPCommand implements CommandExecutor, TabCompleter {
    private static final String[] OPTIONS = { "on", "off" };

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] strings) {
        if (!commandSender.isOp() && !commandSender.hasPermission("pvptoggle.pvptoggle")) {
            return false;
        }

        World world = commandSender.getServer().getWorld(commandSender.getServer().getWorlds().getFirst().getName());
        if (world == null) {
            commandSender.sendMessage("World not found, this should not happen!");
            return false;
        }

        if (strings.length == 0) {
            boolean isEnabled = world.getPVP();
            world.setPVP(!isEnabled);
            commandSender.sendMessage("You have " + (isEnabled ? "disabled" : "enabled") + " PVP.");
            return true;
        }

        if (strings[0].equalsIgnoreCase("on")) {
            world.setPVP(true);
            commandSender.sendMessage("You have enabled PVP.");
            return true;
        }

        if (strings[0].equalsIgnoreCase("off")) {
            world.setPVP(false);
            commandSender.sendMessage("You have disabled PVP.");
            return true;
        }

        commandSender.sendMessage("Usage: /pvp <on|off>");
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(args[0], List.of(OPTIONS), completions);
        return completions;
    }
}
