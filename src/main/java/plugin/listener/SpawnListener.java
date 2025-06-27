package net.kasumadps.spawntp.listener;

import net.kasumadps.spawntp.SpawnTP;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SpawnListener implements Listener {
    private Location spawn() {
        return SpawnTP.getInstance().getConfig().getLocation("general.location");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasPlayedBefore()) {
            Location s = spawn();
            if (s != null) {
                Location loc = s.clone();
                loc.setYaw(0f);
                loc.setPitch(0f);
                e.getPlayer().teleportAsync(loc);
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (p.getBedSpawnLocation() != null) return;
        Location s = spawn();
        if (s != null) {
            Location loc = s.clone();
            loc.setYaw(0f);
            loc.setPitch(0f);
            e.setRespawnLocation(loc);
        }
    }
}
