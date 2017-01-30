package de.lergin.sponge.vigilate;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import de.lergin.sponge.vigilate.commands.CommandRegister;
import de.lergin.sponge.vigilate.data.ImmutableViewerDataManipulator;
import de.lergin.sponge.vigilate.data.ViewerData;
import de.lergin.sponge.vigilate.data.ViewerDataManipulatorBuilder;
import de.lergin.sponge.vigilate.listeners.*;
import de.lergin.sponge.vigilate.utils.LocationSerializer;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.asset.Asset;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.*;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.world.Location;

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

    public Logger getLogger() {
        return logger;
    }

    @Inject
    private PluginContainer container;

    @Inject
    private
    Game game;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private
    Path path;
    @Inject
    @DefaultConfig(sharedRoot = false)
    private
    ConfigurationLoader<CommentedConfigurationNode> loader;

    private ConfigurationNode config;

    @Listener
    public void onGamePreInitialization(GamePreInitializationEvent event) throws IOException, ObjectMappingException {
        Sponge.getDataManager().register(ViewerData.class, ImmutableViewerDataManipulator.class,
                new ViewerDataManipulatorBuilder());

        Asset conf = game.getAssetManager().getAsset(this, "config.conf").get();
        if (!Files.exists(path)) {
            try {
                conf.copyToFile(path);
            } catch (IOException ex) {
                logger.error("Could not copy the config file!");
                try {
                    throw ex;
                } finally {
                    useDefaultConfig();
                }
            }
        }

        try {
            config = loader.load();
        } catch (IOException ex) {
            logger.error("Could not load the config file!");
            try {
                throw ex;
            } finally {
                useDefaultConfig();
            }
        }
    }

    @Listener
    public void onGameStartingServer(GameStartingServerEvent event) {
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Location.class), new LocationSerializer());

        config.getNode("cameras").getChildrenList().forEach((node) -> {
            try {
                Camera cam = node.getValue(TypeToken.of(Camera.class));
                cameras.put(cam.getId(), cam);
            } catch (ObjectMappingException e) {
                logger.warn("Couldn't load Camera: " + e.getMessage());
            }
        });

        logger.info(String.format("Loaded %d Cameras", cameras.size()));

        CommandRegister.registerCommands(this);
    }

    @Listener
    public void onGameInitialization(GameInitializationEvent event) {
        instance = this;
        pluginContainer = container;

        Sponge.getEventManager().registerListeners(this, new ClickListener());
        Sponge.getEventManager().registerListeners(this, new DropListener());
        Sponge.getEventManager().registerListeners(this, new PickUpListener());
        Sponge.getEventManager().registerListeners(this, new InteractInventoryListener());
        Sponge.getEventManager().registerListeners(this, new MoveListener());
    }

    @Listener
    public void onGameServerStopping(GameStoppingServerEvent event) {
        config.removeChild("cameras");

        getCameras().values().forEach((cam)->{
            try {
                config.getNode("cameras").getAppendedNode().setValue(TypeToken.of(Camera.class), cam);
            } catch (ObjectMappingException e) {
                e.printStackTrace();
            }
        });

        try {
            loader.save(config);
            logger.warn("Saved the config!");
        } catch(IOException e) {
            logger.warn("Could not save the config!");
        }
    }

    private void useDefaultConfig() throws IOException, ObjectMappingException {
        try {
            config = loadDefaultConfig();
        } catch (IOException ex) {
            logger.error("Could not load the embedded default config! Disabling plugin.");
            game.getEventManager().unregisterPluginListeners(this);
            throw ex;
        }
    }

    private ConfigurationNode loadDefaultConfig() throws IOException {
        return HoconConfigurationLoader.builder()
                .setURL(game.getAssetManager().getAsset(this, "config.conf").get().getUrl()).build()
                .load(loader.getDefaultOptions());
    }
}
