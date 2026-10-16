package com.gtinfinityascension.ascopt.mixin.gtpreviews;

import com.gregtechceu.gtceu.api.pattern.BlockPattern;
import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = BlockPattern.class, remap = false)
public interface BlockPatternAccessor {

    @Accessor("blockMatches")
    TraceabilityPredicate[][][] ascopt$blockMatches();
}
