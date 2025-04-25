package net.qinghua.tinkersnh.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.qinghua.tinkersnh.TinkersNH;
import net.qinghua.tinkersnh.datagen.material.MaterialDataProvider;
import net.qinghua.tinkersnh.datagen.material.MaterialStatsDataProvider;
import net.qinghua.tinkersnh.datagen.material.MaterialTraitsDataProvider;
import net.qinghua.tinkersnh.modifiers.ModifierProvider;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = TinkersNH.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // 配方
        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput));
        // 战利品表
        generator.addProvider(event.includeServer(), ModLootTableProvider.create(packOutput));

        // 方块状态
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, existingFileHelper));
        // 物品模型
        generator.addProvider(event.includeServer(), new ModItemModelProvider(packOutput, TinkersNH.MOD_ID, existingFileHelper));
        // 方块标签
        ModBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                new ModBlockTagGenerator(packOutput, lookupProvider, TinkersNH.MOD_ID, existingFileHelper));
        // 物品标签
        generator.addProvider(event.includeServer(), new ModItemTagGenerator(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));

        // 流体
        generator.addProvider(event.includeServer(), new ModFluidTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModFluidBucketModelProvider(packOutput));

        // 世界生成
        generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider));

        // 匠魂材料
        MaterialDataProvider materialDataProvider = new MaterialDataProvider(packOutput);
        generator.addProvider(event.includeServer(), materialDataProvider);
        generator.addProvider(event.includeServer(), new MaterialStatsDataProvider(packOutput, materialDataProvider));
        generator.addProvider(event.includeServer(), new MaterialTraitsDataProvider(packOutput, materialDataProvider));

        // 材料特性
        generator.addProvider(event.includeServer(), new ModifierProvider(packOutput));

    }
}