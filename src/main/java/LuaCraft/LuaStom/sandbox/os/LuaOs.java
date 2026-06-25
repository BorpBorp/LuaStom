package LuaCraft.LuaStom.sandbox.os;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

public class LuaOs extends LuaTable {
    public LuaOs() {
        rawset("time", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return LuaValue.valueOf(System.currentTimeMillis() / 1000L);
            }
        });
        rawset("clock", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return LuaValue.valueOf(System.nanoTime() / 1e9);
            }
        });
        rawset("difftime", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue t1, LuaValue t2) {
                return LuaValue.valueOf(t1.todouble() - t2.todouble());
            }
        });
        rawset("date", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue format, LuaValue time) {
                LocalDateTime dateTime = time.isnil()
                        ? LocalDateTime.now()
                        : LocalDateTime.ofEpochSecond(time.tolong(), 0, ZoneOffset.UTC);

                String fmt = format.isnil() ? "%c" : format.tojstring();

                fmt = fmt.replace("%Y", "yyyy")
                        .replace("%m", "MM")
                        .replace("%d", "dd")
                        .replace("%H", "HH")
                        .replace("%M", "mm")
                        .replace("%S", "ss");

                return LuaValue.valueOf(DateTimeFormatter.ofPattern(fmt).format(dateTime));
            }
        });
    }
}
