package joshie.monarchs.item;

import joshie.monarchs.Monarchs;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup MM_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of(Monarchs.MOD_ID, "monarchs_melodies"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.monarchs_melodies"))
                    .icon(() ->  new ItemStack(ModItems.ROSE_CROWN)).entries(((displayContext, entries) ->  {
                        entries.add(ModItems.ROSE_CROWN);
                    })).build());
    public static void registerItemGroups() {
        Monarchs.LOGGER.info("[Monarchs & Melodies] REGISTERING ITEM GROUPS");
    }
}
