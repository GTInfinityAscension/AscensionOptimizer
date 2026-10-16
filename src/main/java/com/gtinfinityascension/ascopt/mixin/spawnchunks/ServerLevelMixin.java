package com.gtinfinityascension.ascopt.mixin.spawnchunks;

import net.minecraft.server.level.ServerLevel;

import com.gtinfinityascension.ascopt.AscoptConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/** Keeps the spawn ticket at the configured radius when the spawn point moves (see MinecraftServerMixin). */
@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {

    @ModifyConstant(method = "setDefaultSpawnPos", constant = @Constant(intValue = 11))
    private int ascopt$spawnTicketRadius(int vanilla) {
        return AscoptConfig.spawnTicketRadius();
    }
}
