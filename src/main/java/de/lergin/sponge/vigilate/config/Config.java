package de.lergin.sponge.vigilate.config;

import com.google.common.reflect.TypeToken;
import de.lergin.sponge.vigilate.Camera;
import de.lergin.sponge.vigilate.Vigilate;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.asset.Asset;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class Config {
    private Vigilate plugin;
    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private Path path;
    private Logger logger;
    private Game game;
    public ConfigurationNode config;

    public Config(Vigilate plugin, ConfigurationLoader<CommentedConfigurationNode> loader, Path path){
        this.plugin = plugin;
        this.loader = loader;
        this.path = path;
        this.logger = plugin.getLogger();
        this.game = plugin.getGame();
    }

    /**
     * saves the configuration of the plugin
     */
    public void save() {
        try {
            config.getNode("translations").setValue(TypeToken.of(TranslationConfig.class), plugin.translations);
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }

        try {
            loader.save(config);
            plugin.getLogger().warn("Saved the config!");
        } catch(IOException e) {
            logger.warn("Could not save the config!");
        }
    }

    public void saveCameras(){
        config.removeChild("cameras");

        plugin.getCameras().values().forEach((cam)->{
            try {
                config.getNode("cameras").getAppendedNode().setValue(TypeToken.of(Camera.class), cam);
            } catch (ObjectMappingException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * loads the configuration of the plugin
     * @throws IOException
     * @throws ObjectMappingException
     */
    public void load() throws IOException, ObjectMappingException {
        if (!Files.exists(path)) {
            Optional<Asset> conf = game.getAssetManager().getAsset(plugin, "config.conf");
            if(conf.isPresent()){
                try {
                    conf.get().copyToFile(path);
                } catch (IOException ex) {
                    logger.error("Could not copy the config file!");
                    try {
                        throw ex;
                    } finally {
                        useDefault();
                    }
                }
            }else{
                logger.error("Could not load the config from the assets manager!");
                useDefault();
            }
        }

        try {
            config = loader.load();
        } catch (IOException ex) {
            logger.error("Could not load the config file!");
            try {
                throw ex;
            } finally {
                useDefault();
            }
        }
    }

    public void loadTranslations() {
        try {
            TranslationConfig trans = config.getNode("translations").getValue(TypeToken.of(TranslationConfig.class));

            if (trans != null) {
                plugin.translations = trans;
                logger.info("Loaded Translations");
            }else {
                plugin.translations = new TranslationConfig();
            }
        } catch (ObjectMappingException e) {
            logger.warn("Couldn't load Translations: " + e.getMessage());
            plugin.translations = new TranslationConfig();
        }
    }

    /**
     * loads the cameras from the config
     *
     * may only be called after the worlds of the server have been loaded
     */
    public void loadCameras() {
        plugin.getCameras().clear();

        // yes i know it is bad to register it every time the config is loaded but i doesn't seem to work otherwise...
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Location.class), new LocationSerializer());

        config.getNode("cameras").getChildrenList().forEach((node) -> {
            try {
                Camera cam = node.getValue(TypeToken.of(Camera.class));
                cam.setLocation(node.getNode("location").getValue(TypeToken.of(Location.class)));
                plugin.getCameras().put(cam.getId(), cam);
            } catch (ObjectMappingException e) {
                System.out.println(TypeSerializers.getDefaultSerializers().get(TypeToken.of(Location.class)));
                logger.warn("Couldn't load Camera: " + e.getMessage());
            }
        });

        logger.info(String.format("Loaded %d Cameras", plugin.getCameras().size()));
    }

    /**
     * reloads the configuration of the server
     *
     * may only be called after the worlds have been loaded
     *
     * @throws IOException
     * @throws ObjectMappingException
     */
    public void reload() throws IOException, ObjectMappingException {
        load();
        loadCameras();
        save();
    }

    private void useDefault() throws IOException, ObjectMappingException {
        try {
            config = loadDefault();
        } catch (IOException ex) {
            logger.error("Could not load the embedded default config! Disabling plugin.");
            game.getEventManager().unregisterPluginListeners(plugin);
            throw ex;
        }
    }

    private ConfigurationNode loadDefault() throws IOException {
        return HoconConfigurationLoader.builder()
                .setURL(game.getAssetManager().getAsset(plugin, "config.conf").get().getUrl()).build()
                .load(loader.getDefaultOptions());
    }
}
