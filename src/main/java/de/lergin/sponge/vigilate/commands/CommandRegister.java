package de.lergin.sponge.vigilate.commands;

import de.lergin.sponge.vigilate.Vigilate;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CommandRegister {
    public static void registerCommands(Vigilate plugin){
        CommandSpec createCameraCommand = CommandSpec.builder()
                .description(plugin.translations.COMMAND_CREATE)
                .permission("vigilate.create")
                .arguments(
                        GenericArguments.string(Text.of("id")),
                        GenericArguments.string(Text.of("name")),
                        GenericArguments.optional(
                                GenericArguments.location(Text.of("location"))
                        ),
                        GenericArguments.optional(
                                GenericArguments.string(Text.of("permission"))
                        )
                )
                .executor(new CreateCameraCommand(plugin))
                .build();

        CommandSpec deleteCameraCommand = CommandSpec.builder()
                .description(plugin.translations.COMMAND_DELETE)
                .permission("vigilate.delete")
                .arguments(
                        new CameraCommandArgument(Text.of("camera"), plugin)
                )
                .executor(new DeleteCameraCommand(plugin))
                .build();

        CommandSpec viewCameraCommand = CommandSpec.builder()
                .description(plugin.translations.COMMAND_VIEW)
                .permission("vigilate.view")
                .arguments(
                        new CameraCommandArgument(Text.of("camera"), plugin)
                )
                .executor(new ViewCameraCommand(plugin))
                .build();

        CommandSpec infoCameraCommand = CommandSpec.builder()
                .description(plugin.translations.COMMAND_INFO)
                .permission("vigilate.info")
                .arguments(
                        new CameraCommandArgument(Text.of("camera"), plugin)
                )
                .executor(new InfoCameraCommand(plugin))
                .build();

        CommandSpec listCamerasCommand = CommandSpec.builder()
                .description(plugin.translations.COMMAND_LIST)
                .permission("vigilate.list")
                .executor(new ListCamerasCommand(plugin))
                .build();

        CommandSpec reloadCommand = CommandSpec.builder()
                .description(plugin.translations.COMMAND_RELOAD)
                .permission("vigilate.reload")
                .executor(new ReloadCommand(plugin))
                .build();

        CommandSpec vigilateCommand = CommandSpec.builder()
                .child(viewCameraCommand, "view")
                .child(createCameraCommand, "create")
                .child(deleteCameraCommand, "delete")
                .child(listCamerasCommand, "list")
                .child(infoCameraCommand, "info")
                .child(reloadCommand, "reload")
                .build();

        Sponge.getGame().getCommandManager().register(plugin, vigilateCommand, "camera", "vigilate");
    }
}
