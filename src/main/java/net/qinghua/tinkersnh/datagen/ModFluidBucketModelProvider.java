package net.qinghua.tinkersnh.datagen;

import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.data.PackOutput.Target;
import net.minecraft.world.item.BucketItem;
import net.qinghua.tinkersnh.TinkersNH;
import slimeknights.mantle.data.GenericDataProvider;

import java.util.concurrent.CompletableFuture;

public class ModFluidBucketModelProvider extends GenericDataProvider {
    public ModFluidBucketModelProvider(PackOutput packOutput) {
        super(packOutput, Target.RESOURCE_PACK, "models/item");
    }
    private static JsonObject makeJson(BucketItem bucket) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", "forge:item/bucket_drip");
        // using our own model as the forge one expects us to use item colors to handle tints, when we could just bake it in
        json.addProperty("loader", "tconstruct:fluid_container");
        json.addProperty("flip_gas", bucket.getFluid().getFluidType().isLighterThanAir());
        json.addProperty("fluid", BuiltInRegistries.FLUID.getKey(bucket.getFluid()).toString());
        return json;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        return allOf(
                BuiltInRegistries.ITEM.entrySet().stream()
                        .filter(entry -> entry.getKey().location().getNamespace().equals(TinkersNH.MOD_ID) && entry.getValue() instanceof BucketItem)
                        .map(entry -> saveJson(cachedOutput, entry.getKey().location(), makeJson((BucketItem)entry.getValue()))));
    }

    @Override
    public String getName() {
        return TinkersNH.MOD_ID + " Fluid Bucket Model Provider";
    }
}
