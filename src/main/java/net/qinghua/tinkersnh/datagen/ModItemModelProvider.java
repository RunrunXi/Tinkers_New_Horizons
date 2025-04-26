package net.qinghua.tinkersnh.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.qinghua.tinkersnh.TinkersNH;
import net.qinghua.tinkersnh.TinkersNH_Items;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(TinkersNH_Items.ENERGETIC_ALLOY_INGOT);
        simpleItem(TinkersNH_Items.UELIBLOOM_INGOT);
        simpleItem(TinkersNH_Items.UELIBLOOM_NUGGET);
        simpleItem(TinkersNH_Items.GRAVITONSTEEL_INGOT);
    }

    private void simpleItem(RegistryObject<Item> item) {
        withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(TinkersNH.MOD_ID, "item/" + item.getId().getPath()));
    }
}
