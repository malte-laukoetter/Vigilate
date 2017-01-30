package de.lergin.sponge.vigilate.commands;

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
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        String arg = args.next().toLowerCase();

        if(plugin.getCameras().containsKey(arg)){
            return plugin.getCameras().get(arg);
        }

        throw args.createError(Text.of("Unknown Camera Id"));
    }

    @Override
    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        List<String> camIds = new ArrayList<>(plugin.getCameras().keySet());
        Optional<String> arg = args.nextIfPresent();

        if(arg.isPresent()){
            camIds.removeIf((camId) -> !camId.startsWith(arg.get().toLowerCase()));
        }

        return camIds;
    }
}
