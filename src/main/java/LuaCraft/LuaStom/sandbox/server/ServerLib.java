package LuaCraft.LuaStom.sandbox.server;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import LuaCraft.LuaStom.sandbox.entities.PlayerLib;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.server.ServerTickMonitorEvent;

public class ServerLib extends LuaTable {
    private Double lastMspt = 0.0;

    public ServerLib() {
        MinecraftServer.getGlobalEventHandler().addListener(ServerTickMonitorEvent.class, event -> {
            lastMspt = event.getTickMonitor().getTickTime();
        });

        rawset("GetMSPT", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                return LuaValue.valueOf(lastMspt);
            }
        });

        rawset("GetPlayers", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                LuaTable tbl = new LuaTable();

                for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                    tbl.set(tbl.length() + 1, new PlayerLib(player));
                }

                return tbl;
            }
        });
    }
}