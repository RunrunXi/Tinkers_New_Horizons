package net.qinghua.tinkersnh;

import net.qinghua.tinkersnh.modifiers.BlessingOfLifeArmorModifier;
import net.qinghua.tinkersnh.modifiers.BlessingOfLifeToolModifier;
import net.qinghua.tinkersnh.modifiers.BulwarkBastionModifier;
import net.qinghua.tinkersnh.modifiers.GraviticAmplificationModifier;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class TinkersNH_Modifiers {
    public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(TinkersNH.MOD_ID);

    public static final StaticModifier<BlessingOfLifeArmorModifier> BLESSING_OF_LIFE_ARMOR = MODIFIERS.register("blessing_of_life_armor", BlessingOfLifeArmorModifier::new);
    public static final StaticModifier<BlessingOfLifeToolModifier> BLESSING_OF_LIFE_TOOL = MODIFIERS.register("blessing_of_life_tool", BlessingOfLifeToolModifier::new);
    // 能力
    public static final StaticModifier<GraviticAmplificationModifier> GRAVITIC_AMPLIFICATION = MODIFIERS.register("gravitic_amplification", GraviticAmplificationModifier::new);
    public static final StaticModifier<BulwarkBastionModifier> BULWARK_BASTION = MODIFIERS.register("bulwark_bastion", BulwarkBastionModifier::new);
}
