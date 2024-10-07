package joshie.monarchs.item;

import joshie.monarchs.Monarchs;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroups {
        public static final CreativeModeTab MM_GROUP = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(Monarchs.MOD_ID, "monarchs_melodies"),
                CreativeModeTab.builder(CreativeModeTab.Row.TOP, 6).title(Component.translatable("itemgroup.monarchs_melodies"))
                        .icon(() -> new ItemStack(ModItems.ROSE_CROWN)).displayItems(((displayContext, entries) -> {
                            entries.accept(ModItems.ROSE_CROWN);
                            entries.accept(ModItems.FACTION_LEAVE);
                        })).build());

        public static void registerItemGroups() {
            Monarchs.LOGGER.info("[Monarchs & Melodies] REGISTERING ITEM GROUPS");
        }
    }
