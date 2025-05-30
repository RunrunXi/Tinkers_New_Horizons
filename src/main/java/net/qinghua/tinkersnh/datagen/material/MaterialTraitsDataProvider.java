package net.qinghua.tinkersnh.datagen.material;

import net.minecraft.data.PackOutput;
import net.qinghua.tinkersnh.modifiers.ModifierIds;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;

public class MaterialTraitsDataProvider extends AbstractMaterialTraitDataProvider {
    public MaterialTraitsDataProvider(PackOutput packOutput, AbstractMaterialDataProvider materials) {
        super(packOutput, materials);
    }

    /** Internal material stats ID for the sake of adding traits exclusive to melee or harvest materials */
    public static final MaterialStatsId MELEE_HARVEST = new MaterialStatsId(TConstruct.getResource("melee_harvest"));
    /** Internal material stats ID for the sake of adding traits exclusive to ranged materials */
    public static final MaterialStatsId RANGED = new MaterialStatsId(TConstruct.getResource("ranged"));
    /** Internal material stats ID for the sake of adding traits exclusive to armor materials */
    public static final MaterialStatsId ARMOR = new MaterialStatsId(TConstruct.getResource("armor"));

    @Override
    protected void addMaterialTraits() {
        // 充能合金
        addTraits(MaterialIds.energetic_alloy, ARMOR, ModifierIds.energeticArmor);
        addTraits(MaterialIds.energetic_alloy, MELEE_HARVEST, ModifierIds.energeticTool);
        // 龙蒿
        addTraits(MaterialIds.uelibloom, ARMOR, ModifierIds.blessingOfLifeArmor);
        addTraits(MaterialIds.uelibloom, MELEE_HARVEST, ModifierIds.blessingOfLifeTool);
        // 亚金
        noTraits(MaterialIds.gravitonsteel);
    }

    @Override
    public @NotNull String getName() {
        return "Tinkers' New Horizons Material Traits";
    }
}
