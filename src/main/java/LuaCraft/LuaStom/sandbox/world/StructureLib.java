package LuaCraft.LuaStom.sandbox.world;

import java.util.ArrayList;
import java.util.List;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ThreeArgFunction;

import LuaCraft.LuaStom.LuaErrorAssert;
import LuaCraft.LuaStom.sandbox.position.PointLib;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;

public class StructureLib extends LuaTable {
    private final List<BlockEntry> blocks = new ArrayList<>();

    record BlockEntry(BlockVec relativePos, Block block) {}

    public static LuaValue creator() {
        LuaTable tbl = new LuaTable();

        tbl.set("New", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                return new StructureLib();
            }
        });

        return tbl;
    }

    public StructureLib() {
        rawset("AddBlock", new ThreeArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue xyz, LuaValue block) {
                int x = LuaErrorAssert.checkInt(xyz.get(1), "Structure:AddBlock", 1);
                int y = LuaErrorAssert.checkInt(xyz.get(2), "Structure:AddBlock", 1);
                int z = LuaErrorAssert.checkInt(xyz.get(3), "Structure:AddBlock", 1);

                Block blockkey = Block.fromKey(LuaErrorAssert.checkString(block, "Structure:AddBlock", 2));

                blocks.add(new BlockEntry(new BlockVec(x, y, z), blockkey));

                return StructureLib.this;
            }
        });

        rawset("Place", new ThreeArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue inst, LuaValue origin) {
                InstanceContainer instance = ((InstanceContainerLib) inst).getContainer();
                Point originPoint = ((PointLib) origin).getPoint();
                for (BlockEntry entry : blocks) {
                    instance.setBlock(originPoint.add(entry.relativePos()), entry.block());
                }
                return LuaValue.NIL;
            }
        });
    }
}
