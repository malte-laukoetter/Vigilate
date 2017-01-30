package de.lergin.sponge.vigilate;

import de.lergin.sponge.vigilate.data.ViewerData;
import de.lergin.sponge.vigilate.data.VigilateKeys;
import de.lergin.sponge.vigilate.data.ViewerDataManipulatorBuilder;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.world.BlockChangeFlag;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

@ConfigSerializable
public class Camera {
    @Setting(value = "location", comment = "Location of the camera")
    private Location<World> loc;
    @Setting(value = "Name", comment = "Name of the camera")
    private Text name;
    @Setting(value = "id", comment = "Id of the camera")
    private String id;
    @Setting(value = "permission", comment = "Permission needed to use the camera")
    private String permission;

    public Camera(){

    }

    public Camera(Location<World> loc, String id) {
        this.loc = loc;
        this.id = id.toLowerCase();

        Vigilate.getInstance().getCameras().put(id, this);
    }

    public Location<World> getLocation() {
        return loc;
    }

    public void setLocation(Location<World> loc) {
        this.loc = loc;
    }

    public Text getName() {
        return name;
    }

    public void setName(Text name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        Vigilate.getInstance().getCameras().remove(this.id);

        this.id = id.toLowerCase();

        Vigilate.getInstance().getCameras().put(this.id, this);
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void placeInWorld(){
        this.getLocation().setBlockType(BlockTypes.GLASS, BlockChangeFlag.NONE, Cause.of(NamedCause.source(Vigilate.getPluginContainer())));
    }

    public void viewCamera(Player player){
        if (player.supports(VigilateKeys.OLD_GAME_MODE)) {
            player.get(VigilateKeys.CAMERA).orElse("");

            if(Vigilate.getInstance().getCameras().containsKey(id)){
                Vigilate.getInstance().getCameras().get(id).endViewCamera(player);
            }
        }

        player.offer(new ViewerDataManipulatorBuilder().create());

        player.getValue(Keys.GAME_MODE).ifPresent(
                (value -> player.offer(VigilateKeys.OLD_GAME_MODE, value.get()))
        );
        player.getValue(Keys.IS_FLYING).ifPresent(
                (value -> player.offer(VigilateKeys.OLD_IS_FLYING, value.get()))
        );
        player.getValue(Keys.AFFECTS_SPAWNING).ifPresent(
                (value -> player.offer(VigilateKeys.OLD_AFFECTS_SPAWNING, value.get()))
        );
        player.getValue(Keys.VANISH).ifPresent(
                (value -> player.offer(VigilateKeys.OLD_VANISH, value.get()))
        );
        player.getValue(Keys.VANISH_PREVENTS_TARGETING).ifPresent(
                (value -> player.offer(VigilateKeys.OLD_VANISH_PREVENTS_TARGETING, value.get()))
        );
        player.getValue(Keys.VANISH_IGNORES_COLLISION).ifPresent(
                (value -> player.offer(VigilateKeys.OLD_VANISH_IGNORES_COLLISION, value.get()))
        );
        player.getValue(Keys.FLYING_SPEED).ifPresent(
                (value -> player.offer(VigilateKeys.OLD_FLYING_SPEED, value.get()))
        );

        player.offer(VigilateKeys.OLD_LOCATION_WORLD, player.getLocation().getExtent().getName());
        player.offer(VigilateKeys.OLD_LOCATION_X, player.getLocation().getX());
        player.offer(VigilateKeys.OLD_LOCATION_Y, player.getLocation().getY());
        player.offer(VigilateKeys.OLD_LOCATION_Z, player.getLocation().getZ());

        player.offer(Keys.GAME_MODE, GameModes.CREATIVE);
        player.offer(Keys.AFFECTS_SPAWNING, false);
        player.offer(Keys.VANISH, true);
        player.offer(Keys.VANISH_PREVENTS_TARGETING, true);
        player.offer(Keys.VANISH_IGNORES_COLLISION, true);
        player.offer(Keys.FLYING_SPEED, 0.0);
        player.offer(Keys.IS_FLYING, true);

        player.offer(VigilateKeys.CAMERA, this.getId());

        player.setLocation(this.getLocation());

        Title title = Title.builder().fadeIn(20).fadeOut(20).title(Text.EMPTY).subtitle(Text.of("Click to go back!")).stay(100000).build();
        player.sendTitle(title);
    }

    public void endViewCamera(Player player){
        Optional<String> cameraId = player.get(VigilateKeys.CAMERA);

        if (cameraId.isPresent() && !cameraId.get().equals("")) {
            Camera.resetPlayer(player);
        }
    }

    static public void resetPlayer(Player player){
        if (player.supports(VigilateKeys.OLD_GAME_MODE)) {

            player.getValue(VigilateKeys.OLD_GAME_MODE).ifPresent(
                    (value -> player.offer(Keys.GAME_MODE, value.get()))
            );
            player.getValue(VigilateKeys.OLD_IS_FLYING).ifPresent(
                    (value -> player.offer(Keys.IS_FLYING, value.get()))
            );
            player.getValue(VigilateKeys.OLD_AFFECTS_SPAWNING).ifPresent(
                    (value -> player.offer(Keys.AFFECTS_SPAWNING, value.get()))
            );
            player.getValue(VigilateKeys.OLD_VANISH).ifPresent(
                    (value -> player.offer(Keys.VANISH, value.get()))
            );
            player.getValue(VigilateKeys.OLD_VANISH_PREVENTS_TARGETING).ifPresent(
                    (value -> player.offer(Keys.VANISH_PREVENTS_TARGETING, value.get()))
            );
            player.getValue(VigilateKeys.OLD_VANISH_IGNORES_COLLISION).ifPresent(
                    (value -> player.offer(Keys.VANISH_IGNORES_COLLISION, value.get()))
            );
            player.getValue(VigilateKeys.OLD_FLYING_SPEED).ifPresent(
                    (value -> player.offer(Keys.FLYING_SPEED, value.get()))
            );

            World world = Sponge.getServer().getWorld(
                    player.get(VigilateKeys.OLD_LOCATION_WORLD).orElse("world")
            ).orElse(Sponge.getServer().getWorlds().iterator().next());

            Location<World> loc = new Location<>(
                    world,
                    player.get(VigilateKeys.OLD_LOCATION_X).orElse(0.0),
                    player.get(VigilateKeys.OLD_LOCATION_Y).orElse(0.0),
                    player.get(VigilateKeys.OLD_LOCATION_Z).orElse(0.0)
            );

            player.setLocation(loc);

            player.remove(ViewerData.class);
        }

        player.clearTitle();
        player.sendMessage(Text.of("Ended Camera view"));
    }
}
