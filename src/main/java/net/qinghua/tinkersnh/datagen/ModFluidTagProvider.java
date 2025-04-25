package net.qinghua.tinkersnh.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.qinghua.tinkersnh.TinkersNH_Fluid;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.common.data.tags.FluidTagProvider;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ModFluidTagProvider extends FluidTagProvider {
    public ModFluidTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper helper) {
        super(packOutput, lookupProvider, helper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        // tinkers_nh
        this.fluidTag(TinkersNH_Fluid.MOLTEN_ENERGETIC_ALLOY);
        this.fluidTag(TinkersNH_Fluid.MOLTEN_UELIBLOOM);
        // 匠魂 tooltips
        this.tag(TinkerTags.Fluids.METAL_TOOLTIPS).addTags(
                TinkersNH_Fluid.MOLTEN_UELIBLOOM.getTag(),
                TinkersNH_Fluid.MOLTEN_ENERGETIC_ALLOY.getTag()
        );
    }

    private void fluidTag(FluidObject<?> fluid) {
        tag(Objects.requireNonNull(fluid.getCommonTag())).add(fluid.get());
    }

    private void fluidTag(FlowingFluidObject<?> fluid) {
        tag(fluid.getLocalTag()).add(fluid.getStill(), fluid.getFlowing());
        TagKey<Fluid> tag = fluid.getCommonTag();
        if (tag != null) {
            tag(tag).addTag(fluid.getLocalTag());
        }
    }
}
