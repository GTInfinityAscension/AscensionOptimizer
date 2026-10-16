package com.gtinfinityascension.ascopt.mixin.spawnchunks;

import net.minecraft.server.MinecraftServer;

import com.gtinfinityascension.ascopt.AscoptConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Vanilla keeps a 21×21 square of spawn chunks (441) loaded and ticking forever, and generates all of it before the
 * world opens. A factory lives where the player builds it, so a smaller square saves boot time, memory and tick
 * time on every server.
 */
@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {

    @ModifyConstant(method = "prepareLevels", constant = @Constant(intValue = 11))
    private int ascopt$spawnTicketRadius(int vanilla) {
        return AscoptConfig.spawnTicketRadius();
    }

    @ModifyConstant(method = "prepareLevels", constant = @Constant(intValue = 441))
    private int ascopt$spawnTickingChunks(int vanilla) {
        return AscoptConfig.spawnTickingChunks();
    }
}
