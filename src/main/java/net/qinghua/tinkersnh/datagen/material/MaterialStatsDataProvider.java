package net.qinghua.tinkersnh.datagen.material;

import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.tools.stats.*;

import static net.minecraft.world.item.Tiers.IRON;
import static net.minecraft.world.item.Tiers.NETHERITE;

public class MaterialStatsDataProvider extends AbstractMaterialStatsDataProvider {
    public MaterialStatsDataProvider(PackOutput packOutput, AbstractMaterialDataProvider materials) {
        super(packOutput, materials);
    }

    @Override
    protected void addMaterialStats() {
        addMeleeHarvest();
        addRanged();
        addArmor();
        addMisc();
    }

    // 近战采集
    private void addMeleeHarvest() {
        // 充能合金
        addMaterialStats(MaterialIds.energetic_alloy,
                new HeadMaterialStats(200, 7.0f, IRON, 3.0f),
                HandleMaterialStats.multipliers().durability(0.9f).miningSpeed(1.1f).attackSpeed(1.1f).attackDamage(1.0f).build(),
                StatlessMaterialStats.BINDING);
        // 龙蒿
        addMaterialStats(MaterialIds.uelibloom,
                new HeadMaterialStats(900, 7.0f, NETHERITE, 5.0f),
                HandleMaterialStats.multipliers().durability(1.2f).miningSpeed(1.2f).attackSpeed(1.2f).attackDamage(1.2f).build(),
                StatlessMaterialStats.BINDING);
    }

    // 远程武器
    private void addRanged() {
        // 充能合金
        addMaterialStats(MaterialIds.energetic_alloy,
                new LimbMaterialStats(200, 0.25f, 0.2f, -0.05f),
                new GripMaterialStats(0.1f, -0.05f, 1.0f));
    }

    // 防具
    private void addArmor() {
        // 龙蒿
        addArmorShieldStats(MaterialIds.uelibloom, PlatingMaterialStats.builder()
                        .durabilityFactor(35)
                        .armor(2, 5, 7, 2)
                        .toughness(3)
                        .knockbackResistance(0.05f)
                , StatlessMaterialStats.MAILLE);
    }

    // 杂项
    private void addMisc() {

    }

    @Override
    public @NotNull String getName() {
        return "Tinker's New Horizons Material Stats";
    }
}
