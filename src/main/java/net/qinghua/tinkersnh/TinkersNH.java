package net.qinghua.tinkersnh;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

/*
* 添加一个新材料
* 1. 首先先注册物品，然后注册流体
* 把他们添加到创造模式物品栏
* 2. 其次，还要添加材质，加图片和mcmeta文件
* 图片包括物品和流体
* 还需要在models文件夹中用json文件定义材质
* 3. 然后在将所有材料和流体注册forge的tags
* 之后在resources/data/tinkersnh/recipes/tools/materials中注册为匠魂材料，共3个json文件
* 4. 之后可以注册配方，浇筑配方、融化配方、合成配方
* 5. 然后在resources/data/tinkersnh/tinkering/materials创建材料相关的属性，共3个json文件
* 6. 最后设定颜色colors.json和以及用匠魂生成材质，添加文本信息
*
* 添加一个新特性
* 1. 首先注册特性id，将特性赋予材料
* 2. 然后写特性相关的代码
* 3，在json文件中定义特性的数据，写colors.json
* 4. 写本地化文本
*
* 添加一个新方块
* 1. 首先注册方块，添加到创造模式物品栏
* 2. 添加材质，在blockstates中加json，在models的item和block中加json，添加图片
* 3. 添加本地化文本
* */
@Mod(TinkersNH.MOD_ID)
public class TinkersNH {
    public static final String MOD_ID = "tinkersnh";
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    // 定义自定义标签页
    public static final RegistryObject<CreativeModeTab> TINKERSNH_TAB = CREATIVE_MODE_TABS.register("tinkersnh_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tinkersnh.tinkersnh_tab")) // 本地化键名
                    .icon(() -> new ItemStack(TinkersNH_Items.ENERGETIC_ALLOY_INGOT.get())) // 标签页图标（使用你的物品）
                    .displayItems((params, output) -> {
                        /* 添加物品-锭 */
                        output.accept(TinkersNH_Items.ENERGETIC_ALLOY_INGOT.get());
                        output.accept(TinkersNH_Items.UELIBLOOM_INGOT.get());
                        output.accept(TinkersNH_Items.GRAVITONSTEEL_INGOT.get());
                        /* 添加物品-粒 */
                        output.accept(TinkersNH_Items.UELIBLOOM_NUGGET.get());

                        /* 添加方块物品-块 */
//                        output.accept(TinkersNH_Blocks.UELIBLOOM_BLOCK.get());
                        /* 添加方块物品-矿石 */
                        output.accept(TinkersNH_Blocks.UELIBLOOM_ORE.get());
                        /* 添加方块物品-深层矿石 */
                        /* 添加方块物品-粗矿 */
                        /* 添加方块物品-粗矿块 */

                        /* 添加流体桶 */
                        output.accept(Objects.requireNonNull(TinkersNH_Fluid.MOLTEN_ENERGETIC_ALLOY.getBucket()));
                        output.accept(Objects.requireNonNull(TinkersNH_Fluid.MOLTEN_UELIBLOOM.getBucket()));
                        output.accept(Objects.requireNonNull(TinkersNH_Fluid.MOLTEN_GRAVITONSTEEL.getBucket()));

                    })
                    .build()
    );

    public TinkersNH(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();


        // 注册物品
        TinkersNH_Items.ITEMS.register(modEventBus);
        TinkersNH_Fluid.FLUIDS.register(modEventBus);
        TinkersNH_Blocks.BLOCKS.register(modEventBus);
        TinkersNH_Effects.EFFECTS.register(modEventBus);
        TinkersNH_Modifiers.MODIFIERS.register(modEventBus);

        // 将延迟注册器注册到模组事件总线，以便注册创造模式标签页
        CREATIVE_MODE_TABS.register(modEventBus);

        // 为服务器和其他游戏事件注册我们自己
        MinecraftForge.EVENT_BUS.register(this);


    }


}
