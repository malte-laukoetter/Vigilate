package de.lergin.sponge.vigilate.commands;

import de.lergin.sponge.vigilate.Vigilate;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.io.IOException;

public class ReloadCommand implements CommandExecutor {
    private Vigilate plugin;

    public ReloadCommand(Vigilate plugin) {
        this.plugin = plugin;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        try {
            plugin.getConfig().reload();

            src.sendMessage(Text.of(TextColors.RED, "Reloaded Vigilate"));
        } catch (ObjectMappingException | IOException e) {
            src.sendMessage(Text.of(TextColors.RED, "Couldn't reload Vigilate!"));
            e.printStackTrace();
        }

        return CommandResult.success();
    }
}
