package de.lergin.sponge.vigilate.commands;

import de.lergin.sponge.vigilate.Vigilate;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CommandRegister {
    public static void registerCommands(Vigilate plugin){
        CommandSpec createCameraCommmand = CommandSpec.builder()
                .description(Text.of("Creates a new Camera"))
                .permission("vigilate.create")
                .arguments(
                        GenericArguments.onlyOne(
                                GenericArguments.string(Text.of("id"))
                        ),
                        GenericArguments.onlyOne(
                                GenericArguments.string(Text.of("Name"))
                        ),
                        GenericArguments.optional(
                                GenericArguments.onlyOne(
                                        GenericArguments.location(Text.of("Location"))
                                )
                        ),
                        GenericArguments.optional(
                                GenericArguments.onlyOne(
                                        GenericArguments.string(Text.of("Permission"))
                                )
                        )
                )
                .executor(new CreateCameraCommand(plugin))
                .build();

        CommandSpec viewCameraCommmand = CommandSpec.builder()
                .description(Text.of("Views a Camera"))
                .permission("vigilate.view")
                .arguments(
                        GenericArguments.onlyOne(
                                GenericArguments.string(Text.of("id"))
                        )
                )
                .executor(new ViewCameraCommand(plugin))
                .build();

        CommandSpec vigilateCommand = CommandSpec.builder()
                .child(viewCameraCommmand, "view")
                .child(createCameraCommmand, "create")
                .build();

        Sponge.getGame().getCommandManager().register(plugin, vigilateCommand, "camera", "vigilate");
    }
}
