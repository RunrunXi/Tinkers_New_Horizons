package net.qinghua.tinkersnh.modifiers;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.qinghua.tinkersnh.TinkersNH_Effects;
import net.qinghua.tinkersnh.TinkersNH_Modifiers;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class BlessingOfLifeArmorModifier extends Modifier {
    private static final TinkerDataCapability.TinkerDataKey<Integer> BLESSING_OF_LIFE_ARMOR = TConstruct.createKey("blessing_of_life_armor");

    public BlessingOfLifeArmorModifier() {
        super();
        MinecraftForge.EVENT_BUS.addListener(BlessingOfLifeArmorModifier::onLivingDeath);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(BLESSING_OF_LIFE_ARMOR, false, null));
    }

    // 当玩家死亡时
    public static void onLivingDeath(LivingDeathEvent event) {
        // 获取实体
        LivingEntity living = event.getEntity();
        // 如果是旁观者就返回
        // 或者在客户端
        // 或者不是玩家
        if (living.isSpectator()
                || living.getCommandSenderWorld().isClientSide
                || !(living instanceof Player player)) {
            return;
        }
        // 获取装备者
        EquipmentContext context = new EquipmentContext(living);
        if (context.hasModifiableArmor()) {
            living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
                // 获取效果等级
                int level = holder.get(BLESSING_OF_LIFE_ARMOR, 0);
                int debuffLevel = 0;
                // 获取debuff等级
                MobEffectInstance heavyOfBlessing = player.getEffect(TinkersNH_Effects.HEAVY_OF_BLESSING.get());
                if (heavyOfBlessing != null) {
                    debuffLevel = heavyOfBlessing.getAmplifier() + 1; // 获取等级参数
                }
                if (level <= debuffLevel) {
                    return;
                }
                int armorCount = 0;
                // 头盔效果
                if (context.getToolInSlot(EquipmentSlot.HEAD) instanceof ToolStack tool) {
                    armorCount += onArmorTick(event, player, tool, level);
                }
                if (context.getToolInSlot(EquipmentSlot.CHEST) instanceof ToolStack tool) {
                    armorCount += onArmorTick(event, player, tool, level);
                }
                if (context.getToolInSlot(EquipmentSlot.LEGS) instanceof ToolStack tool) {
                    armorCount += onArmorTick(event, player, tool, level);
                }
                if (context.getToolInSlot(EquipmentSlot.FEET) instanceof ToolStack tool) {
                    armorCount += onArmorTick(event, player, tool, level);
                }
                if (armorCount > 0)
                {
                    // 取消死亡事件
                    event.setCanceled(true);
                    // 添加buff
                    player.addEffect(new MobEffectInstance(TinkersNH_Effects.HEAVY_OF_BLESSING.get(), 20 * 60, debuffLevel));
                    // 触发回血
                    player.setHealth(player.getMaxHealth());
                    // 不死图腾音效和粒子效果
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0f, 1.0f);
                    if (player.level() instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(
                                ParticleTypes.TOTEM_OF_UNDYING, // 原版不死图腾粒子
                                player.getX(),
                                player.getY() + 1.5, // 调整到玩家胸口高度
                                player.getZ(),
                                50,   // 粒子数量
                                0.5,  // X轴扩散范围
                                0.5,  // Y轴扩散范围
                                0.5,  // Z轴扩散范围
                                0.1   // 基础运动速度
                        );
                    }
                }
            });
        }
    }
    // 盔甲效果
    public static int onArmorTick(LivingDeathEvent event, LivingEntity player, ToolStack tool, int level) {
    // 如果工具有免疫死亡效果，并且没有损坏
        if (tool.getModifierLevel(TinkersNH_Modifiers.BLESSING_OF_LIFE_ARMOR.get()) > 0 && !tool.isBroken()) {
            // 损失护甲耐久
//            ToolDamageUtil.damageAnimated(tool, tool.getStats().getInt(ToolStats.DURABILITY) / level + 1, player);
            return 1;
        }
        return 0;
    }

}
