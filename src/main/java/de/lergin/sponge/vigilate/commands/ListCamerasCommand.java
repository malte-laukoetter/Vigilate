package de.lergin.sponge.vigilate.commands;

import de.lergin.sponge.vigilate.Vigilate;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

import java.util.stream.Collectors;

public class ListCamerasCommand implements CommandExecutor {
    private Vigilate plugin;

    public  ListCamerasCommand(Vigilate plugin){
        this.plugin = plugin;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Iterable<Text> cams = plugin.getCameras().values().parallelStream().map((cam)-> Text.builder().append(cam.getName())
                .onClick(TextActions.runCommand("/vigilate view " + cam.getId()))
                .onHover(TextActions.showText(Text.of(
                        cam.getLocation().getBlockX(), "/",
                        cam.getLocation().getBlockY(), "/",
                        cam.getLocation().getBlockZ())))
                .build()).collect(Collectors.toList());

        PaginationList.builder()
                .title(Text.of("Cameras"))
                .contents(cams)
                .sendTo(src);

        return CommandResult.success();
    }
}
