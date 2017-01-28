package de.lergin.sponge.vigilate.data;

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;

import static org.spongepowered.api.data.DataQuery.of;
import static org.spongepowered.api.data.key.KeyFactory.makeSingleKey;

public class VigilateKeys {
    public static final Key<Value<String>> CAMERA = makeSingleKey(
            TypeToken.of(String.class),
            new TypeToken<Value<String>>() {},
            of("Camera"),
            "vigilate:camera",
            "Camera"
    );

    public static final Key<Value<String>> OLD_LOCATION_WORLD = makeSingleKey(
            TypeToken.of(String.class),
            new TypeToken<Value<String>>() {},
            of("OldLocationWorld"),
            "vigilate:old_location_world",
            "Old Location World"
    );

    public static final Key<Value<Double>> OLD_LOCATION_X = makeSingleKey(
            TypeToken.of(Double.class),
            new TypeToken<Value<Double>>() {},
            of("OldLocationX"),
            "vigilate:old_location_x",
            "Old Location X"
    );

    public static final Key<Value<Double>> OLD_LOCATION_Y = makeSingleKey(
            TypeToken.of(Double.class),
            new TypeToken<Value<Double>>() {},
            of("OldLocationY"),
            "vigilate:old_location_y",
            "Old Location Y"
    );

    public static final Key<Value<Double>> OLD_LOCATION_Z = makeSingleKey(
            TypeToken.of(Double.class),
            new TypeToken<Value<Double>>() {},
            of("OldLocationZ"),
            "vigilate:old_location_z",
            "Old Location Z"
    );

    public static final Key<Value<GameMode>> OLD_GAME_MODE = makeSingleKey(
            TypeToken.of(GameMode.class),
            new TypeToken<Value<GameMode>>() {},
            of("OldGameMode"),
            "vigilate:old_game_mode",
            "Old Gamemode"
    );

    public static final Key<Value<Boolean>> OLD_IS_FLYING = makeSingleKey(
            TypeToken.of(Boolean.class),
            new TypeToken<Value<Boolean>>() {},
            of("OldIsFlying"),
            "vigilate:old_is_flying",
            "Vigilate: Old Is Flying"
    );

    public static final Key<Value<Boolean>> OLD_AFFECTS_SPAWNING = makeSingleKey(
            TypeToken.of(Boolean.class),
            new TypeToken<Value<Boolean>>() {},
            of("OldAffectsSpawning"),
            "vigilate:old_affects_spawning",
            "Vigilate: Old Affects Spawning"
    );

    public static final Key<Value<Boolean>> OLD_VANISH = makeSingleKey(
            TypeToken.of(Boolean.class),
            new TypeToken<Value<Boolean>>() {},
            of("OldVanish"),
            "vigilate:old_vanish",
            "Vigilate: Old Vanish"
    );

    public static final Key<Value<Boolean>> OLD_VANISH_PREVENTS_TARGETING = makeSingleKey(
            TypeToken.of(Boolean.class),
            new TypeToken<Value<Boolean>>() {},
            of("OldVanishPreventsTargeting"),
            "vigilate:old_vanish_prevents_targeting",
            "Vigilate: Old Vanish Prevents Targeting"
    );

    public static final Key<Value<Boolean>> OLD_VANISH_IGNORES_COLLISION = makeSingleKey(
            TypeToken.of(Boolean.class),
            new TypeToken<Value<Boolean>>() {},
            of("OldVanishIgnoresCollision"),
            "vigilate:old_vanish_ignores_collision",
            "Vigilate: Old Vanish Ignores Collision"
    );

    public static final Key<Value<Double>> OLD_FLYING_SPEED = makeSingleKey(
            TypeToken.of(Double.class),
            new TypeToken<Value<Double>>() {},
            of("OldFlyingSpeed"),
            "vigilate:old_flying_speed",
            "Vigilate: Old Flying Speed"
    );
}
