package LuaCraft.LuaStom.sandbox.entities;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

import LuaCraft.LuaStom.LuaErrorAssert;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.damage.DamageType;

public class LivingEntityLib extends EntityLib {
    public LivingEntityLib(LivingEntity entity) {
        super(entity);

        rawset("damage", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue damage) {
                entity.damage(DamageType.GENERIC, LuaErrorAssert.checkInt(damage, "entity:damage", 1));
                return LivingEntityLib.this;
            }
        });
        
        rawset("GetHealth", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                return LuaValue.valueOf(entity.getHealth());
            }
        });

        rawset("IsPlayer", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                return LuaValue.valueOf(entity instanceof Player);
            }
        });

        rawset("AsPlayer", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                if (entity instanceof Player player) return new PlayerLib(player);

                return LuaValue.NIL;
            }
        });
    }
}