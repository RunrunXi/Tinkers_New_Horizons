package net.qinghua.tinkersnh.datagen.material;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.qinghua.tinkersnh.TinkersNH;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;

import java.util.function.Consumer;

public class MaterialRecipeProvider extends RecipeProvider implements IMaterialRecipeHelper {
    public MaterialRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {

    }

    @Override
    public @NotNull String getModId() {
        return TinkersNH.MOD_ID;
    }
}
