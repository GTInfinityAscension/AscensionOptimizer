package com.gtinfinityascension.ascopt.mixin;

import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.LoadingModList;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Decides which mixins load. Each optimization lives in its own package {@code mixin.<feature>} (for example
 * {@code mixin.ldlib}) and can be turned off in {@code config/ascopt-mixins.properties}. A feature whose target
 * mod is missing is skipped, so the mod works in any pack.
 */
public final class AscoptMixinPlugin implements IMixinConfigPlugin {

    private static final String ROOT = "com.gtinfinityascension.ascopt.mixin.";
    private final Properties switches = new Properties();

    @Override
    public void onLoad(String mixinPackage) {
        Path file = FMLPaths.CONFIGDIR.get().resolve("ascopt-mixins.properties");
        try {
            if (Files.exists(file)) {
                try (Reader reader = Files.newBufferedReader(file)) {
                    switches.load(reader);
                }
            }
            for (String feature : Features.ALL.keySet()) switches.putIfAbsent(feature, "true");
            try (Writer writer = Files.newBufferedWriter(file)) {
                switches.store(writer, "Ascension Optimizer: set a feature to false to turn it off (restart needed)");
            }
        } catch (IOException ignored) {
            // EN: Without the file every feature stays on, which is the default anyway.
            // PT: Sem o arquivo, todas as otimizações ficam ligadas, que já é o padrão.
            // ZH: 没有该文件时所有优化保持开启，这本来就是默认值。
        }
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        String rest = mixinClassName.substring(ROOT.length());
        int dot = rest.indexOf('.');
        String feature = dot < 0 ? rest : rest.substring(0, dot);
        String requiredMod = Features.ALL.get(feature);
        if (requiredMod == null) return true;
        if (!Boolean.parseBoolean(switches.getProperty(feature, "true"))) return false;
        return requiredMod.isEmpty() || LoadingModList.get().getModFileById(requiredMod) != null;
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName,
                          IMixinInfo mixinInfo) {}
}
