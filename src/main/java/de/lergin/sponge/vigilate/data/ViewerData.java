package de.lergin.sponge.vigilate.data;

import com.google.common.base.Preconditions;
import org.spongepowered.api.CatalogTypes;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;

import java.util.*;

public class ViewerData extends AbstractData<ViewerData, ImmutableViewerDataManipulator> {
    private String camera = "";
    private String world = "world";
    private Double locX = 0.0;
    private Double locY = 0.0;
    private Double locZ = 0.0;
    private GameMode gameMode = GameModes.NOT_SET;
    private Boolean isFlying = false;
    private Boolean affectsSpawning = true;
    private Boolean vanish = false;
    private Boolean vanishPreventsTargeting = false;
    private Boolean vanishIgnoresCollision = false;
    private Double flyingSpeed = 0.02;

    protected ViewerData(String camera, String world, Double locX, Double locY, Double locZ, GameMode gameMode, Boolean isFlying, Boolean affectsSpawning, Boolean vanish,
                         Boolean vanishPreventsTargeting, Boolean vanishIgnoresCollision, Double flyingSpeed) {
        this.camera = camera;
        this.world = world;
        this.locX = locX;
        this.locY = locY;
        this.locZ = locZ;
        this.gameMode = gameMode;
        this.isFlying = isFlying;
        this.affectsSpawning = affectsSpawning;
        this.vanish = vanish;
        this.vanishPreventsTargeting = vanishPreventsTargeting;
        this.vanishIgnoresCollision = vanishIgnoresCollision;
        this.flyingSpeed = flyingSpeed;

        registerGettersAndSetters();
    }

