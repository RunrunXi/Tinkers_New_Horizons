package net.qinghua.tinkersnh;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FlowingFluidObject;

import static slimeknights.tconstruct.fluids.block.BurningLiquidBlock.createBurning;

public class TinkersNH_Fluid {
    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(TinkersNH.MOD_ID);

    // 定义流体属性配置
    private static FluidType.Properties hot() {
        return FluidType.Properties.create()
                .density(2000)                       // 密度（大于水则下沉）
                .viscosity(10000)                    // 粘度（影响流动速度）
                .temperature(1000)                  // 温度（影响与生物的交互）
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                .motionScale(0.0023333333333333335D)  // 流动速度系数
                .canSwim(false)                      // 是否允许游泳
                .canDrown(false)                     // 是否允许窒息
                .pathType(BlockPathTypes.LAVA)       // 生物路径类型
                .adjacentPathType(null);             // 邻居路径类型?
    }

    // 注册流体时配置属性
    // 充能合金
    public static final FlowingFluidObject<ForgeFlowingFluid> MOLTEN_ENERGETIC_ALLOY =
            FLUIDS.register("molten_energetic_alloy")
                    .type(hot().lightLevel(15))
                    .block(createBurning(MapColor.COLOR_ORANGE, 12, 6, 6f))
                    .bucket()
                    .commonTag()
                    .flowing();

    // 龙蒿
    public static final FlowingFluidObject<ForgeFlowingFluid> MOLTEN_UELIBLOOM =
            FLUIDS.register("molten_uelibloom")
                    .type(hot().lightLevel(15))
                    .block(createBurning(MapColor.COLOR_GREEN, 12, 6, 6f))
                    .bucket()
                    .commonTag()
                    .flowing();

    // 亚金
    public static final FlowingFluidObject<ForgeFlowingFluid> MOLTEN_GRAVITONSTEEL =
            FLUIDS.register("molten_gravitonsteel")
                    .type(hot().lightLevel(15))
                    .block(createBurning(MapColor.COLOR_PURPLE, 12, 6, 6f))
                    .bucket()
                    .commonTag()
                    .flowing();
}
