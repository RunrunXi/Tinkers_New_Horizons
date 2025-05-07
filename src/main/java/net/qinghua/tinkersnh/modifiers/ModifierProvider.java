package net.qinghua.tinkersnh.modifiers;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;
import slimeknights.tconstruct.library.modifiers.modules.build.StatBoostModule;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class ModifierProvider extends AbstractModifierProvider implements IConditionBuilder {
    public ModifierProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void addModifiers() {
        // 充能合金工具
        buildModifier(ModifierIds.energeticTool)
                .levelDisplay(ModifierLevelDisplay.DEFAULT)
                .addModule(StatBoostModule.multiplyBase(ToolStats.ATTACK_SPEED).eachLevel(0.05f)) // 每级增加5%的基础攻击速度
                .addModule(StatBoostModule.multiplyBase(ToolStats.MINING_SPEED).eachLevel(0.05f)) // 每级增加5%的基础挖掘速度
                .addModule(StatBoostModule.multiplyBase(ToolStats.DRAW_SPEED).eachLevel(0.05f)) // 每级增加5%的基础拉伸速度（例如弓的拉力）
                .addModule(StatBoostModule.multiplyBase(ToolStats.VELOCITY).eachLevel(0.05f)); // 每级增加5%的基础速度（例如投掷物的速度）

        // 下面一行用于实现原版破盾效果，但是由于原版远程武器不能破盾，所以不用
//                .addModule(ToolActionTransformModule.builder(SHIELD_DISABLE, SoundEvents.SHIELD_BREAK).build());
    }

    @Override
    public @NotNull String getName() {
        return "Tinkers' New Horizons Modifier";
    }
}
