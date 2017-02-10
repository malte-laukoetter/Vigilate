package de.lergin.sponge.vigilate.commands;

import de.lergin.sponge.vigilate.Camera;
import de.lergin.sponge.vigilate.Vigilate;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class ViewCameraCommand implements CommandExecutor {
    private Vigilate plugin;

    public  ViewCameraCommand(Vigilate plugin){
        this.plugin = plugin;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player){
            Camera cam = args.<Camera>getOne("camera").orElseThrow(() -> new CommandException(Text.of("Unknown Camera")));

            cam.viewCamera((Player) src);
        } else {
            throw new CommandException(plugin.translations.CAMERA_VIEW_NO_PLAYER);
        }

        return CommandResult.success();
    }
}
