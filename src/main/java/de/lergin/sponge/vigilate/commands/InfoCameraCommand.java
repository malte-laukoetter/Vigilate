package de.lergin.sponge.vigilate.commands;

import de.lergin.sponge.vigilate.Camera;
import de.lergin.sponge.vigilate.Vigilate;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

public class InfoCameraCommand implements CommandExecutor {
    private Vigilate plugin;

    public InfoCameraCommand(Vigilate plugin){
        this.plugin = plugin;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Camera cam = args.<Camera>getOne("camera").orElseThrow(() -> new CommandException(Text.of("Unknown Camera")));

        src.sendMessage(
                Text.of("Camera Info - ", cam.getName(), Text.NEW_LINE,
                        "Id: ", cam.getId(), Text.NEW_LINE,
                        "Permission: ", cam.getPermission(), Text.NEW_LINE,
                        "World: ", cam.getLocation().getExtent().getName(), Text.NEW_LINE,
                        "Position: ", cam.getLocation().getX(),
                        "/", cam.getLocation().getY(),
                        "/", cam.getLocation().getZ())
        );

        return CommandResult.success();
    }
}
