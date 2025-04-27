package net.qinghua.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.qinghua.tinkersnh.TinkersNH;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_UELIBLOOM_ORE = registerKey("add_uelibloom_ore");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        // 添加矿石
        /* 矿石名称 */
        context.register(ADD_UELIBLOOM_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                /* 生成群系 */
                biomes.getOrThrow(BiomeTags.IS_JUNGLE),
                /* ModPlacedFeatures文件中注册的矿石key */
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.UELIBLOOM_ORE_PLACED_KEY)),
                /* 生成阶段-地下矿石 */
                GenerationStep.Decoration.UNDERGROUND_ORES));

    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(TinkersNH.MOD_ID, name));
    }
}