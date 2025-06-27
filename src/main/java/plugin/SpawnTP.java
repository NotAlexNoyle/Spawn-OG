package net.kasumadps.spawntp;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kasumadps.spawntp.commands.SetSpawnCommand;
import net.kasumadps.spawntp.commands.SpawnCommand;
import net.kasumadps.spawntp.listener.SpawnListener;
import org.bukkit.plugin.java.JavaPlugin;

@Slf4j
public final class SpawnTP extends JavaPlugin {
    @Getter
    private static SpawnTP instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getCommand("spawn").setPermission("spawntp.teleport");
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("setspawn").setPermission("spawntp.admin");
        getCommand("setspawn").setExecutor(new SetSpawnCommand());
        getServer().getPluginManager().registerEvents(new SpawnListener(), this);
    }

    @Override
    public void onDisable() {
        log.info("Plugin disabled.");
    }
}
