package net.qinghua.tinkersnh;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class TinkersNH_Blocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TinkersNH.MOD_ID);
    // 注册方块
    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        TinkersNH_Items.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    // 注册方块物品
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    /* 注册方块-块 */
//    public static final RegistryObject<Block> UELIBLOOM_BLOCK = registerBlock("uelibloom_block",
//            () -> new Block(BlockBehaviour.Properties.of()
//                    .mapColor(MapColor.COLOR_GREEN)
//                    .requiresCorrectToolForDrops()
//                    .strength(50.0F, 1200.0F)
//                    .sound(SoundType.NETHERITE_BLOCK)));
    /* 注册方块-矿石 */
    public static final RegistryObject<Block> UELIBLOOM_ORE = registerBlock("uelibloom_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GREEN)
                    .requiresCorrectToolForDrops()
                    .strength(50.0F, 1200.0F)
                    .sound(SoundType.ANCIENT_DEBRIS)));
    /* 注册方块-深层矿石 */
    /* 注册方块-粗矿块 */

}
