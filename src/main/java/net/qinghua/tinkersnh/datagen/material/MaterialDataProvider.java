package net.qinghua.tinkersnh.datagen.material;

import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;

public class MaterialDataProvider extends AbstractMaterialDataProvider {
    public MaterialDataProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void addMaterials() {
        addMaterial(MaterialIds.energetic_alloy, 2, ORDER_SPECIAL, false);
        addMaterial(MaterialIds.uelibloom, 5, ORDER_SPECIAL, false);
    }

    @Override
    public @NotNull String getName() {
        return "Tinker's New Horizons Materials";
    }
}
