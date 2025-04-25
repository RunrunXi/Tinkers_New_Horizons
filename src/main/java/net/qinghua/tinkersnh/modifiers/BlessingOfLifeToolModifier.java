package net.qinghua.tinkersnh.modifiers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.qinghua.tinkersnh.TinkersNH;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.nio.charset.StandardCharsets;
import java.util.UUID;


public class BlessingOfLifeToolModifier extends Modifier implements InventoryTickModifierHook {
    private static final UUID BLESSING_UUID = UUID.nameUUIDFromBytes(
            (TinkersNH.MOD_ID + "BLESSING_UUID").getBytes(StandardCharsets.UTF_8)
    );

    @Override
    public void onInventoryTick(@NotNull IToolStackView tool, @NotNull ModifierEntry modifier, Level world, @NotNull LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, @NotNull ItemStack stack) {
        if (world.isClientSide
                || !(holder instanceof Player player)
                || tool.isBroken()
                || !holder.isAlive()
        ) {
            return;
        }
        AttributeInstance maxHealthAttr = player.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealthAttr == null) return;

        // 根据修饰器等级计算加成值（每级+2生命）
        int level = modifier.getLevel();
        double healthBonus = 2.0 * level;
        AttributeModifier modifierObj = new AttributeModifier(
                BLESSING_UUID,
                "blessing_of_life_health_boost",
                healthBonus,
                AttributeModifier.Operation.ADDITION
        );
        // 当工具被选中时添加/刷新修饰器
        if (isSelected) {
            if (!maxHealthAttr.hasModifier(modifierObj)) {
                maxHealthAttr.addTransientModifier(modifierObj);
                syncHealth(player); // 同步生命值
            }
        }
        // 当工具未被选中时移除修饰器
        else {
            if (maxHealthAttr.hasModifier(modifierObj)) {
                maxHealthAttr.removeModifier(BLESSING_UUID);
                syncHealth(player);
            }
        }
    }

    // 同步当前生命值与上限
    private void syncHealth(Player player) {
        float currentHealth = player.getHealth();
        float maxHealth = player.getMaxHealth();
        if (currentHealth > maxHealth) {
            player.setHealth(maxHealth);
        }
    }

    @Override
    protected void registerHooks(ModuleHookMap.@NotNull Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.INVENTORY_TICK);
    }

}
