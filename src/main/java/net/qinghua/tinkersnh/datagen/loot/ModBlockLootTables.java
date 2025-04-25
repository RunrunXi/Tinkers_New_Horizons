package net.qinghua.tinkersnh.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.qinghua.tinkersnh.TinkersNH_Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(TinkersNH_Blocks.UELIBLOOM_ORE.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return TinkersNH_Blocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
