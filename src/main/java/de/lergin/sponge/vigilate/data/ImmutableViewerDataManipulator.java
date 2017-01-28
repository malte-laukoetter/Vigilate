package de.lergin.sponge.vigilate.data;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;

import java.util.*;

public class ImmutableViewerDataManipulator extends AbstractImmutableData<ImmutableViewerDataManipulator, ViewerData> {
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

    protected ImmutableViewerDataManipulator(String camera, String world, Double locX, Double locY, Double locZ, GameMode gameMode, Boolean isFlying, Boolean affectsSpawning, Boolean vanish,
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

        registerGetters();
    }

    public ImmutableValue<String> camera() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.CAMERA, this.camera).asImmutable();
    }

    public ImmutableValue<String> world() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_LOCATION_WORLD, this.world).asImmutable();
    }

    public ImmutableValue<Double> locX() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_LOCATION_X, this.locX).asImmutable();
    }

    public ImmutableValue<Double> locY() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_LOCATION_Y, this.locY).asImmutable();
    }

    public ImmutableValue<Double> locZ() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_LOCATION_Z, this.locZ).asImmutable();
    }

    public ImmutableValue<GameMode> gameMode() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_GAME_MODE, this.gameMode).asImmutable();
    }

    public ImmutableValue<Boolean> isFlying() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_IS_FLYING, this.isFlying).asImmutable();
    }

    public ImmutableValue<Boolean> affectsSpawning() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_AFFECTS_SPAWNING, this.affectsSpawning).asImmutable();
    }

    public ImmutableValue<Boolean> vanish() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_VANISH, this.vanish).asImmutable();
    }

    public ImmutableValue<Boolean> vanishPreventsTargeting() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_VANISH_PREVENTS_TARGETING, this.vanishPreventsTargeting).asImmutable();
    }

    public ImmutableValue<Boolean> vanishIgnoresCollision() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_VANISH_IGNORES_COLLISION, this.vanishIgnoresCollision).asImmutable();
    }

    public ImmutableValue<Double> flyingSpeed() {
        return Sponge.getRegistry().getValueFactory().createValue(VigilateKeys.OLD_FLYING_SPEED, this.flyingSpeed).asImmutable();
    }

    @Override
    protected void registerGetters() {
        registerFieldGetter(VigilateKeys.CAMERA, () -> this.camera);
        registerKeyValue(VigilateKeys.CAMERA, this::camera);

        registerFieldGetter(VigilateKeys.OLD_GAME_MODE, () -> this.gameMode);
        registerKeyValue(VigilateKeys.OLD_GAME_MODE, this::gameMode);

        registerFieldGetter(VigilateKeys.OLD_LOCATION_WORLD, () -> this.world);
        registerKeyValue(VigilateKeys.OLD_LOCATION_WORLD, this::world);

        registerFieldGetter(VigilateKeys.OLD_LOCATION_WORLD, () -> this.world);
        registerKeyValue(VigilateKeys.OLD_LOCATION_WORLD, this::world);

        registerFieldGetter(VigilateKeys.OLD_LOCATION_X, () -> this.locX);
        registerKeyValue(VigilateKeys.OLD_LOCATION_X, this::locX);

        registerFieldGetter(VigilateKeys.OLD_LOCATION_Y, () -> this.locY);
        registerKeyValue(VigilateKeys.OLD_LOCATION_Y, this::locY);

        registerFieldGetter(VigilateKeys.OLD_LOCATION_Z, () -> this.locZ);
        registerKeyValue(VigilateKeys.OLD_LOCATION_Z, this::locZ);

        registerFieldGetter(VigilateKeys.OLD_IS_FLYING, () -> this.isFlying);
        registerKeyValue(VigilateKeys.OLD_IS_FLYING, this::isFlying);

        registerFieldGetter(VigilateKeys.OLD_AFFECTS_SPAWNING, () -> this.affectsSpawning);
        registerKeyValue(VigilateKeys.OLD_AFFECTS_SPAWNING, this::affectsSpawning);

        registerFieldGetter(VigilateKeys.OLD_VANISH, () -> this.vanish);
        registerKeyValue(VigilateKeys.OLD_VANISH, this::vanish);

        registerFieldGetter(VigilateKeys.OLD_VANISH_PREVENTS_TARGETING, () -> this.vanishPreventsTargeting);
        registerKeyValue(VigilateKeys.OLD_VANISH_PREVENTS_TARGETING, this::vanishPreventsTargeting);

        registerFieldGetter(VigilateKeys.OLD_VANISH_IGNORES_COLLISION, () -> this.vanishIgnoresCollision);
        registerKeyValue(VigilateKeys.OLD_VANISH_IGNORES_COLLISION, this::vanishIgnoresCollision);

        registerFieldGetter(VigilateKeys.OLD_FLYING_SPEED, () -> this.flyingSpeed);
        registerKeyValue(VigilateKeys.OLD_FLYING_SPEED, this::flyingSpeed);
    }

    @Override
    public <E> Optional<ImmutableViewerDataManipulator> with(Key<? extends BaseValue<E>> key, E e) {
        if (this.supports(key)) {
            return Optional.of(asMutable().set(key, e).asImmutable());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public ViewerData asMutable() {
        return new ViewerData(camera, world, locX, locY, locZ, gameMode, isFlying, affectsSpawning, vanish, vanishPreventsTargeting, vanishIgnoresCollision, flyingSpeed);
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
