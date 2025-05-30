package net.qinghua.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.qinghua.tinkersnh.TinkersNH;
import net.qinghua.tinkersnh.TinkersNH_Blocks;

import java.util.List;

public class ModConfiguredFeatures {
    // 矿石key
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_UELIBLOOM_ORE_KEY = registerKey("uelibloom_ore");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        // 替换深板岩
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        // 龙蒿生成-可替换的方块，矿石
        List<OreConfiguration.TargetBlockState> overworldUelibloomOres = List.of(
                OreConfiguration.target(deepslateReplaceables, TinkersNH_Blocks.UELIBLOOM_ORE.get().defaultBlockState()));
        // 注册生成-调整一个矿簇的大小
        register(context, OVERWORLD_UELIBLOOM_ORE_KEY, Feature.ORE, new OreConfiguration(overworldUelibloomOres, 2));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(TinkersNH.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
