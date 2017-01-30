package de.lergin.sponge.vigilate.listeners;

import de.lergin.sponge.vigilate.Camera;
import de.lergin.sponge.vigilate.Vigilate;
import de.lergin.sponge.vigilate.data.ViewerData;
import de.lergin.sponge.vigilate.data.VigilateKeys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.filter.data.Has;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;

import java.util.Optional;

public class InteractInventoryListener {
    @Listener
    public void onClickInventory(InteractInventoryEvent event, @Root @Has(ViewerData.class) Player player) {
        Optional<String> camId = player.get(VigilateKeys.CAMERA);

        if(camId.isPresent() && Vigilate.getInstance().getCameras().containsKey(camId.get())){
            Vigilate.getInstance().getCameras().get(camId.get()).endViewCamera(player);
        } else {
            Camera.resetPlayer(player);
        }

        event.setCancelled(true);
    }
}

