package com.gtinfinityascension.ascopt.mixin;

import java.util.Map;

/** Feature package name -> mod ids it needs, comma separated ("" for vanilla/Forge). */
final class Features {

    static final Map<String, String> ALL = Map.of("spawnchunks", "", "gtcovers", "gtceu", "gtpreviews",
            "gtceu,emi,ldlib");

    private Features() {}
}
