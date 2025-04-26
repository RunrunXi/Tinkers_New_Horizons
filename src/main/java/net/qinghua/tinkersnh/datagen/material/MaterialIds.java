package net.qinghua.tinkersnh.datagen.material;

import net.qinghua.tinkersnh.TinkersNH;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

public class MaterialIds {
    public static final MaterialId energetic_alloy = id("energetic_alloy");
    public static final MaterialId uelibloom = id("uelibloom");
    public static final MaterialId gravitonsteel = id("gravitonsteel");

    private static MaterialId id(String name) {
        return new MaterialId(TinkersNH.MOD_ID, name);
    }
}
