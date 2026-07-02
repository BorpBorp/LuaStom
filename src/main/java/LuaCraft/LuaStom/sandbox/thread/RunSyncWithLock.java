package LuaCraft.LuaStom.sandbox.thread;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;

import LuaCraft.LuaStom.LuaErrorAssert;

public class RunSyncWithLock extends ThreeArgFunction {

    @Override
    public LuaValue call(LuaValue self, LuaValue lock, LuaValue function) {
        synchronized (lock) {
            LuaErrorAssert.checkFunction(function, "NextTick", 1).call();
        }

        return LuaValue.NIL;
    }
}
