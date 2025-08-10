package de.allycraft.minestom.perms;

import net.minestom.server.command.CommandSender;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Permissions {

    /// Registers a new permission provider.
    /// Last added permission provider has the highest priority
    public static void registerProvider(@NotNull PermissionProvider provider) {
        PermissionProviderManager.getInstance().register(provider);
    }

    public static @NotNull PermissionTristate getPermissionValue(@NotNull Player player, @NotNull String permission) {
        return PermissionProviderManager.getInstance().getPermissionValue(player, permission);
    }

    public static @NotNull PermissionTristate getPermissionValue(@NotNull CommandSender sender, @NotNull String permission) {
        return PermissionProviderManager.getInstance().getPermissionValue(sender, permission);
    }

    public static boolean check(@NotNull Player player, @NotNull String permission) {
        return getPermissionValue(player, permission).asBoolean();
    }

    public static boolean check(@NotNull CommandSender sender, @NotNull String permission) {
        return getPermissionValue(sender, permission).asBoolean();
    }
}
