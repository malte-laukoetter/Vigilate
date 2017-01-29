package de.lergin.sponge.vigilate.listeners;

import de.lergin.sponge.vigilate.Vigilate;
import de.lergin.sponge.vigilate.data.ViewerData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.action.InteractEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.filter.data.Has;
import org.spongepowered.api.text.Text;

public class ClickListener {
    @Listener
    public void onInteract(InteractEvent event, @Root @Has(ViewerData.class) Player player) {
        Vigilate.cam.endViewCamera(player);
        event.setCancelled(true);
    }
}
