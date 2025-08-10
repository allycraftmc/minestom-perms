package de.allycraft.minestom.perms;

public enum PermissionTristate {
    TRUE,
    FALSE,
    DEFAULT;

    public boolean asBoolean() {
        return this.equals(PermissionTristate.TRUE);
    }
}
