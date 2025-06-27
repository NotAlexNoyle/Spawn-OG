package spawnog.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import spawnog.SpawnOG;

public class SpawnListener implements Listener {
    private Location spawn() {
        return SpawnOG.getInstance().getConfig().getLocation("general.location");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            Location s = spawn();
            if (s != null) {
                Location loc = s.clone();
                loc.setYaw(0f);
                loc.setPitch(0f);
                event.getPlayer().teleportAsync(loc);
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player p = event.getPlayer();
        if (p.getBedSpawnLocation() != null) return;
        Location s = spawn();
        if (s != null) {
            Location loc = s.clone();
            loc.setYaw(0f);
            loc.setPitch(0f);
            event.setRespawnLocation(loc);
        }
    }
}
