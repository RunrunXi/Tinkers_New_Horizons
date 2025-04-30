package net.qinghua.tinkersnh.modifiers;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import javax.annotation.Nullable;

// 为了使远程武器也可以破盾，所以我自己实现了一个破盾效果，和原版几乎一致
public class GraviticAmplificationModifier extends Modifier implements MeleeHitModifierHook, ProjectileHitModifierHook, ToolStatsModifierHook, InventoryTickModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.@NotNull Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.MELEE_HIT);
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_HIT);
        hookBuilder.addHook(this, ModifierHooks.TOOL_STATS);
        hookBuilder.addHook(this, ModifierHooks.INVENTORY_TICK);
    }

    @Override
    public void failedMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageAttempted) {
        LivingEntity attacker = context.getAttacker();
        LivingEntity target = context.getLivingTarget();
        if (!attacker.getCommandSenderWorld().isClientSide
                && !tool.isBroken()
                && target instanceof Player player
                && target.isUsingItem()
                && target.isBlocking()
        ) {
            // 计算盾牌的耐久消耗（至少 3 点）
            EquipmentSlot slot = player.getUsedItemHand() == InteractionHand.MAIN_HAND
                    ? EquipmentSlot.MAINHAND
                    : EquipmentSlot.OFFHAND;
            int amount = Math.max(3, (int) damageAttempted + 1);
            player.getUseItem().hurtAndBreak(amount, attacker, (entity) -> entity.broadcastBreakEvent(slot));
            // 使对方的盾牌进入冷却5秒
            player.getCooldowns().addCooldown(target.getUseItem().getItem(), 100);
            player.stopUsingItem();
            // 播放破盾音效
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.SHIELD_BREAK, SoundSource.PLAYERS, 1.0f, 1.0f);
        }
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData,
                                         ModifierEntry modifier, Projectile projectile, EntityHitResult hit,
                                         @Nullable LivingEntity attacker, @Nullable LivingEntity target
    ) {
        if (attacker != null
                && !attacker.getCommandSenderWorld().isClientSide
                && target instanceof Player player
                && target.isUsingItem()
                && target.isBlocking())
        {
            // 盾牌的耐久消耗
            EquipmentSlot slot = player.getUsedItemHand() == InteractionHand.MAIN_HAND
                    ? EquipmentSlot.MAINHAND
                    : EquipmentSlot.OFFHAND;
            player.getUseItem().hurtAndBreak(3, attacker, (entity) -> entity.broadcastBreakEvent(slot));
            // 使对方的盾牌进入冷却5秒
            player.getCooldowns().addCooldown(target.getUseItem().getItem(), 100);
            player.stopUsingItem();
            // 播放破盾音效
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.SHIELD_BREAK, SoundSource.PLAYERS, 1.0f, 1.0f);

            return true;
        }

        return false;
    }

    @Override
    public void addToolStats(IToolContext iToolContext, ModifierEntry modifierEntry, ModifierStatsBuilder builder) {
        ToolStats.ATTACK_DAMAGE.multiplyAll(builder, 1.4f);
    }

    @Override
    public @NotNull Component getDisplayName(int level) {
        return ModifierLevelDisplay.NO_LEVELS.nameForLevel(this, level);
    }

    @Override
    public void onInventoryTick(@NotNull IToolStackView tool, @NotNull ModifierEntry modifier, Level world, @NotNull LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, @NotNull ItemStack stack) {
        if (world.isClientSide
                || !(holder instanceof Player player)
                || tool.isBroken()
        ) {
            return;
        }
        if (isSelected) {
            holder.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1, 1));
        }
    }
}