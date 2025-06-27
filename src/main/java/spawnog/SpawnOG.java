package spawnog;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
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

        registerCommand("spawn", new SpawnCommand(), "spawnog.teleport");
        registerCommand("setspawn", new SetSpawnCommand(), "spawnog.admin");

        getServer().getPluginManager().registerEvents(new SpawnListener(), this);
    }

    @Override
    public void onDisable() {
        log.info("Plugin disabled.");
    }

    private void registerCommand(String name, CommandExecutor exec, String perm) {
        PluginCommand cmd = getCommand(name);
        if (cmd == null) {
            getLogger().severe("Command '" + name + "' is missing from plugin.yml!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        cmd.setPermission(perm);
        cmd.setExecutor(exec);
    }
}
