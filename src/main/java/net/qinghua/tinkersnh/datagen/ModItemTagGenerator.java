package net.qinghua.tinkersnh.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.qinghua.tinkersnh.TinkersNH;
import net.qinghua.tinkersnh.TinkersNH_Blocks;
import net.qinghua.tinkersnh.TinkersNH_Items;
import net.qinghua.tinkersnh.util.ModTags;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                               CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, TinkersNH.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        /* TinkersNH */
        // Items
        // 充能合金锭
        this.tag(ModTags.Items.INGOTS_ENERGETIC_ALLOY)
                .add(TinkersNH_Items.ENERGETIC_ALLOY_INGOT.get());
        // 龙蒿锭
        this.tag(ModTags.Items.INGOTS_UELIBLOOM)
                .add(TinkersNH_Items.UELIBLOOM_INGOT.get());
        // 龙蒿粒
        this.tag(ModTags.Items.NUGGETS_UELIBLOOM)
                .add(TinkersNH_Items.UELIBLOOM_NUGGET.get());
        // 亚金
        this.tag(ModTags.Items.INGOTS_GRAVITONSTEEL)
                .add(TinkersNH_Items.GRAVITONSTEEL_INGOT.get());
        // BlockItems
        // 龙蒿矿石
        this.tag(ModTags.Items.ORES_UELIBLOOM)
                .add(TinkersNH_Blocks.UELIBLOOM_ORE.get().asItem());

        /* Forge  */
        // Items
        // 锭
        this.tag(Tags.Items.INGOTS)
                .addTag(ModTags.Items.INGOTS_UELIBLOOM)
                .addTag(ModTags.Items.INGOTS_ENERGETIC_ALLOY)
                .addTag(ModTags.Items.INGOTS_GRAVITONSTEEL);
        // 粒
        this.tag(Tags.Items.NUGGETS)
                .addTag(ModTags.Items.NUGGETS_UELIBLOOM);
        // BlockItems
        // 矿石
        this.tag(Tags.Items.ORES)
                .addTag(ModTags.Items.ORES_UELIBLOOM);

        this.tag(Tags.Items.ORE_RATES_SINGULAR)
                .add(TinkersNH_Blocks.UELIBLOOM_ORE.get().asItem());
    }

}
