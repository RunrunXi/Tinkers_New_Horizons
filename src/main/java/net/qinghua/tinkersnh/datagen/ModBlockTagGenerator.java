package net.qinghua.tinkersnh.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.qinghua.tinkersnh.TinkersNH_Blocks;
import net.qinghua.tinkersnh.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        /* 原版 */
        // 需要钻石工具挖掘
//        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
//                .add(TinkersNH_Blocks.UELIBLOOM_ORE.get());
        // 需要下界合金工具挖掘
        this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .add(TinkersNH_Blocks.UELIBLOOM_ORE.get());
        // 使用镐子挖掘
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(TinkersNH_Blocks.UELIBLOOM_ORE.get());
        /* forge */
        this.tag(ModTags.Blocks.ORES_UELIBLOOM)
                .add(TinkersNH_Blocks.UELIBLOOM_ORE.get());
        this.tag(Tags.Blocks.ORES)
                .addTag(ModTags.Blocks.ORES_UELIBLOOM);

        this.tag(Tags.Blocks.ORE_RATES_SINGULAR)
                .add(TinkersNH_Blocks.UELIBLOOM_ORE.get());


    }
}