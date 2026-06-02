package org.example.sandbox.events;

import java.util.concurrent.ConcurrentHashMap;

import org.luaj.vm2.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.item.PickupItemEvent;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.trait.BlockEvent;
import net.minestom.server.event.trait.EntityEvent;
import net.minestom.server.event.trait.InventoryEvent;
import net.minestom.server.event.trait.ItemEvent;
import net.minestom.server.event.trait.PlayerEvent;

public class EventHandler {
    private static final Logger logger = LoggerFactory.getLogger("LuaCraft testLog");

    private EventNode<PlayerEvent> playerNode = EventNode.type("luacraft_playerNode", EventFilter.PLAYER);
    private EventNode<EntityEvent> entityNode = EventNode.type("luacraft_entityNode", EventFilter.ENTITY);
    private EventNode<InventoryEvent> inventoryNode = EventNode.type("luacraft_inventoryNode", EventFilter.INVENTORY);
    private EventNode<ItemEvent> itemNode = EventNode.type("luacraft_itemNode", EventFilter.ITEM);
    private EventNode<BlockEvent> blockNode = EventNode.type("luacraft_blockNode", EventFilter.BLOCK);

    public void initNodes() {
        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();

        handler.addChild(playerNode);
        handler.addChild(entityNode);
        handler.addChild(inventoryNode);
        handler.addChild(itemNode);
        handler.addChild(blockNode);
    }

    public void initListeners(ConcurrentHashMap<String, Globals> allGlobals) {
        // Player Listeners
        playerNode.addListener(PlayerBlockBreakEvent.class, event -> {
            OnPlayerBlockBreak.handle(event, allGlobals);
        });
        playerNode.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            OnAsyncPlayerConfiguration.handle(event, allGlobals);
        });
        entityNode.addListener(PickupItemEvent.class, event -> {
            OnPickupItem.handle(event, allGlobals);
        });
    }
}