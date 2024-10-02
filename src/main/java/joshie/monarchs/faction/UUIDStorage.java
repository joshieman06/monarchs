package joshie.monarchs.faction;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import joshie.monarchs.Monarchs;
import joshie.monarchs.attachment.MonarchsAttachmentTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UUIDStorage {
    private static final String FILE_NAME = "uuid_storage.json";
    private static final Gson GSON = new Gson();
    private List<UUID> uuidList = new ArrayList<>();


    public void saveUUIDs(MinecraftServer server) {
        File file = new File(server.getSavePath(WorldSavePath.ROOT).toFile(), FILE_NAME);
        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(uuidList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUUID(UUID uuid) {
        if (!uuidList.contains(uuid)) {
            uuidList.add(uuid);
        }
    }

    public void loadUUIDs(MinecraftServer server) {
        File file = new File(server.getSavePath(WorldSavePath.ROOT).toFile(), FILE_NAME);
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                Type listType = new TypeToken<List<UUID>>() {}.getType();
                uuidList = GSON.fromJson(reader, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<UUID> getUUIDs() {
        return uuidList;
    }

    public static void checkFaction(ServerPlayer entity) {
        if (Monarchs.factionStorage.getUUIDs().contains(entity.getAttached(MonarchsAttachmentTypes.PERSISTENT_FACTION))) {
            entity.setAttached(MonarchsAttachmentTypes.PERSISTENT_FACTION, null);
        }
    }


}
