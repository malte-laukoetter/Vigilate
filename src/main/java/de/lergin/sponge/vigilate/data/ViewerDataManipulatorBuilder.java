package de.lergin.sponge.vigilate.data;

import org.spongepowered.api.CatalogTypes;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;

import java.util.*;

public class ViewerDataManipulatorBuilder extends AbstractDataBuilder<ViewerData> implements DataManipulatorBuilder<ViewerData, ImmutableViewerDataManipulator> {
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

    public ViewerDataManipulatorBuilder() {
        super(ViewerData.class, 1);
    }

    @Override
    public ViewerData create() {
        return new ViewerData(camera, world, locX, locY, locZ, gameMode, isFlying, affectsSpawning, vanish, vanishPreventsTargeting, vanishIgnoresCollision, flyingSpeed);
    }

    public ViewerDataManipulatorBuilder camera(String camera) {
        this.camera = camera;
        return this;
    }

    public ViewerDataManipulatorBuilder gameMode(GameMode gameMode) {
        this.gameMode = gameMode;
        return this;
    }

    public ViewerDataManipulatorBuilder isFlying(Boolean isFlying) {
        this.isFlying = isFlying;
        return this;
    }

    public ViewerDataManipulatorBuilder affectsSpawning(Boolean affectsSpawning) {
        this.affectsSpawning = affectsSpawning;
        return this;
    }

    public ViewerDataManipulatorBuilder vanish(Boolean vanish) {
        this.vanish = vanish;
        return this;
    }

    public ViewerDataManipulatorBuilder vanishPreventsTargeting(Boolean vanishPreventsTargeting) {
        this.vanishPreventsTargeting = vanishPreventsTargeting;
        return this;
    }

    public ViewerDataManipulatorBuilder vanishIgnoresCollision(Boolean vanishIgnoresCollision) {
        this.vanishIgnoresCollision = vanishIgnoresCollision;
        return this;
    }

    public ViewerDataManipulatorBuilder flyingSpeed(Double flyingSpeed) {
        this.flyingSpeed = flyingSpeed;
        return this;
    }

    @Override
    public Optional<ViewerData> createFrom(DataHolder dataHolder) {
        return Optional.of(dataHolder.get(ViewerData.class).orElseGet(
                () -> new ViewerData(camera, world, locX, locY, locZ, gameMode, isFlying, affectsSpawning, vanish, vanishPreventsTargeting, vanishIgnoresCollision, flyingSpeed)
        ));
    }

    @Override
    protected Optional<ViewerData> buildContent(DataView dataView) {

        return Optional.of(new ViewerData(
                dataView.getString(VigilateKeys.CAMERA.getQuery()).orElse(""),
                dataView.getString(VigilateKeys.OLD_LOCATION_WORLD.getQuery()).orElse("world"),
                dataView.getDouble(VigilateKeys.OLD_LOCATION_X.getQuery()).orElse(0.0),
                dataView.getDouble(VigilateKeys.OLD_LOCATION_Y.getQuery()).orElse(0.0),
                dataView.getDouble(VigilateKeys.OLD_LOCATION_Z.getQuery()).orElse(0.0),
                dataView.getCatalogType(VigilateKeys.OLD_GAME_MODE.getQuery(), CatalogTypes.GAME_MODE).orElse(GameModes.NOT_SET),
                dataView.getBoolean(VigilateKeys.OLD_IS_FLYING.getQuery()).orElse(false),
                dataView.getBoolean(VigilateKeys.OLD_AFFECTS_SPAWNING.getQuery()).orElse(true),
                dataView.getBoolean(VigilateKeys.OLD_VANISH.getQuery()).orElse(false),
                dataView.getBoolean(VigilateKeys.OLD_VANISH_PREVENTS_TARGETING.getQuery()).orElse(false),
                dataView.getBoolean(VigilateKeys.OLD_VANISH_IGNORES_COLLISION.getQuery()).orElse(false),
                dataView.getDouble(VigilateKeys.OLD_FLYING_SPEED.getQuery()).orElse(0.02)
        ));
    }
}
