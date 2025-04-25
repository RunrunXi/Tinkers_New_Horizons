package net.qinghua.tinkersnh;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.qinghua.tinkersnh.effects.HeavyOfBlessing;

public class TinkersNH_Effects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, TinkersNH.MOD_ID);

    public static RegistryObject<MobEffect> HEAVY_OF_BLESSING = EFFECTS.register("heavy_of_blessing",()->
    {
        //3个参数:药水类型(好/坏/中)、药水粒子颜色、是否为即时效果(类似于瞬间伤害效果)
        return new HeavyOfBlessing(MobEffectCategory.HARMFUL, 0xE69949, true);
    });
}
