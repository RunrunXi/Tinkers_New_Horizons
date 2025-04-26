package net.qinghua.tinkersnh.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.qinghua.tinkersnh.TinkersNH;
import net.qinghua.tinkersnh.TinkersNH_Fluid;
import net.qinghua.tinkersnh.TinkersNH_Items;
import net.qinghua.tinkersnh.datagen.material.MaterialIds;
import net.qinghua.tinkersnh.util.ModTags;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder, IMaterialRecipeHelper {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {
        // 原版配方
        addMaterialRecipes(pWriter);

        // 匠魂材料
        addMaterialItems(pWriter);
        addMaterialSmeltery(pWriter);
        addCastingRecipes(pWriter);
        addMeltingRecipes(pWriter);
    }

    private void addMaterialRecipes(Consumer<FinishedRecipe> pWriter) {
        // 龙蒿锭
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TinkersNH_Items.UELIBLOOM_INGOT.get())
                .pattern("NNN")
                .pattern("NNN")
                .pattern("NNN")
                .define('N', TinkersNH_Items.UELIBLOOM_NUGGET.get())
                .unlockedBy(getHasName(TinkersNH_Items.UELIBLOOM_NUGGET.get()), has(TinkersNH_Items.UELIBLOOM_NUGGET.get()))
                .save(pWriter, location("common/materials/uelibloom_ingot_from_nuggets"));
        // 龙蒿粒
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TinkersNH_Items.UELIBLOOM_NUGGET.get(), 9)
                .requires(TinkersNH_Items.UELIBLOOM_INGOT.get())
                .unlockedBy(getHasName(TinkersNH_Items.UELIBLOOM_INGOT.get()), has(TinkersNH_Items.UELIBLOOM_INGOT.get()))
                .save(pWriter, location("common/materials/uelibloom_nuggets_from_ingot"));
    }

    private void addMaterialItems(@NotNull Consumer<FinishedRecipe> consumer) {
        String folder = "tools/materials/";
        // 充能合金
        materialRecipe(consumer, MaterialIds.energetic_alloy,
                Ingredient.of(ModTags.Items.INGOTS_ENERGETIC_ALLOY), 1, 1, folder + "energetic_alloy/ingot");
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
        ingot_cast(consumer, ModTags.Items.INGOTS_ENERGETIC_ALLOY, TinkersNH_Fluid.MOLTEN_ENERGETIC_ALLOY,
                52, folder, "energetic_alloy");
        ingot_cast(consumer, ModTags.Items.INGOTS_UELIBLOOM, TinkersNH_Fluid.MOLTEN_UELIBLOOM,
                72, folder, "uelibloom");
        nugget_cast(consumer, ModTags.Items.NUGGETS_UELIBLOOM, TinkersNH_Fluid.MOLTEN_UELIBLOOM,
                24, folder, "uelibloom");
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
