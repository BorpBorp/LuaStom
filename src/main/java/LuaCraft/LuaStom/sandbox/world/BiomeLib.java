package LuaCraft.LuaStom.sandbox.world;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

import LuaCraft.LuaStom.LuaErrorAssert;
import net.kyori.adventure.key.Key;
import net.minestom.server.MinecraftServer;
import net.minestom.server.world.biome.Biome;

public class BiomeLib extends LuaTable {
    private Biome biome;

    public BiomeLib(Biome biome) {
        this.biome = biome;

        rawset("Register", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue key) {
                MinecraftServer.getBiomeRegistry().register(
                        Key.key(LuaErrorAssert.checkString(key, "Biome:Register", 1)),
                        biome);

                return BiomeLib.this;
            }
        });
    }

    public Biome getBiome() {
        return biome;
    }
}
