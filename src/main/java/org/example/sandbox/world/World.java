package org.example.sandbox.world;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;

public class World extends LuaTable {
    public World() {
        rawset("CreateInstance", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                InstanceManager instanceManager = MinecraftServer.getInstanceManager();
                InstanceContainer instanceContainer = instanceManager.createInstanceContainer();

                return new InstanceContainerLib(instanceContainer);
            }
        });
    }
}
