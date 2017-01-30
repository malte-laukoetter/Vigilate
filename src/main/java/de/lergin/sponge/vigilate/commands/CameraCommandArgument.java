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

    /**
     * Attempt to extract a value for this element from the given arguments.
     * This method is expected to have no side-effects for the source, meaning
     * that executing it will not change the state of the {@link CommandSource}
     * in any way.
     *
     * @param source The source to parse for
     * @param args   the arguments
     * @return The extracted value
     * @throws ArgumentParseException if unable to extract a value
     */
    @Nullable
    @Override
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        String arg = args.next().toLowerCase();

        if(plugin.getCameras().containsKey(arg)){
            return plugin.getCameras().get(arg);
        }

        throw args.createError(Text.of("Unknown Camera Id"));
    }

    /**
     * Fetch completions for command arguments.
     *
     * @param src     The source requesting tab completions
     * @param args    The arguments currently provided
     * @param context The context to store state in
     * @return Any relevant completions
     */
    @Override
    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        Set<String> camIds = new HashSet<>(plugin.getCameras().keySet());
        Optional<String> arg = args.nextIfPresent();

        if(arg.isPresent()){
            camIds.removeIf((camId) -> !camId.startsWith(arg.get().toLowerCase()));
        }

        System.out.println(plugin.getCameras());

        return new ArrayList<> (camIds);
    }
}
