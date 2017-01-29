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
            location = args.<Location<World>>getOne("Location").orElse(((Player) src).getLocation());
        }else{
            location = args.<Location<World>>getOne("Location").orElseThrow(() -> new CommandException(Text.of("No Location")));
        }

        Camera camera = new Camera(location, id);

        Optional<String> name = args.getOne("Name");

        if(name.isPresent()){
            camera.setName(Text.of(name.get()));
        }

        Optional<String> permission = args.getOne("Permission");

        if(permission.isPresent()){
            camera.setPermission(permission.get());
        }

        plugin.getCameras().put(id, camera);

        src.sendMessage(Text.of("Successful created camera ", TextStyles.ITALIC, TextColors.GREEN, id,
                TextStyles.RESET, TextColors.RESET, "!", Text.NEW_LINE, TextStyles.UNDERLINE,
                TextActions.runCommand("/vigilate view " + id), "View Camera"));

        return CommandResult.success();
    }
}
