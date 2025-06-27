package net.kasumadps.spawntp.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.kasumadps.spawntp.SpawnTP;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpawnCommand implements CommandExecutor, TabCompleter {
    private final FileConfiguration config = SpawnTP.getInstance().getConfig();
    private static final String LOCATION_PATH = "general.location";

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Location base = config.getLocation(LOCATION_PATH);
        if (base == null) {
            sender.sendMessage(config.getString("locale.spawnNotSet", "Server spawn not set."));
            return true;
        }
        Location dest = base.clone();
        dest.setYaw(0f);
        dest.setPitch(0f);

        if (args.length == 0) {
            if (!(sender instanceof Player)) return false;
            Player player = (Player) sender;
            if (!player.hasPermission(command.getPermission())) {
                player.sendMessage(
                        config.getString("locale.missingPermission", "You are lacking the required permissions."));
                return true;
            }
            player.teleportAsync(dest);
            return true;
        }

        if (!sender.hasPermission("spawn.others")) {
            sender.sendMessage(
                    config.getString("locale.missingPermission", "You are lacking the required permissions."));
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage(config.getString("locale.playerNotFound", "Player not found."));
            return true;
        }
        target.teleportAsync(dest);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1 && sender.hasPermission("spawn.others")) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(n -> n.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
