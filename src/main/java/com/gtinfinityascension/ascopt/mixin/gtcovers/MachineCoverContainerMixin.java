package com.gtinfinityascension.ascopt.mixin.gtcovers;

import com.gregtechceu.gtceu.api.cover.CoverBehavior;
import com.gregtechceu.gtceu.api.machine.MachineCoverContainer;

import net.minecraft.core.Direction;

import org.spongepowered.asm.mixin.Mixin;

import java.util.ArrayList;
import java.util.List;

/**
 * GTCEu's default {@code ICoverable.getCovers()} streams over the six sides and collects a new list on every call,
 * and machines and addons call it every tick. This override does the same with a plain loop. It still returns a new
 * mutable list, as the original does, in case a caller changes it.
 */
@Mixin(value = MachineCoverContainer.class, remap = false)
public abstract class MachineCoverContainerMixin {

    private static final Direction[] ASCOPT_SIDES = Direction.values();

    public List<CoverBehavior> getCovers() {
        MachineCoverContainer self = (MachineCoverContainer) (Object) this;
        List<CoverBehavior> covers = new ArrayList<>(2);
        for (Direction side : ASCOPT_SIDES) {
            CoverBehavior cover = self.getCoverAtSide(side);
            if (cover != null) covers.add(cover);
        }
        return covers;
    }
}
