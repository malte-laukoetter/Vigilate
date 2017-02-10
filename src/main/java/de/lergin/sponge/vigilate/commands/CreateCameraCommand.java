package de.lergin.sponge.vigilate.commands;

import com.google.common.collect.ImmutableMap;
import de.lergin.sponge.vigilate.Camera;
import de.lergin.sponge.vigilate.Vigilate;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class CreateCameraCommand implements CommandExecutor {
    private Vigilate plugin;

    public  CreateCameraCommand(Vigilate plugin){
        this.plugin = plugin;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String id = args.<String>getOne("id").orElseThrow(() -> new CommandException(Text.of("No Camera Id")));

        Location<World> location;

        if(src instanceof Player){
            location = args.<Location<World>>getOne("location").orElse(((Player) src).getLocation());
        }else{
            location = args.<Location<World>>getOne("location").orElseThrow(() -> new CommandException(Text.of("No Location")));
        }

        Camera camera = new Camera(location, id);

        Optional<String> name = args.getOne("name");

        if(name.isPresent()){
            camera.setName(Text.of(name.get()));
        }

        Optional<String> permission = args.getOne("permission");

        if(permission.isPresent()){
            camera.setPermission(permission.get());
        }

        plugin.getCameras().put(id, camera);
        plugin.getConfig().saveCameras();

        src.sendMessage(plugin.translations.CAMERA_CREATED, camera.templateVariables());

        if(camera.canUseCamera(src)){
            src.sendMessage(plugin.translations.CAMERA_CREATED_HAS_PERMISSIONS.apply(camera.templateVariables())
                    .onClick(TextActions.runCommand("/vigilate view " + camera.getId())).build());
        }

        return CommandResult.success();
    }
}
