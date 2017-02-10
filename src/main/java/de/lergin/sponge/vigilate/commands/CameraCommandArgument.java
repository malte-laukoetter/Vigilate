package de.lergin.sponge.vigilate.commands;

import com.google.common.collect.ImmutableMap;
import de.lergin.sponge.vigilate.Camera;
import de.lergin.sponge.vigilate.Vigilate;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;
import java.util.*;

public class CameraCommandArgument extends CommandElement {
    private Vigilate plugin;

    public CameraCommandArgument(@Nullable Text key, Vigilate plugin) {
        super(key);

        this.plugin = plugin;
    }

    @Nullable
    @Override
    protected Object parseValue(CommandSource src, CommandArgs args) throws ArgumentParseException {
        String arg = args.next().toLowerCase();

        if(plugin.getCameras().containsKey(arg)){
            Camera cam = plugin.getCameras().get(arg);

            if(cam.canUseCamera(src)){
                return cam;
            }
        }

        throw args.createError(
                plugin.translations.UNKNOWN_CAMERA_ID.apply(
                        ImmutableMap.of("camera.id", arg)
                ).build()
        );
    }

    @Override
    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        Map<String, Camera> cams = new HashMap<>(plugin.getCameras());
        Optional<String> arg = args.nextIfPresent();

        if(arg.isPresent()){
            cams.entrySet().removeIf((cam) ->
                    !cam.getKey().startsWith(arg.get().toLowerCase()) || !cam.getValue().canUseCamera(src));
        }

        return new ArrayList<>(cams.keySet());
    }
}
