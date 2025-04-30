package net.qinghua.tinkersnh.modifiers;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.library.data.tinkering.AbstractMaterialTagProvider;


public class ModifierTagProvider extends AbstractMaterialTagProvider {
    protected ModifierTagProvider(PackOutput packOutput, String modId, ExistingFileHelper existingFileHelper) {
        super(packOutput, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        // 能力
//        this.tag(MELEE_ABILITIES).add(TinkersNH_Modifiers.GRAVITIC_AMPLIFICATION.getId());
    }

    @Override
    public String getName() {
        return "Tinkers' New Horizons Modifier Tag Provider";
    }
}
