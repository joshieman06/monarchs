package joshie.monarchs.item;

import joshie.monarchs.Monarchs;
import joshie.monarchs.armor.MonarchsArmorMaterials;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    public static final Item ROSE_CROWN = registerItem("rose_gold_circlet",
            new CrownItem(MonarchsArmorMaterials.ROSE_GOLD, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(670).rarity(Rarity.RARE)));
    public static final Item FACTION_INVITE= registerItem("faction_invite",
            new InviteItem(new Item.Settings().rarity(Rarity.EPIC)));
    public static final Item FACTION_LEAVE = registerItem("faction_leave",
            new LeaveItem(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Monarchs.MOD_ID, name), item);
    }
    public static void registerModItems() {
        Monarchs.LOGGER.info("[Monarchs & Melodies] REGISTERING ITEMS");

    }
}

