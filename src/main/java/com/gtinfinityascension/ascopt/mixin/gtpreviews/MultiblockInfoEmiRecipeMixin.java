package com.gtinfinityascension.ascopt.mixin.gtpreviews;

import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.integration.emi.multipage.MultiblockInfoEmiRecipe;

import com.gtinfinityascension.ascopt.compat.gtceu.MultiblockIngredients;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Fills a multiblock info recipe's ingredients from its block pattern instead of a built preview. */
@Mixin(value = MultiblockInfoEmiRecipe.class, remap = false)
public abstract class MultiblockInfoEmiRecipeMixin {

    @Shadow
    @Final
    private MultiblockMachineDefinition definition;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void ascopt$ingredientsFromPattern(MultiblockMachineDefinition definition, CallbackInfo ci) {
        MultiblockIngredients.fill(this, definition);
    }
}
