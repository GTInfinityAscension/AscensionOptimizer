package com.gtinfinityascension.ascopt.mixin.gtpreviews;

import com.gregtechceu.gtceu.integration.emi.multipage.MultiblockInfoEmiRecipe;

import com.lowdragmc.lowdraglib.emi.ModularEmiRecipe;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Supplier;

/**
 * LDLib builds a recipe's widget in the constructor to read its ingredients. For GTCEu's "multiblock info" recipes
 * that widget is the full structure preview (a dummy world with every block and machine), built for all ~250
 * multiblocks whenever EMI reloads: the memory peak that made a 1.5 GB client run out of memory. Those recipes now
 * start with an empty widget; their ingredients are filled in by {@link MultiblockInfoEmiRecipeMixin} and the real
 * preview is built when a player opens the page.
 */
@Mixin(value = ModularEmiRecipe.class, remap = false)
public abstract class ModularEmiRecipeMixin {

    @Redirect(method = "<init>",
              at = @At(value = "INVOKE", target = "Ljava/util/function/Supplier;get()Ljava/lang/Object;", ordinal = 0))
    private Object ascopt$skipPreviewForMultiblockInfo(Supplier<?> supplier) {
        if ((Object) this instanceof MultiblockInfoEmiRecipe) return new WidgetGroup();
        return supplier.get();
    }
}
