# Ascension Optimizer

Measured performance fixes for large modpacks, made for [GregTech Infinity Ascension](https://github.com/GTInfinityAscension) but usable in any Forge 1.20.1 pack (it needs no other mod).

Every optimization is a separate feature that can be turned off in `config/ascopt-mixins.properties`; a feature whose target mod is missing is skipped. A feature is only added with numbers from before and after, measured with the modpack's `tools/perf_bench.py` (dedicated server, full pack, median of 3 boots).

## Features

| Feature | What it does | Measured gain (GTIA, 129 mods) |
| --- | --- | --- |
| `gtcovers` (needs GTCEu) | GTCEu's `getCovers()` builds a stream and a list on every call, and machines and addons call it every tick; a plain loop replaces it | 2,000 working LV Electric Furnaces with GTNA 0.6.0: 3.93 ms per tick instead of 8.37 (−53 %) |
| `gtpreviews` (client; needs GTCEu, LDLib, EMI) | LDLib builds every recipe widget when EMI reloads, which for GTCEu's "multiblock info" pages means the full structure preview of all ~250 multiblocks at once. Those pages now start empty, take their ingredients from the block pattern's predicates, and build the preview when opened | Client in a world, 3 GB heap: GTCEu's EMI plugin 14.1 s → 5.2 s, EMI reload 20.6 s → 12.2 s, live heap 1,947 → 1,588 MB; with a 1.5 GB heap the client no longer runs out of memory |
| `spawnchunks` | The always-loaded spawn area shrinks from 21×21 = 441 chunks to 5×5 = 25 (`spawnChunkRadius` in `config/ascopt-common.toml`, 2–11; vanilla is 11) | Server ready in 31 s instead of 47 s (−34 %); world preparation 5.9 s instead of 21.0 s (−72 %); live heap after a full GC 625 MB instead of 887 MB (−30 %) |

Spawn chunks stay loaded around the spawn point all the time, so a smaller square also means fewer chunks ticking forever. Farms that relied on the vanilla spawn chunks need a chunk loader instead.

## Building

```sh
./gradlew build
```

## License

[LGPL-3.0](LICENSE.MD). Written from scratch.
