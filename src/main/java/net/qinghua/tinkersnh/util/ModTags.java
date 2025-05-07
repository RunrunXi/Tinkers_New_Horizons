package net.qinghua.tinkersnh.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.qinghua.tinkersnh.TinkersNH;
import slimeknights.mantle.Mantle;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierManager;

import static slimeknights.tconstruct.TConstruct.getResource;

public class ModTags {
    public static class Items {
        // 龙蒿
        public static final TagKey<Item> INGOTS_UELIBLOOM = common("ingots/uelibloom");
        public static final TagKey<Item> NUGGETS_UELIBLOOM = common("nuggets/uelibloom");
        // 充能合金
        public static final TagKey<Item> INGOTS_ENERGETIC_ALLOY = common("ingots/energetic_alloy");
        public static final TagKey<Item> NUGGETS_ENERGETIC_ALLOY = common("nuggets/energetic_alloy");
        // 矿石-龙蒿
        public static final TagKey<Item> ORES_UELIBLOOM = common("ores/uelibloom");

        // 亚金
        public static final TagKey<Item> INGOTS_GRAVITONSTEEL = common("ingots/gravitonsteel");

        public static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(TinkersNH.MOD_ID, name));
        }

        private static TagKey<Item> common(String name) {
            return TagKey.create(Registries.ITEM, Mantle.commonResource(name));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> ORES_UELIBLOOM = common("ores/uelibloom");

        public static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(TinkersNH.MOD_ID, name));
        }

        private static TagKey<Block> common(String name) {
            return TagKey.create(Registries.BLOCK, Mantle.commonResource(name));
        }
    }

    public static class Fluids {
        public static final TagKey<Fluid> FLUIDS_MOLTEN_ENERGETIC_ALLOY = tag("fluids/molten_energetic_alloy");
        public static final TagKey<Fluid> FLUIDS_MOLTEN_UELIBLOOM = tag("fluids/molten_uelibloom");
        public static final TagKey<Fluid> FORGE_FLUIDS_MOLTEN_ENERGETIC_ALLOY = TagKey.create(Registries.FLUID, new ResourceLocation("forge", "fluids/molten_energetic_alloy"));

        public static TagKey<Fluid> tag(String name) {
            return FluidTags.create(new ResourceLocation(TinkersNH.MOD_ID, name));
        }
    }

    public static class Modifiers {
        public static final TagKey<Modifier> MELEE_ABILITIES = tag("abilities/melee");

        public static TagKey<Modifier> tag(String name) {
            return ModifierManager.getTag(getResource(name));
        }
    }

}
