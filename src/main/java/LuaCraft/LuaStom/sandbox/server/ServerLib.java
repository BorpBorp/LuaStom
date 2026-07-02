package LuaCraft.LuaStom.sandbox.server;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import LuaCraft.LuaStom.LuaErrorAssert;
import LuaCraft.LuaStom.sandbox.entities.PlayerLib;
import net.minestom.server.Auth;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.server.ServerTickMonitorEvent;

public class ServerLib extends LuaTable {
    private Double lastMspt = 0.0;
    static MinecraftServer server;

    public ServerLib() {
        rawset("Initialize", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue type) {
                String typeValue = LuaErrorAssert.checkString(type, "Server:Initialize", 1);

                switch (typeValue) {
                    case "ONLINE": {
                        server = MinecraftServer.init(new Auth.Online());
                        break;
                    }
                    case "OFFLINE": {
                        server = MinecraftServer.init(new Auth.Offline());
                    }
                    default: {
                        return LuaValue.NIL;
                    }
                }

                MinecraftServer.getGlobalEventHandler().addListener(ServerTickMonitorEvent.class, event -> {
                    lastMspt = event.getTickMonitor().getTickTime();
                });

                return LuaValue.NIL;
            }
        });

        rawset("Start", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue ip, LuaValue port) {
                server.start(LuaErrorAssert.checkString(ip, "Server.Start", 1), LuaErrorAssert.checkInt(port, "Server.Start", 2));

                return LuaValue.NIL;
            }
        });

        rawset("GetMSPT", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return LuaValue.valueOf(lastMspt);
            }
        });

        rawset("GetPlayers", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                LuaTable tbl = new LuaTable();

                for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                    tbl.set(tbl.length() + 1, new PlayerLib(player));
                }

                return tbl;
            }
        });
    }

    public static MinecraftServer getServer() {
        return server;
    }
}