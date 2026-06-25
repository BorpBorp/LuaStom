package LuaCraft.LuaStom.sandbox.instance;

import java.util.concurrent.ConcurrentHashMap;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

import LuaCraft.LuaStom.LuaErrorAssert;
import de.articdive.jnoise.pipeline.JNoise;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;

public class InstanceManagerLib extends LuaTable {
    private static final ConcurrentHashMap<String, InstanceContainerLib> namedInstances = new ConcurrentHashMap<>();
    private static InstanceManager instanceManager = MinecraftServer.getInstanceManager();

    public InstanceManagerLib() {
        rawset("CreateInstance", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue name) {
                String instanceName = LuaErrorAssert.checkString(name, "Instance:CreateInstance", 1);
                InstanceContainer instanceContainer = instanceManager.createInstanceContainer();
                InstanceContainerLib instanceLib = new InstanceContainerLib(instanceContainer, instanceName);

                namedInstances.put(LuaErrorAssert.checkString(name, "Instance:CreateInstance", 1), instanceLib);

                return instanceLib;
            }
        });

        rawset("CreateJNoise", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                return new JNoiseBuilderLib(JNoise.newBuilder());
            }
        });

        rawset("GetInstanceFromName", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue name) {
                InstanceContainerLib instance = namedInstances.get(LuaErrorAssert.checkString(name, "Instance:GetInstanceFromName", 1));

                if (instance == null) return LuaValue.NIL;

                return instance;
            }
        });
    }
}
