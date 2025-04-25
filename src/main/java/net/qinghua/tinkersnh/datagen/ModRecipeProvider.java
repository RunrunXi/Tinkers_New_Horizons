package net.qinghua.tinkersnh.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.qinghua.tinkersnh.TinkersNH_Items;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {
        // 龙蒿锭
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TinkersNH_Items.UELIBLOOM_INGOT.get())
                .pattern("NNN")
                .pattern("NNN")
                .pattern("NNN")
                .define('N', TinkersNH_Items.UELIBLOOM_NUGGET.get())
                .unlockedBy(getHasName(TinkersNH_Items.UELIBLOOM_NUGGET.get()), has(TinkersNH_Items.UELIBLOOM_NUGGET.get()))
                .save(pWriter);
        // 龙蒿粒
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TinkersNH_Items.UELIBLOOM_NUGGET.get(), 9)
                .requires(TinkersNH_Items.UELIBLOOM_INGOT.get())
                .unlockedBy(getHasName(TinkersNH_Items.UELIBLOOM_INGOT.get()), has(TinkersNH_Items.UELIBLOOM_INGOT.get()))
                .save(pWriter);
    }
}
