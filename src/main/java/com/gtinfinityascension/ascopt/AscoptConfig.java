package com.gtinfinityascension.ascopt;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * Runtime switches. Mixins are switched on or off before the game loads, by {@code config/ascopt-mixins.properties}
 * (see {@link com.gtinfinityascension.ascopt.mixin.AscoptMixinPlugin}); this spec holds the options read later.
 */
public final class AscoptConfig {

    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.BooleanValue LOG_TIMINGS;
    public static final ForgeConfigSpec.IntValue SPAWN_CHUNK_RADIUS;

    static {
        ForgeConfigSpec.Builder b = new ForgeConfigSpec.Builder();
        LOG_TIMINGS = b.comment("Log how long each startup phase took (for comparing runs).")
                .define("logTimings", true);
        SPAWN_CHUNK_RADIUS = b.comment("Radius of the always-loaded spawn chunk square. Vanilla is 11 (21x21 = 441",
                "ticking chunks); 3 keeps 5x5 = 25. Needs the spawnchunks mixin feature on.")
                .defineInRange("spawnChunkRadius", 3, 2, 11);
        SPEC = b.build();
    }

    private AscoptConfig() {}

    /** Ticket level radius for the spawn START ticket; falls back to vanilla before the config is loaded. */
    public static int spawnTicketRadius() {
        return SPEC.isLoaded() ? SPAWN_CHUNK_RADIUS.get() : 11;
    }

    /** Chunks that must be ticking before the world opens: a square of side 2 * radius - 1. */
    public static int spawnTickingChunks() {
        int side = 2 * spawnTicketRadius() - 1;
        return side * side;
    }
}
