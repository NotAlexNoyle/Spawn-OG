package spawnog.commands;

import java.util.*;
import java.util.stream.Collectors;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import spawnog.SpawnOG;

public class SpawnCommand implements CommandExecutor, TabCompleter {

    private final SpawnOG plugin = SpawnOG.getInstance();
    private final FileConfiguration cfg = plugin.getConfig();

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender, @NotNull Command cmd, @NotNull String lbl, @NotNull String[] args) {

        if (args.length == 0) {
            if (!(sender instanceof Player p)) return false;
            if (!p.hasPermission(cmd.getPermission())) {
                p.sendMessage(cfg.getString("locale.missingPermission", "You are lacking the required permissions."));
                return true;
            }
            Location dest = resolve(p);
            if (dest == null) {
                sender.sendMessage(cfg.getString("locale.spawnNotSet", "Server spawn not set."));
                return true;
            }
            p.teleportAsync(dest);
            return true;
        }

        if (!sender.hasPermission("essentials.spawn.others")) {
            sender.sendMessage(cfg.getString("locale.missingPermission", "You are lacking the required permissions."));
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage(cfg.getString("locale.playerNotFound", "Player not found."));
            return true;
        }
        Location dest = resolve(target);
        if (dest == null) {
            sender.sendMessage(cfg.getString("locale.spawnNotSet", "Server spawn not set."));
            return true;
        }
        target.teleportAsync(dest);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender sender, @NotNull Command cmd, @NotNull String lbl, @NotNull String[] args) {

        if (args.length == 1 && sender.hasPermission("essentials.spawn.others")) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(n -> n.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    private Location resolve(Player p) {
        String group = null;
        LuckPerms lp = plugin.getLuckPerms();
        if (lp != null) {
            User u = lp.getUserManager().getUser(p.getUniqueId());
            if (u != null) group = u.getPrimaryGroup();
        }
        if (group != null) {
            Location g = cfg.getLocation("spawns." + group.toLowerCase() + ".location");
            if (g != null) return g.clone();
        }
        Location def = cfg.getLocation("spawns.global.location");
        return def == null ? null : def.clone();
    }
}
