package de.lergin.sponge.vigilate;

import com.google.inject.Inject;
import de.lergin.sponge.vigilate.commands.CommandRegister;
import de.lergin.sponge.vigilate.config.Config;
import de.lergin.sponge.vigilate.data.ImmutableViewerDataManipulator;
import de.lergin.sponge.vigilate.data.ViewerData;
import de.lergin.sponge.vigilate.data.ViewerDataManipulatorBuilder;
import de.lergin.sponge.vigilate.listeners.*;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.asset.Asset;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

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

    private Map<String, Camera> cameras = new HashMap<>();

    public Map<String, Camera> getCameras() {
        return cameras;
    }

    @Inject
    private Logger logger;

    @Inject
    private PluginContainer container;

    @Inject
    Game game;

    @Inject
    @DefaultConfig(sharedRoot = false)
    Path path;
    @Inject
    @DefaultConfig(sharedRoot = false)
    ConfigurationLoader<CommentedConfigurationNode> loader;
    Config config;

    @Listener
    public void onServerStarting(GamePreInitializationEvent event) {
        Sponge.getDataManager().register(ViewerData.class, ImmutableViewerDataManipulator.class,
                new ViewerDataManipulatorBuilder());

        CommandRegister.registerCommands(this);
    }

    @Listener
    public void onGamePreInitializationEvent(GamePreInitializationEvent event)
            throws IOException, ObjectMappingException {
        Asset conf = game.getAssetManager().getAsset(this, "config.conf").get();
        if (!Files.exists(path)) {
            try {
                conf.copyToFile(path);
            } catch (IOException ex) {
                logger.error("Could not copy the config file!");
                try {
                    throw ex;
                } finally {
                    mapDefault();
                }
            }
        }
        ConfigurationNode root;
        try {
            root = loader.load();
        } catch (IOException ex) {
            logger.error("Could not load the config file!");
            try {
                throw ex;
            } finally {
                mapDefault();
            }
        }
        try {
            config = root.getValue(Config.type);
        } catch (ObjectMappingException ex) {
            logger.error("Invalid config file!");
            try {
                throw ex;
            } finally {
                mapDefault();
            }
        }
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        instance = this;
        pluginContainer = container;


        Sponge.getEventManager().registerListeners(this, new ClickListener());
        Sponge.getEventManager().registerListeners(this, new DropListener());
        Sponge.getEventManager().registerListeners(this, new PickUpListener());
        Sponge.getEventManager().registerListeners(this, new InteractInventoryListener());
        Sponge.getEventManager().registerListeners(this, new MoveListener());
    }

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event, @Getter("getTargetEntity") Player player) {
       // cam = new Camera(new Location<>(player.getWorld(), 255, 70, 255), "test");
        System.out.println(config.version);

      //  cam.placeInWorld();
      //  cam.viewCamera(player);
    }


    private void mapDefault() throws IOException, ObjectMappingException {
        try {
            config = loadDefault().getValue(Config.type);
        } catch (IOException | ObjectMappingException ex) {
            logger.error("Could not load the embedded default config! Disabling plugin.");
            game.getEventManager().unregisterPluginListeners(this);
            throw ex;
        }
    }

    private ConfigurationNode loadDefault() throws IOException {
        return HoconConfigurationLoader.builder()
                .setURL(game.getAssetManager().getAsset(this, "config.conf").get().getUrl()).build()
                .load(loader.getDefaultOptions());
    }
}
