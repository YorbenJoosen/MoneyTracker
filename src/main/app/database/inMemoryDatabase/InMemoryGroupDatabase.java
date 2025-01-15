package app.database.inMemoryDatabase;

import app.database.abstractDatabase.AbstractGroupDatabase;
import app.group.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class InMemoryGroupDatabase extends AbstractGroupDatabase {
    private final HashMap<UUID, Group> groupMap = new HashMap<>();

    public InMemoryGroupDatabase() {}

    @Override
    public void addEntry(Group group) {
        groupMap.put(group.getId(), group);
        support.firePropertyChange("Update", null, group);
    }

    @Override
    public void removeEntry(UUID groupId) {
        groupMap.get(groupId).removeAllTickets();
        groupMap.remove(groupId);
    }

    @Override
    public void clear() {
        groupMap.clear();
    }

    @Override
    public ArrayList<Group> getAll() {
        return new ArrayList<>(groupMap.values());
    }

    @Override
    public Group getViaUUID(UUID id) {
        return groupMap.get(id);
    }
}
