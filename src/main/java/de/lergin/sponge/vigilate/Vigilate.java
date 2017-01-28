package de.lergin.sponge.vigilate;

import com.google.inject.Inject;
import de.lergin.sponge.vigilate.commands.CommandRegister;
import de.lergin.sponge.vigilate.data.ImmutableViewerDataManipulator;
import de.lergin.sponge.vigilate.data.ViewerData;
import de.lergin.sponge.vigilate.data.ViewerDataManipulatorBuilder;
import de.lergin.sponge.vigilate.listeners.DropListener;
import de.lergin.sponge.vigilate.listeners.PickUpListener;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.world.Location;

@Plugin(
        id = "vigilate",
        name = "Vigilate",
        version = "1.0-SNAPSHOT",
        description = "A security camera plugin",
        authors = {
                "Lergin"
        }
)
public class Vigilate {
    private static Vigilate instance;

    public static Camera cam;

    public static Vigilate getInstance() {
        return instance;
    }

    private static PluginContainer pluginContainer;

    public static PluginContainer getPluginContainer() {
        return pluginContainer;
    }

    @Inject
    private Logger logger;

    @Inject
    private PluginContainer container;

    @Listener
    public void onServerStarting(GamePreInitializationEvent event) {
        Sponge.getDataManager().register(ViewerData.class, ImmutableViewerDataManipulator.class,
                new ViewerDataManipulatorBuilder());

        CommandRegister.registerCommands(this);
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        instance = this;
        pluginContainer = container;

        Sponge.getEventManager().registerListeners(this, new DropListener());
        Sponge.getEventManager().registerListeners(this, new PickUpListener());
    }

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event, @Getter("getTargetEntity") Player player) {
        cam = new Camera(new Location<>(player.getWorld(), 255, 100, 255), "test");

        cam.placeInWorld();
        cam.viewCamera(player);
    }

}
