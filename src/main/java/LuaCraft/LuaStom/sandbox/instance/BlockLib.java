package LuaCraft.LuaStom.sandbox.instance;

import org.jetbrains.annotations.Nullable;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

import LuaCraft.LuaStom.LuaErrorAssert;
import LuaCraft.LuaStom.sandbox.position.PointLib;
import net.kyori.adventure.key.Key;
import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.registry.Registry;
import net.minestom.server.registry.RegistryTag;

public class BlockLib extends LuaTable {
    private Block block;
    private Point blockVec;

    public static LuaTable creator() {
        LuaTable tbl = new LuaTable();

        tbl.set("New", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue key) {
                Block newBlock = Block.fromKey(Key.key(LuaErrorAssert.checkString(key, "Block:New", 1)));

                return new BlockLib(newBlock, null, null);
            }
        });

        return tbl;
    }

    public BlockLib(Block block, @Nullable Instance instance, Point position) {
        this.block = block;
        this.blockVec = position;

        rawset("GetKey", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                return LuaValue.valueOf(block.name());
            }
        });

        rawset("GetName", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                String blockKey = block.name();

                return LuaValue.valueOf(blockKey.replace("minecraft:", "").replace("_", " "));
            }
        });

        rawset("GetChunk", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                return new ChunkLib(instance.getChunk(position.chunkX(), position.chunkZ()));
            }
        });

        rawset("WithProperty", new ThreeArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue key, LuaValue value) {
                Block newBlock = block.withProperty(LuaErrorAssert.checkString(key, "Block:WithProperty", 1),
                        LuaErrorAssert.checkString(value, "Block:WithProperty", 2));

                return new BlockLib(newBlock, instance, position);
            }
        });

        rawset("GetProperty", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue prop) {
                String property = block.getProperty(LuaErrorAssert.checkString(prop, "Block:GetProperty", 1));

                return LuaValue.valueOf(property);
            }
        });

        rawset("IsAir", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                return LuaValue.valueOf(block.isAir());
            }
        });

        rawset("GetPosition", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                return new PointLib(position);
            }
        });

        rawset("HasTag", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue tag) {
                Registry<Block> registry = Block.staticRegistry();
                RegistryTag<Block> dataTag = registry
                        .getTag(Key.key(LuaErrorAssert.checkString(tag, "Block:HasTag", 1)));
                if (dataTag == null)
                    return LuaValue.FALSE;
                return LuaValue.valueOf(dataTag.contains(block));
            }
        });
    }

    public Block getBlock() {
        return block;
    }

    public Point getBlockPosition() {
        return blockVec;
    }
}