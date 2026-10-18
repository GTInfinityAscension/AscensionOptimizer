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

    /** Size of GTCEu's {@code PatternPreviewWidget}. */
    private static final int PREVIEW_SIZE = 160;

    @Redirect(method = "<init>",
              at = @At(value = "INVOKE", target = "Ljava/util/function/Supplier;get()Ljava/lang/Object;", ordinal = 0))
    private Object ascopt$skipPreviewForMultiblockInfo(Supplier<?> supplier) {
        // EN: EMI pages recipes by the size read from this widget, so the placeholder has the preview's 160 x 160;
        // an empty 0 x 0 group put three multiblocks on a page and the real previews drew over each other.
        // PT: O EMI pagina as receitas pelo tamanho lido deste widget, então o substituto tem os 160 x 160 da prévia;
        // um grupo vazio 0 x 0 punha três multiblocos por página e as prévias reais se desenhavam umas sobre as outras.
        // ZH: EMI 按此组件的尺寸为配方分页，因此占位组件使用预览的 160 x 160；0 x 0 的空组件会让一页放三个多方块，真正的预览互相重叠。
        if ((Object) this instanceof MultiblockInfoEmiRecipe) return new WidgetGroup(0, 0, PREVIEW_SIZE, PREVIEW_SIZE);
        return supplier.get();
    }
}
