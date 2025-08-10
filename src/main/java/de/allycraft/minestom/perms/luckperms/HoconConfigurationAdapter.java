package de.allycraft.minestom.perms.luckperms;

import me.lucko.luckperms.common.config.generic.adapter.ConfigurateConfigAdapter;
import me.lucko.luckperms.common.plugin.LuckPermsPlugin;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.nio.file.Path;

public class HoconConfigurationAdapter extends ConfigurateConfigAdapter {
    public HoconConfigurationAdapter(LuckPermsPlugin plugin) {
        super(plugin, Path.of("luckperms.conf"));
    }

    @Override
    protected ConfigurationLoader<? extends ConfigurationNode> createLoader(Path path) {
        return HoconConfigurationLoader.builder().setPath(path).build();
    }
}
