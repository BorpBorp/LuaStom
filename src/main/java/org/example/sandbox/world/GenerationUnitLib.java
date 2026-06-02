package org.example.sandbox.world;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import net.minestom.server.instance.generator.GenerationUnit;

public class GenerationUnitLib extends LuaTable {
    public GenerationUnitLib(GenerationUnit unit) {
        rawset("GetModifier", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                return new UnitModifierLib(unit.modifier());
            }
        });
    }
}