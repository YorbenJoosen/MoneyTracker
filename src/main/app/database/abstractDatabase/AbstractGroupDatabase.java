package main.app.database.abstractDatabase;

import main.app.group.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class AbstractGroupDatabase extends AbstractDatabase {
    private final HashMap<UUID, Group> groupMap = new HashMap<>();
    public void addGroup(Group group) {
        groupMap.put(group.getId(), group);
        this.support.firePropertyChange("group", null, group);
    }

    public ArrayList<Group> getGroups() {
        return new ArrayList<>(groupMap.values());
    }
    public Group getGroup(UUID id) {
        return groupMap.get(id);
    }
}
