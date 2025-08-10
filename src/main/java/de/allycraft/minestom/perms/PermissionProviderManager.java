package de.allycraft.minestom.perms;

import net.minestom.server.command.CommandSender;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
public class PermissionProviderManager implements PermissionProvider {
    @Nullable
    private static PermissionProviderManager instance;

    public static @NotNull PermissionProviderManager getInstance() {
        if (instance == null) {
            instance = new PermissionProviderManager();
        }
        return instance;
    }

    @NotNull
    private final List<PermissionProvider> providers;

    private PermissionProviderManager() {
        this.providers = new ArrayList<>();
    }

    public void register(@NotNull PermissionProvider permissionProvider) {
        this.providers.add(permissionProvider);
    }

    @Override
    public @NotNull PermissionTristate getPermissionValue(@NotNull Player player, @NotNull String permission) {
        PermissionTristate result = PermissionTristate.DEFAULT;
        for(PermissionProvider provider : providers) {
            PermissionTristate providerResult = provider.getPermissionValue(player, permission);
            if(providerResult != PermissionTristate.DEFAULT) {
                result = providerResult;
            }
        }
        return result;
    }

    @Override
    public @NotNull PermissionTristate getPermissionValue(@NotNull CommandSender sender, @NotNull String permission) {
        PermissionTristate result = PermissionTristate.DEFAULT;
        for(PermissionProvider provider : providers) {
            PermissionTristate providerResult = provider.getPermissionValue(sender, permission);
            if(providerResult != PermissionTristate.DEFAULT) {
                result = providerResult;
            }
        }
        return result;
    }
}
