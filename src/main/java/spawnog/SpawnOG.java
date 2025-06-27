package spawnog;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.plugin.java.JavaPlugin;
import spawnog.commands.SetSpawnCommand;
import spawnog.commands.SpawnCommand;
import spawnog.listener.SpawnListener;

@Slf4j
public final class SpawnOG extends JavaPlugin {
    @Getter
    private static SpawnOG instance;

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
