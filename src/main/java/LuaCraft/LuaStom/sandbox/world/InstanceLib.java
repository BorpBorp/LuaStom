package LuaCraft.LuaStom.sandbox.world;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

import LuaCraft.LuaStom.LuaErrorAssert;
import LuaCraft.LuaStom.sandbox.position.PointLib;
import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;

public class InstanceLib extends LuaTable {
    private Instance instance;

    public InstanceLib(Instance instance) {
        this.instance = instance;

        rawset("LoadChunk", new ThreeArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue x, LuaValue z) {
                instance.loadChunk(
                    LuaErrorAssert.checkInt(x, "Instance:LoadChunk", 1),
                    LuaErrorAssert.checkInt(z, "Instance:LoadChunk", 2)
                );

                return InstanceLib.this;
            }
        });

        rawset("UnloadChunk", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue chunk) {
                if (chunk instanceof ChunkLib) {
                    instance.unloadChunk(((ChunkLib) chunk).getChunk());

                    return InstanceLib.this;
                } else {
                    throw new LuaError("UnloadChunk expects a Chunk, received unknown value");
                }
            }
        });

        rawset("GetChunks", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                LuaTable chunks = new LuaTable();

                for (Chunk chunk : instance.getChunks()) {
                    chunks.insert(chunks.length() + 1, new ChunkLib(chunk));
                }

                return chunks;
            }
        });

        rawset("GetChunkAt", new ThreeArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue x, LuaValue z) {
                return new ChunkLib(instance.getChunkAt(LuaErrorAssert.checkDouble(x, "Instance:GetChunkAt", 1), LuaErrorAssert.checkDouble(z, "Instance:GetChunkAt", 2)));
            }
        });

        rawset("LoadChunk", new ThreeArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue x, LuaValue z) {
                instance.loadChunk(
                    LuaErrorAssert.checkInt(x, "Instance:LoadChunk", 1),
                    LuaErrorAssert.checkInt(z, "Instance:LoadChunk", 2)
                );

                return InstanceLib.this;
            }
        });

        rawset("GetBlock", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue pos) {
                Point position = ((PointLib) pos).getPoint();
                Block block = instance.getBlock(position);
                return new BlockLib(block, instance, position);
            }
        });

        rawset("SetBlock", new ThreeArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue pos, LuaValue block) {
                Block newBlock = ((BlockLib) block).getBlock();

                instance.setBlock(((PointLib) pos).getPoint(), newBlock);

                return LuaValue.NIL;
            }
        });
    }

    public Instance getInstance() {
        return instance;
    }
}
