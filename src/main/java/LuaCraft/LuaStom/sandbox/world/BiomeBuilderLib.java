package LuaCraft.LuaStom.sandbox.world;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import net.minestom.server.world.biome.Biome;

public class BiomeBuilderLib extends LuaTable {
    public LuaValue creator() {
        LuaTable tbl = new LuaTable();

        return tbl;
    }

    public BiomeBuilderLib(Biome.Builder builder) {

    }
}
