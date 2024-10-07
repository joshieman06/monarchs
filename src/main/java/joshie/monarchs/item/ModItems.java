package joshie.monarchs.item;

import joshie.monarchs.Monarchs;
import joshie.monarchs.armor.MonarchsArmorMaterials;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ModItems {
    public static final Item ROSE_CROWN = registerItem("rose_gold_circlet",
            new CrownItem(MonarchsArmorMaterials.ROSE_GOLD, ArmorItem.Type.HELMET, new Item.Properties().durability(670).rarity(Rarity.RARE)));
    public static final Item FACTION_INVITE= registerItem("faction_invite",
            new InviteItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final Item FACTION_LEAVE = registerItem("faction_leave",
            new LeaveItem(new Item.Properties()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Monarchs.MOD_ID, name), item);
    }
    public static void registerModItems() {
        Monarchs.LOGGER.info("[Monarchs & Melodies] REGISTERING ITEMS");

    }
}

