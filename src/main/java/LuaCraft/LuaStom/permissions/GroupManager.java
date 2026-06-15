package LuaCraft.LuaStom.permissions;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class GroupManager {
    public static class GroupData {
        private final Set<String> permissions = ConcurrentHashMap.newKeySet();
        private final ConcurrentHashMap<String, String> metadata = new ConcurrentHashMap<>();

        public Set<String> getPermissions() {
            return permissions;
        }

        public ConcurrentHashMap<String, String> getMetadata() {
            return metadata;
        }
    }

    private final ConcurrentHashMap<String, GroupData> groups = new ConcurrentHashMap<>();

    public void createGroup(String name) {
        if (groups.containsKey(name))
            return;

        groups.putIfAbsent(name, new GroupData());
    }

    public void deleteGroup(String name) {
        
    }

    public void addPermission(String group, String permission) {
        if (!groups.containsKey(group))
            return;

        groups.get(group).getPermissions().add(permission);
    }

    public void removePermission(String group, String permission) {
        if (!groups.containsKey(group))
            return;

        groups.get(group).getPermissions().remove(permission);
    }

    public ConcurrentHashMap<String, GroupData> getPermissionGroups() {
        return groups;
    }
}
