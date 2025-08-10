package de.allycraft.minestom.perms;

import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OpPermissionProvider implements PermissionProvider {
    @NotNull
    private final List<String> adminUsernames;
    @NotNull
    private final List<UUID> adminUUIDs;

    public OpPermissionProvider(@NotNull List<String> adminUsernames, @NotNull List<UUID> adminUUIDs) {
        this.adminUsernames = adminUsernames
                .stream()
                .map(String::toLowerCase)
                .toList();
        this.adminUUIDs = adminUUIDs;
    }

    public static OpPermissionProvider fromEnvironmentVariable() {
        List<String> adminUsernames = new ArrayList<>();
        List<UUID> adminUUIDs = new ArrayList<>();

        String[] entries = Optional.ofNullable(System.getenv("MINESTOM_PERMS_OPS"))
                .orElse("")
                .split("[,;]");

        for(String entry : entries) {
            if(entry.isEmpty()) continue;
            try {
                UUID uuid = UUID.fromString(entry);
                adminUUIDs.add(uuid);
            } catch (IllegalArgumentException e) {
                adminUsernames.add(entry);
            }
        }

        return new OpPermissionProvider(adminUsernames, adminUUIDs);
    }

    @Override
    public @NotNull PermissionTristate getPermissionValue(@NotNull Player player, @NotNull String permission) {
        return (this.adminUsernames.contains(player.getUsername().toLowerCase()) || this.adminUUIDs.contains(player.getUuid()))
                ? PermissionTristate.TRUE : PermissionTristate.DEFAULT;
    }
}
