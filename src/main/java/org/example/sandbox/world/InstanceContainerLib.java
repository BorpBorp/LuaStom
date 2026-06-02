package org.example.sandbox.world;

import java.io.File;

import org.example.LuaErrorAssert;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

import net.minestom.server.instance.DynamicChunk;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.anvil.AnvilLoader;

public class InstanceContainerLib extends LuaTable {
    private InstanceContainer instanceContainer;
    public InstanceContainerLib(InstanceContainer container) {
        this.instanceContainer = container;
        
        rawset("SetWorldName", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue name) {
                container.setChunkLoader(new AnvilLoader("worlds/" + LuaErrorAssert.checkString(name, "SetWorldName", 1)));
                new File("worlds", LuaErrorAssert.checkString(name, "SetWorldName", 1)).mkdirs();

                return InstanceContainerLib.this;
            }
        });

        rawset("SetGenerator", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue callback) {
                container.setGenerator(unit -> {
                    LuaErrorAssert.checkFunction(callback, "SetGenerator", 1).call(new GenerationUnitLib(unit));
                });
                return InstanceContainerLib.this;
            }
        });

        rawset("ToggleLighting", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue toggle) {
                if (LuaErrorAssert.checkBoolean(toggle, "ToggleLighting", 1)) {
                    container.setChunkSupplier(LightingChunk::new);
                } else {
                    container.setChunkSupplier(DynamicChunk::new);
                }

                return InstanceContainerLib.this;
            }
        });
    }

    public InstanceContainer getContainer() {
        return this.instanceContainer;
    }
}