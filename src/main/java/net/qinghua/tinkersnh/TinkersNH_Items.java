package net.qinghua.tinkersnh;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TinkersNH_Items {
    // 使用 DeferredRegister 管理所有物品
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TinkersNH.MOD_ID);
    // 注册物品
    /* 粗矿 */
    /* 锭 */
    // 充能合金
    public static final RegistryObject<Item> ENERGETIC_ALLOY_INGOT =
            ITEMS.register("energetic_alloy_ingot", () -> new Item(new Item.Properties()));
    // 龙蒿
    public static final RegistryObject<Item> UELIBLOOM_INGOT =
            ITEMS.register("uelibloom_ingot", () -> new Item(new Item.Properties()));
    /* 粒 */
    public static final RegistryObject<Item> UELIBLOOM_NUGGET =
            ITEMS.register("uelibloom_nugget", () -> new Item(new Item.Properties()));
}
