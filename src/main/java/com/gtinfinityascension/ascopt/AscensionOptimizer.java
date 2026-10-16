package com.gtinfinityascension.ascopt;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(AscensionOptimizer.MOD_ID)
@SuppressWarnings("removal")
public final class AscensionOptimizer {

    public static final String MOD_ID = "ascopt";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public AscensionOptimizer() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AscoptConfig.SPEC);
    }
}
