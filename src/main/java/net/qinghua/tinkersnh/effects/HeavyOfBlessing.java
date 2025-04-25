package net.qinghua.tinkersnh.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.qinghua.tinkersnh.TinkersNH;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class HeavyOfBlessing extends NoMilkEffect {
    private static final UUID HEAVY_OF_BLESSING_UUID = UUID.nameUUIDFromBytes(
            (TinkersNH.MOD_ID + "HEAVY_OF_BLESSING_UUID").getBytes(StandardCharsets.UTF_8)
    );

    public HeavyOfBlessing(MobEffectCategory typeIn, int color, boolean show) {
        super(MobEffectCategory.HARMFUL, 0xE69949, true);
        this.addAttributeModifier(
                Attributes.MAX_HEALTH,
                HEAVY_OF_BLESSING_UUID.toString(), // 必须转换为字符串
                -2.0,
                AttributeModifier.Operation.ADDITION
        );
    }

    @Override
    public boolean isDurationEffectTick(int tick, int level) {
        return true;
    }

    // 根据等级动态调整修饰值
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide) {
            // 获取当前效果的修饰器
            AttributeInstance maxHealth = entity.getAttribute(Attributes.MAX_HEALTH);
            if (maxHealth != null) {
                // 移除旧的修饰器
                maxHealth.removeModifier(HEAVY_OF_BLESSING_UUID);

                // 根据等级重新计算数值（amplifier从0开始）
                double reduction = -2.0 * (amplifier + 1);

                // 添加新修饰器
                maxHealth.addPermanentModifier(new AttributeModifier(
                        HEAVY_OF_BLESSING_UUID,
                        "heavy_of_blessing",
                        reduction,
                        AttributeModifier.Operation.ADDITION
                ));
            }
        }
    }

    // 移除修饰器时恢复生命值
    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributes, int amplifier) {
        super.removeAttributeModifiers(entity, attributes, amplifier);
        if (!entity.level().isClientSide) {
            // 确保生命值不超过上限
            if (entity.getHealth() > entity.getMaxHealth()) {
                entity.setHealth(entity.getMaxHealth());
            }
        }
    }

}
