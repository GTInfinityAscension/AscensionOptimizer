package com.gtinfinityascension.ascopt.compat.gtceu;

import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate;
import com.gregtechceu.gtceu.api.pattern.predicates.SimplePredicate;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import com.gtinfinityascension.ascopt.AscensionOptimizer;
import com.gtinfinityascension.ascopt.mixin.gtpreviews.BlockPatternAccessor;
import com.gtinfinityascension.ascopt.mixin.gtpreviews.ModularEmiRecipeAccessor;
import dev.emi.emi.api.stack.EmiStack;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Every block a multiblock's pattern accepts, read from its predicates (no 3D walk, no preview world). */
public final class MultiblockIngredients {

    private MultiblockIngredients() {}

    public static void fill(Object recipe, MultiblockMachineDefinition definition) {
        try {
            var pattern = definition.getPatternFactory().get();
            Map<Item, ItemStack> items = new LinkedHashMap<>();
            // EN: Huge patterns repeat the same predicate object at most positions; read each one once.
            // PT: Padrões enormes repetem o mesmo objeto de predicado na maioria das posições; cada um é lido uma vez.
            // ZH: 巨型结构的大多数位置共用同一个谓词对象；每个谓词只读取一次。
            Set<TraceabilityPredicate> seen = Collections.newSetFromMap(new IdentityHashMap<>());
            for (TraceabilityPredicate[][] layer : ((BlockPatternAccessor) pattern).ascopt$blockMatches())
                for (TraceabilityPredicate[] row : layer)
                    for (TraceabilityPredicate predicate : row) {
                        if (predicate == null || !seen.add(predicate)) continue;
                        add(items, predicate.common);
                        add(items, predicate.limited);
                    }
            items.remove(definition.getItem());
            List<dev.emi.emi.api.stack.EmiIngredient> inputs = ((ModularEmiRecipeAccessor) recipe).ascopt$inputs();
            for (ItemStack stack : items.values()) inputs.add(EmiStack.of(stack));
        } catch (RuntimeException e) {
            AscensionOptimizer.LOGGER.warn("Could not read the blocks of {} for EMI", definition.getId(), e);
        }
    }

    private static void add(Map<Item, ItemStack> items, List<SimplePredicate> predicates) {
        for (SimplePredicate predicate : predicates) {
            for (ItemStack stack : predicate.getCandidates()) {
                if (!stack.isEmpty()) items.putIfAbsent(stack.getItem(), stack);
            }
        }
    }
}
