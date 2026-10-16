package com.gtinfinityascension.ascopt.mixin.gtpreviews;

import com.lowdragmc.lowdraglib.emi.ModularEmiRecipe;

import dev.emi.emi.api.stack.EmiIngredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = ModularEmiRecipe.class, remap = false)
public interface ModularEmiRecipeAccessor {

    @Accessor("inputs")
    List<EmiIngredient> ascopt$inputs();
}
