package de.allycraft.minestom.perms.luckperms;

import de.allycraft.minestom.perms.PermissionProvider;
import de.allycraft.minestom.perms.PermissionTristate;
import me.lucko.luckperms.minestom.CommandRegistry;
import me.lucko.luckperms.minestom.LuckPermsMinestom;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.util.Tristate;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class LuckPermsProvider implements PermissionProvider {
    @NotNull
    private final LuckPerms luckPerms;

    public LuckPermsProvider() {
        this.luckPerms = LuckPermsMinestom.builder(Path.of("luckperms"))
                .commandRegistry(CommandRegistry.minestom())
                .configurationAdapter(HoconConfigurationAdapter::new)
                .enable();
    }

    public LuckPermsProvider(@NotNull LuckPerms luckPerms) {
        this.luckPerms = luckPerms;
    }

    @Override
    public @NotNull PermissionTristate getPermissionValue(@NotNull Player player, @NotNull String permission) {
        Tristate result = this.luckPerms.getPlayerAdapter(Player.class)
                .getUser(player)
                .getCachedData()
                .getPermissionData()
                .checkPermission(permission);

        return switch (result) {
            case TRUE -> PermissionTristate.TRUE;
            case FALSE -> PermissionTristate.FALSE;
            case UNDEFINED -> PermissionTristate.DEFAULT;
        };
    }
}
