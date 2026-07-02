package LuaCraft.LuaStom;

import java.util.concurrent.ConcurrentHashMap;

import org.luaj.vm2.Globals;

import net.minestom.server.MinecraftServer;

public class Main {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Globals> allGlobals = new ConcurrentHashMap<>();
        new ScriptGeneration();
        ScriptHandler.loadAllScripts(allGlobals, true);

        MinecraftServer.getCommandManager().register(new LuaCommand(allGlobals, ScriptHandler.getScriptsFolder()));
    }
}
