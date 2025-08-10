package de.allycraft.minestom.perms;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface PermissionProvider {
    @NotNull PermissionTristate getPermissionValue(@NotNull Player player, @NotNull String permission);

    default @NotNull PermissionTristate getPermissionValue(@NotNull CommandSender sender, @NotNull String permission) {
        if(sender instanceof Player player) {
            return getPermissionValue(player, permission);
        }

        if(sender instanceof ConsoleSender) {
            return PermissionTristate.TRUE;
        }

        return PermissionTristate.DEFAULT;
    }
}
