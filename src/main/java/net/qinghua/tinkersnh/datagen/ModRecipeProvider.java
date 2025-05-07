package net.qinghua.tinkersnh.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.common.crafting.IntersectionIngredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.qinghua.tinkersnh.TinkersNH;
import net.qinghua.tinkersnh.TinkersNH_Fluid;
import net.qinghua.tinkersnh.TinkersNH_Items;
import net.qinghua.tinkersnh.TinkersNH_Modifiers;
import net.qinghua.tinkersnh.datagen.material.MaterialIds;
import net.qinghua.tinkersnh.util.ModTags;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.alloying.AlloyRecipeBuilder;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder, IMaterialRecipeHelper {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {
        // 原版配方
        // 工作台
        addMaterialRecipes(pWriter);

        // 匠魂材料
        // 材料价值?
        addMaterialItems(pWriter);
        // 材料对应流体?
        addMaterialSmeltery(pWriter);
        // 材料浇筑配方
        addCastingRecipes(pWriter);
        // 材料熔炼配方
        addMeltingRecipes(pWriter);
        // 合金配方
        addAlloyRecipes(pWriter);
        // 强化配方
        addModifierRecipes(pWriter);
    }

    private void addMaterialRecipes(Consumer<FinishedRecipe> pWriter) {
        // 充能合金
        ingot_to_nuggets(pWriter, TinkersNH_Items.ENERGETIC_ALLOY_INGOT.get(), TinkersNH_Items.ENERGETIC_ALLOY_NUGGET.get(), "energetic_alloy");
        nuggets_to_ingot(pWriter, TinkersNH_Items.ENERGETIC_ALLOY_INGOT.get(), TinkersNH_Items.ENERGETIC_ALLOY_NUGGET.get(), "energetic_alloy");
        // 龙蒿
        ingot_to_nuggets(pWriter, TinkersNH_Items.UELIBLOOM_INGOT.get(), TinkersNH_Items.UELIBLOOM_NUGGET.get(), "uelibloom");
        nuggets_to_ingot(pWriter, TinkersNH_Items.UELIBLOOM_INGOT.get(), TinkersNH_Items.UELIBLOOM_NUGGET.get(), "uelibloom");
    }

    // 锭合成粒
    private void ingot_to_nuggets(Consumer<FinishedRecipe> pWriter, Item ingot, Item nugget, String name) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nugget, 9)
                .requires(ingot)
                .unlockedBy(getHasName(ingot), has(ingot))
                .save(pWriter, location("common/materials/" + name + "_nuggets_from_ingot"));
    }
    // 粒合成锭
    private void nuggets_to_ingot(Consumer<FinishedRecipe> pWriter, Item ingot, Item nugget, String name) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ingot)
                .pattern("NNN")
                .pattern("NNN")
                .pattern("NNN")
                .define('N', nugget)
                .unlockedBy(getHasName(nugget), has(nugget))
                .save(pWriter, location("common/materials/" + name + "_ingot_from_nuggets"));
    }

    private void addMaterialItems(@NotNull Consumer<FinishedRecipe> consumer) {
        String folder = "tools/materials/";
        // 充能合金
        materialRecipe(consumer, MaterialIds.energetic_alloy,
                Ingredient.of(ModTags.Items.INGOTS_ENERGETIC_ALLOY), 1, 1, folder + "energetic_alloy/ingot");
        materialRecipe(consumer, MaterialIds.energetic_alloy,
                Ingredient.of(ModTags.Items.NUGGETS_ENERGETIC_ALLOY), 1, 9, folder + "energetic_alloy/nugget");
        // 龙蒿
        materialRecipe(consumer, MaterialIds.uelibloom,
                Ingredient.of(ModTags.Items.INGOTS_UELIBLOOM), 1, 1, folder + "uelibloom/ingot");
        materialRecipe(consumer, MaterialIds.uelibloom,
                Ingredient.of(ModTags.Items.NUGGETS_UELIBLOOM), 1, 9, folder + "uelibloom/nugget");
        // 亚金
        materialRecipe(consumer, MaterialIds.gravitonsteel,
                Ingredient.of(ModTags.Items.INGOTS_GRAVITONSTEEL), 1, 1, folder + "gravitonsteel/ingot");
    }

    private void addMaterialSmeltery(@NotNull Consumer<FinishedRecipe> consumer) {
        String folder = "tools/materials/";
        // 充能合金
        materialMeltingCasting(consumer, MaterialIds.energetic_alloy, TinkersNH_Fluid.MOLTEN_ENERGETIC_ALLOY, folder);
        // 龙蒿
        materialMeltingCasting(consumer, MaterialIds.uelibloom, TinkersNH_Fluid.MOLTEN_UELIBLOOM, folder);
        // 亚金
        materialMeltingCasting(consumer, MaterialIds.gravitonsteel, TinkersNH_Fluid.MOLTEN_GRAVITONSTEEL, folder);
    }

    private void addCastingRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "smeltery/casting/";
        // 浇筑配方
        // 充能合金
        ingot_cast(consumer, ModTags.Items.INGOTS_ENERGETIC_ALLOY, TinkersNH_Fluid.MOLTEN_ENERGETIC_ALLOY,
                52, folder, "energetic_alloy");
        nugget_cast(consumer, ModTags.Items.NUGGETS_ENERGETIC_ALLOY, TinkersNH_Fluid.MOLTEN_ENERGETIC_ALLOY,
                18, folder, "energetic_alloy");
        // 龙蒿
        ingot_cast(consumer, ModTags.Items.INGOTS_UELIBLOOM, TinkersNH_Fluid.MOLTEN_UELIBLOOM,
                72, folder, "uelibloom");
        nugget_cast(consumer, ModTags.Items.NUGGETS_UELIBLOOM, TinkersNH_Fluid.MOLTEN_UELIBLOOM,
                24, folder, "uelibloom");
        // 亚金
        ingot_cast(consumer, ModTags.Items.INGOTS_GRAVITONSTEEL, TinkersNH_Fluid.MOLTEN_GRAVITONSTEEL,
                72, folder, "gravitonsteel");

        ItemCastingRecipeBuilder.tableRecipe(TinkersNH_Items.ENERGETIC_ALLOY_INGOT.get())
                .setFluid(TinkersNH_Fluid.MOLTEN_ENERGETIC_ALLOY.get(), FluidValues.INGOT)
                .setCast(Tags.Items.DUSTS_REDSTONE, true)
                .setCoolingTime(80)
                .save(consumer, location(folder + "energetic_alloy/ingot"));
    }

    private void addMeltingRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "smeltery/melting/";
        // 熔炼配方
        // 龙蒿矿
        MeltingRecipeBuilder.melting(
                Ingredient.of(ModTags.Items.ORES_UELIBLOOM), TinkersNH_Fluid.MOLTEN_UELIBLOOM, FluidValues.INGOT, 2.0f)
                .addByproduct(TinkerFluids.moltenGold.result(FluidValues.METAL_BLOCK))
                .setOre(IMeltingContainer.OreRateType.METAL)
                .save(consumer, location(folder + "uelibloom/ore"));
        // 锭
        MeltingRecipeBuilder.melting(
                Ingredient.of(ModTags.Items.INGOTS_ENERGETIC_ALLOY), TinkersNH_Fluid.MOLTEN_ENERGETIC_ALLOY, FluidValues.INGOT)
                .save(consumer, location(folder + "energetic_alloy/ingot"));
        MeltingRecipeBuilder.melting(
                        Ingredient.of(ModTags.Items.INGOTS_UELIBLOOM), TinkersNH_Fluid.MOLTEN_UELIBLOOM, FluidValues.INGOT)
                .save(consumer, location(folder + "uelibloom/ingot"));
        MeltingRecipeBuilder.melting(
                        Ingredient.of(ModTags.Items.INGOTS_GRAVITONSTEEL), TinkersNH_Fluid.MOLTEN_GRAVITONSTEEL, FluidValues.INGOT)
                .save(consumer, location(folder + "gravitonsteel/ingot"));
        // 粒
        MeltingRecipeBuilder.melting(
                Ingredient.of(ModTags.Items.NUGGETS_UELIBLOOM), TinkersNH_Fluid.MOLTEN_UELIBLOOM, FluidValues.NUGGET)
                .save(consumer, location(folder + "uelibloom/nugget"));
        MeltingRecipeBuilder.melting(
                Ingredient.of(ModTags.Items.NUGGETS_ENERGETIC_ALLOY), TinkersNH_Fluid.MOLTEN_ENERGETIC_ALLOY, FluidValues.NUGGET)
                .save(consumer, location(folder + "energetic_alloy/nugget"));
    }

    // 合金配方
    private void addAlloyRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "smeltery/alloys/";

        // 亚金 = 200mB熔融黑曜石，200mB熔融玫瑰金，90mB熔融玛玉灵
        AlloyRecipeBuilder.alloy(TinkersNH_Fluid.MOLTEN_GRAVITONSTEEL, FluidValues.INGOT)
                .addInput(TinkerFluids.moltenObsidian.ingredient(200))
                .addInput(TinkerFluids.moltenRoseGold.ingredient(200))
                .addInput(TinkerFluids.moltenManyullyn.ingredient(FluidValues.INGOT))
                .save(consumer, prefix(TinkersNH_Fluid.MOLTEN_GRAVITONSTEEL, folder));

    }

    // 强化配方
    private void addModifierRecipes(Consumer<FinishedRecipe> consumer) {
        String abilityFolder = "tools/modifiers/ability/";
        String abilitySalvage = "tools/modifiers/salvage/ability/";

        Ingredient blockWhileCharging = IntersectionIngredient.of(
                Ingredient.of(TinkerTags.Items.DURABILITY),
                ingredientFromTags(TinkerTags.Items.INTERACTABLE_RIGHT,
                        TinkerTags.Items.SHIELDS, TinkerTags.Items.BOWS));
        // 亚金之力
        ModifierRecipeBuilder.modifier(TinkersNH_Modifiers.GRAVITIC_AMPLIFICATION)
                .setTools(blockWhileCharging)
                .addInput(Items.ANVIL)
                .addInput(ModTags.Items.INGOTS_GRAVITONSTEEL)
                .addInput(Items.IRON_SWORD)
                .setMaxLevel(1).checkTraitLevel()
                .setSlots(SlotType.ABILITY, 1)
                .saveSalvage(consumer, prefix(TinkersNH_Modifiers.GRAVITIC_AMPLIFICATION, abilitySalvage))
                .save(consumer, prefix(TinkersNH_Modifiers.GRAVITIC_AMPLIFICATION, abilityFolder));
        // 亚金之力-护甲
        ModifierRecipeBuilder.modifier(TinkersNH_Modifiers.BULWARK_BASTION)
                .setTools(ingredientFromTags(TinkerTags.Items.ARMOR))
                .addInput(Items.ANVIL)
                .addInput(ModTags.Items.INGOTS_GRAVITONSTEEL)
                .addInput(Items.IRON_SWORD)
                .setMaxLevel(1).checkTraitLevel()
                .setSlots(SlotType.DEFENSE, 1)
                .saveSalvage(consumer, prefix(TinkersNH_Modifiers.BULWARK_BASTION, abilitySalvage))
                .save(consumer, prefix(TinkersNH_Modifiers.BULWARK_BASTION, abilityFolder));
    }

    @SafeVarargs
    private static Ingredient ingredientFromTags(TagKey<Item>... tags) {
        Ingredient[] tagIngredients = new Ingredient[tags.length];
        for (int i = 0; i < tags.length; i++) {
            tagIngredients[i] = Ingredient.of(tags[i]);
        }
        return CompoundIngredient.of(tagIngredients);
    }

    public @NotNull String getModId() {
        return TinkersNH.MOD_ID;
    }

    // 批量注册浇筑配方
    public void ingot_cast(Consumer<FinishedRecipe> consumer, TagKey<Item> ingotTag,
                           FlowingFluidObject<ForgeFlowingFluid> fluid,
                           int coolingTime, String folder, String name
    ) {
        // 金铸模
        ItemCastingRecipeBuilder.tableRecipe(ingotTag)
                .setFluid(fluid.getTag(), FluidValues.INGOT)
                .setCast(TinkerSmeltery.ingotCast.getMultiUseTag(), false)
                .setCoolingTime(coolingTime)
                .save(consumer, location(folder + name + "/ingot_gold_cast"));
        // 沙铸模
        ItemCastingRecipeBuilder.tableRecipe(ingotTag)
                .setFluid(fluid.getTag(), FluidValues.INGOT)
                .setCast(TinkerSmeltery.ingotCast.getSingleUseTag(), true)
                .setCoolingTime(coolingTime)
                .save(consumer, location(folder + name + "/ingot_sand_cast"));
    }
    public void nugget_cast(Consumer<FinishedRecipe> consumer, TagKey<Item> nuggetTag,
                           FlowingFluidObject<ForgeFlowingFluid> fluid,
                           int coolingTime, String folder, String name
    ) {
        // 金粒模
        ItemCastingRecipeBuilder.tableRecipe(nuggetTag)
                .setFluid(fluid.getTag(), FluidValues.NUGGET)
                .setCast(TinkerSmeltery.nuggetCast.getMultiUseTag(), false)
                .setCoolingTime(coolingTime)
                .save(consumer, location(folder + name + "/nugget_gold_cast"));
        // 沙粒模
        ItemCastingRecipeBuilder.tableRecipe(nuggetTag)
                .setFluid(fluid.getTag(), FluidValues.NUGGET)
                .setCast(TinkerSmeltery.nuggetCast.getSingleUseTag(), true)
                .setCoolingTime(coolingTime)
                .save(consumer, location(folder + name + "/nugget_sand_cast"));
    }
}
