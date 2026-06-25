package LuaCraft.LuaStom.sandbox.boundingbox;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

import LuaCraft.LuaStom.sandbox.position.PointLib;
import net.minestom.server.collision.BlockBoundingBox;
import net.minestom.server.coordinate.Point;

public class BlockBoundingBoxLib extends LuaTable {
    private BlockBoundingBox boundingBox;

    public static LuaValue creator() {
        LuaTable tbl = new LuaTable();

        tbl.set("New", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue minCorner, LuaValue maxCorner) {
                if (minCorner instanceof PointLib pointLib1 && maxCorner instanceof PointLib pointLib2) {
                    Point point1 = pointLib1.getPoint();
                    Point point2 = pointLib2.getPoint();

                    BlockBoundingBox box = new BlockBoundingBox(point1, point2);

                    return new BlockBoundingBoxLib(box);
                }

                return LuaValue.NIL;
            }
        });

        return tbl;
    }

    public BlockBoundingBoxLib(BlockBoundingBox boundingBox) {
        this.boundingBox = boundingBox;

        rawset("GetMax", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                return new PointLib(boundingBox.max());
            }
        });

        rawset("GetMin", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                return new PointLib(boundingBox.min());
            }
        });

        rawset("GetMinX", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                return LuaValue.valueOf(boundingBox.minX());
            }
        });

        rawset("GetMinY", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                return LuaValue.valueOf(boundingBox.minY());
            }
        });

        rawset("GetMinZ", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                return LuaValue.valueOf(boundingBox.minZ());
            }
        });

        rawset("GetMaxX", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                return LuaValue.valueOf(boundingBox.maxX());
            }
        });

        rawset("GetMaxY", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                return LuaValue.valueOf(boundingBox.maxY());
            }
        });

        rawset("GetMaxZ", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                return LuaValue.valueOf(boundingBox.maxZ());
            }
        });
    }

    public BlockBoundingBox getBoundingBox() {
        return boundingBox;
    }
}
