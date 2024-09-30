package joshie.monarchs.faction;

import java.util.List;
import java.util.UUID;

public class Monarchy {

    private List<UUID> factionMembers;
    private UUID monarchyOwner;
    private String monarchyType;
    private UUID id;

    public Monarchy(UUID owner, UUID id) {
        this.monarchyOwner = owner;
        this.id = id;
    }

    public List<UUID> getMembers() {
        return this.factionMembers;
    }

    public void addMember(UUID user) {
        factionMembers.add(user);
    }

    public UUID getId() {
        return this.id;
    }

    public String getType() {
        return monarchyType;
    }


}
