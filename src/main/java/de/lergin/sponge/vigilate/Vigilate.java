package de.lergin.sponge.vigilate;

import com.google.inject.Inject;
import de.lergin.sponge.vigilate.bstats.Metrics;
import de.lergin.sponge.vigilate.commands.CommandRegister;
import de.lergin.sponge.vigilate.config.Config;
import de.lergin.sponge.vigilate.config.TranslationConfig;
import de.lergin.sponge.vigilate.data.ImmutableViewerDataManipulator;
import de.lergin.sponge.vigilate.data.ViewerData;
import de.lergin.sponge.vigilate.data.ViewerDataManipulatorBuilder;
import de.lergin.sponge.vigilate.listeners.*;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.*;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Plugin(
        id = "vigilate",
        name = "Vigilate",
        version = "1.0.1",
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

    public Game getGame() {
        return game;
    }

    @Inject
    private Metrics metrics;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private
    Path path;
    @Inject
    @DefaultConfig(sharedRoot = false)
    private
    ConfigurationLoader<CommentedConfigurationNode> loader;

    private Config config;

    public Config getConfig() {
        return config;
    }

    public TranslationConfig translations;

    @Listener
    public void onGamePreInitialization(GamePreInitializationEvent event) throws IOException, ObjectMappingException {
        config = new Config(this, loader, path);
        Sponge.getDataManager().register(ViewerData.class, ImmutableViewerDataManipulator.class,
                new ViewerDataManipulatorBuilder());

        config.load();
        config.loadTranslations();
    }

    @Listener
    public void onGameStartingServer(GameStartingServerEvent event) {
        config.loadCameras();

        CommandRegister.registerCommands(this);

        metrics.addCustomChart(new Metrics.SingleLineChart("cameras") {
            @Override
            public int getValue() {
                return cameras.size();
            }
        });
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
        Sponge.getEventManager().registerListeners(this, new CommandListener());
    }

    @Listener
    public void onGameServerStopping(GameStoppingServerEvent event) {
        config.save();
    }
}
