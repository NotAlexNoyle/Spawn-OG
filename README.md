# Spawn-OG

**Spawn-OG** is a Minecraft server plugin that allows server administrators to set world spawn points and manage player teleportation to those spawns. It provides functionality similar to EssentialsXSpawn – such as setting and teleporting to spawn points, and first-join spawn control, with additional configuration options from TrueOG Network. Spawn-OG works in conjunction with the [Essentials-OG](https://github.com/true-og/Essentials-OG) fork of EssentialsX. It handles the spawn-related features originally handled by EssentialsSpawn. This is one small step towards a post-Essentials TrueOG Network.

## Features

- **Set Spawn Points:**
```/setspawn```
- **Teleport to Spawn:**
```/spawn```
- **Teleport Others to Spawn:**
```/spawn <player```
- **First Join Spawn (Newbie Spawn):**
- **Respawn Handling:**
By default, if `respawn-at-home` is set to false, players will respawn at the spawn point. If `respawn-at-home` is true, players will respawn at their bed or home location (if defined) instead of spawn. The plugin respects beds and Essentials homes according to this setting.
- **Spawn-on-Join:**
Optionally, the plugin can force players to go to spawn every time they log in, by enabling `spawn-on-join` in the config.

## Commands

- **`/spawn`**
Teleport yourself to the defined spawn location. If used by a player, it sends that player to the spawn (global or their group’s spawn):contentReference[oaicite:21]{index=21}. If used from console or by an admin with a target, see below.  
- **`/spawn <player>`**
Teleport the specified player to spawn. The target player will be sent to the spawn point, provided the command executor has permission to send others. The target will receive a message (e.g. “<*Player*> teleported you to spawn.”).  
- **`/setspawn`**
Set the spawn point for the current world/group to your current location. With no arguments, this sets the “default” global spawn point:contentReference[oaicite:22]{index=22}. This is typically the main spawn for your server (and is used for all players unless group-specific spawns are set).  
- **`/setspawn <group>`**
Set a spawn point for a specific group name. For example, `/setspawn newbie` will set the spawn location used for new players (if your config’s `newbies.spawnpoint` is “newbie”), or `/setspawn builders` could set a special spawn for the “builders” permission group.

## Permissions

- **`essentials.spawn`** – Allows use of the `/spawn` command to teleport oneself to spawn. *Default:* true.
- **`essentials.spawn.others`** – Allows sending *other players* to spawn with `/spawn <player>`. *Default:* false.
- **`essentials.setspawn`** – Allows use of `/setspawn` to define spawn points. *Default:* false.

## Prerequisites

[Essentials-OG](https://github.com/true-og/Essentials-OG)

## Building

Use the TrueOG Bootstrap to build automatically, or run:

```./gradlew clean build eclipse```

## Licese

[MIT](https://raw.githubusercontent.com/true-og/Spawn-OG/refs/heads/main/LICENSE)
