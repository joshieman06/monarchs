package joshie.monarchs.faction;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MonarchyManager {
    private static final String MONARCHY_SAVE_FILE = "monarchy_data";
    private static final Map<UUID, Monarchy> monarchies = new HashMap<>();

    // Load monarchies from file when the server starts
    public static void loadMonarchies(MinecraftServer server) {
        File saveFile = getMonarchySaveFile(server);
        if (saveFile.exists()) {
            CompoundTag tag = readFromFile(saveFile);
            for (String key : tag.getAllKeys()) {
                UUID monarchyId = UUID.fromString(key);
                monarchies.put(monarchyId, Monarchy.fromNbt(tag.getCompound(key)));
            }
        }
    }

    // Save monarchies to file when the server stops
    public static void saveMonarchies(MinecraftServer server) {
        CompoundTag tag = new CompoundTag();
        for (UUID monarchyId : monarchies.keySet()) {
            Monarchy monarchy = monarchies.get(monarchyId);
            tag.put(monarchyId.toString(), monarchy.toNbt());
        }
        writeToFile(getMonarchySaveFile(server), tag);
    }

    // Get monarchy by UUID
    public static Monarchy getMonarchy(UUID monarchyId) {
        return monarchies.get(monarchyId);
    }

    // Update or add monarchy data
    public static void updateMonarchy(UUID monarchyId, Monarchy monarchy) {
        monarchies.put(monarchyId, monarchy);
    }

    // Remove monarchy data
    public static void removeMonarchy(UUID monarchyId) {
        monarchies.remove(monarchyId);
    }

    // Save and load helpers
    private static File getMonarchySaveFile(MinecraftServer server) {
        LevelStorageSource.LevelStorageAccess storageAccess = server.getWorldPath(new LevelResource("monarchy_data"));
        return new File(storageAccess.getDimensionPath().toFile(), MONARCHY_SAVE_FILE + ".dat");
    }

    private static CompoundTag readFromFile(File file) {
        // Add logic to read CompoundTag from file
        return new CompoundTag();
    }

    private static void writeToFile(File file, CompoundTag tag) {
        // Add logic to write CompoundTag to file
    }
}