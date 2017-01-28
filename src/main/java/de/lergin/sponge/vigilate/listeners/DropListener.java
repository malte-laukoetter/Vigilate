package de.lergin.sponge.vigilate.listeners;

import de.lergin.sponge.vigilate.Vigilate;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.item.inventory.DropItemEvent;

public class DropListener {
    @Listener
    public void onDropItem(DropItemEvent event, @Root Player player) {
        Vigilate.cam.endViewCamera(player);
        event.setCancelled(true);
    }
}
