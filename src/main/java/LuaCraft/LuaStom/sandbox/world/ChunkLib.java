package LuaCraft.LuaStom.sandbox.world;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

import LuaCraft.LuaStom.LuaErrorAssert;
import net.kyori.adventure.key.Key;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.Chunk;

public class ChunkLib extends LuaTable {
    private Chunk chunk;

    public ChunkLib(Chunk chunk) {
        this.chunk = chunk;

        rawset("GetX", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                return LuaValue.valueOf(chunk.getChunkX());
            }
        });

        rawset("GetZ", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                return LuaValue.valueOf(chunk.getChunkZ());
            }
        });

        rawset("SetBiome", new ThreeArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue block, LuaValue biomeKey) {
                if (!(block instanceof BlockLib)) throw new LuaError("Chunk:SetBiome() expects a 1st argument of Block, received unexpected value");

                chunk.setBiome(((BlockLib) block).getBlockPosition(), MinecraftServer.getBiomeRegistry().getKey(Key.key(LuaErrorAssert.checkString(biomeKey, "Chunk:SetBiome", 1))));

                return ChunkLib.this;
            }
        });
    }

    public Chunk getChunk() {
        return chunk;
    }
}
