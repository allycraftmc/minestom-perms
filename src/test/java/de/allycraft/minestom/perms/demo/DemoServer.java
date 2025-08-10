package de.allycraft.minestom.perms.demo;

import de.allycraft.minestom.perms.luckperms.LuckPermsProvider;
import de.allycraft.minestom.perms.OpPermissionProvider;
import de.allycraft.minestom.perms.PermissionProviderManager;
import de.allycraft.minestom.perms.Permissions;
import me.lucko.luckperms.minestom.CommandRegistry;
import me.lucko.luckperms.minestom.LuckPermsMinestom;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.luckperms.api.LuckPerms;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.InstanceContainer;

import java.nio.file.Path;

public class DemoServer {
    public static void main(String[] args) {
        MinecraftServer minecraftServer = MinecraftServer.init();

        MojangAuth.init();

        Permissions.registerProvider(OpPermissionProvider.fromEnvironmentVariable());
        LuckPerms luckPerms = LuckPermsMinestom.builder(Path.of("luckperms"))
                .commandRegistry(CommandRegistry.minestom())
                .enable();
        Permissions.registerProvider(new LuckPermsProvider(luckPerms));

        InstanceContainer instance = MinecraftServer.getInstanceManager().createInstanceContainer();

        MinecraftServer.getGlobalEventHandler().addListener(AsyncPlayerConfigurationEvent.class, event -> {
            event.setSpawningInstance(instance);
        });

        MinecraftServer.getGlobalEventHandler().addListener(PlayerSpawnEvent.class, event -> {
           if(!event.isFirstSpawn()) return;

           Player player = event.getPlayer();
           String[] permissions = new String[] {"test.perm1", "test.perm2", "test.perm3"};
           for(String permission : permissions){
               player.sendMessage(
                       Component.text(permission)
                               .append(Component.text(": "))
                               .append(
                                       Permissions.check(player, permission) ?
                                               Component.text("Yes", NamedTextColor.GREEN) :
                                               Component.text("No", NamedTextColor.RED)
                               )
               );
            }
        });

        MinecraftServer.getSchedulerManager().buildShutdownTask(LuckPermsMinestom::disable);

        minecraftServer.start("0.0.0.0", 25565);
    }
}
