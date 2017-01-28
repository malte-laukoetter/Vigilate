package de.lergin.sponge.vigilate.listeners;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.item.inventory.ChangeInventoryEvent;

public class PickUpListener {
    @Listener
    public void onChangeInventoryPickup(ChangeInventoryEvent.Pickup event, @Root Player player) {
        event.setCancelled(true);
    }
}
