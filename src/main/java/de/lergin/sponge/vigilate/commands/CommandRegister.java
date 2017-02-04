package de.lergin.sponge.vigilate.commands;

import de.lergin.sponge.vigilate.Vigilate;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CommandRegister {
    public static void registerCommands(Vigilate plugin){
        CommandSpec createCameraCommand = CommandSpec.builder()
                .description(Text.of("Creates a new Camera"))
                .permission("vigilate.create")
                .arguments(
                        GenericArguments.string(Text.of("id")),
                        GenericArguments.string(Text.of("Name")),
                        GenericArguments.optional(
                                GenericArguments.location(Text.of("Location"))
                        ),
                        GenericArguments.optional(
                                GenericArguments.string(Text.of("Permission"))
                        )
                )
                .executor(new CreateCameraCommand(plugin))
                .build();

        CommandSpec viewCameraCommand = CommandSpec.builder()
                .description(Text.of("Views a Camera"))
                .permission("vigilate.view")
                .arguments(
                        new CameraCommandArgument(Text.of("camera"), plugin)
                )
                .executor(new ViewCameraCommand(plugin))
                .build();

        CommandSpec infoCameraCommand = CommandSpec.builder()
                .description(Text.of("Shows Infos about a Camera"))
                .permission("vigilate.info")
                .arguments(
                        new CameraCommandArgument(Text.of("camera"), plugin)
                )
                .executor(new InfoCameraCommand(plugin))
                .build();

        CommandSpec listCamerasCommand = CommandSpec.builder()
                .description(Text.of("List all Cameras"))
                .permission("vigilate.list")
                .executor(new ListCamerasCommand(plugin))
                .build();

        CommandSpec vigilateCommand = CommandSpec.builder()
                .child(viewCameraCommand, "view")
                .child(createCameraCommand, "create")
                .child(listCamerasCommand, "list")
                .child(infoCameraCommand, "info")
                .build();

        Sponge.getGame().getCommandManager().register(plugin, vigilateCommand, "camera", "vigilate");
    }
}