    public Value<String> world() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_LOCATION_WORLD, this.world);
    }

    public Value<Double> locX() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_LOCATION_X, this.locX);
    }

    public Value<Double> locY() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_LOCATION_Y, this.locY);
    }

    public Value<Double> locZ() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_LOCATION_Z, this.locZ);
    }

    public Value<String> camera() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.CAMERA, this.camera);
    }

    public Value<GameMode> gameMode() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_GAME_MODE, this.gameMode);
    }

    public Value<Boolean> isFlying() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_IS_FLYING, this.isFlying);
    }

    public Value<Boolean> affectsSpawning() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_AFFECTS_SPAWNING, this.affectsSpawning);
    }

    public Value<Boolean> vanish() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_VANISH, this.vanish);
    }

    public Value<Boolean> vanishPreventsTargeting() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_VANISH_PREVENTS_TARGETING, this.vanishPreventsTargeting);
    }

    public Value<Boolean> vanishIgnoresCollision() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_VANISH_IGNORES_COLLISION, this.vanishIgnoresCollision);
    }

    public Value<Double> flyingSpeed() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_FLYING_SPEED, this.flyingSpeed);
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(VigilateKeys.CAMERA, () -> this.camera);
        registerFieldSetter(VigilateKeys.CAMERA, value -> this.camera = value);
        registerKeyValue(VigilateKeys.CAMERA, this::camera);

        registerFieldGetter(VigilateKeys.OLD_LOCATION_WORLD, () -> this.world);
        registerFieldSetter(VigilateKeys.OLD_LOCATION_WORLD, value -> this.world = value);
        registerKeyValue(VigilateKeys.OLD_LOCATION_WORLD, this::world);

        registerFieldGetter(VigilateKeys.OLD_LOCATION_WORLD, () -> this.world);
        registerFieldSetter(VigilateKeys.OLD_LOCATION_WORLD, value -> this.world = value);
        registerKeyValue(VigilateKeys.OLD_LOCATION_WORLD, this::world);

        registerFieldGetter(VigilateKeys.OLD_LOCATION_X, () -> this.locX);
        registerFieldSetter(VigilateKeys.OLD_LOCATION_X, value -> this.locX = value);
        registerKeyValue(VigilateKeys.OLD_LOCATION_X, this::locX);

        registerFieldGetter(VigilateKeys.OLD_LOCATION_Y, () -> this.locY);
        registerFieldSetter(VigilateKeys.OLD_LOCATION_Y, value -> this.locY = value);
        registerKeyValue(VigilateKeys.OLD_LOCATION_Y, this::locY);

        registerFieldGetter(VigilateKeys.OLD_LOCATION_Z, () -> this.locZ);
        registerFieldSetter(VigilateKeys.OLD_LOCATION_Z, value -> this.locZ = value);
        registerKeyValue(VigilateKeys.OLD_LOCATION_Z, this::locZ);

        registerFieldGetter(VigilateKeys.OLD_GAME_MODE, () -> this.gameMode);
        registerFieldSetter(VigilateKeys.OLD_GAME_MODE, value -> this.gameMode = value);
        registerKeyValue(VigilateKeys.OLD_GAME_MODE, this::gameMode);

        registerFieldGetter(VigilateKeys.OLD_IS_FLYING, () -> this.isFlying);
        registerFieldSetter(VigilateKeys.OLD_IS_FLYING, value -> this.isFlying = value);
        registerKeyValue(VigilateKeys.OLD_IS_FLYING, this::isFlying);

        registerFieldGetter(VigilateKeys.OLD_AFFECTS_SPAWNING, () -> this.affectsSpawning);
        registerFieldSetter(VigilateKeys.OLD_AFFECTS_SPAWNING, value -> this.affectsSpawning = value);
        registerKeyValue(VigilateKeys.OLD_AFFECTS_SPAWNING, this::affectsSpawning);

        registerFieldGetter(VigilateKeys.OLD_VANISH, () -> this.vanish);
        registerFieldSetter(VigilateKeys.OLD_VANISH, value -> this.vanish = value);
        registerKeyValue(VigilateKeys.OLD_VANISH, this::vanish);

        registerFieldGetter(VigilateKeys.OLD_VANISH_PREVENTS_TARGETING, () -> this.vanishPreventsTargeting);
        registerFieldSetter(VigilateKeys.OLD_VANISH_PREVENTS_TARGETING, value -> this.vanishPreventsTargeting = value);
        registerKeyValue(VigilateKeys.OLD_VANISH_PREVENTS_TARGETING, this::vanishPreventsTargeting);

        registerFieldGetter(VigilateKeys.OLD_VANISH_IGNORES_COLLISION, () -> this.vanishIgnoresCollision);
        registerFieldSetter(VigilateKeys.OLD_VANISH_IGNORES_COLLISION, value -> this.vanishIgnoresCollision = value);
        registerKeyValue(VigilateKeys.OLD_VANISH_IGNORES_COLLISION, this::vanishIgnoresCollision);

        registerFieldGetter(VigilateKeys.OLD_FLYING_SPEED, () -> this.flyingSpeed);
        registerFieldSetter(VigilateKeys.OLD_FLYING_SPEED, value -> this.flyingSpeed = value);
        registerKeyValue(VigilateKeys.OLD_FLYING_SPEED, this::flyingSpeed);
    }

    @Override
    public Optional<ViewerData> fill(DataHolder dataHolder, MergeFunction mergeFunction) {
        ViewerData jobDataManipulator = Preconditions.checkNotNull(mergeFunction).merge(copy(),
                dataHolder.get(ViewerData.class).orElse(copy()));

        return Optional.of(jobDataManipulator);
    }

    @Override
    public Optional<ViewerData> from(DataContainer dataContainer) {
        //todo loc
        this.camera = dataContainer.getString(VigilateKeys.CAMERA.getQuery()).orElse("");
        this.gameMode = dataContainer.getCatalogType(VigilateKeys.OLD_GAME_MODE.getQuery(), CatalogTypes.GAME_MODE).orElse(GameModes.NOT_SET);
        this.isFlying = dataContainer.getBoolean(VigilateKeys.OLD_IS_FLYING.getQuery()).orElse(false);
        this.affectsSpawning = dataContainer.getBoolean(VigilateKeys.OLD_AFFECTS_SPAWNING.getQuery()).orElse(true);
        this.vanish = dataContainer.getBoolean(VigilateKeys.OLD_VANISH.getQuery()).orElse(false);
        this.vanishPreventsTargeting = dataContainer.getBoolean(VigilateKeys.OLD_VANISH_PREVENTS_TARGETING.getQuery()).orElse(false);
        this.vanishIgnoresCollision = dataContainer.getBoolean(VigilateKeys.OLD_VANISH_IGNORES_COLLISION.getQuery()).orElse(false);
        this.flyingSpeed = dataContainer.getDouble(VigilateKeys.OLD_FLYING_SPEED.getQuery()).orElse(0.02);
        return Optional.of(this);
    }

    @Override
    public ViewerData copy() {
        return new ViewerData(camera, world, locX, locY, locZ, gameMode, isFlying, affectsSpawning, vanish, vanishPreventsTargeting, vanishIgnoresCollision, flyingSpeed);
    }

    @Override
    public ImmutableViewerDataManipulator asImmutable() {
        return new ImmutableViewerDataManipulator(camera, world, locX, locY, locZ, gameMode, isFlying, affectsSpawning, vanish, vanishPreventsTargeting, vanishIgnoresCollision, flyingSpeed);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(VigilateKeys.CAMERA, this.camera)
                .set(VigilateKeys.OLD_LOCATION_X, this.locX)
                .set(VigilateKeys.OLD_LOCATION_Y, this.locY)
                .set(VigilateKeys.OLD_LOCATION_Z, this.locZ)
                .set(VigilateKeys.OLD_LOCATION_WORLD, this.world)
                .set(VigilateKeys.OLD_GAME_MODE, this.gameMode)
                .set(VigilateKeys.OLD_IS_FLYING, this.isFlying)
                .set(VigilateKeys.OLD_AFFECTS_SPAWNING, this.affectsSpawning)
                .set(VigilateKeys.OLD_VANISH, this.vanish)
                .set(VigilateKeys.OLD_VANISH_PREVENTS_TARGETING, this.vanishPreventsTargeting)
                .set(VigilateKeys.OLD_VANISH_IGNORES_COLLISION, this.vanishIgnoresCollision)
                .set(VigilateKeys.OLD_FLYING_SPEED, this.flyingSpeed);
    }
}
