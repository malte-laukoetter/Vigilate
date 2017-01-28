package de.lergin.sponge.vigilate.commands;

import de.lergin.sponge.vigilate.Vigilate;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CommandRegister {
    public static void registerCommands(Vigilate plugin){
        CommandSpec createCameraCommmand = CommandSpec.builder()
                .description(Text.of("Creates a new Camera"))
                //.permission("")
                .arguments(
                        GenericArguments.string(Text.of("id")),
                        GenericArguments.string(Text.of("Name")),
                        GenericArguments.optional(
                                GenericArguments.string(Text.of("Permission"))
                        ),
                        GenericArguments.optional(
                                GenericArguments.world(Text.of("World"))
                        ),
                        GenericArguments.optional(
                                GenericArguments.vector3d(Text.of("Location"))
                        )
                )
                .executor(new CreateCameraCommand())
                .build();

        Sponge.getGame().getCommandManager().register(plugin, createCameraCommmand, "camera", "vigilate");
    }
}
