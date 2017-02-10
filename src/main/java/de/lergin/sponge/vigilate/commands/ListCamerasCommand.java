package de.lergin.sponge.vigilate.commands;

import de.lergin.sponge.vigilate.Camera;
import de.lergin.sponge.vigilate.Vigilate;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListCamerasCommand implements CommandExecutor {
    private Vigilate plugin;

    public  ListCamerasCommand(Vigilate plugin){
        this.plugin = plugin;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        List<Camera> cams = new ArrayList<>(plugin.getCameras().values());
        cams.removeIf((cam)-> !cam.canUseCamera(src));

        Iterable<Text> texts = cams.parallelStream().map((cam)->
                plugin.translations.CAMERA_LIST_ITEM.apply(cam.templateVariables())
                .onHover(TextActions.showText(
                        plugin.translations.CAMERA_LIST_ITEM_HOVER.apply(cam.templateVariables()).build()
                ))
                .onClick(TextActions.runCommand("/camera view " + cam.getId())).build()
        ).collect(Collectors.toList());

        PaginationList.builder()
                .title(plugin.translations.CAMERA_LIST_TITLE)
                .contents(texts)
                .sendTo(src);

        return CommandResult.success();
    }
}
