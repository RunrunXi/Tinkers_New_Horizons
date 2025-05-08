package net.qinghua.tinkersnh.modifiers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.EquipmentChangeModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.UUID;

public class EnergeticArmorModifier extends Modifier implements EquipmentChangeModifierHook {
    private static final TinkerDataCapability.TinkerDataKey<Integer> ENERGETIC_ARMOR = TConstruct.createKey("energetic_armor");
    private static final UUID SPEED_UUID = UUID.nameUUIDFromBytes("EnergeticArmor".getBytes());
    private static final String SPEED_KEY = "EnergeticArmor";

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(ENERGETIC_ARMOR, false, null));
        hookBuilder.addHook(this, ModifierHooks.EQUIPMENT_CHANGE);
    }

    // 装备与卸载时触发速度修改
    @Override
    public void onEquip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        LivingEntity entity = context.getEntity();
        if (!entity.level().isClientSide && !tool.isBroken()) {
            reloadSpeed(context);
        }
    }

    @Override
    public void onUnequip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        LivingEntity entity = context.getEntity();
        if (!entity.level().isClientSide && !tool.isBroken()) {
            reloadSpeed(context);
        }
    }

    // 重载速度属性
    private void reloadSpeed(EquipmentChangeContext context) {
        LivingEntity living = context.getEntity();
        // 获取速度修改器
        AttributeInstance speed = living.getAttribute(Attributes.MOVEMENT_SPEED);
        // 穿装备时
        if (context.hasModifiableArmor()) {
            // 获取装备属性
            living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
                // 获取属性等级
                int level = holder.get(ENERGETIC_ARMOR, 0);
                double speedAmount = 0.05 * level;  // 修改这个值调整速度，每级增加5%移速
                // 移除原有速度属性修改
                if (speed != null) {
                    speed.removeModifier(SPEED_UUID);
                    // 添加移动速度属性修改
                    speed.addTransientModifier(
                            new AttributeModifier(
                                    SPEED_UUID,
                                    SPEED_KEY,
                                    Math.min(speedAmount, 0.2), // 限制速度上限为20%
                                    AttributeModifier.Operation.MULTIPLY_TOTAL
                            )
                    );
                }
            });
        }
        // 卸装备时移除速度属性修改
        else if (speed != null) {
            speed.removeModifier(SPEED_UUID);
        }
    }
}
