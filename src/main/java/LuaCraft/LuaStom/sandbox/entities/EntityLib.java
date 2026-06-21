package LuaCraft.LuaStom.sandbox.entities;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

import LuaCraft.LuaStom.LuaErrorAssert;
import LuaCraft.LuaStom.sandbox.position.PointLib;
import LuaCraft.LuaStom.sandbox.position.PositionLib;
import LuaCraft.LuaStom.sandbox.world.InstanceContainerLib;
import LuaCraft.LuaStom.sandbox.world.InstanceLib;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.instance.InstanceContainer;

public class EntityLib extends LuaTable {
    private Entity entity;

    public static final LuaTable ENTITY_METATABLE = new LuaTable();

    static {
        ENTITY_METATABLE.rawset("__index", ENTITY_METATABLE);

        ENTITY_METATABLE.rawset("SetInstance", new ThreeArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue inst, LuaValue pos) {
                if (!(self instanceof EntityLib entityLib)) {
                    return LuaValue.NIL;
                }

                if (!(inst instanceof InstanceContainerLib container)) {
                    return LuaValue.NIL; 
                }

                if (!(pos instanceof PositionLib position)) {
                    return LuaValue.NIL;
                }

                Entity ent = entityLib.getEntity();
                InstanceContainer instance = container.getContainer();
                Pos instancePosition = position.getPoint();

                ent.setInstance(instance, instancePosition);

                return self;
            }
        });

        ENTITY_METATABLE.rawset("GetInstance", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                if (self instanceof EntityLib entityLib) {
                    Entity ent = entityLib.getEntity();

                    return new InstanceLib(ent.getInstance());
                } else {
                    return LuaValue.NIL;
                }
            }
        });

        ENTITY_METATABLE.rawset("GetPosition", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                if (self instanceof EntityLib entityLib) {
                    Entity ent = entityLib.getEntity();

                    return new PointLib(ent.getPosition());
                } else {
                    return LuaValue.NIL;
                }
            }
        });

        ENTITY_METATABLE.rawset("GetYaw", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                if (self instanceof EntityLib entityLib) {
                    Entity ent = entityLib.getEntity();

                    return LuaValue.valueOf(ent.getPosition().yaw());
                } else {
                    return LuaValue.NIL;
                }
            }
        });

        ENTITY_METATABLE.rawset("GetFacing", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                if (self instanceof EntityLib entityLib) {
                    Entity ent = entityLib.getEntity();

                    return LuaValue.valueOf(ent.getPosition().facing().toString());
                } else {
                    return LuaValue.NIL;
                }
            }
        });
        ENTITY_METATABLE.rawset("GetOnGround", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                if (self instanceof EntityLib entityLib) {
                    Entity ent = entityLib.getEntity();
                    return LuaValue.valueOf(ent.isOnGround());

                } else {
                    return LuaValue.NIL;
                }
            }
        });

        ENTITY_METATABLE.rawset("Teleport", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue pos) {
                if (!(self instanceof EntityLib entityLib)) {
                    return LuaValue.NIL;
                }
                if (!(pos instanceof PositionLib position)) {
                    return LuaValue.NIL;
                }

                Entity ent = entityLib.getEntity();
                Pos newPosition = position.getPoint();

                ent.teleport(newPosition);

                return self;
            }
        });
        ENTITY_METATABLE.rawset("GetGravity", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                if (self instanceof EntityLib entityLib) {
                    Entity ent = entityLib.getEntity();
                    return LuaValue.valueOf(ent.hasNoGravity());
                } else {
                    return LuaValue.NIL;
                }
            }
        });
        ENTITY_METATABLE.rawset("SetGravity", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue gravity) {
                if (self instanceof EntityLib entityLib) {
                    Entity ent = entityLib.getEntity();
                    ent.setNoGravity(LuaErrorAssert.checkBoolean(gravity, "Entity:SetGravity", 1));
                    return self;
                } else {
                    return LuaValue.NIL;
                }
            }
        });
        ENTITY_METATABLE.rawset("GetIsInvisible", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                if (self instanceof EntityLib entityLib) {
                    Entity ent = entityLib.getEntity();
                    return LuaValue.valueOf(ent.isInvisible());
                } else {
                    return LuaValue.NIL;
                }
            }
        });
        ENTITY_METATABLE.rawset("SetInvisible", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue gravity) {
                if (self instanceof EntityLib entityLib) {
                    Entity ent = entityLib.getEntity();
                    ent.setInvisible(LuaErrorAssert.checkBoolean(gravity, "Entity:SetInvisible", 1));
                    return self;
                } else {
                    return LuaValue.NIL;
                }
            }
        });
        ENTITY_METATABLE.rawset("GetHeadRotation", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                if (self instanceof EntityLib entityLib) {
                    Entity ent = entityLib.getEntity();
                    return LuaValue.valueOf(ent.getHeadRotation());
                } else {
                    return LuaValue.NIL;
                }
            }
        });
        ENTITY_METATABLE.rawset("GetEyeHeight", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                if (self instanceof EntityLib entityLib) {
                    Entity ent = entityLib.getEntity();
                    return LuaValue.valueOf(ent.getEyeHeight());
                } else {
                    return LuaValue.NIL;
                }
            }
        });
        ENTITY_METATABLE.rawset("GetHeadRotation", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                if (self instanceof EntityLib entityLib) {
                    Entity ent = entityLib.getEntity();
                    return LuaValue.valueOf(ent.getHeadRotation());
                } else {
                    return LuaValue.NIL;
                }
            }
        });
    }



    public EntityLib(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
